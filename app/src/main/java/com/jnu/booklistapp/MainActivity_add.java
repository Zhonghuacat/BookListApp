package com.jnu.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_add extends AppCompatActivity {

    public int resultCode = 666;
    private String[] tagList=new String[]{
            "其他",
            "科技",
            "历史",
            "人文",
    };
    private String tag = tagList[0];;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        EditText editText = this.findViewById(R.id.editText2_id);
        Button button_yes = this.findViewById(R.id.button2_yes_id);
        Button button_no = this.findViewById(R.id.button2_no_id);
        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item ,tagList);
        spinner.setAdapter(adapter2);


        if (intent.getStringExtra("book_name")!=null){
            resultCode = 888;
            editText.setText(intent.getStringExtra("book_name"));
        }
        if (intent.getStringExtra("tag")!=null){
            resultCode = 888;
            tag=intent.getStringExtra("tag");
            for (int i=0;i<tagList.length;i++){
                if (tagList[i].equals(tag)){
                    spinner.setSelection(i);
                    break;
                }
            }
        }

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = String.valueOf(editText.getText());
                intent.putExtra("position",position);
                intent.putExtra("data",str);
                intent.putExtra("tag",tag);
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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tag = tagList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

}