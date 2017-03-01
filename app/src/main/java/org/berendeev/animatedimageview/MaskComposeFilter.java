package org.berendeev.animatedimageview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class MaskComposeFilter extends ComposeFilter {

    private final Paint fillPaint;


    public MaskComposeFilter(int durationMilliseconds) {
        super(durationMilliseconds);

        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.BLACK);
    }

    @Override
    public final void compose(Canvas canvas, BitmapTransferObject outBitmapTransferObject,
                        BitmapTransferObject inBitmapTransferObject, int currentTime) {
        canvas.drawBitmap(outBitmapTransferObject.getBitmap(), outBitmapTransferObject.getX(),
                outBitmapTransferObject.getY(), null);
        canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), fillPaint);
        canvas.drawBitmap(inBitmapTransferObject.getBitmap(), inBitmapTransferObject.getX(),
                inBitmapTransferObject.getY(), null);
        drawMask(canvas, currentTime);
        canvas.restore();
    }

    protected abstract void drawMask(Canvas canvas, int currentTime);


}
