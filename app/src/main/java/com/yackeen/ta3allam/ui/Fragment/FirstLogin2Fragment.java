package com.yackeen.ta3allam.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yackeen.ta3allam.Capsule.Book;
import com.yackeen.ta3allam.Capsule.Category;
import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.adapter.FirstLoginAdapter;
import com.yackeen.ta3allam.adapter.FirstLoginAdapter2;
import com.yackeen.ta3allam.model.dto.request.FirstLogin1Request;
import com.yackeen.ta3allam.model.dto.request.FirstLogin2Request;
import com.yackeen.ta3allam.model.dto.response.FirstLoginResponse1;
import com.yackeen.ta3allam.model.dto.response.FirstLoginResponse2;
import com.yackeen.ta3allam.server.api.API;
import com.yackeen.ta3allam.ui.activity.FirstLogin;
import com.yackeen.ta3allam.ui.activity.Home;

import java.util.List;

public class FirstLogin2Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView firstLoginRecyclerView2;
    private Button finishButton;
    private FirstLoginAdapter2 firstLoginAdapter2;
    private String TAG="first_login_fragment2";

    public static NewsFeed newInstance(String param1, String param2) {
        NewsFeed fragment = new NewsFeed();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_first_login_2, container, false);
        firstLoginRecyclerView2 = (RecyclerView) rootView.findViewById(R.id.first_login_recyclerview2);
        firstLoginRecyclerView2.setHasFixedSize(true);
        firstLoginRecyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        firstLoginAdapter2= new FirstLoginAdapter2(getContext());
        feachBooksFromApi();
        firstLoginRecyclerView2.setAdapter(firstLoginAdapter2);
        return rootView;
    }
    public void feachBooksFromApi(){
        FirstLogin2Request body = new FirstLogin2Request();
        API.getUserAPIs().getAllbooks(body,getCoursesListener(),
                getCoursesFailedListener(),getContext());


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         finishButton = (Button) getActivity().findViewById(R.id.btn_finish);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Home.class);
                startActivity(intent);
            }
        });
    }
    //network response
    private Response.Listener<FirstLoginResponse2> getCoursesListener(){
        return new Response.Listener<FirstLoginResponse2>() {
            @Override
            public void onResponse(FirstLoginResponse2 response) {
                Log.e(TAG, "network_response:" + response.BooksList.size());
                List<Book> books = response.BooksList;
                Log.d(TAG, "network_response:" + books.size());
                firstLoginAdapter2.addAll(books);
            }
        };
    }
    private Response.ErrorListener getCoursesFailedListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));
                Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();

            }
        };
    }
}
