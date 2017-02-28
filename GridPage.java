package com.cmpe277labs.amipa.gameioflife;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.R.attr.x;
import static android.R.attr.y;

import static android.content.DialogInterface.*;
import static com.cmpe277labs.amipa.gameioflife.R.*;
import static com.cmpe277labs.amipa.gameioflife.R.drawable.circle;

public class GridPage extends AppCompatActivity {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public static boolean state[][]=new boolean[12][12];
    public static int cc[][] = new int[12][12];
    int i=0,j=0;
    Button b00;
    private GoogleApiClient client;
    GridLayout layout;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MediaPlayer nexts = MediaPlayer.create(this, R.raw.button);
        final MediaPlayer grid = MediaPlayer.create(this, R.raw.grid);
        final MediaPlayer resumes = MediaPlayer.create(this, R.raw.resume);
        final MediaPlayer gamestart = MediaPlayer.create(this, R.raw.gamestart);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_page);
        gamestart.start();

        layout=(GridLayout) findViewById(R.id.grid1);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        layout.removeAllViews();


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);



        Resources r = getResources();

        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, r.getDisplayMetrics());
        final int dim = (metrics.widthPixels - (px * 24)) / 12;

        int counter = 0;

        for(i=0;i<12;i++){
            for(j=0;j<12;j++){
                final Button button = new Button(this);
                final GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(i),GridLayout.spec(j));
                params.setMargins(px,px,px,px);
                params.width = dim;
                params.height = dim;
                button.isSoundEffectsEnabled();
                button.setLayoutParams(params);
                button.setPadding(px,px,px,px);
                button.setBackgroundColor(getResources().getColor(android.R.color.white));
                layout.addView(button);
//                button.setId((i*10)+j);
                button.setId(counter);
                counter++;
               // button.setText(""+i+""+j);
                button.setTag(i+","+j);
             //   button.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                state[i][j]=false;
                cc[i][j]=0;
                button.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                    //    v.playSoundEffect(SoundEffectConstants.CLICK);

                        String tag = (String)button.getTag();
                        String[] splitTag = tag.split(",");

                        int currentI = Integer.parseInt(splitTag[0]);
                        int currentJ = Integer.parseInt(splitTag[1]);

                        grid.start();
                            int k=currentI;
                            int m=currentJ;
                        cc[k][m]++;
                            Log.d("k m",""+k+".."+m);

                        if(cc[k][m]%2!=0) {
                              //v.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                                state[k][m]=true;
                              //CircleAdd ca=new CircleAdd(GridPage.this,k,m);
                            Log.d("k m","in circle");
                         //   button.setBackgroundColor(getResources().getColor(android.R.color.white));

                            button.setBackground( getResources().getDrawable(drawable.circle));


                            //   setContentView(new CircleAdd(GridPage.this,(int)v.getX(),(int)v.getY(),(int)v.getWidth()/2));


                        }
                        else{
                                v.setBackgroundColor(getResources().getColor(android.R.color.white));
                                state[k][m]=false;
                        }
                    }
                });
            }
        }

        //resume
        Button resume=(Button)findViewById(id.resumebutton);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.playSoundEffect(SoundEffectConstants.CLICK);
                resumes.start();
                final AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(GridPage.this).create();
                alertDialog.setTitle("Reset");
                alertDialog.setMessage("Do you really want to reset?");


                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"YES", new OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Button b[][]=new Button[12][12];
                        int counter=0;
                        for(int r=0;r<12;r++) {
                            for (int c=0;c<12;c++) {
                                Button currentButton = (Button) findViewById(counter);
                                counter++;
                                Log.d("cntr","..."+counter);
                                state[r][c]=false;
                                currentButton.setBackgroundColor(getResources().getColor(android.R.color.white));

                            }
                        }
                    }
                });

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"NO", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        //next generation
        Button next=(Button)findViewById(id.nextbutton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // v.playSoundEffect(SoundEffectConstants.CLICK);
                nexts.start();
//                        Button b[][]=new Button[12][12];
                int counter = 0;
                for(int l=0;l<12;l++){
                    for(int z=0;z<12;z++){
//                                b[l][z]=(Button)findViewById((l*10)+z);
//                        Button currentButton=(Button)findViewById((l*10)+z);
                        Button currentButton = (Button) findViewById(counter);
                        counter++;

                        int live=0,dead=0;
                        if(l>0 && state[l-1][z]==true)
                            live++;
                        else
                            dead++;
                        if(l>0 && z>0 && state[l-1][z-1]==true)
                            live++;
                        else
                            dead++;
                        if(l>0 && z<11 && state[l-1][z+1]==true)
                            live++;
                        else
                            dead++;
                        if(l<11 && z<11 && state[l+1][z+1]==true)
                            live++;
                        else
                            dead++;
                        if(l<11 && z>0 && state[l+1][z-1]==true)
                            live++;
                        else
                            dead++;
                        if(z>0 && state[l][z-1]==true)
                            live++;
                        else
                            dead++;
                        if(l<11 && state[l+1][z]==true)
                            live++;
                        else
                            dead++;
                        if(z<11 && state[l][z+1]==true)
                            live++;
                        else
                            dead++;

                        if(state[l][z]==true && live<2) {
                            System.err.println("l: "+l+" z:"+z);
                            state[l][z] = false;
                            currentButton.setBackgroundColor(getResources().getColor(android.R.color.white));
//                            currentButton.setBackground( getResources().getDrawable(drawable.green_circle));
                        }
                        if(state[l][z]==true && (live==2 || live==3)) {
                            currentButton.setBackground( getResources().getDrawable(drawable.circle));

                            state[l][z] = true;
                        }
                        if(state[l][z]==true && live>3) {
                            state[l][z] = false;
                            currentButton.setBackgroundColor(getResources().getColor(android.R.color.white));
                        }
                        if(state[l][z]==false && live==3) {
                            state[l][z] = true;
                            currentButton.setBackground( getResources().getDrawable(drawable.circle));

                            //  b[l][z].setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                        }
                        currentButton.invalidate();
                    }
                }
            }
        });
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("GridPage Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
