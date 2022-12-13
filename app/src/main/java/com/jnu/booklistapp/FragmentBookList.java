package com.jnu.booklistapp;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class FragmentBookList extends Fragment {

    private List<Book> mBookList = new ArrayList<>();
    private RecyclerView recyclerView;
    public BookAdapter mBookAdapter;
    private Context context;
    private EditText editText_select;
    private ImageView imageView_no;
    private TextView textView_select;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_book_list, container, false);
        initView(view);

        context=getActivity();
        setRecyclerView();
        return view;
    }

    private void initView(View view) {
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView_id);
        editText_select = view.findViewById(R.id.edittext_select);
        imageView_no = view.findViewById(R.id.imageview_select);
        textView_select = view.findViewById(R.id.textview_select);

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
                    Toast.makeText(context,"请输入搜索内容",Toast.LENGTH_LONG).show();
                }else{
                    Intent intent =new Intent(context,MainActivity_select.class);
                    intent.putExtra("str",editText_select.getText().toString().trim());
                    startActivity(intent);
                }
            }
        });


    }

    private void setRecyclerView(){
        loadData();
        if (mBookList.isEmpty()){
            initBook();
        }
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mBookAdapter=new BookAdapter(mBookList,context);
        recyclerView.setAdapter(mBookAdapter);
    }

    public FragmentBookList() {

    }

    public static FragmentBookList newInstance() {
        FragmentBookList fragment = new FragmentBookList();
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
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 1:
                Intent intent=new Intent(context,MainActivity_add.class);
                intent.putExtra("position",mBookAdapter.getContextMenuPosition());
                test.launch(intent);
                break;
            case 2:
                Intent intent2=new Intent(context,MainActivity_add.class);
                intent2.putExtra("book_name",mBookList.get(mBookAdapter.getContextMenuPosition()).getName());
                intent2.putExtra("position",mBookAdapter.getContextMenuPosition());
                intent2.putExtra("tag",mBookList.get(mBookAdapter.getContextMenuPosition()).getTag());
                intent2.putExtra("author",mBookList.get(mBookAdapter.getContextMenuPosition()).getAuthors());
                test.launch(intent2);
                break;
            case 3:
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setTitle("DELETE").setMessage("是否确定要删除")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mBookList.remove(mBookAdapter.getContextMenuPosition());
                                mBookAdapter.notifyItemRemoved(mBookAdapter.getContextMenuPosition());
                                save();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {}
                        }).create();
                alertDialog.show();
                break;
            default:break;
        }
        return super.onContextItemSelected(item);
    }

    public void CreateMenu(Menu menu){
        MenuItem star=menu.add(Menu.NONE,1,1,"ADD");
        MenuItem edit=menu.add(Menu.NONE,2,2,"Edit");
        MenuItem delete=menu.add(Menu.NONE,3,3,"Delete");
    }

    private void initBook() {
        for(int i=0;i<5;i++){
            Book book01=new Book(R.drawable.book_1,"信息安全数学基础（第2版）","科技");
            book01.setLike(false);book01.setAuthors("无");book01.setTag("科技");
            Book book02=new Book(R.drawable.book_2,"软件项目管理案例教程（第4版）","科技");
            book02.setLike(false);book02.setAuthors("无");book02.setTag("科技");
            Book book03=new Book(R.drawable.book_no_name,"创新工程实践","科技");
            book03.setLike(false);book03.setAuthors("无");book03.setTag("科技");
            mBookList.add(book01);
            mBookList.add(book02);
            mBookList.add(book03);
        }
    }

    //启动修改添加页面的
    public ActivityResultLauncher test = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 666){
                        int position = result.getData().getIntExtra("position",0);
                        String name = result.getData().getStringExtra("data");
                        String tag = result.getData().getStringExtra("tag");
                        String author = result.getData().getStringExtra("author");
                        Book book = new Book(R.drawable.book_no_name,name,tag);
                        book.setAuthors(author);
                        mBookList.add(position,book);
                        mBookAdapter.notifyItemInserted(position);
                    }
                    if (result.getResultCode() == 888){
                        int position = result.getData().getIntExtra("position",0);
                        String name = result.getData().getStringExtra("data");
                        String tag = result.getData().getStringExtra("tag");
                        String author = result.getData().getStringExtra("author");
                        mBookList.get(position).setTag(tag);
                        mBookList.get(position).setName(name);
                        mBookList.get(position).setAuthors(author);
                        mBookAdapter.notifyItemChanged(position);
                    }
                    save();
                }
            });

    //启动详细页面的
    public ActivityResultLauncher test2 = registerForActivityResult(
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

    private void save(){
        ObjectOutputStream outputStream = null;
        try{
            FileOutputStream fout =context.openFileOutput("data.txt", MODE_PRIVATE);
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

    private void loadData(){
        ObjectInputStream inputStream = null;
        try{
            FileInputStream fin =context.openFileInput("data.txt");
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
}