package com.example.onemoreclick;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
}