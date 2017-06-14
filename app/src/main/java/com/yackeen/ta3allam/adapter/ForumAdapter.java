package com.yackeen.ta3allam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yackeen.ta3allam.Capsule.Follower;
import com.yackeen.ta3allam.Capsule.News;
import com.yackeen.ta3allam.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed essam on 13/06/2017.
 */

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder> {
    private String TAG="forum_adapter";


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView profileImage;
        TextView nameTextView;
        TextView timeTextView;
        TextView descriptionTextView;
        TextView shareTextView;
        TextView commentTextView;
        TextView likeTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            profileImage=(ImageView) itemView.findViewById(R.id.profileimage);
            nameTextView=(TextView) itemView.findViewById(R.id.name);
            timeTextView=(TextView) itemView.findViewById(R.id.timewent);
            descriptionTextView=(TextView) itemView.findViewById(R.id.descritption);
            shareTextView=(TextView) itemView.findViewById(R.id.share);
            commentTextView=(TextView) itemView.findViewById(R.id.comment);
            likeTextView=(TextView) itemView.findViewById(R.id.like);
        }
        public void bindView(News news,Context context){

            Picasso.with(context).load(news.getImage()).error(R.drawable.default_emam).into(profileImage);
            nameTextView.setText(news.getName());
            timeTextView.setText(news.getTime());
            descriptionTextView.setText(news.getDescription());
            shareTextView.setText(news.getShare()+"");
            commentTextView.setText(news.getComment()+"");
            likeTextView.setText(news.getLike()+"");
        }

        @Override
        public void onClick(View view) {

        }
    }
    private List<News> mNews;
    private Context mContext;

    public ForumAdapter(Context mContext) {
        this.mContext = mContext;
        mNews=new ArrayList<>();
    }

    public Context getmContext() {
        return mContext;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View categoryView = inflater.inflate(R.layout.news_card_view, parent, false);

        ForumAdapter.ViewHolder viewHolder = new ForumAdapter.ViewHolder(categoryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(mNews.get(position),getmContext());

    }
    public void addAll(List<News> newsList){
        Log.e(TAG, "addAll: "+newsList.size());
        mNews.clear();
        mNews.addAll(newsList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }
}
