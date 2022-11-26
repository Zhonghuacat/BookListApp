package com.jnu.booklistapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentClassify#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentClassify extends Fragment {

    private ImageButton imageButton_science;
    private ImageButton imageButton_history;
    private ImageButton imageButton_culture;
    private ImageButton imageButton_other;
    private Context context;

    public FragmentClassify() {
        // Required empty public constructor
    }

    public static FragmentClassify newInstance() {
        FragmentClassify fragment = new FragmentClassify();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_classify, container, false);
        context = getActivity();
        initView(view);
        setListener();
        return view;
    }

    private void setListener() {
        imageButton_science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity_selected.class);
                intent.putExtra("tag", "科技");
                startActivity(intent);
            }
        });
        imageButton_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity_selected.class);
                intent.putExtra("tag", "历史");
                startActivity(intent);
            }
        });
        imageButton_culture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity_selected.class);
                intent.putExtra("tag", "人文");
                startActivity(intent);
            }
        });
        imageButton_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity_selected.class);
                intent.putExtra("tag", "其他");
                startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        imageButton_science = (ImageButton) view.findViewById(R.id.imageButton_science);
        imageButton_history = (ImageButton) view.findViewById(R.id.imageButton_history);
        imageButton_culture = (ImageButton) view.findViewById(R.id.imageButton_culture);
        imageButton_other = (ImageButton) view.findViewById(R.id.imageButton_other);

        setStyle(R.drawable.science,imageButton_science);
        setStyle(R.drawable.history,imageButton_history);
        setStyle(R.drawable.culture,imageButton_culture);
        setStyle(R.drawable.other,imageButton_other);
    }

    private void setStyle(int id, ImageButton imageButton) {
        Drawable drawable = context.getResources().getDrawable(id);
        drawable.setBounds(0,0,1,1);
        imageButton.setImageDrawable(drawable);
    }
}
