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

import com.google.android.gms.nearby.messages.Message;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import yackeen.education.ta3allam.Capsule.Category;
import yackeen.education.ta3allam.Capsule.MessageItem;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.ui.activity.ChatActivity;
import yackeen.education.ta3allam.util.UserHelper;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by ahmed essam on 16/06/2017.
 */

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView profileIMage;
        private ImageView readeOrUnreade;

        private TextView nameText;
        private TextView lastMessage;
        private TextView timeText;


        public ViewHolder(View itemView) {
            super(itemView);
            profileIMage = (ImageView) itemView.findViewById(R.id.profile_image_messages);
            readeOrUnreade = (ImageView) itemView.findViewById(R.id.unreade_message_list);
            nameText = (TextView) itemView.findViewById(R.id.name_messages);
            lastMessage = (TextView) itemView.findViewById(R.id.message_text);
            timeText = (TextView) itemView.findViewById(R.id.time_message);
            itemView.setOnClickListener(this);
        }

        public void bindView(MessageItem messageItem) {
            Picasso.with(getContext()).load(messageItem.getConversationUserImageUrl()).placeholder(R.drawable.default_emam).error(R.drawable.default_emam).into(profileIMage);
            nameText.setText(messageItem.getConversationUserName());
            lastMessage.setText(messageItem.getLastMessage());
            String strDate = messageItem.getLastMessageDate();
            if (messageItem.isSeen()){
                readeOrUnreade.setImageResource(R.drawable.reade_gray);
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
            Date date = null;
            try {
                date = dateFormat.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            timeText.setText(DateUtils.getRelativeTimeSpanString(date.getTime(), new Date().getTime(), DateUtils.DAY_IN_MILLIS)+"");

            timeText.setText(messageItem.getLastMessageDate());
            Log.e(TAG, "bindView: MessageListAdapter");


        }

        @Override
        public void onClick(View view) {
            Intent intent = ChatActivity.newUserChatIntent(getContext(), UserHelper.getUserId(getContext()));
            getContext().startActivity(intent);

        }
    }

    private Context context;
    private List<MessageItem> contactsList;

    public MessagesListAdapter(Context context) {
        this.context = context;
        contactsList = new ArrayList<>();
    }

    public Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateViewHolder: MessageListAdapter");
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.messages_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(contactsList.get(position));
        Log.e(TAG, "onBindViewHolder: MessageListAdapter");
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public void addAll(List<MessageItem> messageItems) {
        Log.e(TAG, "addAll: MessageListAdapter" + messageItems.size());
        this.contactsList.clear();
        this.contactsList.addAll(messageItems);
        notifyDataSetChanged();
    }

    public void addItem(MessageItem messageItem) {
        this.contactsList.add(0, messageItem);
        notifyDataSetChanged();
    }

}
