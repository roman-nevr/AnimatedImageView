package org.berendeev.animatedimageview;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class ActionFilter { // базовый класс для фильтров показа и скрытия.

    private int duration, variant;

    public ActionFilter(int duration) {
        this.duration = duration;
    }

    public abstract void paintFrame(BitmapTransferObject bitmapTransferObject, int curFrame); // отрисовывает следующий кадр.

    public final int getDuration() {
        return duration;
    }

    protected int getVariant() {
        return variant;
    }

    public ActionFilter setVariant(int variant) {
        this.variant = variant;
        return this;
    }
}