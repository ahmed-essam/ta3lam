package yackeen.education.ta3allam.ui.Fragment;

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
import yackeen.education.ta3allam.Capsule.Book;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.adapter.FirstLoginAdapter2;
import yackeen.education.ta3allam.model.dto.request.FirstLogin2Request;
import yackeen.education.ta3allam.model.dto.request.SetUserBookRequest;
import yackeen.education.ta3allam.model.dto.response.FirstLoginResponse2;
import yackeen.education.ta3allam.model.dto.response.SetUserBookResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.ui.activity.Home;
import yackeen.education.ta3allam.util.UserHelper;

import java.util.List;

public class FirstLogin2Fragment extends Fragment {
    private static final String ARG_COURSE_ID = "courseID";
    private RecyclerView firstLoginRecyclerView2;
    private Button finishButton;
    private FirstLoginAdapter2 firstLoginAdapter2;
    private String TAG="first_login_fragment2";
    private int courseID;
    private List<Book> books;
    private int selectedPosition;

    public static FirstLogin2Fragment newInstance(int param1) {
        FirstLogin2Fragment fragment = new FirstLogin2Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COURSE_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_first_login_2, container, false);
        if (getArguments() != null) {
            courseID = getArguments().getInt(ARG_COURSE_ID);
        }
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
        int[] courseIds = {courseID};
        body.setCourseIDs(courseIds);
        API.getUserAPIs().getAllbooks(body,getCoursesListener(),
                getCoursesFailedListener(),getContext());


    }
    public void setUserBooksToApi(int[] bookIds){
        SetUserBookRequest body = new SetUserBookRequest();
        body.setUserID(UserHelper.getUserId(getContext()));
        body.setBooksIDs(bookIds);
        API.getUserAPIs().setUserBook(body,setBookListener(),
                setBookFailedListener(),getContext());


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         finishButton = (Button) getActivity().findViewById(R.id.btn_finish);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=firstLoginAdapter2.getSelected_position();
                if (selectedPosition != -1){
                    int[] bookID= {books.get(selectedPosition).getId()};
                    setUserBooksToApi(bookID);
                    Log.e(TAG, "onClick: "+bookID[0]);

                }else{
                    Toast.makeText(getContext(), "اختر كتاب من فضلك", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    //network response
    private Response.Listener<FirstLoginResponse2> getCoursesListener(){
        return new Response.Listener<FirstLoginResponse2>() {
            @Override
            public void onResponse(FirstLoginResponse2 response) {
                Log.e(TAG, "network_response:" + response.BooksList.size());
                books = response.BooksList;
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
    private Response.Listener<SetUserBookResponse> setBookListener(){
        return new Response.Listener<SetUserBookResponse>() {
            @Override
            public void onResponse(SetUserBookResponse response) {
                Intent intent = new Intent(getActivity(), Home.class);
                startActivity(intent);
                getActivity().finish();
                Log.e(TAG, "network_response:add_user_book" + response.isSuccess());
            }
        };
    }
    private Response.ErrorListener setBookFailedListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));
                Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();

            }
        };
    }
}
