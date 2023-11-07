package com.example.onemoreclick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileOutputStream;

public class menu_screen extends AppCompatActivity {

    Button newGameBtn;
    Button howToPlayBtn;
    Button resetMaxPointsBtn;
    ImageButton closeApp;
    String pointsFileName;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        pointsFileName = "maxpoints.txt";
        newGameBtn = (Button) findViewById(R.id.newGameBtn);
        closeApp = (ImageButton) findViewById(R.id.closeAppBtn);
        howToPlayBtn = (Button) findViewById(R.id.howPlayBtn);
        resetMaxPointsBtn = (Button) findViewById(R.id.resetPointsBtn);

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

        resetMaxPointsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetMax();
            }
        });

    }

    public void resetMax() {
        Context context = getApplicationContext();
        File searchFile = new File(context.getFilesDir(), pointsFileName);
        if (searchFile.exists()) {
            searchFile.delete();
        }
    }

    public void clickNewGame() throws Exception{
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
}