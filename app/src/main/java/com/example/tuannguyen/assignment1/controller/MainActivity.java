package com.example.tuannguyen.assignment1.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.tuannguyen.assignment1.R;
import com.example.tuannguyen.assignment1.model.GameModel;
import com.example.tuannguyen.assignment1.model.TileData;
import com.example.tuannguyen.assignment1.view.TileView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ArrayList<TileView> tileViews;
    private static final int numTiles = 18;
    private GameModel model;
    private boolean isDisplayingImage;
    private ArrayList<Drawable> images;
    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //prepare resources
        images  = new ArrayList<>();


        //load images
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.baldhill, null));
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.cathedral, null));
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.evening, null));
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.magnificent, null));
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.ocean, null));
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.scenery, null));
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.waterfall, null));
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.lighthouse, null));
        images.add(ResourcesCompat.getDrawable(getResources(), R.drawable.lake, null));


        //initialise model object

        model = new GameModel(numTiles, images);

        model.setControllerInterface(new GameModel.ControllerInterface() {
            @Override
            public void gameDidComplete(final GameModel model) {
                //create the dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("You won!");
                builder.setMessage("Your score is " + model.getScore());
                builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetGame();
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void didMatchTile(GameModel model, int tileIndex, int previousTileIndex) {
                //prevent users from selecting other images
                isDisplayingImage = true;
                final TileView curTile = tileViews.get(tileIndex);
                final TileView previousTile = tileViews.get(previousTileIndex);

                //wait 1 seconds before hiding images
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        curTile.hideImage();
                        previousTile.hideImage();
                        isDisplayingImage = false;
                    }
                }, 1000);


            }

            @Override
            public void didFailToMatchTile(GameModel model, int tileIndex, int previousTileIndex) {
                //prevent users from selecting other images
                isDisplayingImage = true;
                final TileView curTile = tileViews.get(tileIndex);
                final TileView previousTile = tileViews.get(previousTileIndex);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //wait 1 seconds before covering images
                        curTile.flipImage();
                        previousTile.flipImage();
                        isDisplayingImage = false;
                    }
                }, 1000);

            }

            @Override
            public void scoreDidUpdate(GameModel model, int newScore) {
                scoreTextView.setText("Score: " + newScore);
            }
        });

        Log.d("DEBUG TAG", model.toString());

        //loading Views
        scoreTextView = (TextView)findViewById(R.id.tvScore);

        tileViews = new ArrayList<>();

        for (int i = 1; i <= numTiles; i++)
        {
            int id = getResources().getIdentifier("tile"+i, "id", getPackageName());
            TileData data = model.getTileAt(i - 1);
            //populate data for tileView
            TileView tileView = (TileView)findViewById(id);
            tileView.setImage(data.getImage());
            tileView.setTileIndex(i - 1);
            tileView.coverImage();
            tileView.setTileViewListener(new TileView.TileViewListener() {
                @Override
                public void didSelectTile(TileView tileView) {
                    if (!isDisplayingImage)
                    {
                        //check if the card is already flipped face-up
                        if (tileView.isBackOfCardShowing())
                        {
                            tileView.flipImage();
                            model.pushTileIndex(tileView.getTileIndex());
                        }
                    }

                }
            });
            tileViews.add(tileView);
        }

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
        //a reset menu item
        if (id == R.id.reset)
        {
            resetGame();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //reset the game
    public void resetGame()
    {
        scoreTextView.setText("Score: 0");
        model.reset(numTiles,images);
        for (int i = 0; i < numTiles; i++)
        {
            final TileView tileView = tileViews.get(i);
            tileView.coverImage();
            tileView.setImage(model.getTileAt(i).getImage());
            tileView.setVisibility(View.VISIBLE);
        }
    }
}
