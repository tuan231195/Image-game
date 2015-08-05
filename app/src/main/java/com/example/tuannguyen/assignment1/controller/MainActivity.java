package com.example.tuannguyen.assignment1.controller;

import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tuannguyen.assignment1.R;
import com.example.tuannguyen.assignment1.model.GameModel;
import com.example.tuannguyen.assignment1.model.TileData;
import com.example.tuannguyen.assignment1.view.TileView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ArrayList<TileView> tileViews;
    private static final int numTiles = 18;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Drawable> images = new ArrayList<>();


        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.baldhill, null));
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.cathedral, null));
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.evening, null));
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.magnificent, null));
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.ocean, null));
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.scenery, null));
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.waterfall, null));
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.lighthouse, null));
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.lake, null));

        GameModel model = new GameModel(numTiles, images);

        tileViews = new ArrayList<>();
        for (int i = 1; i <= numTiles; i++)
        {
            int id = getResources().getIdentifier("tile"+i, "id", getPackageName());
            TileData data = model.getTileAt(i - 1);
            TileView tileView = (TileView)findViewById(id);
            tileView.setImage(data.getImage());
            tileView.coverImage();
            tileView.setTileIndex(i - 1);
            tileView.setTileViewListener(new TileView.TileViewListener() {
                @Override
                public void didSelectTile(TileView tileView) {
                    tileView.revealImage();
                }
            });
        }

        Log.d("DEBUG TAG", model.toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
