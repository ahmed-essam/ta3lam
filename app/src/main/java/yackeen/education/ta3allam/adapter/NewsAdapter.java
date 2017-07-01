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
import yackeen.education.ta3allam.Capsule.News;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.model.dto.request.LikeAndShareRequest;
import yackeen.education.ta3allam.model.dto.response.EmptyResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.ui.activity.ForumComentsActivity;
import yackeen.education.ta3allam.util.UserHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devar on 8/22/16.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private String TAG="news_adapter";
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private String TAG1="news_view_holder";
        ImageView profileImage;
        ImageView likeImage;
        ImageView shareImage;
        ImageView commentImage;
        LinearLayout postLinearLayout;
        TextView nameTextView;
        TextView timeTextView;
        TextView descriptionTextView;
        TextView shareTextView;
        TextView commentTextView;
        TextView likeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            profileImage=(ImageView) itemView.findViewById(yackeen.education.ta3allam.R.id.profileimage);
            likeImage=(ImageView) itemView.findViewById(R.id.like_image);
            shareImage=(ImageView) itemView.findViewById(R.id.share_image);
            commentImage=(ImageView) itemView.findViewById(R.id.comment_image);
            postLinearLayout=(LinearLayout) itemView.findViewById(R.id.post_linear_layout);
            nameTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.name);
            timeTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.timewent);
            descriptionTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.descritption);
            shareTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.share);
            commentTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.comment);
            likeTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.like);
            addListener();

        }
        public void addListener(){
            likeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    likePostUsingApi(getPosition());

                }
            });
            shareImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sharePostUsingApi(getPosition());

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
        public void likePostUsingApi(int position){
            LikeAndShareRequest body = new LikeAndShareRequest();
            body.setPostID(mNews.get(position).getPostId());
            body.setUserID(UserHelper.getUserId(mContext));
            API.getUserAPIs().likePost(body,getLikeListener(),
                    getFailedListener(),mContext);
        }
        public void sharePostUsingApi(int position){
            LikeAndShareRequest body = new LikeAndShareRequest();
            body.setPostID(mNews.get(position).getPostId());
            body.setUserID(UserHelper.getUserId(mContext));
            API.getUserAPIs().sharePost(body,getShareListener(),
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
                    likeImage.setBackgroundDrawable(itemView.getResources().getDrawable(R.drawable.icon_heart_orange));
                    int num = mNews.get(getPosition()).getLike()+1;
                    likeTextView.setText(""+num);
                }
            };
        }
        private Response.Listener<EmptyResponse> getShareListener(){
            return new Response.Listener<EmptyResponse>() {
                @Override
                public void onResponse(EmptyResponse response) {

                }
            };
        }
    }
    public static List<News> mNews;
    public static Context mContext;
    public static boolean isLiked;
    public NewsAdapter(Context context)
    {
        mContext=context;
        mNews=new ArrayList<>();
    }
    public Context getmContext() {
        return mContext;
    }
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View categoryView = inflater.inflate(yackeen.education.ta3allam.R.layout.news_card_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(categoryView);
        return viewHolder;

    }
    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder viewHolder,int position)
    {
        News news=mNews.get(position);
        ImageView profileImage=viewHolder.profileImage;
        Picasso.with(getmContext()).load(news.getImage()).placeholder(R.drawable.default_emam).error(yackeen.education.ta3allam.R.drawable.default_emam).into(profileImage);
        TextView name=viewHolder.nameTextView;
        name.setText(news.getName());
        TextView time=viewHolder.timeTextView;
        time.setText(news.getTime());
        TextView description=viewHolder.descriptionTextView;
        description.setText(news.getDescription());
        TextView share=viewHolder.shareTextView;
        share.setText(news.getShare()+"");
        TextView comment=viewHolder.commentTextView;
        comment.setText(news.getComment()+"");
        TextView like=viewHolder.likeTextView;
        like.setText(news.getLike()+"");
        isLiked= news.isLiked();
        if (news.isLiked()){
            viewHolder.likeImage.setBackgroundDrawable(getmContext().getResources().getDrawable(R.drawable.icon_heart_orange));
        }
    }
    @Override
    public int getItemCount() {
        return mNews.size();
    }


    public void addAll(List<News> newsList){
        this.mNews.clear();
        this.mNews.addAll(newsList);
        notifyDataSetChanged();
    }
}