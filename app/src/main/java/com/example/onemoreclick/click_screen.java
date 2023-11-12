package com.example.onemoreclick;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class click_screen extends AppCompatActivity {

    /**
     * Variables to control percentages
     */
    double doublePointsPercentage = 1.00d;
    double resetLuckPercentage = 0.50d;
    double decreaseLuckPercentage = 10.00d;


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
    String pointsFileName;
    private MediaPlayer mediaPlayer;
    private List<Integer> soundList;

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

        pointsFileName = "maxpoints.txt";

        actualPoints = 0;
        luck = 100;
        actualPointsDisplay.setText("Actual points: 0");
        maxPoints = readMaxPointsFile(pointsFileName);
        maxPointsDisplay.setText("Max points: " + String.valueOf(maxPoints));

        deactivateBtn(savePointsBtn);

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

        soundList = new ArrayList<>();
        soundList.add(R.raw.clickbtn1);
        soundList.add(R.raw.clickbtn2);
        soundList.add(R.raw.clickbtn3);
        soundList.add(R.raw.clickbtn4);
        soundList.add(R.raw.clickbtn5);
        soundList.add(R.raw.clickbtn6);
        mediaPlayer = new MediaPlayer();

        /**
         * MORE:
         */

    }

    public void playRandomClickSound() {
        int randomIndex = new Random().nextInt(soundList.size());
        int soundResourceId = soundList.get(randomIndex);
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        mediaPlayer = MediaPlayer.create(this, soundResourceId);
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroy();
    }

    public int readMaxPointsFile(String pointsFilePath) {
        int toReturn = 0;
        String textInFile = "";
        Context context = getApplicationContext();
        try {
            byte[] bytes = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder();
            FileInputStream fis = context.openFileInput(pointsFilePath);
            int bytesRead;
            while ((bytesRead = fis.read(bytes)) != -1) {
                stringBuilder.append(new String(bytes, 0, bytesRead));
            }
            textInFile = stringBuilder.toString();
            fis.close();
        } catch (Exception e) {
        }
        toReturn = Integer.parseInt(textInFile.trim());
        System.out.println("Puntuacion: " + toReturn);
        return toReturn;
    }

    public void showDoublePointsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("YOU HAVE EARNED DOUBLE POINTS!");
        builder.setPositiveButton("NICEEEEE!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showLoserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("YOU HAVE LOST ALL THE POINTS. BETTER LUCK NEXT TIME LOSER");
        builder.setPositiveButton("OK D:", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showResetLuckDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("YOUR LUCK HAS BEEN RESTORED");
        builder.setPositiveButton("LUCKY DAY!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void moreClickBtnOnClick(View view) {
        playRandomClickSound();
        if (aleatoric() > decreaseLuckPercentage) {
            decreaseLuck();
        }
        int testLuck = 0;
        if (actualPoints <= maxPoints) {
            if (aleatoric(0,100) < 20) {
                testLuck = (int) aleatoric(0, 50);
            } else {
                testLuck = (int) aleatoric(0, 75);
            }
        } else {
            testLuck = (int) aleatoric(0, 100);
        }
        System.out.println("-------------------------------------------------------------");
        System.out.println("Test luck: " + testLuck);
        if (testLuck > luck && actualPoints != 0) {
            showLoserDialog();
            resetPoints();
        } else {
            aleatoricDoublePoints();
            actualPoints += (int) aleatoric(1, 100);
            actualPointsDisplay.setText("Actual points: " + String.valueOf(actualPoints));
            if (maxPoints < actualPoints) {
                activateBtn(savePointsBtn);
            }
            aleatoricResetLuck();
            System.out.println("Actual luck: " + luck);
            System.out.println("-------------------------------------------------------------");
        }
    }

    public void aleatoricDoublePoints() {
        if (actualPoints > maxPoints && maxPoints != 0) {
            if (aleatoric() < doublePointsPercentage) {
                actualPoints *= 2;
                showDoublePointsDialog();
                doublePointsPercentage = 1.00d;
            } else {
                doublePointsPercentage += 0.25d;
                System.out.println("Actual doublePointsPercentage: " + doublePointsPercentage);
            }
        }
    }

    public void aleatoricResetLuck() {
        if (aleatoric() < resetLuckPercentage) {
            showResetLuckDialog();
            luck = 100;
            resetLuckPercentage = 0.50d;
        } else {
            resetLuckPercentage += 0.10d;
            System.out.println("Actual resetLuckPercentage: " + resetLuckPercentage);
        }
    }

    public void savePointsBtnOnClick(View view) {
        if (actualPoints > maxPoints) {
            maxPoints = actualPoints;
            maxPointsDisplay.setText(String.valueOf("Max points: " + String.valueOf(maxPoints)));
            savePointsInFile();
            resetPoints();
        }
    }

    public void savePointsInFile() {
        try {
            String toWrite = String.valueOf(actualPoints);
            FileOutputStream newFile = openFileOutput(pointsFileName, Context.MODE_PRIVATE);
            newFile.write(toWrite.getBytes());
        } catch (Exception e) {
        }
        ;
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
        luck -= aleatoric(0.05, 1.00);
        return luck;
    }

    public double aleatoric() {
        double number = Math.round((Math.random() * 100) * 100.00) / 100.00;
        return number;
    }

    public double aleatoric(double min, double max) {
        double number = Math.round((Math.random() * (max - min) + min) * 100.00) / 100.00;
        return number;
    }

    @Override
    public void onBackPressed() {
    }

}