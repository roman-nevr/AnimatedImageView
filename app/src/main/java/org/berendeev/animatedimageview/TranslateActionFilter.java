package org.berendeev.animatedimageview;

public class TranslateActionFilter extends ActionFilter {

    private float offset;

    public TranslateActionFilter(int duration, float offset) {
        super(duration);
        this.offset = offset;
    }

    @Override
    public void paintFrame(BitmapTransferObject bitmapTransferObject, int currentTime) {
        float timeCoef = (getDuration() - (float)currentTime)/ getDuration();
        switch (getVariant() % 4){
            case 0:{
                bitmapTransferObject.setX((int) (offset * timeCoef * bitmapTransferObject.getBitmap().getWidth()));
                break;
            }
            case 1:{
                bitmapTransferObject.setX((int) (-offset * timeCoef * bitmapTransferObject.getBitmap().getWidth()));
                break;
            }
            case 2:{
                bitmapTransferObject.setY((int)(offset * timeCoef * bitmapTransferObject.getBitmap().getHeight()));
                break;
            }
            case 3:{
                bitmapTransferObject.setY((int)(-offset * timeCoef * bitmapTransferObject.getBitmap().getHeight()));
                break;
            }
        }
    }
}
