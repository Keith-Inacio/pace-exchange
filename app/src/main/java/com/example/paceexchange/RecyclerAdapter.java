package com.example.paceexchange;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter {

    Context mContext;
    List<InventoryData> mListData;
    View.OnClickListener mRowClickListener;

    public RecyclerAdapter(Context context, List<InventoryData> listData, View.OnClickListener rowClickListener){

        mContext=context;
        mListData=listData;
        mRowClickListener=rowClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.recycler_view, null);

        ViewHolder viewHolder = new ViewHolder(view, mRowClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        InventoryData inventoryDisplay = mListData.get(position);

        ViewHolder vHolder = (ViewHolder) viewHolder;

        vHolder.mItemImage.setImageResource(inventoryDisplay.getmImage());
        vHolder.mItemName.setText(inventoryDisplay.getmItemName());

    }


    @Override
    public int getItemCount() {

        return mListData.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView mItemImage;
        public TextView mItemName;

        public ViewHolder(@NonNull View itemView, View.OnClickListener rowClickListener) {
            super(itemView);

            this.itemView.setOnClickListener(rowClickListener);
            mItemImage = itemView.findViewById(R.id.itemImage);
            mItemName = itemView.findViewById(R.id.itemName);

        }
    }
}
