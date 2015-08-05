package com.example.tuannguyen.assignment1.model;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by vdtn359 on 3/08/2015.
 */
public class GameModel {
    private int mLastIndex;
    private int mSecondLastIndex;
    private boolean mFirstTaped;
    private int mMatchCount;
    private int mScore;
    private ArrayList<TileData> mTiles;
    private ControllerInterface mInterface;

    public GameModel(int numTile, ArrayList<Drawable> images)
    {
        mTiles = new ArrayList<TileData>();
        reset(numTile, images);

    }

    public void reset(int numTile, ArrayList<Drawable> images) {

        mLastIndex = mSecondLastIndex = -1;
        mFirstTaped = false;
        mMatchCount = 0;
        mScore = 0;
        mTiles.clear();
        //populate the array of TileData
        int counter = 0;
        int index = 0;
        while (counter < numTile)
        {
            Drawable d = images.get(index);
            TileData tile = new TileData(d, index);
            mTiles.add(tile);
            mTiles.add(tile);
            index = (index + 1) % (images.size());//get the next image
            counter += 2;
        }

        shuffle();
    }

    private void shuffle() {
        int index;
        Random random = new Random();
        for (int i = mTiles.size() - 1; i > 0; i--)
        {
            index = random.nextInt(i);//generate random index
            TileData temp = mTiles.get(index);
            mTiles.set(index, mTiles.get(i));
            mTiles.set(i, temp);
        }
    }

    //class description
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for (TileData data : mTiles)
        {
            builder.append("\n" + data.getIdentifier());
        }
        return builder.toString();
    }

    public void pushTileIndex(int tapedIndex)
    {
        //if player clicks the same card 2 times
        if (mFirstTaped && mLastIndex == tapedIndex)
        {
            return;
        }

        mSecondLastIndex = mLastIndex;
        mLastIndex = tapedIndex;
        mFirstTaped = !mFirstTaped;
        if (!mFirstTaped)
        {
            //compare 2 cards
            if (mTiles.get(mLastIndex).getIdentifier() == mTiles.get(mSecondLastIndex).getIdentifier())
            {
                mMatchCount++;
                mScore += 200;
                mInterface.didMatchTile(this, mLastIndex, mSecondLastIndex);
            }
            else
            {
                mScore -= 100;
                mInterface.didFailToMatchTile(this, mLastIndex, mSecondLastIndex);
            }
            //update score
            mInterface.scoreDidUpdate(this, mScore);
            if (mMatchCount == mTiles.size()/2)
            {
                mInterface.gameDidComplete(this);
            }
        }
    }


    public void setControllerInterface(ControllerInterface reference)
    {
        this.mInterface = reference;
    }

    public boolean isFirstTaped() {
        return mFirstTaped;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int mScore) {
        this.mScore = mScore;
    }

    public int getSecondLastIndex() {
        return mSecondLastIndex;
    }

    public int getLastIndex() {
        return mLastIndex;
    }

    public TileData getTileAt(int index)
    {
        return mTiles.get(index);
    }

    public interface ControllerInterface {
        public void gameDidComplete(GameModel model);
        public void didMatchTile(GameModel model, int tileIndex, int previousTileIndex);
        public void didFailToMatchTile(GameModel model, int tileIndex, int previousTileIndex);
        public void scoreDidUpdate(GameModel model, int newScore);
    }
}
