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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import yackeen.education.ta3allam.Capsule.Message;
import yackeen.education.ta3allam.Capsule.SendMessage;
import yackeen.education.ta3allam.Capsule.UserBooks;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.adapter.ChatAdapter;
import yackeen.education.ta3allam.model.dto.request.ConversationRequest;
import yackeen.education.ta3allam.model.dto.request.MessageListRequest;
import yackeen.education.ta3allam.model.dto.request.SendMessageRequest;
import yackeen.education.ta3allam.model.dto.response.ConversationResponse;
import yackeen.education.ta3allam.model.dto.response.EmptyResponse;
import yackeen.education.ta3allam.model.dto.response.MessageListResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.util.UserHelper;

import static yackeen.education.ta3allam.ui.activity.ForumComentsActivity.hideSoftKeyboard;

public class ChatActivity extends AppCompatActivity {
    private static final String Userparam ="userID";
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private EditText sendEditText;
    private ImageButton sendImageButton;
    private TextView chatName;
    private ImageView chatImage;
    private String TAG="Chat_activity";
    List<Message> messageList;
    private String USERID;
    private int lastMessageId;
    Toolbar chatToolbar;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

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
        lastMessageId=0;
        feachConversationFromApi(lastMessageId);
        List<Message> userBooksList = new ArrayList<>();
        Message userBooks = new Message();
        userBooks.setBody("اهلا بك ");
        userBooks.setMine(true);
        userBooksList.add(userBooks);
        chatAdapter.addAll(userBooksList);
        Message message = new Message();
        message.setBody("اهلا بك ");
        message.setMine(false);
        chatAdapter.addItem(message);

        chatRecyclerView.setAdapter(chatAdapter);
        chatRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;

                            feachConversationFromApi(lastMessageId);

                        }
                    }
                }
            }
        });
        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sendEditText.getText() != null) {
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
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }



    public void createView(){
        chatToolbar=(Toolbar)findViewById(R.id.chat_toolbar) ;
        chatRecyclerView =(RecyclerView)findViewById(R.id.chat_recycler_view);
        chatName=(TextView) findViewById(R.id.chat_name);
        chatImage=(ImageView) findViewById(R.id.chat_image);
        sendEditText=(EditText) findViewById(R.id.send_message_edit);
        sendImageButton=(ImageButton) findViewById(R.id.send_button);
        chatAdapter= new ChatAdapter(this);
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
    public static Intent newUserChatIntent(Context context, String userID){
        Intent intent = new Intent(context,ChatActivity.class);
        intent.putExtra(Userparam,userID);
        return intent;
    }
    private String getUSERIDFromId() {
        Intent intent = getIntent();
        String user = intent.getStringExtra(Userparam);
        return user;
    }
//network call
    public void feachConversationFromApi(int fromMessageId){
        ConversationRequest body = new ConversationRequest();
        body.setConversationUserID(USERID);
        body.setLastMessageID(fromMessageId);
        body.setUserID(UserHelper.getUserId(this));
        API.getUserAPIs().getConversationMessages(body,getconversationListener(),
                getConversationFailedListener(),this);
    }
    public void sendMessageUsingAPI(SendMessage sendMessage){
        SendMessageRequest body = new SendMessageRequest();
        body.setUserID(UserHelper.getUserId(this));
        API.getUserAPIs().getSendMessages(body,getSendMessageListener(),
                getConversationFailedListener(),this);
    }
    //network response
    private Response.Listener<ConversationResponse> getconversationListener(){
        return new Response.Listener<ConversationResponse>() {
            @Override
            public void onResponse(ConversationResponse response) {
                Log.e(TAG,"network_response:"+response.ConversationMessages.size());
                messageList = response.ConversationMessages;
                lastMessageId=messageList.get((messageList.size())-1).getMessageID();
                Log.d(TAG,"network_response:"+messageList.size());
                chatAdapter.addAll(messageList);
                chatName.setText(response.getConversationUserName());
                Picasso.with(ChatActivity.this).load(response.getConversationUserImageUrl()).placeholder(R.drawable.default_emam).error(R.drawable.default_emam).into(chatImage);

            }
        };
    }
    private Response.ErrorListener getConversationFailedListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));
                Toast.makeText(ChatActivity.this, yackeen.education.ta3allam.R.string.network_error, Toast.LENGTH_SHORT).show();

            }
        };
    }
    private Response.Listener<EmptyResponse> getSendMessageListener(){
        return new Response.Listener<EmptyResponse>() {
            @Override
            public void onResponse(EmptyResponse response) {
                Message message = new Message();
                message.setMine(true);
                message.setBody(sendEditText.getText().toString());
                message.setDateTime(Long.toString(new Date().getTime()));
                chatAdapter.addItem(message);
                sendEditText.setText(null);

            }
        };
    }
}
