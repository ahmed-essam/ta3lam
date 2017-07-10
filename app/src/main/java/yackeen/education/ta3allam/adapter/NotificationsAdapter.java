package yackeen.education.ta3allam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import yackeen.education.ta3allam.Capsule.News;
import yackeen.education.ta3allam.Capsule.Notification;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.model.dto.response.MessageNotificationBody;
import yackeen.education.ta3allam.model.dto.response.PostCommentNotificationBody;
import yackeen.education.ta3allam.ui.activity.ChatActivity;
import yackeen.education.ta3allam.ui.activity.ForumComentsActivity;
import yackeen.education.ta3allam.ui.activity.FriendsActivity;
import yackeen.education.ta3allam.ui.activity.UserProfileActivity;
import yackeen.education.ta3allam.util.UserHelper;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by ahmed essam on 11/06/2017.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder>{
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public TextView notificationDetail;
        public TextView notificationTime;
        private CardView notificationCard;
        CircleImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.name_netofy);
            notificationDetail = (TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.notification_detail);
            notificationTime = (TextView) itemView.findViewById(yackeen.education.ta3allam.R.id.notification_time);
            imageView = (CircleImageView) itemView.findViewById(yackeen.education.ta3allam.R.id.notification_from_image);
            notificationCard = (CardView) itemView.findViewById(R.id.notification_card);

        }

        public void bindView(Notification notification) {
            String bodyString = notification.getNotificationBody();
            if (notification.getType() == 1 || notification.getType() == 2) {
                MessageNotificationBody messageNotificationBody = new Gson().fromJson(bodyString, MessageNotificationBody.class);
                nameTextView.setText(messageNotificationBody.getUserName());
                notificationDetail.setText(messageNotificationBody.getContent());
            }
            if (notification.getType() == 3 || notification.getType() == 4) {
                MessageNotificationBody messageNotificationBody = new Gson().fromJson(bodyString, MessageNotificationBody.class);
                notificationDetail.setText(messageNotificationBody.getContent());
            }

            Picasso.with(mContext)
                    .load(notification.getUserPictureURL())
                    .placeholder(R.drawable.default_emam)
                    .error(yackeen.education.ta3allam.R.drawable.default_emam)
                    .into(imageView);
            if (!notification.isSeen()) {
                notificationCard.setCardBackgroundColor(itemView.getResources().getColor(R.color.colorLayoutBackground));
            }
        }

        @Override
        public void onClick(View view) {

        }
    }
    private List<Notification> mNotifications;
    private Context mContext;


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
