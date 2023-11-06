package com.example.onemoreclick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class menu_screen extends AppCompatActivity {

    Button newGameBtn;
    ImageButton closeApp;
    ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        newGameBtn = (Button) findViewById(R.id.newGameBtn);
        closeApp = (ImageButton) findViewById(R.id.closeAppBtn);

        closeApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu_screen.this, click_screen.class);
                startActivity(intent);
            }
        });

    }
}