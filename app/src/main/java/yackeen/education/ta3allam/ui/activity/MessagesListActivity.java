package yackeen.education.ta3allam.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import yackeen.education.ta3allam.Capsule.MessageItem;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.adapter.MessagesListAdapter;
import yackeen.education.ta3allam.model.dto.request.FirstLogin1Request;
import yackeen.education.ta3allam.model.dto.request.MessageListRequest;
import yackeen.education.ta3allam.model.dto.response.FirstLoginResponse1;
import yackeen.education.ta3allam.model.dto.response.MessageListResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.util.UserHelper;

public class MessagesListActivity extends AppCompatActivity {

    private RecyclerView contactsRecyclerView;
   private MessagesListAdapter contactsAdapter;
    private Toolbar messagesToolbar;
    private String TAG="Message_List_Activity";
    List<MessageItem> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(yackeen.education.ta3allam.R.layout.activity_messages_list);
        messagesToolbar = (Toolbar)findViewById(R.id.messages_toolbar);
        messagesToolbar.setTitle(null);
        setSupportActionBar(messagesToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        contactsRecyclerView=(RecyclerView)findViewById(R.id.messages_recycler_view);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsAdapter= new MessagesListAdapter(this);
        List<MessageItem> messageItems = new ArrayList<>();
        MessageItem messageItem = new MessageItem();
        messageItem.setConversationUserID("132154,kojoph");
        messageItem.setConversationUserName("احمد عصام ");
        messageItem.setLastMessage("اهلا بك");
        messageItems.add(messageItem);
        contactsAdapter.addAll(messageItems);
        contactsRecyclerView.setAdapter(contactsAdapter);

    }

    public void feachContactsFromApi(){
        MessageListRequest body = new MessageListRequest();
        body.setUserID(UserHelper.getUserId(this));
        API.getUserAPIs().getUserMessagesContacts(body,getContactsListener(),
                getContactsFailedListener(),this);


    }
    //network response
    private Response.Listener<MessageListResponse> getContactsListener(){
        return new Response.Listener<MessageListResponse>() {
            @Override
            public void onResponse(MessageListResponse response) {
                Log.e(TAG,"network_response:"+response.MessagesContactList.size());
                contacts = response.MessagesContactList;
                Log.d(TAG,"network_response:"+contacts.size());
                contactsAdapter.addAll(contacts);

            }
        };
    }
    private Response.ErrorListener getContactsFailedListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));
                Toast.makeText(MessagesListActivity.this, yackeen.education.ta3allam.R.string.network_error, Toast.LENGTH_SHORT).show();

            }
        };
    }
}
