package com.jnu.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity_add extends AppCompatActivity {
    public int resultCode = 666;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        EditText editText = this.findViewById(R.id.editText2_id);
        Button button_yes = this.findViewById(R.id.button2_yes_id);
        Button button_no = this.findViewById(R.id.button2_no_id);

        if (intent.getStringExtra("book_name")!=null){
            resultCode = 888;
            editText.setText(intent.getStringExtra("book_name"));
        }

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = String.valueOf(editText.getText());
                intent.putExtra("position",position);
                intent.putExtra("data",str);
                setResult(resultCode,intent);
                finish();
            }
        });
        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}