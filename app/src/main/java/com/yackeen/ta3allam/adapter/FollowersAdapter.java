package com.yackeen.ta3allam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yackeen.ta3allam.Capsule.Follower;
import com.yackeen.ta3allam.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed essam on 07/06/2017.
 */

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {
    private Context context;
    List<Follower> followers;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView followerImage;
        public ViewHolder(View itemView) {
            super(itemView);
            followerImage=(ImageView)itemView.findViewById(R.id.followers_image);

        }
        public void bindView(Follower follower,Context context){
            Picasso.with(context).load(follower.getPhotoURL()).error(R.drawable.default_emam).into(followerImage);

        }
    }

    public FollowersAdapter(Context context) {
        this.context = context;
        followers=new ArrayList<>();
    }

    public Context getContext() {
        return context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View followerView = inflater.inflate(R.layout.follower_item_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(followerView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(followers.get(position),this.getContext());

    }
    public void addAll(List<Follower> followerList){
       followers.clear();
        followers.addAll(followerList);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return followers.size();
    }
}
