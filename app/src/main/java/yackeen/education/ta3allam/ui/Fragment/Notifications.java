package yackeen.education.ta3allam.ui.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import yackeen.education.ta3allam.Capsule.Notification;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.adapter.NotificationsAdapter;
import yackeen.education.ta3allam.model.dto.request.NotificationsRequest;
import yackeen.education.ta3allam.model.dto.response.NotificationsResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.util.EndlessRecyclerViewScrollListener;
import yackeen.education.ta3allam.util.UserHelper;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Notifications.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Notifications#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notifications extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG="notifications_fragment";
    private RecyclerView notificationRecyclerView;
    private NotificationsAdapter notificationsAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private EndlessRecyclerViewScrollListener scrollListener;
    private OnFragmentInteractionListener mListener;
    private int notificationID;
    private List<Notification> notifications;
    private TextView noDataText;

    public Notifications() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notifications.
     */
    // TODO: Rename and change types and number of parameters
    public static Notifications newInstance(String param1, String param2) {
        Notifications fragment = new Notifications();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(yackeen.education.ta3allam.R.layout.fragment_notifications, container, false);
        notificationRecyclerView=(RecyclerView)rootView.findViewById(yackeen.education.ta3allam.R.id.notification_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        noDataText = (TextView) rootView.findViewById(R.id.no_data_text);
        noDataText.setEnabled(false);
        noDataText.setVisibility(View.INVISIBLE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        notificationRecyclerView.setLayoutManager(layoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                notificationID = notifications.get(notifications.size()-1).getID();
                feachNotificationsFromApi(notificationID);
            }
        };
        notificationRecyclerView.addOnScrollListener(scrollListener);
        notificationsAdapter = new NotificationsAdapter(getContext());
        notificationID = 0;
        feachNotificationsFromApi(notificationID);
        notificationRecyclerView.setAdapter(notificationsAdapter);
        return rootView;
    }
    public void feachNotificationsFromApi(int notifictionId){
        NotificationsRequest body = new NotificationsRequest();
        body.setUserID(UserHelper.getUserId(getContext()));
        body.setNotificationID(notifictionId);
        API.getUserAPIs().getAllNotifications(body,getNotificationListener(),
                getNotificationsFailedListener(),getContext());
    }
    //network response
    private Response.Listener<NotificationsResponse> getNotificationListener(){
        return new Response.Listener<NotificationsResponse>() {
            @Override
            public void onResponse(NotificationsResponse response) {
                if (response.AllNotificaions.size() == 0){
                    noDataText.setVisibility(View.VISIBLE);
                    noDataText.setText("لاتوجد بيانات");
                    noDataText.setEnabled(true);
                }
                Log.e(TAG,"network_response:"+response.AllNotificaions.size());
                 notifications = response.AllNotificaions;
                Log.d(TAG,"network_response:"+notifications.size());
                notificationsAdapter.addAll(notifications);
                mSwipeRefreshLayout.setRefreshing(false);
            }


        };
    }
    private Response.ErrorListener getNotificationsFailedListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                noDataText.setVisibility(View.VISIBLE);
                noDataText.setText("خطأ بالشبكه");
                noDataText.setEnabled(true);
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));
                Toast.makeText(getActivity(), yackeen.education.ta3allam.R.string.network_error, Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };
    }


        // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        feachNotificationsFromApi(notificationID);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
