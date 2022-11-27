package com.jnu.booklistapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapterLike extends RecyclerView.Adapter<BookAdapterLike.MyHolder>{
    private int position;
    private List<Book> mBookList;
    private Context mContext;

    public int getContextMenuPosition() { return position; }
    public void setContextMenuPosition(int position) { this.position = position; }

    //构造函数初始化数据
    public BookAdapterLike(List<Book>mBookList, Context mContext){
        this.mBookList=mBookList;
        this.mContext=mContext;
    }

    //创建Holder时候将去绑定一个Item
    @Override
    public BookAdapterLike.MyHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        BookAdapterLike.MyHolder holder=new BookAdapterLike.MyHolder(view);
        return holder;
    }

    //给Holder的Item的View绑定值
    @Override
    public void onBindViewHolder(@NonNull final BookAdapterLike.MyHolder holder, int position) {
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContextMenuPosition(holder.getLayoutPosition());
                Intent intent = new Intent(mContext,MainActivity_detail.class);
                intent.putExtra("bookId",book.getBookId());
                intent.putExtra("name",book.getName());
                intent.putExtra("tag",book.getTag());
                intent.putExtra("author",book.getAuthors());
                intent.putExtra("imageId",book.getImageId());
                intent.putExtra("isLike",book.isLike());
//                mContext.startActivity(intent);
                ((MainActivity_like)mContext).test_like.launch(intent);
            }
        });


    }

    //获取数据的量，以加载视图
    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    //用Holder找到Item的中的view
    class MyHolder extends RecyclerView.ViewHolder{
        private TextView bookName;
        private ImageView bookImage;
        private TextView bookTag;
        public MyHolder(View view) {
            super(view);
            bookImage=view.findViewById(R.id.imageView);
            bookName=view.findViewById(R.id.textView_name);
            bookTag=view.findViewById(R.id.textView_tag);
        }

    }
}
