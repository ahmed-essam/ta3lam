package yackeen.education.ta3allam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import yackeen.education.ta3allam.Capsule.Follower;
import yackeen.education.ta3allam.ui.activity.UserProfileActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed essam on 07/06/2017.
 */

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {
    public static Context context;
    public static List<Follower> followers;
    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private ImageView followerImage;
        public ViewHolder(View itemView) {
            super(itemView);
            followerImage=(ImageView)itemView.findViewById(yackeen.education.ta3allam.R.id.followers_image);

        }
        public void bindView(Follower follower,Context context){
            Picasso.with(context).load(follower.getPhotoURL()).error(yackeen.education.ta3allam.R.drawable.default_emam).into(followerImage);

        }

        @Override
        public void onClick(View view) {
            Intent intent = UserProfileActivity.newUserProfileIntent(context,followers.get(getPosition()).getId());
            context.startActivity(intent);
        }
    }

    public FollowersAdapter(Context context) {
        this.context = context;
        followers=new ArrayList<>();
    }

    public static Context getContext() {
        return context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View followerView = inflater.inflate(yackeen.education.ta3allam.R.layout.follower_item_layout,parent,false);
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
