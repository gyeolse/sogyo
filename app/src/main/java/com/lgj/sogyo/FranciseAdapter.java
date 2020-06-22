package com.lgj.sogyo.Judgement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.lgj.sogyo.R;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
public class FranciseAdapter extends RecyclerView.Adapter<FranciseAdapter.ViewHolder>{
    public ArrayList<Francise_item> rvViewDatalist;
    Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ctg;
        TextView store;
        TextView cost;

        ViewHolder(View itemView){
            super(itemView);
            ctg = itemView.findViewById(R.id.ctg);
            store = itemView.findViewById(R.id.store);
            cost = itemView.findViewById(R.id.cost);
        }
    }
    public FranciseAdapter (ArrayList<Francise_item> list){
        this.rvViewDatalist=list;
    }

    public FranciseAdapter(ArrayList<Francise_item> rvViewDatalist,Context mContext){
        this.rvViewDatalist=rvViewDatalist;
        this.mContext=mContext;
    }

    @Override
    public FranciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {


        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_list,parent,false);
        FranciseAdapter.ViewHolder vh = new FranciseAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(FranciseAdapter.ViewHolder holder, int position){
        System.out.println(position);

        holder.ctg.setText(rvViewDatalist.get(position).getCtg());
        holder.store.setText(rvViewDatalist.get(position).getStore());
        if(rvViewDatalist.get(position).getCost()== null){
            holder.cost.setText("");
        }
        else{
            holder.cost.setText(rvViewDatalist.get(position).getCost());
        }
    }

    @Override
    public int getItemCount() {
        return rvViewDatalist.size();
    }
}
