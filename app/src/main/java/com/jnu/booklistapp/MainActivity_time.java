package com.jnu.booklistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity_time extends AppCompatActivity {

    private List<Book> mBookList_time =new ArrayList<Book>();
    private RecyclerView recyclerView;
    private BookAdapter2 mBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_time);
        loadData();

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView_time);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mBookAdapter=new BookAdapter2(mBookList_time,this);
        recyclerView.setAdapter(mBookAdapter);

    }

    private void loadData(){
        ObjectInputStream inputStream = null;
        try{
            FileInputStream fin =openFileInput("dataTime.txt");
            inputStream = new ObjectInputStream(fin);
            mBookList_time = (List<Book>)inputStream.readObject();
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
            FileOutputStream fout =openFileOutput("dataTime.txt", MODE_PRIVATE);
            outputStream = new ObjectOutputStream(fout);
            outputStream.writeObject(mBookList_time);
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