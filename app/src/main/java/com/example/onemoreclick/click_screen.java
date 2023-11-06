package com.example.onemoreclick;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class click_screen extends AppCompatActivity {

    /**
     * Variables to control percentages
     */
    double doublePointsPercentage = 1.0d;
    double resetLuckPercentage = 0.05;


    /**
     * Android variables for objects
     */
    TextView actualPointsDisplay;
    TextView maxPointsDisplay;
    Button moreClickBtn;
    Button savePointsBtn;
    ImageButton backBtn;
    /**
     * Variables of the game
     */
    int actualPoints;
    int maxPoints;
    double luck;
    Toast wipeText;
    Toast resetLuckToast;
    Toast doublePoints;

    protected void test1(View view) {
        actualPointsDisplay.setText(String.valueOf(aleatoric()));
    }

    /**
     * Method to create all the items on the ClickScreen and initialize the variables
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_screen);

        actualPointsDisplay = (TextView) findViewById(R.id.actualPointsDisplay);
        maxPointsDisplay = (TextView) findViewById(R.id.maxPointsDisplay);
        moreClickBtn = (Button) findViewById(R.id.moreClickBtn);
        savePointsBtn = (Button) findViewById(R.id.savePointsBtn);
        backBtn = (ImageButton) findViewById(R.id.backClickBtn);

        actualPoints = 0;
        actualPointsDisplay.setText("Actual points: 0");
        maxPoints = 0;
        maxPointsDisplay.setText("Max points: 0");
        wipeText = Toast.makeText(getApplicationContext(), "BETTER LUCK NEXT TIME LOSER", Toast.LENGTH_LONG);
        resetLuckToast = Toast.makeText(getApplicationContext(), "YOUR LUCK HAS BEEN RESTORED", Toast.LENGTH_LONG);
        doublePoints = Toast.makeText(getApplicationContext(), "DOUBLE POINTS !!!", Toast.LENGTH_LONG);

        if (maxPoints == 0) {
            deactivateBtn(savePointsBtn);
        }

        moreClickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreClickBtnOnClick(view);
            }
        });
        savePointsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePointsBtnOnClick(view);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * Seguir aquí
         */


    }

    public void moreClickBtnOnClick(View view) {
        decreaseLuck();
        int testLuck = (int) aleatoric(0, 100);
        // System.out.println("testLuck: " + testLuck);
        if (testLuck > luck) {
            wipeText.show();
            resetPoints();
        } else {
            if (actualPoints > maxPoints && maxPoints != 0) {
                if (aleatoric() < doublePointsPercentage) {
                    actualPoints *= 2;
                    doublePoints.show();
                }
            }
            actualPoints += (int) aleatoric(1, 100);
            actualPointsDisplay.setText("Actual points: " + String.valueOf(actualPoints));
            if (maxPoints < actualPoints) {
                activateBtn(savePointsBtn);
            }
            aleatoricResetLuck();
            // System.out.println("Actual luck: " + luck);
        }
    }

    public void aleatoricResetLuck() {

        if (aleatoric() < resetLuckPercentage) {
            resetLuckToast.show();
            luck = 100;
        }

    }

    public void savePointsBtnOnClick(View view) {
        if (actualPoints > maxPoints) {
            maxPoints = actualPoints;
            maxPointsDisplay.setText(String.valueOf("Max points: " + String.valueOf(maxPoints)));
            resetPoints();
        }
    }

    public void resetPoints() {
        actualPointsDisplay.setText("Actual points: 0");
        actualPoints = 0;
        luck = 100;
        deactivateBtn(savePointsBtn);
    }


    public void activateBtn(Button btn) {
        btn.setEnabled(true);
        btn.setBackgroundColor(Color.parseColor("#1A8F1C"));
    }

    public void deactivateBtn(Button btn) {
        btn.setEnabled(false);
        btn.setBackgroundColor(Color.parseColor("#9F2858"));
        btn.setTextColor(Color.parseColor("#FFFFFF"));
    }

    public double decreaseLuck() {
        luck -= aleatoric(0.05, 1);
        return luck;
    }

    public double aleatoric() {
        double number = Math.round((Math.random() * 100) * 100.0) / 100.0;
        return number;
    }

    public double aleatoric(double min, double max) {
        double number = Math.round((Math.random() * (max - min) + min) * 100.0) / 100.0;
        return number;
    }

    @Override
    public void onBackPressed() {
        // No action
    }

}