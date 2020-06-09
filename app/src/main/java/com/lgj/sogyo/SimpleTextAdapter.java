package com.lgj.sogyo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {

    private ArrayList<Store> mData = null;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView BizName;
        TextView category;
        TextView floor;
        TextView IsOpen;
        TextView openYear;
        TextView closeYear;

        ViewHolder(View itemView){
            super(itemView);
            BizName = itemView.findViewById(R.id.BizName);
            category = itemView.findViewById(R.id.category);
            floor = itemView.findViewById(R.id.floor);
            IsOpen = itemView.findViewById(R.id.IsOpen);
            openYear = itemView.findViewById(R.id.openYear);
            closeYear = itemView.findViewById(R.id.closeYear);
        }
    }

    //생성자
    SimpleTextAdapter (ArrayList<Store> list){
        mData=list;
    }

    @Override
    public SimpleTextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_list,parent,false);
        SimpleTextAdapter.ViewHolder vh = new SimpleTextAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(SimpleTextAdapter.ViewHolder holder, int position){
        holder.BizName.setText(mData.get(position).BizName);
        holder.category.setText(mData.get(position).upperCategory);
        holder.floor.setText(mData.get(position).floor);
        holder.IsOpen.setText(mData.get(position).IsOpenStr);
        holder.openYear.setText(mData.get(position).openYear);
        holder.closeYear.setText(mData.get(position).closeYear);

    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}
