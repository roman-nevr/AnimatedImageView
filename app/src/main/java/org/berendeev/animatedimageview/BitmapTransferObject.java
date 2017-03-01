package org.berendeev.animatedimageview;

import android.graphics.Bitmap;

public class BitmapTransferObject {
    private Bitmap bitmap;
    private int x,y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public BitmapTransferObject(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
