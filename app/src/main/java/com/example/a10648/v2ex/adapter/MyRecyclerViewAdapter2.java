package com.example.a10648.v2ex.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.model.TopicModel;

import java.util.List;

/**
 * Created by 10648 on 2016/7/10 0010.
 */
public class MyRecyclerViewAdapter2 extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener{


    private List<TopicModel> linkList;
    private Context context;


    public MyRecyclerViewAdapter2(List<TopicModel> linkList, Context context) {
        this.linkList = linkList;
        this.context = context;
    }

    private OnRecycleViewItemClickListener mOnItemClickListener = null;

    public void setmOnItemClickListener(OnRecycleViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.topic_title.setText(linkList.get(position).getTitle());
        holder.topic_content.setText(linkList.get(position).getContent());
        holder.itemView.setTag(linkList.get(position));

    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (TopicModel)v.getTag());
        }
    }


    public static interface OnRecycleViewItemClickListener {
        void onItemClick(View view, TopicModel data);
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{
    TextView topic_title;
    TextView topic_content;

    public MyViewHolder(View itemView) {
        super(itemView);
        topic_title = (TextView)itemView.findViewById(R.id.txt_view_topic_title);
        topic_content = (TextView) itemView.findViewById(R.id.txt_view_topic_content);


        //下面几行真正实现了RecycleView 中的点击事件
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MyRecycleViewAdapter", "you click the item");

            }
        });


    }

}