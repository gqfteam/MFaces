package com.wgc.mfaces.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.faceplusplus.api.FaceDetecter.Face;

public class FaceMask extends View {
    Paint localPaint = null;
    Face[] faceinfos = null;
    RectF rect = null;

    public FaceMask(Context context, AttributeSet atti) {
        super(context, atti);
        rect = new RectF();
        localPaint = new Paint();
        localPaint.setColor(0xff00b4ff);
        localPaint.setStrokeWidth(5);
        localPaint.setStyle(Paint.Style.STROKE);
    }

    public void setFaceInfo(Face[] faceinfos)
    {
        this.faceinfos = faceinfos;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rect.set((float)getWidth() * (float)0.30, (float)getHeight()
                * (float)0.40, (float)getWidth() * (float)0.70,
                (float)getHeight()
                        * (float)0.70);
        canvas.drawRect(rect, localPaint);
        if (faceinfos == null)
            return;
        for (Face localFaceInfo : faceinfos) {
            rect.set(getWidth() * localFaceInfo.left, getHeight()
                    * localFaceInfo.top, getWidth() * localFaceInfo.right,
                    getHeight()
                            * localFaceInfo.bottom);
            
            Log.i("gqf", "gqf left"+ localFaceInfo.left);//0.3
            Log.i("gqf", "gqf top"+localFaceInfo.top);//0.4
            Log.i("gqf", "gqf right"+ localFaceInfo.right);//0.7
            Log.i("gqf", "gqf bottom"+ localFaceInfo.bottom);//0.7

            canvas.drawRect(rect, localPaint);
        }
        
        
    }
}
