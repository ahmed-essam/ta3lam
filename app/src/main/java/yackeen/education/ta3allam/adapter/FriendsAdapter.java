package yackeen.education.ta3allam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import yackeen.education.ta3allam.Capsule.Category;
import yackeen.education.ta3allam.Capsule.FollowerDetail;
import yackeen.education.ta3allam.Capsule.Message;
import yackeen.education.ta3allam.R;

/**
 * Created by ahmed essam on 22/06/2017.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>{

    private List<FollowerDetail> mFollowers;
    private Context mContext;
    public Context getmContext()
    {
        return mContext;
    }

    public FriendsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View categoryView = inflater.inflate(R.layout.friend_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(categoryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FollowerDetail followerDetail = mFollowers.get(position);
        holder.bindView(followerDetail);

    }

    @Override
    public int getItemCount() {
        return mFollowers.size();
    }
    public void addAll(List<FollowerDetail> followerDetailList){
        this.mFollowers.clear();
        this.mFollowers.addAll(followerDetailList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public TextView categoryTextView;
        public CircleImageView followerImageView;
        public ProgressBar levelProgressBar;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.friend_name);
            categoryTextView = (TextView) itemView.findViewById(R.id.friend_course);
            levelProgressBar = (ProgressBar) itemView.findViewById(R.id.ProgressBar);
            followerImageView = (CircleImageView) itemView.findViewById(R.id.friend_profile_image);
            itemView.setOnClickListener(this);
        }
        public void bindView(FollowerDetail follower){
            nameTextView.setText(follower.getName());
            categoryTextView.setText(follower.getCategoryName());
            levelProgressBar.setProgress(follower.getPercentage());
            Picasso.with(getmContext()).load(follower.getFollowerPictureUR()).placeholder(R.drawable.default_emam).into(followerImageView);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext,"clicked",Toast.LENGTH_SHORT).show();
        }
    }
}
