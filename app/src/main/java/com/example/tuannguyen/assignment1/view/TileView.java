package com.example.tuannguyen.assignment1.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tuannguyen.assignment1.R;

/**
 * Created by tuannguyen on 4/08/2015.
 */
public class TileView extends LinearLayout {
    private Drawable mImage;
    private ImageView mImageView;
    private int mTileIndex;
    private TileViewListener mListener;

    public TileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TileView(Context context) {
        super(context);
        init();
    }

    public void init()
    {
        setPadding(5, 5, 5, 5);
        mImageView = new ImageView(getContext());
        mImageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        mImageView.setContentDescription("Press Me");
        mImageView.setImageResource(R.drawable.question);

        mImageView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mListener != null)
                {
                    mListener.didSelectTile(TileView.this);
                }
                return true;
            }
        });
        addView(mImageView);
    }

    public int getTileIndex() {
        return mTileIndex;
    }

    public void setTileIndex(int tileIndex) {
        this.mTileIndex = tileIndex;
    }

    public void setImage(Drawable image)
    {
        mImage = image;
    }


    public void setTileViewListener(TileViewListener listener)
    {
        this.mListener = listener;
    }

    public void revealImage()
    {
        mImageView.setImageDrawable(mImage);
    }

    public void coverImage()
    {
        mImageView.setImageResource(R.drawable.question);
    }

    public void hideImage()
    {
        setVisibility(View.GONE);
    }

    public interface TileViewListener
    {
        void didSelectTile(TileView tileView);
    }


}
