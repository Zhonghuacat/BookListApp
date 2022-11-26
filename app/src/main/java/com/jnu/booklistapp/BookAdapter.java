package com.jnu.booklistapp;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyHolder> {

    private int position;
    private List<Book> mBookList;
    private Context mContext;

    public int getContextMenuPosition() { return position; }
    public void setContextMenuPosition(int position) { this.position = position; }

    //构造函数初始化数据
    public BookAdapter(List<Book>mBookList, Context mContext){
        this.mBookList=mBookList;
        this.mContext=mContext;
    }

    //创建Holder时候将去绑定一个Item
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    //给Holder的Item的View绑定值
    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        Book book=mBookList.get(position);
        holder.bookName.setText(book.getName());
        holder.bookImage.setImageResource(book.getImageId());
        holder.bookTag.setText(book.getTag());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setContextMenuPosition(holder.getLayoutPosition());
                return false;
            }
        });
    }

    //获取数据的量，以加载视图
    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    //用Holder找到Item的中的view
    class MyHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        private TextView bookName;
        private ImageView bookImage;
        private TextView bookTag;
        public MyHolder(View view) {
            super(view);
            bookImage=view.findViewById(R.id.imageView);
            bookName=view.findViewById(R.id.textView_name);
            bookTag=view.findViewById(R.id.textView_tag);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            Book book = mBookList.get(getContextMenuPosition());
            Log.i("Adapter", "onCreateContextMenu: "+getContextMenuPosition());
            menu.setHeaderTitle(book.getName());
            ((FragmentBookList)(((MainActivity)mContext).fragmentBookList)).CreateMenu(menu);
        }
    }
}
