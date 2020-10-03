package com.example.lenovo.androidproject1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavouriteNewsAdapter extends RecyclerView.Adapter<FavouriteNewsAdapter.FavouriteViewHolder> {

    private ArrayList<FavouriteItem> mFavouritesList;
    private OnFavItemClickListener mListener;

    public interface OnFavItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onUrlClick(int position);
        void onShareClick(int position);
    }

    public void setOnItemClickListener(OnFavItemClickListener listener) {
        mListener = listener;
    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleView;
        public Button mUrlButton;
        public ImageButton mDeleteButton, mShareButton;

        public FavouriteViewHolder(@NonNull View itemView, final OnFavItemClickListener listener) {
            super(itemView);
            mTitleView = itemView.findViewById(R.id.favItemTitle);
            mUrlButton = itemView.findViewById(R.id.favItemOpenInChrome);
            mDeleteButton = itemView.findViewById(R.id.favItemDelete);
            mShareButton = itemView.findViewById(R.id.favItemShare);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mUrlButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onUrlClick(position);
                        }
                    }
                }
            });

            mShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onShareClick(position);
                        }
                    }
                }
            });

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public FavouriteNewsAdapter(ArrayList<FavouriteItem> favouritesList) {
        mFavouritesList = favouritesList;
    }


    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite, parent, false);
        FavouriteViewHolder fvh = new FavouriteViewHolder(v, mListener);
        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        FavouriteItem currentItem = mFavouritesList.get(position);
        holder.mTitleView.setText(currentItem.getItemTitle());
    }

    @Override
    public int getItemCount() {
        return mFavouritesList.size();
    }

}