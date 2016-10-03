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
import com.example.a10648.v2ex.model.Node;
import com.example.a10648.v2ex.widget.SelectorImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10648 on 2016/7/24 0024.
 * 所有节点的adapter
 */
public class NodeAdapter extends RecyclerView.Adapter<NodeViewHolder>  implements View.OnClickListener{

    private List<Node> nodeList = new ArrayList<>();
    private Context context;

    public NodeAdapter(List<Node> nodeList, Context context) {
        this.nodeList = nodeList;
        this.context = context;
    }


    private OnRecycleViewItemClickListener mOnItemClickListener = null;

    public void setmOnItemClickListener(OnRecycleViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public NodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_node, parent, false);
        NodeViewHolder myViewHolder = new NodeViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final NodeViewHolder holder, int position) {
        Glide.with(context).load(nodeList.get(position).getAvatar_normal()).into(holder.avatar_normal);
        holder.title.setText(nodeList.get(position).getTitle());
        holder.header.setText(nodeList.get(position).getHeader());
        holder.topics.setText(nodeList.get(position).getTopics() + "  个主题");
        holder.itemView.setTag(nodeList.get(position));

    }

    @Override
    public int getItemCount() {
        return nodeList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (Node) v.getTag());
        }
    }

    public  interface OnRecycleViewItemClickListener {
        void onItemClick(View view, Node data);
    }
}
class NodeViewHolder extends RecyclerView.ViewHolder {

    SelectorImageView avatar_normal;
    TextView title;
    TextView header;
    TextView topics;
    public NodeViewHolder(View itemView) {
        super(itemView);
        avatar_normal = (SelectorImageView) itemView.findViewById(R.id.avatar_normal);
        title  = (TextView) itemView.findViewById(R.id.title_a);
        header = (TextView) itemView.findViewById(R.id.header);
        topics = (TextView) itemView.findViewById(R.id.topics);
    }
}