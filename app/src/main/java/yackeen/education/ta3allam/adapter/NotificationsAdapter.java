package yackeen.education.ta3allam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import yackeen.education.ta3allam.Capsule.Notification;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by ahmed essam on 11/06/2017.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder>{
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public TextView notificationDetail;
        public TextView notificationTime;
        CircleImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.name_netofy);
            notificationDetail = (TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.notification_detail);
            notificationTime = (TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.notification_time);
            imageView = (CircleImageView) itemView.findViewById(yackeen.education.ta3allam.R.id.notification_from_image);



        }
        public void bindView(Notification notification){

            nameTextView.setText(notification.getFromUserID());
            notificationDetail.setText(notification.getNotificationBody());
            Picasso.with(mContext).load(notification.getUserPictureURL()).error(yackeen.education.ta3allam.R.drawable.default_emam).into(imageView);

        }

        @Override
        public void onClick(View view) {
            notifyItemChanged(selected_position);
            selected_position = getPosition();
            notifyItemChanged(selected_position);
        }
    }
    private List<Notification> mNotifications;
    private Context mContext;
    int selected_position = -1;

    public NotificationsAdapter(Context mContext) {
        this.mContext = mContext;
        mNotifications= new ArrayList<>();

    }
    public Context getmContext()
    {
        return mContext;
    }

    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View notificationsView = inflater.inflate(yackeen.education.ta3allam.R.layout.notification_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(notificationsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(mNotifications.size()!=0) {
            Notification notification = mNotifications.get(position);
            holder.bindView(notification);
        }else{
            Log.e(TAG, "onBindViewHolder: empty list" );
        }

    }

    public void addAll(List<Notification> notifications){
        mNotifications.clear();
        mNotifications.addAll(notifications);
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        Log.e(TAG, "getItemCount: "+mNotifications.size() );
        return mNotifications.size();
    }
}
