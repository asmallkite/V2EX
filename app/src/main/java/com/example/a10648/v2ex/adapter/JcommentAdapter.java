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
import com.example.a10648.v2ex.model.CommentModel;
import com.example.a10648.v2ex.widget.SelectorImageView;

import java.util.List;

/**
 * Created by 10648 on 2016/7/20 0020.
 */
public class JcommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    List<CommentModel> commentModelList ;
    Context context;


    public JcommentAdapter(List<CommentModel> commentModelList, Context context) {
        this.commentModelList = commentModelList;
        this.context = context;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jsoup_comment_layout, parent, false);
        CommentViewHolder commentViewHolder = new CommentViewHolder(view);
        return commentViewHolder;

    }

    @Override
    public void onBindViewHolder(final CommentViewHolder holder, int position) {

        Glide.with(context).load(commentModelList.get(position).getAvatar()).into(holder.avatar);
        holder.name.setText(commentModelList.get(position).getName());
        holder.create.setText(commentModelList.get(position).getCreate());
        holder.comment.setText(commentModelList.get(position).getComment());
        holder.replies.setText(commentModelList.get(position).getReplies());
    }

    @Override
    public int getItemCount() {
        return commentModelList.size();
    }
}

class CommentViewHolder extends RecyclerView.ViewHolder {

    SelectorImageView avatar;
    TextView name ;
    TextView create;
    TextView comment;
    TextView replies;

    public CommentViewHolder(View itemView) {
        super(itemView);
        avatar = (SelectorImageView) itemView.findViewById(R.id.avatar);
        name = (TextView) itemView.findViewById(R.id.name);
        create = (TextView) itemView.findViewById(R.id.create);
        comment = (TextView) itemView.findViewById(R.id.comment);
        replies = (TextView)itemView.findViewById(R.id.replies);
    }
}