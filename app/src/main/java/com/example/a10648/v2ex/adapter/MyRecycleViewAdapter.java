package com.example.a10648.v2ex.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a10648.v2ex.R;

import java.util.List;

/**
 * Created by 10648 on 2016/7/12 0012.
 */
public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<String> itemlist; //recycleview items的数组
    private Context context;

    public MyRecycleViewAdapter(List<String> itemlist, Context context) {
        this.itemlist = itemlist;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(context).inflate(R.layout.recycleview_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mtv.setText(itemlist.get(position));



    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder {
    TextView mtv;

    public MyViewHolder(CardView itemView) {
        super(itemView);
       mtv = (TextView) itemView.findViewById(R.id.RV_item_text);

    }
}
