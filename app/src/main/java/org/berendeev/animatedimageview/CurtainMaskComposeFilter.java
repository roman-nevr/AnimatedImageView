package org.berendeev.animatedimageview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

public class CurtainMaskComposeFilter extends MaskComposeFilter {

    private float step;
    private static final int CURTAIN_NUMBER = 6;
    private final Paint maskPaint;

    public CurtainMaskComposeFilter(int durationMillisecons) {
        super(durationMillisecons);
        maskPaint = new Paint();
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        step = 1f / (CURTAIN_NUMBER * durationMillisecons);
    }

    @Override
    protected void drawMask(Canvas canvas, int currentTime){
        float left, top, right, bottom ;
        switch (getVariant() % 4){
            case 0:{
                for (int i = 0; i < 6; i++){
                    left = i * canvas.getWidth() / CURTAIN_NUMBER;
                    right = left + (getDurationMilliseconds() - currentTime) * canvas.getWidth() * step;
                    top = 0;
                    bottom = canvas.getHeight();
                    canvas.drawRect(left, top, right, bottom, maskPaint);
                }
                break;
            }
            case 1:{
                for (int i = 0; i < 6; i++){
                    right = (i + 1) * canvas.getWidth() / CURTAIN_NUMBER;
                    left = right - (getDurationMilliseconds() - currentTime) * canvas.getWidth() * step;
                    top = 0;
                    bottom = canvas.getHeight();
                    canvas.drawRect(left, top, right, bottom, maskPaint);
                }
                break;
            }
            case 2:{
                for (int i = 0; i < 6; i++){
                    right = canvas.getWidth();
                    left = 0;
                    top = i * canvas.getHeight() / CURTAIN_NUMBER;
                    bottom = top + (getDurationMilliseconds() - currentTime) * canvas.getHeight() * step;
                    canvas.drawRect(left, top, right, bottom, maskPaint);
                }
                break;
            }
            case 3:{
                for (int i = 0; i < 6; i++){
                    right = canvas.getWidth();
                    left = 0;
                    bottom = (i + 1) * canvas.getHeight() / CURTAIN_NUMBER;
                    top = bottom - (getDurationMilliseconds() - currentTime) * canvas.getHeight() * step;
                    canvas.drawRect(left, top, right, bottom, maskPaint);
                }
                break;
            }
            default:throw new IllegalArgumentException();

        }

    }
}
