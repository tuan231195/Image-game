package com.example.tuannguyen.assignment1.model;

import android.graphics.drawable.Drawable;

/**
 * Created by tuannguyen on 3/08/2015.
 */
public class TileData {
    private Drawable mImage;
    private int mIdent;
    public TileData(Drawable image, int identifier)
    {
        this.mImage = image;
        this.mIdent = identifier;
    }

    public int getIdentifier() {
        return mIdent;
    }

    public void setIdentifier(int identifier) {
        this.mIdent = identifier;
    }

    public final Drawable getImage() {
        return mImage;
    }

    public void setImage(Drawable image) {
        this.mImage = image;
    }
}
