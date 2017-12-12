package com.hw.klt.ktlActivity.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hw.hwpermissionapply.PermissionUtil.AndPermission;
import com.hw.klt.R;
import com.hw.klt.actionThread.SceenRecordThread;
import com.hw.klt.bean.SceenRecordOption;
import com.hw.klt.ktlActivity.Base.BaseActivity;
import com.hw.klt.ktlActivity.Fragement.BdPhotoFragement.BdPhotoFragement;
import com.hw.klt.ktlActivity.Fragement.movie.MovieFragement;
import com.hw.klt.ktlActivity.Fragement.movie.MovieMainFragment;
import com.hw.klt.ktlActivity.Fragement.news.NewsMainFragment;
import com.hw.klt.ktlActivity.setting.SettingsActivity;
import com.hw.klt.util.GlideCircleTransform;
import com.hw.klt.util.HeadImageviewSetting;
import com.hw.klt.util.camera.CropImageUtils;
import com.hw.klt.util.camera.PermissionValue;
import com.ktl.ifmsc.NlpManager;
import com.ktl.ifmsc.NlpManager.NlpListener;

import java.io.File;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener,View.OnLongClickListener,NlpListener {

    @BindView(R.id.nav_view)
    protected NavigationView navigationView;
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    @BindView(R.id.fab)
    protected FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    protected ImageView hostHead;
    protected TextView hostName ;
    //quit time of finish activity
    protected TextView connectEmail;
    private long mExitTime = 0;
    private MediaProjectionManager mMediaProjectionManager;
    private SceenRecordThread mRecorder;

    @Override
    public void onBackPressed() {
        // 获取堆栈里有几个
        final int stackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (stackEntryCount == 1) {
            // 如果剩一个说明在主页，提示按两次退出app
            _exit();
        } else {
            // 获取上一个堆栈中保存的是哪个页面，根据name来设置导航项的选中状态
            final String tagName = getSupportFragmentManager().getBackStackEntryAt(stackEntryCount - 2).getName();
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            replaceFragment(R.id.fl_container, new NewsMainFragment(),  "新闻");
        } else if (id == R.id.nav_gallery) {
            replaceFragment(R.id.fl_container, new BdPhotoFragement(), "图片");
        } else if (id == R.id.nav_video) {
            replaceFragment(R.id.fl_container, new MovieMainFragment(), "影视 ");
        } else if (id == R.id.nav_setting) {
            SettingsActivity.launch(HomeActivity.this);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        int viewid = view.getId();
        if (viewid == R.id.myself_home_headheart){
            HeadImageviewSetting.getSingleInstall(HomeActivity.this).selectSource();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        MediaProjection mediaProjection = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP && requestCode == SceenRecordThread.REQUEST_CODE ) {
            mediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
            if (mediaProjection == null) {
                Log.e("@@", "media projection is null");
                return;
            }
            // video size/*width, height, bitrate, 1, mediaProjection, file.getAbsolutePath()*/
            File file = new File(Environment.getExternalStorageDirectory(),
                    "record-" + width + "x" + height + "-" + System.currentTimeMillis() + ".mp4");
            mRecorder = new SceenRecordThread();
            SceenRecordOption option = new SceenRecordOption();
            option.Width = width;
            option.Height = height;
            option.BitRate =bitrate;
            option.Dpi = 1;
            option.DstPath = file.getAbsolutePath();
            mRecorder.setOption(option,mediaProjection);
            mRecorder.start();
            Toast.makeText(this, "Screen recorder is running...", Toast.LENGTH_SHORT).show();
//            moveTaskToBack(true);
        }

        CropImageUtils.getInstance().onActivityResult(this, requestCode, resultCode, data, new CropImageUtils.OnResultListener()
        {
            @Override
            public void takePhotoFinish(String path)
            {
                //拍照回调，去裁剪
                CropImageUtils.getInstance().cropPicture(HomeActivity.this, path);
            }

            @Override
            public void selectPictureFinish(String path)
            {
                //相册回调，去裁剪
                CropImageUtils.getInstance().cropPicture(HomeActivity.this, path);
            }

            @Override
            public void cropPictureFinish(final String path)
            {
                //裁剪回调
                Glide.with(HomeActivity.this).load(path).transform(new GlideCircleTransform(HomeActivity.this)).into((ImageView) findViewById(R.id.myself_home_headheart));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Bitmap head = Glide.with(HomeActivity.this).load(path).asBitmap().centerCrop().into(HeadImageviewSetting.HEADSIZE, HeadImageviewSetting.HEADSIZE).get();
                            HeadImageviewSetting.getSingleInstall(HomeActivity.this).setImageToHeadView(head);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }
                ).start();
            }
        });
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
                if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            SceenRecordThread.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                }else{
                    if (mRecorder != null) {
                        mRecorder.quit();
                        mRecorder = null;
                        Toast.makeText(HomeActivity.this, "结束录制！", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent captureIntent = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
                            startActivityForResult(captureIntent, SceenRecordThread.REQUEST_CODE);
                        }
                    }
                }

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        ButterKnife.bind(this);
        NlpManager nlpManager = new NlpManager(getBaseContext());
        nlpManager.init(getBaseContext(),this);
        nlpManager.TtsResult("你好！");
        AndPermission.with(this).requestCode(PermissionValue.REQUEST_CODE_RECORD_AUDIO)
                .permission(Manifest.permission.RECORD_AUDIO)
                .send();
        View navigationViewHeadLayout = navigationView.getHeaderView(0);
        setDrawerLayout(mDrawerLayout);

//        _initDrawerLayout(mDrawerLayout, navigationView);
        hostHead= ButterKnife.findById(navigationViewHeadLayout, R.id.myself_home_headheart);
        hostName = ButterKnife.findById(navigationViewHeadLayout, R.id.myself_home_daily_chat);
        connectEmail = ButterKnife.findById(navigationViewHeadLayout, R.id.myself_home_connectus);
        fab.setOnLongClickListener(this);
        hostHead.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
    }
    final int bitrate = 6000000;
    final int width =1080;
    final int height = 1920;

    @Override
    protected void updateViews(boolean isRefresh) {
        navigationView.setCheckedItem(R.id.nav_news);
        addFragment(R.id.fl_container, new NewsMainFragment(), "News");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode,grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == SceenRecordThread.WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
            } else {
                // Permission Denied
            }
        }
    }


    /**
     * 退出
     */
    private void _exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.fab){
            NlpManager.getmNlpManager().startSemanticParsing();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mRecorder != null){
            mRecorder.quit();
            mRecorder = null;
        }
    }

    @Override
    public void startVoluce() {

    }

    @Override
    public void stopVolunce() {

    }

    @Override
    public void showVolunce(String s) {

    }

    @Override
    public void semantic(String resultStr) {
        Log.d("tag",resultStr);
    }
}
