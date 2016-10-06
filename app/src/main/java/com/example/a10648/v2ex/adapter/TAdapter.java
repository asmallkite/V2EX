package com.example.a10648.v2ex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.model.TModel;
import com.example.a10648.v2ex.model.TopicModel;
import com.example.a10648.v2ex.widget.RelativeTimeTextView;
import com.example.a10648.v2ex.widget.SelectorImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10648 on 2016/10/3 0003.
 */

public class TAdapter extends RecyclerView.Adapter<THolder> implements View.OnClickListener{


    private List<TModel> linkList = new ArrayList<>();
    private Context context;


    public TAdapter(List<TModel> linkList, Context context) {
        this.linkList = linkList;
        this.context = context;
    }

    private OnRecycleViewItemClickListener mOnItemClickListener = null;

    public void setmOnItemClickListener(OnRecycleViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public THolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_item, parent, false);
        THolder myViewHolder = new THolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final THolder holder, int position) {
        holder.topic_title.setText(linkList.get(position).getTitle());
//        holder.topic_title.setText("lizeheng");
        holder.topic_content.setText(linkList.get(position).getContent());

        Glide.with(context).load("https:" + linkList.get(position).getMember().getAvatar_large()).into(holder.img_view_topic_head);
        Log.d("lizheng", linkList.get(position).getMember().getAvatar_large());
        holder.txt_view_topic_name.setText(linkList.get(position).getMember().getUsername());
        Log.d("lizheng", linkList.get(position).getMember().getUsername());
        holder.txt_view_topic_node.setText(linkList.get(position).getNode().getName());



        holder.txt_view_topic_time.setReferenceTime(linkList.get(position).getCreated() * 1000);
        holder.txt_view_topic_replies.setText(String.valueOf(linkList.get(position).getReplies()) + "条 回复");
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

    public void clearData() {
        linkList.clear();
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<TModel> models) {
        linkList.addAll(models);
        notifyDataSetChanged();
    }

}

class THolder extends RecyclerView.ViewHolder {
    TextView topic_title;
    TextView topic_content;
    SelectorImageView img_view_topic_head;
    TextView txt_view_topic_name;
    RelativeTimeTextView txt_view_topic_time;
    TextView txt_view_topic_replies;
    TextView txt_view_topic_node;

    public THolder(View itemView) {
        super(itemView);
        topic_title = (TextView) itemView.findViewById(R.id.txt_view_topic_title);
        topic_content = (TextView) itemView.findViewById(R.id.txt_view_topic_content);
        img_view_topic_head = (SelectorImageView) itemView.findViewById(R.id.img_view_topic_head);
        txt_view_topic_name = (TextView) itemView.findViewById(R.id.txt_view_topic_name);
        txt_view_topic_time = (RelativeTimeTextView) itemView.findViewById(R.id.txt_view_topic_time);
        txt_view_topic_replies = (TextView) itemView.findViewById(R.id.txt_view_topic_replies);
        txt_view_topic_node = (TextView) itemView.findViewById(R.id.txt_view_topic_node);

    }
}

