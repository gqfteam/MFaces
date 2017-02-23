
package com.wgc.mfaces.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.faceplusplus.api.FaceDetecter;
import com.faceplusplus.api.FaceDetecter.Face;
import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.wgc.mfaces.R;
import com.wgc.mfaces.view.FaceMask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@SuppressLint("HandlerLeak")
public class CameraPreviewActivity extends Activity implements Callback, PreviewCallback {
    SurfaceView camerasurface = null;
    FaceMask mask = null;
    Camera camera = null;
    ImageView gqf;
    HandlerThread handleThread = null;
    Runnable detectRunnalbe = null;
    private int width = 320;
    private int height = 240;
    FaceDetecter facedetecter = null;
    HttpRequests request = null;// 在线api
    FaceDetecter detecter = null;



    /*public static String key="TTp70O1MXSSFU8Nk_lX_udSI18XV-eVf";
    public static String Secret="j8iFhFnu_zS8mn8dJjE791G0Wny3STHZ";*/
    //Face++ key值
    public static String key="cxM294O0X6RKVPvndq4wl9l3GaGEWpYl";
    public static String Secret="qo56sZd_CAKFQSy3ui-kHhCHHt1EgT6q";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camerapreview);
        camerasurface = (SurfaceView) findViewById(R.id.camera_preview);
        mask = (FaceMask) findViewById(R.id.mask);
        gqf=(ImageView)findViewById(R.id.gqf);
//        LayoutParams para = new LayoutParams(480, 800);
        WindowManager wm = this.getWindowManager();

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        LayoutParams para = new LayoutParams(width,height);
        handleThread = new HandlerThread("dt");
        handleThread.start();
        detectHandler = new Handler(handleThread.getLooper());
        para.addRule(RelativeLayout.CENTER_IN_PARENT);
        camerasurface.setLayoutParams(para);
        mask.setLayoutParams(para);
        camerasurface.getHolder().addCallback(this);
        camerasurface.setKeepScreenOn(true);

        facedetecter = new FaceDetecter();
        facedetecter.init(this, key);

//        detecter=new FaceDetecter();
//        detecter.init(this, "3777d0f8e2840e4407929c0e2e3d6dcd");

        // facedetecter.setTrackingMode(true);
        //FIXME 替换成申请的key
        request = new HttpRequests(key,
                Secret);
    }
    Handler detectHandler = null;
    @Override
    protected void onResume() {
        super.onResume();
        camera = getCameraInstance();
        Parameters para = camera.getParameters();
        para.setPreviewSize(width, height);
        camera.setParameters(para);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            finish();
        }
    }
    public void finsh(){
        this.finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        facedetecter.release(this);
        handleThread.quit();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.setDisplayOrientation(90);
        camera.startPreview();
        camera.setPreviewCallback(this);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    int i=0;
    int index=-1;
    @Override
    public void onPreviewFrame(final byte[] data, Camera camera) {
        camera.setPreviewCallback(null);
        detectHandler.post(new Runnable() {

            @Override
            public void run() {
                byte[] ori = new byte[width * height];
                int is = 0;
                for (int x = width - 1; x >= 0; x--) {

                    for (int y = height - 1; y >= 0; y--) {

                        ori[is] = data[y * width + x];
                        is++;
                    }

                }

                final Face[] faceinfo = facedetecter.findFaces( ori, height,width);

                //[[141,221,168,168]]


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        mask.setFaceInfo(faceinfo);

                    }
                });




                if(index==-1){

                    if(index==-1&&faceinfo!=null&&detecter==null&&data!=null){
                        index=0;
                        b=data;
                        r=facedetecter.getResultJsonString();
                        detecter=new FaceDetecter();
                        detecter.init(CameraPreviewActivity.this, key);
                        Message message = new Message();
                        message.what = 3;
                        myHandler.sendMessage(message);


                    }
                }else if(TestDemo.isface){
                    Message message = new Message();
                    message.what = 4;
                    myHandler.sendMessage(message);
                }
                CameraPreviewActivity.this.camera.setPreviewCallback(CameraPreviewActivity.this);
            }
        });

    }
    String faceid1="";
    String faceid2="";
    byte [] b;
    String r;
    private Bitmap curBitmap;
    public void getmsg(final Bitmap map){
        if(index==0&&detecter!=null&&map!=null){
            index++;

            //detecter.setTrackingMode(true);

            // TODO Auto-generated method stub
            //在线api交互
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "开始检测", Toast.LENGTH_SHORT).show();

                }
            });

            //curBitmap=adjustPhotoRotation(map,90+180);

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    gqf.setBackgroundDrawable(new BitmapDrawable(adjustPhotoRotation2(map,270)));
                }
            });

            HttpRequests requests=new HttpRequests(key,Secret);

            Face[] faceinfo = detecter.findFaces(adjustPhotoRotation2(map,270));// 进行人脸检测

            try {

                JSONObject j=null;
                //[[105,240,130,130]]
                //j=requests.offlineDetect(detecter.getImageByteArray(),detecter.getResultJsonString(), new PostParameters());

                //r="";
                j=requests.detectionDetect(new PostParameters().setImg(detecter.getImageByteArray()));
                //j=requests.detectionDetect(new PostParameters().setUrl(path));
                if(j!=null){
                    Toast.makeText(getApplicationContext(), "检测成功"+j.toString(), Toast.LENGTH_SHORT).show();
                    Log.i("gqf", "gqf检测成功");

                    if(faceid1.equals("")){
                        faceid1 = j.getJSONArray("face").getJSONObject(0).getString("face_id");
                    }else if(!faceid1.equals("")&&faceid2.equals("")){
                        faceid2 = j.getJSONArray("face").getJSONObject(0).getString("face_id");
                        JSONObject Compare = requests.recognitionCompare(new PostParameters().setFaceId1(faceid1).setFaceId2(faceid2));
                        final Double smilar = Double.valueOf(Compare.getString("similarity"));
                        Toast.makeText(getApplicationContext(), "两张脸的相似程度："+smilar, Toast.LENGTH_SHORT).show();
                        TestDemo.faced=smilar;
                        if(smilar>90){
                            TestDemo.isface=true;
                                        /*runOnUiThread(new Runnable() {

                                            @Override
                                            public void run() {
                                                Message m=new Message();
                                                m.what=4;
                                                m.obj=smilar;
                                                myHandler.sendMessage(m);

                                            }
                                        });*/


                        }
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "检测失败0", Toast.LENGTH_SHORT).show();

                }
            }
            catch (FaceppParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "检测失败1"+e.getErrorMessage(), Toast.LENGTH_SHORT).show();

            }
            catch(IllegalArgumentException e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "检测失败2"+e.getMessage(), Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                detecter.release(CameraPreviewActivity.this);
                detecter=null;
                index=-1;
                if(!faceid1.equals("")&&!faceid2.equals("")){
                    index=100;
                }
            }




        }else{
            detecter.release(CameraPreviewActivity.this);
            detecter=null;
            index=-1;
        }



    }
    Bitmap
    adjustPhotoRotation2(Bitmap bm, final int orientationDegree)
    {
        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);

        try {
            Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
            return bm1;
        } catch (OutOfMemoryError ex) {
        }
        return null;
    }
    Bitmap
    adjustPhotoRotation(Bitmap bm, final int orientationDegree)
    {

        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        float targetX, targetY;
        if (orientationDegree == 90) {
            targetX = bm.getHeight();
            targetY = 0;
        } else {
            targetX = bm.getHeight();
            targetY = bm.getWidth();
        }

        final float[] values = new float[9];
        m.getValues(values);

        float x1 = values[Matrix.MTRANS_X];
        float y1 = values[Matrix.MTRANS_Y];

        m.postTranslate(targetX - x1, targetY - y1);

        Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bm1);
        canvas.drawBitmap(bm, m, paint);

        return bm1;
    }
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

