
/**
 *
 * Created by vdtn359 on 4/08/2015.
 *
 * Reference:
 *  Card Flip Animation:http://www.techrepublic.com/blog/software-engineer/use-androids-scale-animation-to-simulate-a-3d-flip/
 */

package com.example.tuannguyen.assignment1.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tuannguyen.assignment1.R;

public class TileView extends LinearLayout implements Animation.AnimationListener {
    private Drawable mImage;
    private ImageView mImageView;
    private int mTileIndex;
    private TileViewListener mListener;
    private Animation animation1;
    private Animation animation2;
    private Animation hideAnimation;
    private boolean isBackOfCardShowing; //check if the card is face-down

    public TileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TileView(Context context) {
        super(context);
        init();
    }

    public void init() {

        //set up layout params
        setPadding(5, 5, 5, 5);
        mImageView = new ImageView(getContext());
        mImageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        mImageView.setContentDescription("Press Me");
        mImageView.setImageResource(R.drawable.question);

        mImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.didSelectTile(TileView.this);
                }
            }
        });
        addView(mImageView);


        //load animations
        animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.to_middle);
        animation1.setAnimationListener(this);
        animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.from_middle);
        animation2.setAnimationListener(this);
        
        hideAnimation = new AlphaAnimation(1.0f, 0.0f);
        hideAnimation.setDuration(300);
        hideAnimation.setAnimationListener(this);
        
        isBackOfCardShowing = true;

    }

    public int getTileIndex() {
        return mTileIndex;
    }

    public void setTileIndex(int tileIndex) {
        this.mTileIndex = tileIndex;
    }

    public void setImage(Drawable image) {
        mImage = image;
    }


    public void setTileViewListener(TileViewListener listener) {
        this.mListener = listener;
    }

    public void flipImage()
    {
        mImageView.startAnimation(animation1);
    }

    public void revealImage() {
        mImageView.setImageDrawable(mImage);
        isBackOfCardShowing = false;
    }

    public void coverImage() {
        mImageView.setImageResource(R.drawable.question);
        isBackOfCardShowing = true;
    }

    public void hideImage() {
        mImageView.startAnimation(hideAnimation);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (animation == animation1)
        {
            mImageView.setClickable(false);
        }
        else if (animation == hideAnimation)
        {
            mImageView.setClickable(false);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animation1)
        {
            if (isBackOfCardShowing)
            {
                revealImage();
                mImageView.startAnimation(animation2);
            }
            else if (!isBackOfCardShowing)
            {
                coverImage();
                mImageView.startAnimation(animation2);
            }
        }
        else if (animation == animation2)
        {
            mImageView.setClickable(true);
        }
        else if (animation == hideAnimation)
        {
            mImageView.setClickable(true);
            setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public boolean isBackOfCardShowing() {
        return isBackOfCardShowing;
    }

   

    public interface TileViewListener {
        void didSelectTile(TileView tileView);
    }
}
