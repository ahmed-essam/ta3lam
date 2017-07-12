package yackeen.education.ta3allam.ui.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import yackeen.education.ta3allam.Capsule.Message;
import yackeen.education.ta3allam.adapter.FirstLoginAdapter2;
import yackeen.education.ta3allam.adapter.NewsAdapter;
import yackeen.education.ta3allam.Capsule.News;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.model.dto.request.NewsRequest;
import yackeen.education.ta3allam.model.dto.response.ChatNotificationResponse;
import yackeen.education.ta3allam.model.dto.response.NewsResponse;
import yackeen.education.ta3allam.model.dto.response.OtherNotificationResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.util.Connectivity;
import yackeen.education.ta3allam.util.EndlessRecyclerViewScrollListener;
import yackeen.education.ta3allam.util.UserHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFeed.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFeed#newInstance} factory method to
 * create an instance Nof this fragment.
 */
public class NewsFeed extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG = NewsFeed.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static NewsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static TextView noDataText;
    private static List<News> news;
    int postID;
    private OnFragmentInteractionListener mListener;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private EndlessRecyclerViewScrollListener scrollListener;


    public NewsFeed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFeed.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFeed newInstance(String param1, String param2) {
        NewsFeed fragment = new NewsFeed();
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
        View view = inflater.inflate(R.layout.fragment_news_feed, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.news);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        news = new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (Connectivity.isConnected(getContext())){
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            }

        });
        noDataText = (TextView) view.findViewById(R.id.no_data_text);
        noDataText.setEnabled(false);
        noDataText.setVisibility(View.INVISIBLE);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                feachNewsFromApi(postID);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new NewsAdapter(getContext());
        postID = 0;
        feachNewsFromApi(postID);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void feachNewsFromApi(int postId) {
        NewsRequest body = new NewsRequest();
        body.setUserID(UserHelper.getUserId(getContext()));
        body.setPostID(postId);
        Log.e(TAG, "FeachNewsFromApi: " + body.getUserID());
        API.getUserAPIs().getAllNews(body, getNewsListener(),
                getNewsFailedListener(), getContext());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OtherNotificationResponse notificationResponse) {
        Log.d(TAG, "onMessageEvent: ");
        if (notificationResponse.type == 3) {
            adapter.editcommentNumber(notificationResponse.getPostId());
        } else {
            if (notificationResponse.type == 4) {
                adapter.editLikesNumber(notificationResponse.getPostId());
            }
        }
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
        postID = 0;
        feachNewsFromApi(postID);

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

    //network response
    private Response.Listener<NewsResponse> getNewsListener() {
        return new Response.Listener<NewsResponse>() {
            @Override
            public void onResponse(NewsResponse response) {
                if (response.HomePosts.size() == 0) {
                    noDataText.setVisibility(View.VISIBLE);
                    noDataText.setText("لاتوجد بيانات");
                    noDataText.setEnabled(true);
                }
                Log.e(TAG, "network_response:" + response.HomePosts.size());
                List<News> newsList = response.HomePosts;
                Log.d(TAG, "network_response:" + newsList.size());
                if (newsList.size() > 0)
                    postID = newsList.get(newsList.size() - 1).getPostId();
                Log.d(TAG, "onResponse: " + postID);
                adapter.addAll(newsList);
                mSwipeRefreshLayout.setRefreshing(false);
            }

        };
    }

    private Response.ErrorListener getNewsFailedListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                noDataText.setVisibility(View.VISIBLE);
                noDataText.setText("خطأ بالشبكه");
                noDataText.setEnabled(true);
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));
                Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);

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
