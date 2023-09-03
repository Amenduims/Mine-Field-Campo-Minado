package com.example.jogominado2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectLinesCustomMode extends AppCompatActivity {
    public String num;
    Button btn[];
    private String texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lines_custom_mode);

        btn = new Button[11];

        btn[0] = findViewById(R.id.btn1);
        btn[1] = findViewById(R.id.btn2);
        btn[2] = findViewById(R.id.btn3);
        btn[3] = findViewById(R.id.btn4);
        btn[4] = findViewById(R.id.btn5);
        btn[5] = findViewById(R.id.btn6);
        btn[6] = findViewById(R.id.btn7);
        btn[7] = findViewById(R.id.btn8);
        btn[8] = findViewById(R.id.btn9);
        btn[9] = findViewById(R.id.btn10);
        btn[10] = findViewById(R.id.btn11);


        for(int i = 0; i < 11; i++){
            btn[i].setText(String.valueOf(i+5));
        }
    }

    public void click(View v){

        for (int i = 0; i < 11; i++){
            if(btn[i].equals(v)){
                Intent selectColumn = new Intent(this, SelectColumnCustomMode.class);
                selectColumn.putExtra("tamLines", Integer.parseInt((String) btn[i].getText()));
                startActivity(selectColumn);
            }
        }
    }

}