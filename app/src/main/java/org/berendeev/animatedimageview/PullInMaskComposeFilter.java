package org.berendeev.animatedimageview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

public class PullInMaskComposeFilter extends MaskComposeFilter {

    private final Paint maskPaint;
    private Path pathFirst, pathSecond;

    public PullInMaskComposeFilter(int durationMilliseconds) {
        super(durationMilliseconds);
        maskPaint = new Paint();
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        pathFirst = new Path();
        pathSecond = new Path();
    }

    @Override
    protected void drawMask(Canvas canvas, int currentTime) {
        float left, top, right, bottom, center, timeCoef;
        timeCoef = (getDurationMilliseconds() - (float)currentTime) / getDurationMilliseconds();
        switch (getVariant() % 8){
            case 0:{
                top = 0;
                bottom = canvas.getHeight();
                left = canvas.getHeight() / 2 * (getDurationMilliseconds() - currentTime) / getDurationMilliseconds();
                right = canvas.getWidth() - left;
                canvas.drawRect(left, top, right, bottom, maskPaint);
                break;
            }
            case 1:{
                canvas.save();
                canvas.rotate(90, canvas.getWidth()/2, canvas.getHeight()/2);
                top = 0;
                bottom = canvas.getHeight();
                left = canvas.getHeight() / 2 * (getDurationMilliseconds() - currentTime) / getDurationMilliseconds();
                right = canvas.getWidth() - left;
                canvas.drawRect(left, top, right, bottom, maskPaint);
                canvas.restore();
                break;
            }
            case 2: {
                center = canvas.getHeight() / 2;
                top = - center;
                bottom = canvas.getHeight() * 1.5f;
                left = center - canvas.getHeight() / 2 * (getDurationMilliseconds() - currentTime) / getDurationMilliseconds();
                right = center + canvas.getHeight() / 2 * (getDurationMilliseconds() - currentTime) / getDurationMilliseconds();
                canvas.drawRect(left, top, right, bottom, maskPaint);
                break;
            }
            case 3: {
                canvas.save();
                canvas.rotate(90, canvas.getWidth()/2, canvas.getHeight()/2);
                center = canvas.getHeight() / 2;
                top = - center;
                bottom = canvas.getHeight() * 1.5f;
                left = center - canvas.getHeight() / 2 * (getDurationMilliseconds() - currentTime) / getDurationMilliseconds();
                right = center + canvas.getHeight() / 2 * (getDurationMilliseconds() - currentTime) / getDurationMilliseconds();
                canvas.drawRect(left, top, right, bottom, maskPaint);
                canvas.restore();
                break;
            }
            case 4:{
                drawDiagonalToCenter(canvas, timeCoef);
                break;
            }
            case 5:{
                canvas.save();
                canvas.rotate(90, canvas.getWidth()/2, canvas.getHeight()/2);
                drawDiagonalToCenter(canvas, timeCoef);
                canvas.restore();
                break;
            }
            case 6:{
                drawDiagonalFromCenter(canvas, timeCoef);
                break;
            }
            case 7:{
                canvas.save();
                canvas.rotate(90, canvas.getWidth()/2, canvas.getHeight()/2);
                drawDiagonalFromCenter(canvas, timeCoef);
                canvas.restore();
                break;
            }
        }
    }

    private void drawDiagonalFromCenter(Canvas canvas, float timeCoef){
        pathFirst.reset();
        pathFirst.setLastPoint(0, 0);
        pathFirst.lineTo(canvas.getWidth() * timeCoef, 0);
        pathFirst.lineTo(0, canvas.getHeight() * timeCoef);
        pathFirst.close();

        pathSecond.reset();
        pathSecond.setLastPoint(canvas.getWidth(), canvas.getHeight());
        pathSecond.lineTo(canvas.getWidth(), canvas.getHeight() - canvas.getHeight() * timeCoef);
        pathSecond.lineTo(canvas.getWidth() - canvas.getWidth() * timeCoef, canvas.getHeight());
        pathSecond.close();
        canvas.drawPath(pathFirst, maskPaint);
        canvas.drawPath(pathSecond, maskPaint);
    }

    private void drawDiagonalToCenter(Canvas canvas, float timeCoef){
        pathFirst.reset();
        pathFirst.setLastPoint(0, 0);
        pathFirst.lineTo(canvas.getWidth() * (1 - timeCoef), 0);
        pathFirst.lineTo(0, canvas.getHeight() * (1 - timeCoef));
        pathFirst.close();

        pathSecond.reset();
        pathSecond.setLastPoint(canvas.getWidth(), canvas.getHeight());
        pathSecond.lineTo(canvas.getWidth(), canvas.getHeight() * timeCoef);
        pathSecond.lineTo(canvas.getWidth() * timeCoef, canvas.getHeight());
        pathSecond.close();
        canvas.drawPath(pathFirst, maskPaint);
        canvas.drawPath(pathSecond, maskPaint);
    }
}
