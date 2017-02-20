package com.wgc.mfaces.activity;

import android.graphics.Bitmap;
import android.util.Log;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class FaceppDetect {
	public interface callBack{//�ص�����
		void success(JSONObject result);
		void error(FaceppParseException exception);
	}
	public static void detect(final Bitmap bm,final callBack callback){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//��������
				
				
				try {
					HttpRequests requests=new HttpRequests(CameraPreviewActivity.key,CameraPreviewActivity.Secret,true,true);
					Bitmap btSmall = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight());
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					btSmall.compress(Bitmap.CompressFormat.JPEG, 100, stream);
					byte[] array = stream.toByteArray();//ѹ��ͼƬ������ͼƬ��������
					
					PostParameters params=new PostParameters();
					params.setImg(array);
			
					JSONObject jsonobject=requests.detectionDetect(params);
					Log.e("TAG",jsonobject.toString());
					
					//JSONObject FaceId=jsonobject.get;
					
					if(callback!=null){
						callback.success(jsonobject);
						
					}
					
				} catch (FaceppParseException e) {
					// TODO Auto-generated catch block
					if(callback!=null){
						callback.error(e);
					}
					e.printStackTrace();
				}
			}
		}).start();
		
	}

}
