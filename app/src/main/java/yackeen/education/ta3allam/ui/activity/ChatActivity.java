package yackeen.education.ta3allam.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.List;

import yackeen.education.ta3allam.Capsule.Message;
import yackeen.education.ta3allam.Capsule.SendMessage;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.adapter.ChatAdapter;
import yackeen.education.ta3allam.model.dto.request.ConversationRequest;
import yackeen.education.ta3allam.model.dto.request.SendMessageRequest;
import yackeen.education.ta3allam.model.dto.response.ChatNotificationResponse;
import yackeen.education.ta3allam.model.dto.response.ConversationResponse;
import yackeen.education.ta3allam.model.dto.response.EmptyResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.util.EndlessRecyclerViewScrollListener;
import yackeen.education.ta3allam.util.TextHelper;
import yackeen.education.ta3allam.util.UserHelper;

public class ChatActivity extends AppCompatActivity {
    private static final String Userparam = "userID";
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private EditText sendEditText;
    private ImageButton sendImageButton;
    private TextView chatName;
    private ImageView chatImage;
    private String TAG = "Chat_activity";
    List<Message> messageList;
    private String USERID;
    private int lastMessageId;
    Toolbar chatToolbar;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private EndlessRecyclerViewScrollListener scrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        createView();
        setupUI(chatRecyclerView);
        setSupportActionBar(chatToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        USERID = getUSERIDFromId();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        chatRecyclerView.setLayoutManager(layoutManager);
        lastMessageId = 0;
        feachConversationFromApi(lastMessageId);
        chatRecyclerView.setAdapter(chatAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                feachConversationFromApi(lastMessageId);
            }
        };
        chatRecyclerView.addOnScrollListener(scrollListener);
        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextHelper.isEditTextEmpty(new EditText[]{sendEditText})) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setBody(sendEditText.getText().toString());
                    sendMessage.setToUserID(USERID);
                    sendMessageUsingAPI(sendMessage);
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void createView() {
        chatToolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        chatRecyclerView = (RecyclerView) findViewById(R.id.chat_recycler_view);
        chatName = (TextView) findViewById(R.id.chat_name);
        chatImage = (ImageView) findViewById(R.id.chat_image);
        sendEditText = (EditText) findViewById(R.id.send_message_edit);
        sendImageButton = (ImageButton) findViewById(R.id.send_button);
        chatAdapter = new ChatAdapter(this);
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(ChatActivity.this);
                    return false;
                }
            });
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static Intent newUserChatIntent(Context context, String userID) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(Userparam, userID);
        return intent;
    }

    private String getUSERIDFromId() {
        Intent intent = getIntent();
        String user = intent.getStringExtra(Userparam);
        return user;
    }

    //notification listener
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChatNotificationResponse notificationResponse) {
        Log.d(TAG, "onMessageEvent: ");
        if (notificationResponse.type ==1) {
            if ((notificationResponse.getMessageBody().FromUserID).equals(USERID)) {
                Message message = new Message();
                message.setBody(notificationResponse.getMessageBody().Content);
                message.setMine(false);
                chatAdapter.addItem(message);
            }
        }
    }


    //network call
    public void feachConversationFromApi(int fromMessageId) {
        ConversationRequest body = new ConversationRequest();
        body.setConversationUserID(USERID);
        body.setLastMessageID(fromMessageId);
        body.setUserID(UserHelper.getUserId(this));
        API.getUserAPIs().getConversationMessages(body, getconversationListener(),
                getConversationFailedListener(), this);
    }
    public void feachNewConversationFromApi(int fromMessageId) {
        ConversationRequest body = new ConversationRequest();
        body.setConversationUserID(USERID);
        body.setLastMessageID(fromMessageId);
        body.setUserID(UserHelper.getUserId(this));
        API.getUserAPIs().getNewConversationMessages(body, getNewconversationListener(),
                getConversationFailedListener(), this);
    }
    public void sendMessageUsingAPI(SendMessage sendMessage) {
        SendMessageRequest body = new SendMessageRequest();
        body.setUserID(UserHelper.getUserId(this));
        body.setMessage(sendMessage);
        API.getUserAPIs().getSendMessages(body, getSendMessageListener(),
                getConversationFailedListener(), this);
    }

    //network response
    private Response.Listener<ConversationResponse> getconversationListener() {
        return new Response.Listener<ConversationResponse>() {
            @Override
            public void onResponse(ConversationResponse response) {
                if (lastMessageId ==0) {
                    Log.e(TAG, "network_response:" + response.ConversationMessages.size());
                    messageList = response.ConversationMessages;
                    if (messageList.size() > 0) {
                        lastMessageId = messageList.get((messageList.size()) - 1).getMessageID();
                    }
                    Log.d(TAG, "network_response:" + messageList.size());
                    chatAdapter.addAll(messageList);
                    chatName.setText(response.getConversationUserName());
                    Picasso.with(ChatActivity.this).load(response.getConversationUserImageUrl()).placeholder(R.drawable.default_emam).error(R.drawable.default_emam).into(chatImage);
                }else{
                    if (response.ConversationMessages != null && response.ConversationMessages.size() != 0){
                        messageList.addAll(response.ConversationMessages);
                        lastMessageId = messageList.get((messageList.size()) - 1).getMessageID();
                        chatAdapter.addSomeItems(response.ConversationMessages);
                    }
                }

            }
        };
    }

    private Response.ErrorListener getConversationFailedListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));
                Toast.makeText(ChatActivity.this, yackeen.education.ta3allam.R.string.network_error, Toast.LENGTH_SHORT).show();

            }
        };
    }

    private Response.Listener<EmptyResponse> getSendMessageListener() {
        return new Response.Listener<EmptyResponse>() {
            @Override
            public void onResponse(EmptyResponse response) {
                if (response.isSuccess()) {
                    Message message = new Message();
                    message.setMine(true);
                    message.setBody(sendEditText.getText().toString());
                    message.setDateTime(Long.toString(new Date().getTime()));
                    chatAdapter.addItem(message);
                    sendEditText.setText(null);
                    chatRecyclerView.scrollToPosition(0);
                }else {
                    Log.d(TAG, "onResponse: "+response.getErrorMessage());
                }

            }
        };
    }
    private Response.Listener<ConversationResponse> getNewconversationListener() {
        return new Response.Listener<ConversationResponse>() {
            @Override
            public void onResponse(ConversationResponse response) {
                Log.e(TAG, "network_response:" + response.ConversationMessages.size());
                messageList = response.ConversationMessages;
                lastMessageId = messageList.get((messageList.size()) - 1).getMessageID();
                Log.d(TAG, "network_response:" + messageList.get(messageList.size()-1).getMessageID());
                chatAdapter.addAll(messageList);
                Picasso.with(ChatActivity.this).load(response.getConversationUserImageUrl()).placeholder(R.drawable.default_emam).error(R.drawable.default_emam).into(chatImage);

            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
