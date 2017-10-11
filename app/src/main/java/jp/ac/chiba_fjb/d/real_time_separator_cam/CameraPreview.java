package jp.ac.chiba_fjb.d.real_time_separator_cam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.TextureView;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CameraPreview implements TextureView.SurfaceTextureListener,  Camera.AutoFocusCallback {
    private static Camera mCamera;
    private int mCameraId;
    private TextureView mTextureView;
    private WindowManager mWindowManager;
    private String mFileName;

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            //カメラデバイスを開く
            if(mCamera == null)
                mCamera = Camera.open(mCameraId);
            if(mCamera == null)
                return;

            mCamera.setPreviewTexture(surface);
            //回転状況の設定
            int rot = setCameraDisplayOrientation();
            //アスペクト比から最適なプレビューサイズを設定
            setPreviewSize(width,height,rot);
            //サイズから幅と高さの調整
            double video_width;
            double video_height;
            Camera.Parameters p = mCamera.getParameters();

            if(rot == 0){
                video_width = p.getPreviewSize().height;
                video_height = p.getPreviewSize().width;
            } else {
                video_width = p.getPreviewSize().width;
                video_height = p.getPreviewSize().height;
            }

            final double req = video_width / (double) video_height;
            final double view_aspect = width / (double) height;


            Matrix m = new Matrix();
            if (req > view_aspect)
                m.setScale(1.0f, (float) (1.0 / (req / view_aspect)));
            else
                m.setScale((float) (req / view_aspect), 1.0f);
            mTextureView.setTransform(m);

            mCamera.setPreviewTexture(surface);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        //カメラデバイスの解放
        mCamera.release();
        mCamera = null;
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        try {
            Bitmap bitmap = mTextureView.getBitmap();
            FileOutputStream fos = null;
            fos = new FileOutputStream(new File(mFileName));
            // jpegで保存
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            // 保存処理終了
            fos.close();
            System.out.println(mFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setPreviewSize(int width,int height,int rot) {
        double aspect;
        if(rot == 0)
            aspect = (double)height/ width;
        else
            aspect = (double)width / height;


        int index = 0;
        double a = 10.0;
        Camera.Parameters p = mCamera.getParameters();
        List<Camera.Size> previewSizes = p.getSupportedPreviewSizes();

        for (int i = 0; i < previewSizes.size(); i++) {
            Camera.Size s = previewSizes.get(i);
            double aspect2 = (double) s.width / s.height;
            if(Math.abs(aspect - aspect2) < Math.abs(aspect-a)) {
                a = aspect2;
                index = i;
            }
        }
        Camera.Size s = previewSizes.get(index);
        p.setPreviewSize(s.width, s.height);
        mCamera.setParameters(p);
        //System.out.format("%d %d %.3f %.3f %.3f\n",s.width, s.height,(double)s.width/s.height,aspect,a);
    }
    public boolean open(int id){
        mCameraId = id;

        //カメラデバイスを開く
        mCamera = Camera.open(mCameraId);
        if(mCamera == null)
            return false;
        return true;
    }
    public boolean setTextureView(TextureView view){
        if(mCamera != null)
            return false;

        mWindowManager = (WindowManager)view.getContext().getSystemService(Context.WINDOW_SERVICE);
        mTextureView = view;
        view.setSurfaceTextureListener(this);

        return true;
    }
    public int setCameraDisplayOrientation() {
        // カメラの情報取得
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraId, cameraInfo);
        // ディスプレイの向き取得
        int rotation = mWindowManager.getDefaultDisplay().getRotation();
        int degrees = 0;
        final int[] DEG = {0,90,180,270};
        degrees = DEG[rotation];

        // プレビューの向き計算
        int result;
        if (cameraInfo.facing == cameraInfo.CAMERA_FACING_FRONT) {
            result = (360-(cameraInfo.orientation + degrees) % 360)%360;
        } else {  // back-facing
            result = (cameraInfo.orientation - degrees + 360) % 360;
        }
        // ディスプレイの向き設定
        mCamera.setDisplayOrientation(result);

        return rotation%2;
    }
    //プレビュー画像をファイルに保存
    //実際の保存はフォーカスが
    public boolean save(String fileName){
        if(mCamera == null)
            return false;
        mFileName = fileName;
        mCamera.autoFocus(this);

        return true;
    }

    public boolean setLight(boolean flag){
        if(mCamera == null)
            return false;
        Camera.Parameters params = mCamera.getParameters();
        params.setFlashMode(flag?Camera.Parameters.FLASH_MODE_TORCH:Camera.Parameters.FLASH_MODE_OFF);
        mCamera.setParameters(params);
        return true;
    }


}