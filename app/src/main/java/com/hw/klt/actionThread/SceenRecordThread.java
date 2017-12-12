package com.hw.klt.actionThread;

import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.projection.MediaProjection;
import android.os.Build;
import android.util.Log;
import android.view.Surface;

import com.hw.klt.bean.SceenRecordOption;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by hao on 2017/11/18.
 */

public class SceenRecordThread extends Thread {
    private static final String TAG = "ScreenRecorder";

    public static final int REQUEST_CODE = 1;
    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 11;

    private SceenRecordOption mSceenRecord;
    // parameters for the encoder
    private static final String MIME_TYPE = "video/avc"; // H.264 Advanced Video Coding
    private static final int FRAME_RATE = 30; // 30 fps
    private static final int IFRAME_INTERVAL = 10; // 10 seconds between I-frames
    private static final int TIMEOUT_US = 10000;

    public SceenRecordThread() {

    }
    private AtomicBoolean mQuit = new AtomicBoolean(false);
    private MediaCodec mEncoder;
    private Surface mSurface;
    private MediaMuxer mMuxer;
    private VirtualDisplay mVirtualDisplay;
    private boolean mMuxerStarted = false;
    private MediaCodec.BufferInfo mBufferInfo = new MediaCodec.BufferInfo();
    private int mVideoTrackIndex = -1;
    private ByteBuffer encodedData;
    private MediaProjection mMediaProjection;

    public void setOption(SceenRecordOption sceenRecordOption,MediaProjection mediaProjection) {
        this.mSceenRecord = sceenRecordOption;
        this.mMediaProjection = mediaProjection;
    }

    @Override
    public void run() {
        try {
            try {
                prepareEncoder();
                mMuxer = new MediaMuxer(mSceenRecord.DstPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mVirtualDisplay = mMediaProjection.createVirtualDisplay(TAG + "-display",
                        mSceenRecord.Width, mSceenRecord.Height, mSceenRecord.Dpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC,
                        mSurface, null, null);
            }
            Log.d(TAG, "created virtual display: " + mVirtualDisplay);
            recordVirtualDisplay();

        } finally {
            release();
        }
    }

    /**
     * stop task
     */
    public final void quit() {
        mQuit.set(true);
    }


    private void release() {
        if (mEncoder != null) {
            mEncoder.stop();
            mEncoder.release();
            mEncoder = null;
        }

        if (mVirtualDisplay != null) {
            mVirtualDisplay.release();
        }

        if (mMediaProjection != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mMediaProjection.stop();
            }
        }

        if (mMuxer != null) {
            mMuxer.stop();
            mMuxer.release();
            mMuxer = null;
        }
    }

    //配置mediaFormat ，输出文件
    private void prepareEncoder() throws IOException{
        MediaFormat format = MediaFormat.createVideoFormat(MIME_TYPE, mSceenRecord.Width,mSceenRecord.Height);
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        format.setInteger(MediaFormat.KEY_BIT_RATE,mSceenRecord.BitRate);
        format.setInteger(MediaFormat.KEY_FRAME_RATE, FRAME_RATE);
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, IFRAME_INTERVAL);

        Log.d(TAG, "created video format: " + format);
        mEncoder = MediaCodec.createEncoderByType(MIME_TYPE);
        mEncoder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        mSurface = mEncoder.createInputSurface();
        Log.d(TAG, "created input surface: " + mSurface);
        mEncoder.start();
    }

    private void recordVirtualDisplay() {
        while (!mQuit.get()) {
            int index = mEncoder.dequeueOutputBuffer(mBufferInfo, TIMEOUT_US);
            Log.d(TAG, "output buffer = " + index);

            if (index == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                resetOutputFormal();
            } else if (index == MediaCodec.INFO_TRY_AGAIN_LATER) {
                Log.d(TAG, "retrie buffers time out !");
            } else if (index >= 0) {
                if (!mMuxerStarted) {
                    throw new IllegalStateException("MediaMuxer dose not call addTrack(format)");
                }
                encode2VideoTrack(index);
                mEncoder.releaseOutputBuffer(index, false);
            }

        }
    }

    private void encode2VideoTrack(int index) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            encodedData = mEncoder.getOutputBuffer(index);
        }

        if ((mBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
            // The codec config data was pulled out and fed to the muxer when we got
            // the INFO_OUTPUT_FORMAT_CHANGED status.
            // Ignore it.
            Log.d(TAG, "ignoring BUFFER_FLAG_CODEC_CONFIG");
            mBufferInfo.size = 0;
        }

        if (mBufferInfo.size == 0) {
            Log.d(TAG, "info.size == 0, drop it.");
            if (encodedData != null) {
                encodedData = null;
            }
        } else {
            Log.d(TAG, "got buffer, info: size=" + mBufferInfo.size
                    + ", presentationTimeUs=" + mBufferInfo.presentationTimeUs
                    + ", offset=" + mBufferInfo.offset);
        }
        if (encodedData != null) {
            encodedData.position(mBufferInfo.offset);
            encodedData.limit(mBufferInfo.offset + mBufferInfo.size);
            mMuxer.writeSampleData(mVideoTrackIndex, encodedData, mBufferInfo);
            Log.i(TAG, "sent " + mBufferInfo.size + " bytes to muxer...");
        }
    }

    private void resetOutputFormal() {
        if (mMuxerStarted) {
            throw new IllegalStateException("output format alread changed");
        }
        MediaFormat format = mEncoder.getOutputFormat();
        Log.d(TAG, "output format changed.\n new format: " + format.toString());
        mVideoTrackIndex = mMuxer.addTrack(format);
        mMuxer.start();
        mMuxerStarted = true;

    }
}
