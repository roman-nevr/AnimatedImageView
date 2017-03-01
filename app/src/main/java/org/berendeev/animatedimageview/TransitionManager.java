package org.berendeev.animatedimageview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TransitionManager {
    private int duration;
    private List<ActionFilter> outBitmapFilters;
    private List<ActionFilter> inBitmapFilters;
    private ComposeFilter composeFilter;

    private Bitmap outBitmap, inBitmap, cacheOutBitmap, cacheInBitmap;

    public TransitionManager() {
        outBitmapFilters = new ArrayList<>();
        inBitmapFilters = new ArrayList<>();
    }

    public TransitionManager addHideFilter(ActionFilter actionFilter){
        outBitmapFilters.add(actionFilter);
        return this;
    }

    public TransitionManager addShowFilter(ActionFilter actionFilter){
        inBitmapFilters.add(actionFilter);
        return this;
    }

    public TransitionManager setComposeFilter(ComposeFilter composeFilter){
        this.composeFilter = composeFilter;
        return this;
    }

    public TransitionManager setDuration(int duration){
        this.duration = duration;
        return this;
    }

    void paintFrame(Canvas canvas, int currentTime){
        LogTimer logTimer = new LogTimer();
        logTimer.startTimer();
        BitmapTransferObject hideBitmapTransferObject = getOutResult(currentTime);
        BitmapTransferObject showBitmapTransferObject = getInResult(currentTime);
        drawResult(canvas, hideBitmapTransferObject, showBitmapTransferObject, currentTime);
        logTimer.stopTime("paint frame");
    }

    private BitmapTransferObject getInResult(int currentTime) {
        if (inBitmapFilters.size() == 0){
            return new BitmapTransferObject(cacheInBitmap);
        }
        updateInCacheBitmap();
        BitmapTransferObject bitmapTransferObject = new BitmapTransferObject(cacheInBitmap);
        for (ActionFilter actionFilter : inBitmapFilters) {
            actionFilter.paintFrame(bitmapTransferObject, currentTime);
        }
        return bitmapTransferObject;
    }

    private BitmapTransferObject getOutResult(int currentTime) {
        if(outBitmapFilters.size() == 0){
            return new BitmapTransferObject(cacheOutBitmap);
        }
        updateOutCacheBitmap();
        BitmapTransferObject bitmapTransferObject = new BitmapTransferObject(cacheOutBitmap);
        for (ActionFilter actionFilter : outBitmapFilters) {
            actionFilter.paintFrame(bitmapTransferObject, currentTime);
        }
        return bitmapTransferObject;
    }

    private void updateInCacheBitmap() {
        Canvas canvas = new Canvas(cacheInBitmap);
        canvas.drawBitmap(inBitmap, 0, 0, null);
    }

    private void updateOutCacheBitmap() {
        Canvas canvas = new Canvas(cacheOutBitmap);
        canvas.drawBitmap(outBitmap, 0, 0, null);
    }

    private void drawResult(Canvas canvas, BitmapTransferObject outBitmapTransferObject, BitmapTransferObject inBitmapTransferObject, int currentTime) {
        LogTimer logTimer = new LogTimer();
        logTimer.startTimer();
        if (composeFilter != null){
            composeFilter.compose(canvas, outBitmapTransferObject, inBitmapTransferObject, currentTime);
        }else {
            canvas.drawBitmap(inBitmapTransferObject.getBitmap(), 0, 0, null);
        }
        logTimer.stopTime("drawResult");
    }

    public TransitionManager setOutBitmap(Bitmap outBitmap) {
        this.outBitmap = outBitmap;
        cacheOutBitmap = outBitmap.copy(Bitmap.Config.ARGB_8888, true);
        return this;
    }

    public TransitionManager setInBitmap(Bitmap inBitmap) {
        this.inBitmap = inBitmap;
        cacheInBitmap = inBitmap.copy(Bitmap.Config.ARGB_8888, true);
        return this;
    }

    public int getDuration(){
        return duration;
    }



    private class LogTimer{
        private long startTime = -1;

        public void startTimer(){
            startTime = System.currentTimeMillis();
        }

        public void stopTime(String message){
            if(startTime == -1){
                throw new IllegalArgumentException();
            }
            Log.d("timer","time: "+ (System.currentTimeMillis() - startTime) + ", " +  message);
        }
    }

}