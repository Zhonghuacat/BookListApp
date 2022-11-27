package com.jnu.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity_detail extends AppCompatActivity {

    private List<Book> mBookList=new ArrayList<Book>();
    Intent intent;
    private int resultCode=111;
    private Book book;
    private long bookId;
    private String name;
    private String tag;
    private String author;
    private int imageId;
    private boolean isLike;

    private TextView textView_name;
    private TextView textView_tag;
    private TextView textView_author;
    private ImageView imageView;
    private ImageButton imageButton_like;
    private ImageButton imageButton_disLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);

        intent = getIntent();
        bookId = intent.getLongExtra("bookId",0);
        name = intent.getStringExtra("name");
        tag = intent.getStringExtra("tag");
        author = intent.getStringExtra("author");
        imageId = intent.getIntExtra("imageId",0);
        isLike = intent.getBooleanExtra("isLike",false);

        loadData();
//        book = new Book(imageId,name,tag);
//        book.setBookId(bookId);
//        book.setAuthors(author);
//        book.setLike(isLike);

        textView_name = findViewById(R.id.textView_detail_name);
        textView_tag = findViewById(R.id.textView_detail_tag);
        textView_author = findViewById(R.id.textView_detail_author);
        imageView = findViewById(R.id.imageView_detail);
        imageButton_like = findViewById(R.id.imageButton_like);
        imageButton_disLike = findViewById(R.id.imageButton_dislike);

        textView_name.setText(name);
        textView_tag.setText(tag);
        textView_author.setText(author);
        imageView.setImageResource(imageId);
        if (isLike){
            imageButton_like.setVisibility(View.VISIBLE);
            imageButton_disLike.setVisibility(View.GONE);
        }else{
            imageButton_like.setVisibility(View.GONE);
            imageButton_disLike.setVisibility(View.VISIBLE);
        }

        imageButton_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLike=false;
                imageButton_like.setVisibility(View.GONE);
                imageButton_disLike.setVisibility(View.VISIBLE);
            }
        });
        imageButton_disLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLike=true;
                imageButton_like.setVisibility(View.VISIBLE);
                imageButton_disLike.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            for (Book book : mBookList) {
                if (book.getBookId()==bookId&&book.getName().equals(name)
                        &&book.getTag().equals(tag)&&book.getAuthors().equals(author)&&book.getImageId()==imageId){
                    book.setLike(isLike);
                    int position = mBookList.indexOf(book);
                    intent.putExtra("position",position);
                    intent.putExtra("isLike",isLike);
                    setResult(resultCode,intent);
//                    mBookAdapter.notifyAll();
                    save();
                    break;
                }
            }
            finish();
        }
        return true;
    }


    private void loadData(){
        ObjectInputStream inputStream = null;
        try{
            FileInputStream fin =openFileInput("data.txt");
            inputStream = new ObjectInputStream(fin);
            mBookList = (List<Book>)inputStream.readObject();
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

    private void save(){
        ObjectOutputStream outputStream = null;
        try{
            FileOutputStream fout =openFileOutput("data.txt", MODE_PRIVATE);
            outputStream = new ObjectOutputStream(fout);
            outputStream.writeObject(mBookList);
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

}