package com.lgj.sogyo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FranciseAdapter extends RecyclerView.Adapter<FranciseAdapter.ViewHolder> {

    public ArrayList<Francise_item> mData = null;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ctg;
        TextView cost;
        TextView store;
        ViewHolder(View itemView){
            super(itemView);
            ctg = itemView.findViewById(R.id.ctg);
            cost = itemView.findViewById(R.id.cost);
            store = itemView.findViewById(R.id.store);
        }
    }

    //생성자
    FranciseAdapter(ArrayList<Francise_item> list) {
        mData = list;
    }
    @Override
    public FranciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.rv_item_list,parent,false);
        FranciseAdapter.ViewHolder vh = new FranciseAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(FranciseAdapter.ViewHolder holder, int position){
        holder.cost.setText(mData.get(position).getCost());
        holder.ctg.setText(mData.get(position).getCtg());
        holder.store.setText(mData.get(position).getStore());
    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}