//                	  Toast.makeText(CameraPreview.this, "offlineDetect"+msg.obj.toString(), Toast.LENGTH_LONG)
//                      .show();
                    Toast.makeText(CameraPreviewActivity.this, "检测成功", Toast.LENGTH_LONG)
                            .show();
                    index=-1;
                    break;
                case 2:

                    Toast.makeText(CameraPreviewActivity.this, "检测失败", Toast.LENGTH_LONG)
                            .show();
                    index=-1;
                    break;
                case 3:
                    camera.autoFocus(new AutoFocusCallback() {

                        @Override
                        public void onAutoFocus(boolean arg0, Camera arg1) {
                            // TODO Auto-generated method stub
                            arg1.takePicture(new ShutterCallback() {

                                @Override
                                public void onShutter() {
                                    // TODO Auto-generated method stub

                                }
                            }, null,  new PictureCallback() {
                                @Override
                                public void onPictureTaken(final byte[] data, Camera arg1) {
                                    camera.startPreview();
                                    detectHandler.post(new Runnable() {

                                        @Override
                                        public void run() {
                                            getmsg(Bytes2Bimap(data));

                                        }
                                    });
                                }


                            });
                        }
                    });

                    break;
                case 4:
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void save(byte[] data){
        if(data==null){
            Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();
        }else{
            FileOutputStream fos=null;

            try {
                File file = null;
                String path=
                        SystemClock.uptimeMillis() + ".jpg";

                file = new File(Environment
                        .getExternalStorageDirectory()
                        + "/" + "MusicLrc" + "/",
                        path);

                fos = new FileOutputStream(
                        file);
                fos.write(data);
                Toast.makeText(getApplicationContext(), "路径", Toast.LENGTH_SHORT).show();


                //压缩图片


                BitmapFactory.Options op=new BitmapFactory.Options();
                op.inJustDecodeBounds=true;
//						BitmapFactory.decodeFile(Environment
//								.getExternalStorageDirectory()
//								+ "/" + "MusicLrc" + "/"+path,op);
                op.inSampleSize = Math.max(1, (int)Math.ceil(Math.max((double)op.outWidth / 1024f, (double)op.outHeight / 1024f)));
                op.inJustDecodeBounds = false;
                curBitmap = BitmapFactory.decodeFile(Environment
                        .getExternalStorageDirectory()
                        + "/" + "MusicLrc" + "/"+path, op);





            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }				finally{

                if(fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        getmsg(curBitmap);
    }


    public Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }
    /** 创建一个摄像头的实例. */
    @SuppressLint("NewApi")
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(1); // open()不传值或者传0，就是使用后置摄像头，如果传1，那就是使用前置摄像头。
            Parameters parameters = c.getParameters();
            parameters.setFlashMode(parameters.FLASH_MODE_AUTO);// 设置闪光灯强制打开
            // FLASH_MODE_TORCH
            parameters.setWhiteBalance(Parameters.WHITE_BALANCE_AUTO);// 设置白平衡，WHITE_BALANCE
            parameters.setColorEffect(parameters.EFFECT_SEPIA);// 设置照片颜色特效，EFFECT

            parameters.setPictureSize(1280, 720);// 设置拍摄照片的尺寸
            parameters.setPreviewSize(1280, 720);// 设置照片的预览尺寸
            parameters.setJpegQuality(100);// 设置照片的质量

            // 2.2以后
            c.setDisplayOrientation(90); // 0 水平 90垂直方向

            System.out.println("parameters:" + parameters.flatten());

        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    /** 检测手机上是否有摄像头 */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
}
