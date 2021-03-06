package yackeen.education.ta3allam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import yackeen.education.ta3allam.Capsule.Comment;
import yackeen.education.ta3allam.Capsule.Message;
import yackeen.education.ta3allam.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by ahmed essam on 13/06/2017.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder>{
    public static final String TAG = CommentsAdapter.class.getSimpleName();

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView profileImage;
        TextView nameTextView;
        TextView timeTextView;
        TextView descriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            profileImage=(ImageView) itemView.findViewById(yackeen.education.ta3allam.R.id.profileimage);
            nameTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.name);
            timeTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.timewent);
            descriptionTextView=(TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.descritption);

        }
        public void bindView(Comment comment, Context context){
            Picasso.with(context).load(comment.getUserPictureURL()).placeholder(R.drawable.default_emam).error(yackeen.education.ta3allam.R.drawable.default_emam).into(profileImage);
            nameTextView.setText(comment.getUserName());
            String strDate = comment.getDateTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
            Date date = null;
            try {
                date = dateFormat.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            timeTextView.setText(DateUtils.getRelativeTimeSpanString(date.getTime(), new Date().getTime(), DateUtils.DAY_IN_MILLIS)+"");

            descriptionTextView.setText(comment.getBody());

        }

        @Override
        public void onClick(View view) {

        }


    }
    private List<Comment> comments;
    private Context mContext;

    public CommentsAdapter(Context mContext) {
        this.mContext = mContext;
        comments= new ArrayList<>();
    }

    public Context getmContext() {
        return mContext;
    }

    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View categoryView = inflater.inflate(yackeen.education.ta3allam.R.layout.card_view_comment, parent, false);

        CommentsAdapter.ViewHolder viewHolder = new CommentsAdapter.ViewHolder(categoryView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(comments.get(position),getmContext());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void addAll(List<Comment> commentList){
        Log.e(TAG, "addAll: "+commentList.size());
        this.comments.clear();
        this.comments.addAll(commentList);
        Log.e(TAG, "addAll: "+comments.size());
        notifyDataSetChanged();
    }
    public void addItem(Comment comment){
        this.comments.add(comment);
        Log.e(TAG, "addItem: "+comments.size());
        notifyDataSetChanged();
    }
}
