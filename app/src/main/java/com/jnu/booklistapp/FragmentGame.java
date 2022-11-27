package com.jnu.booklistapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class FragmentGame extends Fragment {

    private Context context;
    private int score;
    private Button button;
    private TextView textView_score;

    public FragmentGame() {
        // Required empty public constructor
    }

    public static FragmentGame newInstance() {
        FragmentGame fragment = new FragmentGame();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        button = view.findViewById(R.id.button_start);
        textView_score = view.findViewById(R.id.textView_score);
        context = getActivity();
        loadData();
        textView_score.setText("最高分："+score);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity_game.class);
                intent.putExtra("score",score);
                test_game.launch(intent);
            }
        });
        return view;
    }

    public ActivityResultLauncher test_game = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 555){
                        score = result.getData().getIntExtra("score",0);
                        textView_score.setText("最高分："+score);
                        save();
                    }
                }
            });

    private void save(){
        ObjectOutputStream outputStream = null;
        try{
            FileOutputStream fout = context.openFileOutput("score.txt", MODE_PRIVATE);
            outputStream = new ObjectOutputStream(fout);
            outputStream.writeObject(score);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(outputStream != null)
                    outputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void loadData(){
        ObjectInputStream inputStream = null;
        try{
            FileInputStream fin = context.openFileInput("score.txt");
            inputStream = new ObjectInputStream(fin);
            score = (int)inputStream.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(inputStream != null)
                    inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}