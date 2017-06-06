package com.yackeen.ta3allam.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.yackeen.ta3allam.Capsule.Category;
import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.adapter.FirstLoginAdapter;
import com.yackeen.ta3allam.model.dto.request.FirstLogin1Request;
import com.yackeen.ta3allam.model.dto.response.FirstLoginResponse1;
import com.yackeen.ta3allam.server.api.API;
import com.yackeen.ta3allam.ui.activity.FirstLogin;
import com.yackeen.ta3allam.ui.activity.LoginActivity;
import com.yackeen.ta3allam.util.UserHelper;

import java.util.List;

public class FirstLogin1Fragment extends Fragment {
    private RecyclerView firstLoginRecyclerView;
    private Button nextButton;
    private FirstLoginAdapter firstLoginAdapter;
    private String TAG="first_login_fragment1";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first_login_1, container, false);
        firstLoginRecyclerView =(RecyclerView)rootView.findViewById(R.id.first_login_recyclerview1);
        firstLoginRecyclerView.setHasFixedSize(true);
        firstLoginRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firstLoginAdapter= new FirstLoginAdapter(getContext());
        feachCategoriesFromApi();
        firstLoginRecyclerView.setAdapter(firstLoginAdapter);

        return rootView;
    }
    public void feachCategoriesFromApi(){
        FirstLogin1Request body = new FirstLogin1Request();
        API.getUserAPIs().getAllCourses(body,getCoursesListener(),
                getCoursesFailedListener(),getContext());


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nextButton = (Button) getActivity().findViewById(R.id.btn_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((FirstLogin)getActivity()).switchFragment(new FirstLogin2Fragment());
            }
        });
    }

    //network response
    private Response.Listener<FirstLoginResponse1> getCoursesListener(){
        return new Response.Listener<FirstLoginResponse1>() {
            @Override
            public void onResponse(FirstLoginResponse1 response) {
                Log.e(TAG,"network_response:"+response.CoursesList.size());
                List<Category> categories = response.CoursesList;
                Log.d(TAG,"network_response:"+categories.size());
                firstLoginAdapter.addAll(categories);

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
