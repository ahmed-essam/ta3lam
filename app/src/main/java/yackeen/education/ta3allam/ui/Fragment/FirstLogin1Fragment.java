package yackeen.education.ta3allam.ui.Fragment;

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
import yackeen.education.ta3allam.Capsule.Category;
import yackeen.education.ta3allam.adapter.FirstLoginAdapter;
import yackeen.education.ta3allam.model.dto.request.FirstLogin1Request;
import yackeen.education.ta3allam.model.dto.response.FirstLoginResponse1;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.ui.activity.FirstLogin;

import java.util.List;

public class FirstLogin1Fragment extends Fragment {
    private RecyclerView firstLoginRecyclerView;
    private Button nextButton;
    private FirstLoginAdapter firstLoginAdapter;
    private String TAG="first_login_fragment1";
    List<Category> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(yackeen.education.ta3allam.R.layout.fragment_first_login_1, container, false);
        firstLoginRecyclerView =(RecyclerView)rootView.findViewById(yackeen.education.ta3allam.R.id.first_login_recyclerview1);
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

        nextButton = (Button) getActivity().findViewById(yackeen.education.ta3allam.R.id.btn_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstLoginAdapter.getSelected_position() !=-1) {
                    Fragment fragment = FirstLogin2Fragment.newInstance(categories.get(firstLoginAdapter.getSelected_position()).getId());

                    ((FirstLogin) getActivity()).switchFragment(new FirstLogin2Fragment());
                }else{
                    Toast.makeText(getContext(), "اختر فئه اولا", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //network response
    private Response.Listener<FirstLoginResponse1> getCoursesListener(){
        return new Response.Listener<FirstLoginResponse1>() {
            @Override
            public void onResponse(FirstLoginResponse1 response) {
                Log.e(TAG,"network_response:"+response.CoursesList.size());
                categories = response.CoursesList;
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
                Toast.makeText(getActivity(), yackeen.education.ta3allam.R.string.network_error, Toast.LENGTH_SHORT).show();

            }
        };
    }
}
