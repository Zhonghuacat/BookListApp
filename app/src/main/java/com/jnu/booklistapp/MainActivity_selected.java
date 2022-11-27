package com.jnu.booklistapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity_selected extends AppCompatActivity {

    private List<Book> mBookList=new ArrayList<Book>();
    private List<Book> mBookList_selected=new ArrayList<Book>();
    private String tag;
    private RecyclerView recyclerView;
    private BookAdapterClassify mBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_selected);
        loadData();

        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        if (tag.equals("其他")){
            String[] tags = new String[]{"科技","历史","人文"};
            selectByNoTag(tags);
        }else{
            selectByTag(tag);
        }

        recyclerView=(RecyclerView)findViewById(R.id.recycler_selected);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mBookAdapter=new BookAdapterClassify(mBookList_selected,this);
        recyclerView.setAdapter(mBookAdapter);

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

    private void selectByTag(String tag) {
        mBookList_selected.clear();
        for (Book book : mBookList) {
            if (book.getTag().equals(tag)){
                mBookList_selected.add(book);
            }
        }
    }

    private void selectByNoTag(String[] tags) {
        mBookList_selected.clear();
        for (Book book : mBookList) {
            mBookList_selected.add(book);
            for (int i=0;i<tags.length;i++){
                if (book.getTag().equals(tags[i])){
                    mBookList_selected.remove(book);break;
                }
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

    public ActivityResultLauncher test_classify = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    Boolean isLike = intent.getBooleanExtra("isLike",false);
                    int position = intent.getIntExtra("position",0);
                    mBookList.get(position).setLike(isLike);
                    mBookAdapter.notifyItemChanged(position);
                    save();
                }
            });

}