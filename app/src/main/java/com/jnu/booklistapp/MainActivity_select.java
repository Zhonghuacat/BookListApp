package com.jnu.booklistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity_select extends AppCompatActivity {

    private Intent intent;
    private RecyclerView recyclerView;
    private BookAdapter2 adapter;
    private EditText editText_select;
    private ImageView imageView_no;
    private TextView textView_select;
    private List<Book> books_all = new ArrayList<>();
    private List<Book> books_selected = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_select);
        initView();
        loadData();
        intent = getIntent();
        String str = intent.getStringExtra("str");
        setSelectedResult(str);

    }

    private void setSelectedResult(String str) {
        books_selected.clear();
        if (!books_all.isEmpty()){
            for (Book book :books_all) {
                if (book.getName().contains(str)){
                    books_selected.add(book);
                }
            }
        }
        if (books_selected.isEmpty()){
            Book book = new Book(R.drawable.no_book,"没有找到对应的图书","");
            book.setAuthors("");book.setLike(false);
            books_selected.add(book);
        }
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new BookAdapter2(books_selected,this);
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        recyclerView =(RecyclerView) findViewById(R.id.recyclerView_select2);
        editText_select = findViewById(R.id.edittext_select2);
        imageView_no = findViewById(R.id.imageview_select2);
        textView_select = findViewById(R.id.textview_select2);

        imageView_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_select.setText("");
            }
        });

        editText_select.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    imageView_no.setVisibility(View.GONE);
                }else{
                    imageView_no.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        textView_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_select.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"请输入搜索内容",Toast.LENGTH_LONG).show();
                }else{
                    //修改列表并通知
                    setSelectedResult(editText_select.getText().toString().trim());
                }
            }
        });
    }

    private void loadData(){
        ObjectInputStream inputStream = null;
        try{
            FileInputStream fin =openFileInput("data.txt");
            inputStream = new ObjectInputStream(fin);
            books_all = (List<Book>)inputStream.readObject();
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