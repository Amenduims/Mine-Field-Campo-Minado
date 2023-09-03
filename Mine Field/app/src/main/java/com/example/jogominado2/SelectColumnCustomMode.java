
package com.example.jogominado2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectColumnCustomMode extends AppCompatActivity {

    private Button btn[];
    private int tamLines, tamColumn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_column_custom_mode);

        tamLines = getIntent().getExtras().getInt("tamLines");
        btn = new Button[6];

        btn[0] = findViewById(R.id.btn1);
        btn[1] = findViewById(R.id.btn2);
        btn[2] = findViewById(R.id.btn3);
        btn[3] = findViewById(R.id.btn4);
        btn[4] = findViewById(R.id.btn5);
        btn[5] = findViewById(R.id.btn6);


        for(int i = 0; i < 6; i++){
            btn[i].setText(String.valueOf(i+5));
        }

    }

   public void click(View v){

        for (int i = 0; i < 6; i++){
            if(btn[i].equals(v)){
               tamColumn = Integer.parseInt((String) btn[i].getText());
               Intent customGame = new Intent(this, CustomMode.class);
               customGame.putExtra("tamColumn", tamColumn);
               customGame.putExtra("tamLines", tamLines);
               startActivity(customGame);
            }

        }
    }

}