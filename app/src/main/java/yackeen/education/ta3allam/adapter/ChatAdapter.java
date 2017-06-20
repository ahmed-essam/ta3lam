package yackeen.education.ta3allam.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import yackeen.education.ta3allam.Capsule.Message;
import yackeen.education.ta3allam.Capsule.MessageItem;
import yackeen.education.ta3allam.R;

/**
 * Created by ahmed essam on 18/06/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
   private List<Message> messages;

    public ChatAdapter(Context context) {
        this.context = context;
        this.messages=new ArrayList<>();
    }

    public Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View categoryView = inflater.inflate(R.layout.chat_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(categoryView);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public void addAll(List<Message> messageList){
       this.messages.clear();
        this.messages.addAll(messageList);
        notifyDataSetChanged();
    }

    public void addItem(Message message){
        this.messages.add(message);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView messageText;



        public ViewHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.message_text_view);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void bindView(Message message) {
            if (message.isMine()) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) messageText.getLayoutParams();
                layoutParams.gravity = Gravity.LEFT;
                messageText.setLayoutParams(layoutParams);
                messageText.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.message_background_white));
                messageText.setTextColor(itemView.getResources().getColor(R.color.mesages_header));
            }else{
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) messageText.getLayoutParams();
                layoutParams.gravity = Gravity.RIGHT;
                messageText.setLayoutParams(layoutParams);
                messageText.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.message_background_green));
                messageText.setTextColor(itemView.getResources().getColor(R.color.colorCardBackground));
            }
            messageText.setText(message.getBody());
        }

    }
}
