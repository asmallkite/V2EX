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

import java.util.List;

/**
 * Created by 10648 on 2016/7/10 0010.
 */
public class MyRecyclerViewAdapter2 extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener{


    private List<String> linkList;
    private Context context;


    public MyRecyclerViewAdapter2(List<String> linkList, Context context) {
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
        holder.mTv.setText(linkList.get(position));
        holder.itemView.setTag(linkList.get(position));

    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (String)v.getTag());
        }
    }


    public static interface OnRecycleViewItemClickListener {
        void onItemClick(View view, String data);
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{
    TextView mTv;

    public MyViewHolder(View itemView) {
        super(itemView);
        mTv = (TextView)itemView.findViewById(R.id.RV_item_text);
//        ButterKnife.inject(this, itemView); //实现效果同上,只有这一句话是不行的，会报空指针异常


        //下面几行真正实现了RecycleView 中的点击事件
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MyRecycleViewAdapter", "you click the item");
//                Toast.makeText(get, "you click the item", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("www.baidu.com"));


            }
        });


    }

}