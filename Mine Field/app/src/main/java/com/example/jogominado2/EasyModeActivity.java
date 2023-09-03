package com.example.jogominado2;

import static com.example.jogominado2.R.drawable.bandeirinha;
import static com.example.jogominado2.R.drawable.bomb;
import static com.example.jogominado2.R.drawable.question;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Timer;
import java.util.TimerTask;

public class EasyModeActivity extends AppCompatActivity {

    private Switch swtFlag, swtQuestion;
    private boolean perdeu = false;
    private int tamLines, tamColumn, displayWidth;
    private DisplayMetrics dm;
    private LinearLayout layouts[];
    private LinearLayout layoutInicial;
    private MaterialButton btn[][];
    private Button btnReset;
    private Context context;
    private CampoMinado camp;
    private int mat[][];
    private Handler handler = new Handler(Looper.getMainLooper());
    private int cont, timeGame;
    private TextView qtdCLicks, tempoJogo;
    private Timer timer;
    private TimerTask task;
    private double time;
    private int rounded , hours, minutes, seconds;
    private float originalTextSize;

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_mode);

        tamLines = 9;
        tamColumn = 9;

        swtFlag = findViewById(R.id.swtFlag);
        swtQuestion = findViewById(R.id.swtQuestion);
        btn = new MaterialButton[15][10];
        btnReset = findViewById(R.id.btnReset);
        cont = 0;
        qtdCLicks = findViewById(R.id.qtdClicks);
        tempoJogo = findViewById(R.id.tempoJogo);
        cont = 0;
        timeGame = 0;
        handler = new Handler();
        timer = new Timer();
        time = 0.0;
        rounded = (int) Math.round(time);
        dm = new DisplayMetrics();

        EasyModeActivity.this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        camp = new CampoMinado(tamLines, tamColumn);

        btn = new MaterialButton[tamLines][tamColumn];


        layoutInicial = findViewById(R.id.layoutInical);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutInicial.setOrientation(LinearLayout.HORIZONTAL);
        else
            layoutInicial.setOrientation(LinearLayout.VERTICAL);

        layouts = new LinearLayout[tamColumn];


        for (int i = 0; i < tamColumn; i++) {

            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                layouts[i] = new LinearLayout(this);
                layouts[i].setOrientation(LinearLayout.VERTICAL);
                layouts[i].setLayoutParams(new LinearLayout.LayoutParams(((dm.widthPixels-50)/tamColumn), dm.heightPixels-350));
                layoutInicial.addView(layouts[i]);
            }else{
                layouts[i] = new LinearLayout(this);
                layouts[i].setOrientation(LinearLayout.HORIZONTAL);
                layouts[i].setLayoutParams(new LinearLayout.LayoutParams(((dm.widthPixels)- 100), (dm.heightPixels-100)/tamColumn));
                layoutInicial.addView(layouts[i]);
            }
        }

        for (int i = 0; i < tamLines; i++) {
            for (int j = 0; j < tamColumn; j++) {
                Context ctx = new ContextThemeWrapper(new MaterialButton(this).getContext(), com.google.android.material.R.style.Widget_MaterialComponents_Button_Icon);
                btn[i][j] = new MaterialButton(ctx);
                btn[i][j].setInsetTop(0);
                btn[i][j].setInsetBottom(0);

                if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    btn[i][j].setLayoutParams(new LinearLayout.LayoutParams((dm.widthPixels-350)/tamLines, (dm.heightPixels-100)/tamColumn));
                else
                    btn[i][j].setLayoutParams(new LinearLayout.LayoutParams((dm.widthPixels-50)/tamColumn, (dm.heightPixels-350)/tamLines));

                if(tamLines > 10 || tamColumn > 10)
                    btn[i][j].setTextSize(10);

                btn[i][j].setOnClickListener(view -> click(view));
                layouts[j].addView(btn[i][j]);
            }
        }

        resetarJogo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        rounded++;
                        seconds = ((rounded % 86400) % 3600) % 60;
                        minutes = ((rounded % 86400) % 3600) / 60;
                        hours = ((rounded % 86400) / 3600);

                        String temporizador = hours + ":" + minutes + ":" + seconds;

                        tempoJogo.setText(temporizador);
                        handler.postDelayed(this, 1000);
                    }
                });
            }
        }).start();


    }

    public void contClick() {
        ++cont;
        qtdCLicks.setText(String.valueOf(cont));

    }


    public void click(View v){

        for (int i = 0; i < tamLines; i++) {
            for (int j = 0; j < tamColumn; j++) {

                if (btn[i][j].equals(v)) {
                    if(swtFlag.isChecked() == false && swtQuestion.isChecked() == false && perdeu == false && btn[i][j].getText() == " ") {
                        if (camp.getIndex(i, j) == -1 && btn[i][j].getText() == " ") {
                            btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                            btn[i][j].setText("B");
                        } else if (camp.getIndex(i, j) == 0 && btn[i][j].getText() == " ") {
                            btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                            btn[i][j].setText("0");
                        } else if (camp.getIndex(i, j) == 1 && btn[i][j].getText() == " ") {
                            btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                            btn[i][j].setText("1");
                        } else if (camp.getIndex(i, j) == 2 && btn[i][j].getText() == " ") {
                            btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                            btn[i][j].setText("2");
                        } else if (camp.getIndex(i, j) == 3 && btn[i][j].getText() == " ") {
                            btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                            btn[i][j].setText("3");
                        } else if (camp.getIndex(i, j) == 4 && btn[i][j].getText() == " ") {
                            btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                            btn[i][j].setText("4");
                        } else if (camp.getIndex(i, j) == 5 && btn[i][j].getText() == " ") {
                            btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                            btn[i][j].setText("5");
                        } else if (camp.getIndex(i, j) == 6 && btn[i][j].getText() == " ") {
                            btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                            btn[i][j].setText("6");
                        } else if (camp.getIndex(i, j) == 7 && btn[i][j].getText() == " ") {
                            btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                            btn[i][j].setText("7");
                        } else if (camp.getIndex(i, j) == 8 && btn[i][j].getText() == " ") {
                            btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                            btn[i][j].setText("8");
                        }

                        contClick();

                    }else if(swtFlag.isChecked() == true && swtQuestion.isChecked() == false && perdeu == false){
                        if (btn[i][j].getText() == " ") {

                            btn[i][j].setText(".");
                            btn[i][j].setIcon(getDrawable(bandeirinha));
                            btn[i][j].setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_START);
                            btn[i][j].setTextColor(Color.TRANSPARENT);

                            contClick();

                        }else if(btn[i][j].getText() == "."){

                            btn[i][j].setText(" ");
                            btn[i][j].setIcon(null);
                            btn[i][j].setTextColor(Color.WHITE);

                            if((i+j) % 2 != 0)
                                btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.CYAN));
                            else
                                btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));

                            contClick();

                        }

                    }else if(swtQuestion.isChecked() == true && swtFlag.isChecked() == false && perdeu == false){

                        if (btn[i][j].getText() == " ") {

                            btn[i][j].setText(",");
                            btn[i][j].setCompoundDrawablesWithIntrinsicBounds(getDrawable(question), null, null, null);
                            btn[i][j].setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_START);
                            btn[i][j].setTextColor(Color.TRANSPARENT);

                            contClick();

                        }else if(btn[i][j].getText() == ","){

                            btn[i][j].setText(" ");
                            btn[i][j].setTextColor(Color.WHITE);
                            btn[i][j].setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                            if((i+j) % 2 != 0)
                                btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.CYAN));
                            else
                                btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));

                            contClick();

                        }
                    }
                }

                if(btn[i][j].getText().equals("B")) {
                    btn[i][j].setText(" ");
                    btn[i][j].setIcon(getDrawable(bomb));
                    btn[i][j].setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_START);
                    Toast.makeText(EasyModeActivity.this, "Perdeu!!!", Toast.LENGTH_SHORT).show();
                    perdeu = true;
                    liberarTudo();
                }

                if(btn[i][j].getText() == "0" && perdeu == false){
                    liberarvisinhos(i,j);
                }

                if(btnReset.equals(v)){
                    perdeu = false;
                    resetarJogo();
                }
            }
        }


        swtFlag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(swtFlag.isChecked() == true){
                    swtQuestion.setChecked(false);
                }
            }
        });


        swtQuestion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(swtQuestion.isChecked() == true){
                    swtFlag.setChecked(false);
                }
            }
        });

    }

    public void resetarJogo() {

        rounded = (int) Math.round(time);
        tempoJogo.setText("0:0:0");
        cont = 0;
        qtdCLicks.setText("0");

        camp = new CampoMinado(tamLines,tamColumn);

        for(int i = 0; i < tamLines; i++) {
            for(int j = 0; j < tamColumn; j++) {

                btn[i][j].setText(" ");
                btn[i][j].setTextSize(14);
                btn[i][j].setTextColor(Color.WHITE);
                btn[i][j].setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                btn[i][j].setIcon(null);

                if((i+j) % 2 != 0) {
                    btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.CYAN));
                }else {
                    btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
                }
            }
        }

        swtQuestion.setChecked(false);
        swtFlag.setChecked(false);

    }

    public void liberarTudo(){
        for(int i = 0; i < tamLines; i++) {
            for(int j = 0; j < tamColumn; j++) {


                if(camp.getIndex(i,j) != -1) {
                    btn[i][j].setText(String.valueOf(camp.getIndex(i, j)));
                    btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                }else {
                    btn[i][j].setCompoundDrawablesWithIntrinsicBounds(getDrawable(bomb), null, null, null);
                    btn[i][j].setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_START);
                    btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
            }
        }
    }


    public void liberarvisinhos(int i , int j){

        try{

            if (btn[i][j].getText() == " " || btn[i][j].getText() == "0") {
                btn[i][j].setText(String.valueOf(camp.getIndex(i,j)));
                btn[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));


                if(camp.getIndex(i,j) == 0){
                    btn[i][j].setText("   ");
                    liberarvisinhos(i+1,j);
                    liberarvisinhos(i-1,j);
                    liberarvisinhos(i,j+1);
                    liberarvisinhos(i,j-1);
                    liberarvisinhos(i+1,j+1);
                    liberarvisinhos(i+1,j-1);
                    liberarvisinhos(i-1,j+1);
                    liberarvisinhos(i-1,j-1);
                }
            }


        }catch(ArrayIndexOutOfBoundsException e){

        }

    }

}