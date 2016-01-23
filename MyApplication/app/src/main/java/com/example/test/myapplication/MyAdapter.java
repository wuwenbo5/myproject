package com.example.test.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by admin on 2015/11/17.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private String[] mSetData;
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;
        private ImageView iv;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }

    public MyAdapter(String[] mSetData) {
        this.mSetData = mSetData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);

        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv.setText(mSetData[position]);
        holder.iv.setImageResource(R.mipmap.ic_launcher);

    }

    @Override
    public int getItemCount() {
        return mSetData.length;
    }
}
