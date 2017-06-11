package com.yackeen.ta3allam.ui.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.yackeen.ta3allam.Capsule.Category;
import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.adapter.TracksAdapter;
import com.yackeen.ta3allam.model.dto.request.FirstLogin1Request;
import com.yackeen.ta3allam.model.dto.response.FirstLoginResponse1;
import com.yackeen.ta3allam.server.api.API;

import java.util.List;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tracks.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tracks#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tracks extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private TracksAdapter tracksAdapter;
    private RecyclerView tracksRecyclerView;
    private String TAG="Tracks_fragmnet";

    public Tracks() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tracks.
     */
    // TODO: Rename and change types and number of parameters
    public static Tracks newInstance(String param1, String param2) {
        Tracks fragment = new Tracks();
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
        View rootView =inflater.inflate(R.layout.fragment_tracks, container, false);
        tracksRecyclerView = (RecyclerView)rootView.findViewById(R.id.track_recyclerview);
        tracksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tracksRecyclerView.setHasFixedSize(true);
        tracksAdapter=new TracksAdapter(getContext());
        feachCategoriesFromApi();
        tracksRecyclerView.setAdapter(tracksAdapter);
     return rootView;
    }
    public void feachCategoriesFromApi(){
        FirstLogin1Request body = new FirstLogin1Request();
        API.getUserAPIs().getAllCourses(body,getCoursesListener(),
                getCoursesFailedListener(),getContext());


    }
    //network response
    private Response.Listener<FirstLoginResponse1> getCoursesListener(){
        return new Response.Listener<FirstLoginResponse1>() {
            @Override
            public void onResponse(FirstLoginResponse1 response) {
                Log.e(TAG,"network_response:"+response.CoursesList.size());
                List<Category> courses = response.CoursesList;
                Log.d(TAG,"network_response:"+courses.size());
                tracksAdapter.addAll(courses);

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
