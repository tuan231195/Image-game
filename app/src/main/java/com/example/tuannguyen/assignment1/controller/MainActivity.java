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

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

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

        GameModel model = new GameModel(18, images);
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
