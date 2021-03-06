package com.example.a10648.v2ex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.model.TopicModel;
import com.example.a10648.v2ex.widget.RelativeTimeTextView;
import com.example.a10648.v2ex.widget.SelectorImageView;
import java.util.List;

/**
 * Created by 李争 on 2016/7/10 0010.
 * RecyclerView的适配器
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
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.topic_title.setText(linkList.get(position).getTitle());
        holder.topic_content.setText(linkList.get(position).getContent());

        Glide.with(context).load(linkList.get(position).getAvatar()).into(holder.img_view_topic_head);
        holder.txt_view_topic_name.setText(linkList.get(position).getUsername());
        holder.txt_view_topic_time.setReferenceTime(linkList.get(position).getCreated() * 1000);
        holder.txt_view_topic_replies.setText(String.valueOf(linkList.get(position).getReplies()) + "条 回复");
        holder.txt_view_topic_node.setText(linkList.get(position).getNodename());
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


    public  interface OnRecycleViewItemClickListener {
        void onItemClick(View view, TopicModel data);
    }

}

class MyViewHolder extends RecyclerView.ViewHolder{
    TextView topic_title;
    TextView topic_content;
    SelectorImageView img_view_topic_head;
    TextView txt_view_topic_name;
    RelativeTimeTextView txt_view_topic_time;
    TextView txt_view_topic_replies;
    TextView txt_view_topic_node;

    public MyViewHolder(View itemView) {
        super(itemView);
        topic_title = (TextView)itemView.findViewById(R.id.txt_view_topic_title);
        topic_content = (TextView) itemView.findViewById(R.id.txt_view_topic_content);
        img_view_topic_head = (SelectorImageView)itemView.findViewById(R.id.img_view_topic_head);
        txt_view_topic_name = (TextView)itemView.findViewById(R.id.txt_view_topic_name);
        txt_view_topic_time = (RelativeTimeTextView)itemView.findViewById(R.id.txt_view_topic_time);
        txt_view_topic_replies = (TextView)itemView.findViewById(R.id.txt_view_topic_replies);
        txt_view_topic_node = (TextView)itemView.findViewById(R.id.txt_view_topic_node);
        //下面几行真正实现了RecycleView 中的点击事件
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("MyRecycleViewAdapter", "you click the item");
//
//            }
//        });
    }

}