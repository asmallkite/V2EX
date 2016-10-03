package com.example.a10648.v2ex.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.model.JtopicModel;
import com.example.a10648.v2ex.widget.SelectorImageView;


import java.util.List;

/**
 * Created by 10648 on 2016/7/19 0019.
 */
public class JRecycleViewAdapter extends RecyclerView.Adapter<JViewHolder> implements View.OnClickListener {

    private List<JtopicModel> jtopicModelList;
    private Context context;


    public JRecycleViewAdapter(List<JtopicModel> linkList, Context context) {
        this.jtopicModelList = linkList;
        this.context = context;
    }

    private JOnRecycleViewItemClickListener mOnItemClickListener = null;

    public void setmOnItemClickListener(JOnRecycleViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override
    public JViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View a_view = LayoutInflater.from(context).inflate(R.layout.jsoup_item1_layout, parent, false);
        JViewHolder jViewHolder = new JViewHolder(a_view);
        a_view.setOnClickListener(this);
        return jViewHolder;
    }

    @Override
    public void onBindViewHolder(final JViewHolder holder, int position) {

        Glide.with(context).load(jtopicModelList.get(position).getJavatar()).into(holder.avatar_iv);

        holder.title_tv.setText(jtopicModelList.get(position).getJtitle());
        holder.node_title_tv.setText(jtopicModelList.get(position).getJnodename());
        holder.user_name_tv.setText(jtopicModelList.get(position).getJusername());
        holder.created_tv.setText(jtopicModelList.get(position).getJcreated());
        holder.replies_tv.setText(jtopicModelList.get(position).getJreplies());

        holder.itemView.setTag(jtopicModelList.get(position));

    }

    @Override
    public int getItemCount() {
        return jtopicModelList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (JtopicModel)v.getTag());
        }
    }


    public  interface JOnRecycleViewItemClickListener {
        void onItemClick(View view, JtopicModel data);
    }
}

class JViewHolder extends RecyclerView.ViewHolder {

    SelectorImageView avatar_iv;
    TextView title_tv;
    TextView node_title_tv;
    TextView user_name_tv;
    TextView created_tv;
    TextView replies_tv;


    public JViewHolder(View itemView) {
        super(itemView);
        avatar_iv = (SelectorImageView) itemView.findViewById(R.id.avatar);
        title_tv = (TextView) itemView.findViewById(R.id.title);
        node_title_tv = (TextView)itemView.findViewById(R.id.node_title);
        user_name_tv = (TextView)itemView.findViewById(R.id.name);
        created_tv = (TextView)itemView.findViewById(R.id.create);
        replies_tv = (TextView)itemView.findViewById(R.id.replies);
    }
}
