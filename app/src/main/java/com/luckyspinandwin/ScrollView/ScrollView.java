package com.luckyspinandwin.ScrollView;



import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.luckyspinandwin.R;

public class ScrollView extends FrameLayout {

    private static int ANIMATION_DUR = 150;
    ImageView current_image, next_image;
    int last_result = 0, old_value = 0;
    public int SLOT1=0,SLOT2=1,SLOT3=2,SLOT4=3,SLOT5=4,SLOT7=5;
    IEventEnd eventEnd;

    public void setEventEnd(IEventEnd eventEnd) {
        this.eventEnd = eventEnd;
    }

    public ScrollView(Context context) {
        super(context);
        init(context);
    }

    public ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.image_view_scrolling, this);
        current_image = getRootView().findViewById(R.id.current_image);
        next_image = getRootView().findViewById(R.id.next_image);

        next_image.setTranslationY(getHeight());
    }

    public void setValueRandom(final int image, final int rotate_count){
        current_image.animate().translationY(-getHeight()).setDuration(ANIMATION_DUR).start();
        next_image.setTranslationY(next_image.getHeight());
        next_image.animate().translationY(0)
                .setDuration(ANIMATION_DUR)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setImage(current_image, old_value%6);
                        current_image.setTranslationY(0);
                        if(old_value != rotate_count){
                            setValueRandom(image, rotate_count);
                            old_value++;
                        }else{
                            last_result = 0;
                            old_value = 0;
                            setImage(next_image, image);
                            eventEnd.eventEnd(image%6, rotate_count);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    private void setImage(ImageView image_view, int value) {
        if(value == SLOT1){
            image_view.setImageResource(R.drawable.bar);
        }else if(value == SLOT2){
            image_view.setImageResource(R.drawable.a_2);
        }else if(value == SLOT3){
            image_view.setImageResource(R.drawable.a_3);
        }else if(value == SLOT4){
            image_view.setImageResource(R.drawable.seven);
        }else if(value == SLOT5){
            image_view.setImageResource(R.drawable.a_5);
        }else if(value == SLOT7){
            image_view.setImageResource(R.drawable.a_7);
        }

        image_view.setTag(value);
        last_result = value;
    }

    public int getValue(){
        return Integer.parseInt(next_image.getTag().toString());
    }
}
