package yackeen.education.ta3allam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by devar on 8/22/16.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private String TAG="news_adapter";
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private String TAG1="news_view_holder";
        ImageView profileImage;
        ImageView likeImage;
        ImageView commentImage;
        LinearLayout postLinearLayout;
        TextView nameTextView;
        TextView timeTextView;
        TextView descriptionTextView;
        TextView commentTextView;
        TextView likeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            profileImage=(ImageView) itemView.findViewById(yackeen.education.ta3allam.R.id.profileimage);
            likeImage=(ImageView) itemView.findViewById(R.id.like_image);
            commentImage=(ImageView) itemView.findViewById(R.id.comment_image);
            postLinearLayout=(LinearLayout) itemView.findViewById(R.id.post_linear_layout);
            nameTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.name);
            timeTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.timewent);
            descriptionTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.descritption);
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

            commentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = ForumComentsActivity.newCommentIntent(mContext,mNews.get(getPosition()));
                    Log.d("NewsAdapter", "onClick: "+mNews.get(getPosition()).getPostId());
                    mContext.startActivity(intent);
                }
            });
            postLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = ForumComentsActivity.newCommentIntent(mContext,mNews.get(getPosition()));
                    mContext.startActivity(intent);
                    Log.d("NewsAdapter", "onClick: "+mNews.get(getPosition()).getPostId());

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
                    if (! mNews.get(getPosition()).isLiked()) {
                        likeImage.setImageResource(R.drawable.heart_orange);
                        int num = mNews.get(getPosition()).getLike() + 1;
                        likeTextView.setText("" + num);
                        mNews.get(getPosition()).setLike(num);
                        mNews.get(getPosition()).setLiked(true);

                    }else {
                        Toast.makeText(mContext, "already liked", Toast.LENGTH_SHORT).show();
                    }
                }
            };
        }

    }
    public static List<News> mNews;
    public static Context mContext;
    public static boolean isLiked;
    ViewHolder viewHolder;
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
        try {
            String strDate = news.getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S", Locale.getDefault());
            Date date = dateFormat.parse(strDate);
            TextView time=viewHolder.timeTextView;
            String displayedDate = (String) DateUtils.getRelativeTimeSpanString(date.getTime(), new Date().getTime(), DateUtils.DAY_IN_MILLIS);
            time.setText(displayedDate);
        } catch (ParseException e) {
            Log.d(TAG, "onBindViewHolder: "+e.getMessage());
            e.printStackTrace();
        }

        ImageView profileImage=viewHolder.profileImage;
        Picasso.with(getmContext()).load(news.getImage()).placeholder(R.drawable.default_emam).error(yackeen.education.ta3allam.R.drawable.default_emam).into(profileImage);
        TextView name=viewHolder.nameTextView;
        name.setText(news.getName());
        TextView description=viewHolder.descriptionTextView;
        description.setText(news.getDescription());
        TextView comment=viewHolder.commentTextView;
        comment.setText(news.getComment()+"");
        TextView like=viewHolder.likeTextView;
        like.setText(news.getLike()+"");
        isLiked= news.isLiked();
        if (news.isLiked()){
            viewHolder.likeImage.setImageResource(R.drawable.heart_orange);
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
}