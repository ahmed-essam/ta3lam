package yackeen.education.ta3allam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import yackeen.education.ta3allam.Capsule.Comment;
import yackeen.education.ta3allam.Capsule.News;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.model.dto.request.ForumRequest;
import yackeen.education.ta3allam.model.dto.request.LikeAndShareRequest;
import yackeen.education.ta3allam.model.dto.response.EmptyResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.ui.activity.ForumComentsActivity;
import yackeen.education.ta3allam.ui.activity.ForumsShowActivity;
import yackeen.education.ta3allam.util.UserHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ahmed essam on 13/06/2017.
 */

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder> {
    private String TAG="forum_adapter";


    public static class ViewHolder extends RecyclerView.ViewHolder  {
        private String TAG1="forum_view_holder";
        ImageView profileImage;
        ImageView likeImage;
        ImageView commentImage;
        LinearLayout postLinearLayout;
        TextView nameTextView;
        TextView timeTextView;
        TextView descriptionTextView;
        TextView commentTextView;
        TextView likeTextView;
        boolean isLiked;
        public ViewHolder(View itemView) {
            super(itemView);
            profileImage=(ImageView) itemView.findViewById(R.id.profileimage);
            likeImage=(ImageView) itemView.findViewById(R.id.like_image);
            commentImage=(ImageView) itemView.findViewById(R.id.comment_image);
            postLinearLayout=(LinearLayout) itemView.findViewById(R.id.post_linear_layout);
            nameTextView=(TextView) itemView.findViewById(R.id.name);
            timeTextView=(TextView) itemView.findViewById(R.id.timewent);
            descriptionTextView=(TextView) itemView.findViewById(R.id.descritption);
            commentTextView=(TextView) itemView.findViewById(R.id.comment);
            likeTextView=(TextView) itemView.findViewById(R.id.like);
            likeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    likePostUsingApi(getPosition());

                }
            });

            commentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = ForumComentsActivity.newCommentIntent(mContext,mNews.get(getPosition()));
                    mContext.startActivity(intent);
                }
            });
            postLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = ForumComentsActivity.newCommentIntent(mContext,mNews.get(getPosition()));
                    mContext.startActivity(intent);
                }
            });
        }
        public void bindView(News news,Context context){
            Picasso.with(context).load(news.getImage()).placeholder(R.drawable.default_emam).error(R.drawable.default_emam).into(profileImage);
            nameTextView.setText(news.getName());
            timeTextView.setText(news.getTime());
            descriptionTextView.setText(news.getDescription());
            commentTextView.setText(news.getComment()+"");
            likeTextView.setText(news.getLike()+"");
            isLiked= news.isLiked();
            if (news.isLiked()){
                likeImage.setImageResource(R.drawable.heart_orange_new);
            }

        }

        public void likePostUsingApi(int position){
            LikeAndShareRequest body = new LikeAndShareRequest();
            body.setPostID(mNews.get(position).getPostId());
            body.setUserID(UserHelper.getUserId(mContext));
            API.getUserAPIs().likePost(body,getLikeListener(),
                    getFailedListener(),mContext);
        }


        //network response
        private Response.ErrorListener getFailedListener(){
            return new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG1, "onErrorResponse: ".concat(error.toString()));
                    Toast.makeText(mContext, R.string.network_error, Toast.LENGTH_SHORT).show();

                }
            };
        }
        private Response.Listener<EmptyResponse> getLikeListener(){
            return new Response.Listener<EmptyResponse>() {
                @Override
                public void onResponse(EmptyResponse response) {
                    likeImage.setImageResource(R.drawable.heart_orange_new);
                }
            };
        }

    }


     public static List<News> mNews;
     public static Context mContext;
    ViewHolder viewHolder;

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
        viewHolder = holder;
        holder.bindView(mNews.get(position),getmContext());

    }
    public void addAll(List<News> newsList){
        Log.e(TAG, "addAll: "+newsList.size());
        mNews.clear();
        mNews.addAll(newsList);
        notifyDataSetChanged();
    }
    public void addItem(News news){
        mNews.add(0,news);
        notifyDataSetChanged();
    }
    public void editcommentNumber(int postId){
        for (int i = 0;i<mNews.size();i++){
            if (postId ==mNews.get(i).getPostId()){
                viewHolder.commentTextView.setText(Integer.parseInt(viewHolder.commentTextView.getText().toString())+1);
            }
        }
    }
    public void editLikesNumber(int postId){
        for (int i = 0;i<mNews.size();i++){
            if (postId ==mNews.get(i).getPostId()){
                viewHolder.likeTextView.setText(Integer.parseInt(viewHolder.likeTextView.getText().toString())+1);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }


}
