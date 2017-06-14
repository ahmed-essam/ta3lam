package yackeen.education.ta3allam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import yackeen.education.ta3allam.Capsule.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devar on 8/22/16.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView nameTextView;
        TextView timeTextView;
        TextView descriptionTextView;
        TextView shareTextView;
        TextView commentTextView;
        TextView likeTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            profileImage=(ImageView) itemView.findViewById(yackeen.education.ta3allam.R.id.profileimage);
            nameTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.name);
            timeTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.timewent);
            descriptionTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.descritption);
            shareTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.share);
            commentTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.comment);
            likeTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.like);
        }
    }
    private List<News> mNews;
    private Context mContext;
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
        Picasso.with(getmContext()).load(news.getImage()).error(yackeen.education.ta3allam.R.drawable.default_emam).into(profileImage);
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