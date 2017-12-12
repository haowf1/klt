package com.hw.klt.util.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.hw.hwpermissionapply.PermissionUtil.GetPathFromUri;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by on 2017/3/8.
 * 裁剪图片工具
 */

public class CropImageUtils
{
    //7.0  ContentUri
    public static final String FILE_CONTENT_FILEPROVIDER = "com.hw.klt.fileprovider";
    private static CropImageUtils instance;
    public static final String APP_NAME = "com.hw.klt";
    //打开相机的返回码
    public static final int REQUEST_CODE_TAKE_PHOTO = 11111;
    //打开相册的返回码
    public static final int REQUEST_CODE_SELECT_PICTURE = REQUEST_CODE_TAKE_PHOTO+1;
    //剪切图片的返回码
    public static final int REQUEST_CODE_CROP_PICTURE = REQUEST_CODE_TAKE_PHOTO+2;
    //相机拍照默认存储路径
    public static final String PICTURE_DIR = Environment.getExternalStorageDirectory() + "/DCIM";
    public String DATE = "";
    //照片图片名
    private String photo_image;
    //截图图片名
    private String crop_image;

    public static CropImageUtils getInstance()
    {
        if (instance == null)
        {
            synchronized (CropImageUtils.class)
            {
                if (instance == null)
                {
                    instance = new CropImageUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 打开系统相册
     */
    public void openAlbum(Activity activity)
    {
        DATE = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date());
        if (isSdCardExist())
        {
            Intent intent;
            if (Build.VERSION.SDK_INT < 19)
            {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
            } else
            {
                intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            activity.startActivityForResult(intent, REQUEST_CODE_SELECT_PICTURE);
        }
    }

    /**
     * 打开系统相机
     */
    public void takePhoto(Activity activity)
    {
        DATE = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date());
        if (isSdCardExist())
        {
            photo_image = openImagePath(APP_NAME + DATE);
            File file = new File(photo_image);
            if (!file.getParentFile().exists())
            {
                file.getParentFile().mkdirs();
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Android7.0以上URI
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //通过FileProvider创建一个content类型的Uri
                Uri uri = FileProvider.getUriForFile(activity, FILE_CONTENT_FILEPROVIDER, file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            }

            activity.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);

        }
    }

    /**
     * 调用系统剪裁功能
     */
    public void cropPicture(Activity activity, String path)
    {
        File file = new File(path);
        if (!file.getParentFile().exists())
        {
            file.getParentFile().mkdirs();
        }
        Uri imageUri;
        Uri outputUri;
        crop_image = openImagePath(APP_NAME + "_crop_" + DATE);

        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //通过FileProvider创建一个content类型的Uri
            imageUri = FileProvider.getUriForFile(activity, FILE_CONTENT_FILEPROVIDER, file);
            outputUri = Uri.fromFile(new File(crop_image));
            //TODO:outputUri不需要ContentUri,否则失败
            //outputUri = FileProvider.getUriForFile(activity, "com.solux.furniture.fileprovider", new File(crop_image));
        } else
        {
            imageUri = Uri.fromFile(file);
            outputUri = Uri.fromFile(new File(crop_image));
        }
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        //设置宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //设置裁剪图片宽高
        intent.putExtra("outputX", HeadImageviewSetting.HEADSIZE);
        intent.putExtra("outputY", HeadImageviewSetting.HEADSIZE);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, REQUEST_CODE_CROP_PICTURE);
    }

    /**
     * 拍照/打开相册/剪裁图片的回调
     */
    public void onActivityResult(Activity activity, int requestCode, int resultCode
            , Intent data, OnResultListener listener)
    {
        switch (requestCode)
        {
            case REQUEST_CODE_TAKE_PHOTO:
                if (!TextUtils.isEmpty(photo_image))
                {
                    File file = new File(photo_image);
                    if (file.isFile() && listener != null)
                        listener.takePhotoFinish(photo_image);
                }
                break;
            case REQUEST_CODE_SELECT_PICTURE:
                if (data != null)
                {
                    Uri uri = data.getData();
                    if (uri != null)
                    {
                        String path = GetPathFromUri.getInstance().getPath(activity, uri);
                        File file = new File(path);
                        if (file.isFile() && listener != null)
                            listener.selectPictureFinish(path);
                    }
                }
                break;
            case REQUEST_CODE_CROP_PICTURE:
                if (!TextUtils.isEmpty(crop_image))
                {
                    File file = new File(crop_image);
                    if (file.isFile() && listener != null)
                        listener.cropPictureFinish(crop_image);
                }
                break;
        }
    }

    /**
     * 打开图片的存储路径
     */
    public static String openImagePath(String imageName)
    {
        String dir = PICTURE_DIR;
        File destDir = new File(dir);
        if (!destDir.exists())
        {
            destDir.mkdirs();
        }
        File file = null;
        if (!TextUtils.isEmpty(imageName))
        {
            file = new File(dir, imageName + ".jpeg");
        }
        return file.getAbsolutePath();
    }

    /**
     * 检查SD卡是否存在
     */
    public boolean isSdCardExist()
    {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public interface OnResultListener
    {
        //拍照回调
        void takePhotoFinish(String path);

        //选择图片回调
        void selectPictureFinish(String path);

        //截图回调
        void cropPictureFinish(String path);
    }
}
