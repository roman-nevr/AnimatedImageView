package org.berendeev.animatedimageview;

import android.graphics.Canvas;

public abstract class ComposeFilter {
    private int durationMilliseconds;
    private int variant;

    public ComposeFilter(int durationMilliseconds) {
        this.durationMilliseconds = durationMilliseconds;
    }

    public ComposeFilter setVariant(int variant){
        this.variant = variant;
        return this;
    }

    public final int getDurationMilliseconds(){
        return durationMilliseconds;
    }

    public abstract void compose(Canvas canvas, BitmapTransferObject outBitmapTransferObject,
                                 BitmapTransferObject inBitmapTransferObject, int time);

    protected final int getVariant() {
        return variant;
    }
}
