package com.example.onemoreclick;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.media.MediaPlayer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class menu_screen extends AppCompatActivity {

    String actualVersionSet = "Version 0.1";

    Button newGameBtn;
    Button howToPlayBtn;
    Button resetMaxPointsBtn;
    EditText versionText;
    ImageButton closeApp;
    String pointsFileName;
    MediaPlayer backgroundMediaPlayer;
    private List<Integer> backgroundSoundList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        pointsFileName = "maxpoints.txt";
        newGameBtn = (Button) findViewById(R.id.newGameBtn);
        closeApp = (ImageButton) findViewById(R.id.closeAppBtn);
        howToPlayBtn = (Button) findViewById(R.id.howPlayBtn);
        resetMaxPointsBtn = (Button) findViewById(R.id.resetPointsBtn);
        versionText = (EditText) findViewById(R.id.versionTextBox);
        versionText.setText(actualVersionSet);

        backgroundSoundList = new ArrayList<>();
        backgroundSoundList.add(R.raw.backgroundmusic1);
        backgroundSoundList.add(R.raw.backgroundmusic2);
        backgroundSoundList.add(R.raw.backgroundmusic3);
        backgroundMediaPlayer = new MediaPlayer();

        closeApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //System.exit(0);
            }
        });
        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    clickNewGame();
                } catch (Exception e) {
                }
            }
        });

        howToPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHowToPlay();
            }
        });

        resetMaxPointsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetMax();
            }
        });

        playRandomBackgroundMusic();

    }

    public void playRandomBackgroundMusic() {
        int randomIndex = new Random().nextInt(backgroundSoundList.size());
        int soundResourceId = backgroundSoundList.get(randomIndex);
        if (backgroundMediaPlayer.isPlaying()) {
            backgroundMediaPlayer.stop();
            backgroundMediaPlayer.reset();
        }
        backgroundMediaPlayer = MediaPlayer.create(this, soundResourceId);
        backgroundMediaPlayer.setLooping(true);
        backgroundMediaPlayer.setVolume(0.15f, 0.15f);
        backgroundMediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        if (backgroundMediaPlayer != null) {
            backgroundMediaPlayer.release();
        }
        super.onDestroy();
    }

    public void resetMax() {
        showResetDialog();
    }

    public void showResetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("DO YOU WANT TO RESET YOUR MAXIMUM POINTS?");
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("RESET POINTS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Context context = getApplicationContext();
                File searchFile = new File(context.getFilesDir(), pointsFileName);
                if (searchFile.exists()) {
                    searchFile.delete();
                }
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void clickNewGame() throws Exception {
        Context context = getApplicationContext();
        File searchFile = new File(context.getFilesDir(), pointsFileName);
        if (!searchFile.exists()) {
            FileOutputStream newFile = openFileOutput(pointsFileName, Context.MODE_PRIVATE);
            newFile.write("0".getBytes());
        } else {
            System.out.println("File already exists");
        }
        Intent intent = new Intent(menu_screen.this, click_screen.class);
        startActivity(intent);
    }

    public void clickHowToPlay() {
        Intent intent = new Intent(menu_screen.this, how_to_play.class);
        startActivity(intent);
    }

}