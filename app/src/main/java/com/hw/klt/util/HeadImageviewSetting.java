package com.hw.klt.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;

import com.hw.hwpermissionapply.PermissionUtil.AndPermission;
import com.hw.klt.util.camera.CropImageUtils;
import com.hw.klt.util.camera.PermissionValue;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hao on 17-7-19.
 */

public class HeadImageviewSetting {

    public static Context mcontext;

    public static int HEADSIZE = 200;


    public HeadImageviewSetting(){

    }

    private static HeadImageviewSetting headImageviewSetting = null;

    public static HeadImageviewSetting getSingleInstall(Context context){
        mcontext = context;
        if (headImageviewSetting == null){
            headImageviewSetting = new HeadImageviewSetting();
        }
        return headImageviewSetting;
    }

    public void selectSource(){
        String[] item = new String[]{"拍照","从相册中选择"};

        AlertDialog.Builder sourceDialog = new AlertDialog.Builder(mcontext);

        sourceDialog.setTitle("选择头像")
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                      if (which == 0){
                          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                          {
                              checkPermission(PermissionValue.REQUEST_CODE_PERMISSION_CAMERA);
                          } else
                          {
                              CropImageUtils.getInstance().takePhoto((Activity) mcontext);
                          }
                      } else if(which == 1){
                          CropImageUtils.getInstance().openAlbum((Activity) mcontext);
                      }
                    }
                }).setNegativeButton("取消",null)
                .show();

    }

    public void checkPermission(int permissionType){
        if (Build.VERSION.SDK_INT>=23){
            switch (permissionType){
                case PermissionValue.REQUEST_CODE_PERMISSION_STORAGE:
                    AndPermission.with((Activity) mcontext).requestCode(PermissionValue.REQUEST_CODE_PERMISSION_STORAGE)
                            .permission(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE)
                            .send();
                    break;
                case PermissionValue.REQUEST_CODE_PERMISSION_CAMERA:
                    if (!AndPermission.hasPermission(mcontext, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    {
                        AndPermission.with((Activity) mcontext)
                                .requestCode(PermissionValue.REQUEST_CODE_PERMISSION_CAMERA)
                                .permission(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                                .send();
                    } else
                    {
                        CropImageUtils.getInstance().takePhoto((Activity) mcontext);
                    }
            }
        }

    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    public void setImageToHeadView(Bitmap headBitmap) {
        if (headBitmap != null) {
            //保存到本地
            saveBitmapToLocal(headBitmap);


        }
    }

    public void saveBitmapToService(Bitmap headBitmap){
        //提交到网络服务器端
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            headBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
//            try {
//                bos.flush();
//                bos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            byte[] imageBytes = bos.toByteArray();

//            OkHttpUtil.userOperateEnqueuePost(UrlUtil.getUploadHeadImg(token), imageBytes, new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Log.e("aaa", "onFailure");
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    Log.e("aaa", "onResponse");
//                    if (response.isSuccessful()) {
//                        Log.e("aaa", "isSuccessful");
//
//                        String ret = response.body().string();
//                        if (ret != null) {
//                            Log.e("aaa", "ret != null");
//
//                            UpdateUserReturn updateUserReturn = JsonUtils.toBean(UpdateUserReturn.class, ret);
//                            if (updateUserReturn != null) {
//
//                                Message msg = mHandler.obtainMessage(HEADIMAGE_NOTIFICATION);
//                                if (updateUserReturn.getResultCode() == USEROPERATE_SUCCESS) {
//                                    //将数据保存到本地
//                                    DBUtil.saveUser(AppContext.userState);
//                                    msg.obj = OPERATION_SUCCESS;
//                                } else {
//                                    msg.obj = OPERATION_FAIL;
//                                }
//                            }
//                        }
//                    }
//                }
//            });
    }

    /*
     *保存图片到本地
     */
    public void saveBitmapToLocal(Bitmap bmp) {
        File file = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO) {
            file = new File(mcontext.getExternalFilesDir("header").getPath() + "/", "temp_head_image");
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
