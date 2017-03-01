package org.berendeev.animatedimageview;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AnimatedImageView extends ImageView {

    private Bitmap currentBitmap, outBitmap, inBitmap;
    private int currentTime;
    private TransitionManager transitionManager;
    private boolean animated;

    public AnimatedImageView(Context context) {
        super(context);
        init();
    }

    public AnimatedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimatedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        animated = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(animated){
            if(!isBitmapFitView(inBitmap)){
                inBitmap = fitToSize(inBitmap, getWidth(), getHeight());
                transitionManager.setInBitmap(inBitmap);
            }
            if(!isBitmapFitView(outBitmap)){
                outBitmap = fitToSize(outBitmap, getWidth(), getHeight());
                transitionManager.setOutBitmap(outBitmap);
            }
            transitionManager.paintFrame(canvas, currentTime);
        }else {
            showStaticBitmap(canvas);
        }
    }

    @SuppressLint({"DrawAllocation", "WrongCall"})
    private void showStaticBitmap(Canvas canvas) {
        if(currentBitmap ==null){
            super.onDraw(canvas);
            return;
        }
        if(!isBitmapFitView(currentBitmap)){
            currentBitmap = fitToSize(currentBitmap, getWidth(), getHeight());
        }
        canvas.drawBitmap(currentBitmap, 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = measureWidth(widthMeasureSpec);
        int measuredHeight = measureWidth(widthMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    private int measureWidth(int widthMeasureSpec) {
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        return specSize;
    }

    public void animateToBitmap(@NonNull Bitmap inBitmap, TransitionManager transitionManager){

        this.inBitmap = inBitmap;
        this.outBitmap = currentBitmap;
        currentBitmap = inBitmap;

        this.transitionManager = transitionManager;

        transitionManager.setInBitmap(inBitmap);
        transitionManager.setOutBitmap(outBitmap);

        currentTime = 0;
        animated = true;
        ValueAnimator animator = ValueAnimator.ofInt(0, transitionManager.getDuration());
        animator.setDuration(transitionManager.getDuration());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentTime = (int)animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    public void setCurrentBitmap(Bitmap currentBitmap) {
        this.currentBitmap = currentBitmap;
        invalidate();
    }

    private boolean isBitmapFitView(Bitmap bitmap){
        if(bitmap == null){
            return true;
        }
        if(bitmap.getWidth() != getWidth() || bitmap.getHeight() != getHeight()){
            return false;
        }else {
            return true;
        }
    }

    private Bitmap fitToSize(Bitmap bitmap, int width, int height) {
        Bitmap croppedBitmap;
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            croppedBitmap = Bitmap.createBitmap(bitmap, (bitmap.getWidth() - bitmap.getHeight()) / 2, 0,
                    bitmap.getHeight(), bitmap.getHeight());

        } else {
            croppedBitmap = Bitmap.createBitmap(bitmap, 0, (bitmap.getHeight() - bitmap.getWidth()) / 2,
                    bitmap.getWidth(), bitmap.getWidth());
        }
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(croppedBitmap, width, height, false);
        croppedBitmap.recycle();
        return scaledBitmap;
    }
}
