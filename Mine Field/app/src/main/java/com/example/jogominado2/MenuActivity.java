package com.example.jogominado2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button btnEasyMode, btnMediumMode, btnCustomMode;
    private Intent intentEasyMode, intentMediumMode, intentSelectLines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnEasyMode = findViewById(R.id.btnEasyMode);
        btnMediumMode = findViewById(R.id.btnMediumMode);
        btnCustomMode = findViewById(R.id.btnPersonalizado);


}

    public void click(View v){

        intentEasyMode = new Intent(this, EasyModeActivity.class);
        intentMediumMode = new Intent(this,  MediumModeActivity.class);
        intentSelectLines = new Intent(this, SelectLinesCustomMode.class);

        if(btnEasyMode.equals(v)){
            startActivity(intentEasyMode);
        }

        if(btnMediumMode.equals(v)) {
            startActivity(intentMediumMode);

        }


        if(btnCustomMode.equals(v)){
            startActivity(intentSelectLines);
        }

    }

}
