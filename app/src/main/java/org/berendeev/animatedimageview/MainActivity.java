package org.berendeev.animatedimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TransitionManager curtainManager, pullInOutManager, transitionManager;
    private AnimatedImageView animatedImageView;

    private Bitmap outBitmap, inBitmap, sky;
    private List<Bitmap> bitmaps;
    private int currentBitmapIndex;
    private int currentVariant;
    private ComposeFilter curtainComposeFilter, pullInOutComposeFilter, composeFilter;
    private ActionFilter translation;
    private Button nextFilterButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animatedImageView = (AnimatedImageView) findViewById(R.id.image);
        animatedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBitmapIndex++;
                currentVariant++;
                if (currentBitmapIndex == bitmaps.size()) {
                    currentBitmapIndex = 0;
                }
                if (currentVariant == 8){
                    currentVariant = 0;
                }
                translation.setVariant(currentVariant + 2);
                composeFilter.setVariant(currentVariant);
                animatedImageView.animateToBitmap(bitmaps.get(currentBitmapIndex), transitionManager);
            }
        });

        textView = (TextView) findViewById(R.id.textView);

        nextFilterButton = (Button) findViewById(R.id.change_button);
        nextFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(transitionManager == curtainManager){
                    transitionManager = pullInOutManager;
                    composeFilter = pullInOutComposeFilter;
                    textView.setText("PullInOutFilter");
                }else {
                    transitionManager = curtainManager;
                    composeFilter = curtainComposeFilter;
                    textView.setText("CurtainFilter");
                }
            }
        });

        int duration = 1000;
        translation = new TranslateActionFilter(duration, 0.25f);
        curtainComposeFilter = new CurtainMaskComposeFilter(duration);
        pullInOutComposeFilter = new PullInMaskComposeFilter(duration).setVariant(currentVariant);

        composeFilter = curtainComposeFilter;
        curtainManager = new TransitionManager()
                .addShowFilter(translation)
                .setComposeFilter(curtainComposeFilter)
                .setDuration(duration);

        pullInOutManager = new TransitionManager()
                .addShowFilter(translation)
                .setComposeFilter(pullInOutComposeFilter)
                .setDuration(duration);
        transitionManager = curtainManager;

        outBitmap = getBitmap(R.drawable.night);
        inBitmap = getBitmap(R.drawable.material);
        sky = getBitmap(R.drawable.sky_bgr);
        bitmaps = Arrays.asList(outBitmap, inBitmap, sky);

        animatedImageView.setCurrentBitmap(bitmaps.get(currentBitmapIndex));
    }

    public Bitmap getBitmap(@DrawableRes int id) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id, options);
        return bitmap;
    }

    //git remote add origin https://github.com/roman-nevr/AnimatedImageView.git

}
