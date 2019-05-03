package com.example.paceexchange;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AuctionFragment extends Fragment {

    private ImageView mItemImage;
    private TextView mItemName;
    private ArrayList<Integer> mAuctionItemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.auction_item_fragment, container, false);
        mItemImage = view.findViewById(R.id.itemImage);
        mItemName = view.findViewById(R.id.itemName);

        mAuctionItemList = new ArrayList<>();
        mAuctionItemList.add(R.drawable.android);
        mAuctionItemList.add(R.drawable.keyboard);
        mAuctionItemList.add(R.drawable.gown);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle mBundle = new Bundle();
        mBundle = getArguments();
        //int counter = Integer.parseInt(mBundle.getString(Auction.BID_ITEM_MESSAGE));
       // Log.d("COUNTER", String.valueOf(counter));
        //mItemImage.setImageDrawable(ContextCompat.getDrawable(getContext(), (mAuctionItemList.get(counter))));
    }

}
