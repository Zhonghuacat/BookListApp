package com.jnu.booklistapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMine#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMine extends Fragment {

    LinearLayout linearLayout_time;
    LinearLayout linearLayout_like;
    private Context context;

    public FragmentMine() {
        // Required empty public constructor
    }


    public static FragmentMine newInstance() {
        FragmentMine fragment = new FragmentMine();
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
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        linearLayout_time = view.findViewById(R.id.linearLayout_time);
        linearLayout_like = view.findViewById(R.id.linearLayout_like);
        context = getActivity();
        linearLayout_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity_time.class);
                startActivity(intent);
            }
        });
        linearLayout_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity_like.class);
                startActivity(intent);
            }
        });

        return view;
    }
}