package yackeen.education.ta3allam.ui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;
import yackeen.education.ta3allam.Capsule.Follower;
import yackeen.education.ta3allam.Capsule.UserBooks;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.adapter.FollowersAdapter;
import yackeen.education.ta3allam.adapter.GridCoursesAdapter;
import yackeen.education.ta3allam.model.dto.request.FollowerRequest;
import yackeen.education.ta3allam.model.dto.response.FollwerResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.ui.activity.AllBooksActivity;
import yackeen.education.ta3allam.ui.activity.EditProfileActivity;
import yackeen.education.ta3allam.ui.activity.FriendsActivity;
import yackeen.education.ta3allam.ui.activity.LoginActivity;
import yackeen.education.ta3allam.ui.activity.UserProfileActivity;
import yackeen.education.ta3allam.util.UserHelper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int type;

    private OnFragmentInteractionListener mListener;
    private RecyclerView followerRecyclerView;
    private FollowersAdapter followersAdapter;
    private TextView name;
    private TextView about;
    private TextView aboutWord;
    private TextView studyNow;
    private TextView showAll;
    private TextView followerNumTextView;
    private Button logOut;
    private FloatingActionButton floatingAction;
    CircleImageView userImage;
    GridView gridView;
    GridCoursesAdapter gridCoursesAdapter;
    private static FollwerResponse profileResponse;
    private List<UserBooks> userBooksList;
    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
        View rootView = inflater.inflate(yackeen.education.ta3allam.R.layout.fragment_profile, container, false);
        type = UserHelper.getUserType(getContext());
        name = (TextView)rootView.findViewById(yackeen.education.ta3allam.R.id.name);
        followerNumTextView = (TextView)rootView.findViewById(R.id.follower_num_rounded_text);
        followerNumTextView.setVisibility(View.INVISIBLE);
        showAll = (TextView)rootView.findViewById(R.id.show_all_text);
        showAll.setEnabled(false);
        showAll.setVisibility(View.INVISIBLE);
        about = (TextView)rootView.findViewById(yackeen.education.ta3allam.R.id.about);
        aboutWord = (TextView)rootView.findViewById(R.id.about_text_view);
        studyNow = (TextView)rootView.findViewById(R.id.study_now_fragment);
        if (type == 1){
            about.setEnabled(false);
            about.setVisibility(View.INVISIBLE);
            aboutWord.setVisibility(View.INVISIBLE);
            aboutWord.setEnabled(false);
            studyNow.setText(getResources().getString(R.string.study_now));
        }
        userImage=(CircleImageView)rootView.findViewById(yackeen.education.ta3allam.R.id.profile_image);
        logOut=(Button) rootView.findViewById(R.id.btn_log_out);
        floatingAction=(FloatingActionButton) rootView.findViewById(R.id.edit_floating_button);
        gridView =(GridView)rootView.findViewById(yackeen.education.ta3allam.R.id.courses_grid);
        gridCoursesAdapter = new GridCoursesAdapter(getContext(), yackeen.education.ta3allam.R.layout.grid_item_laayout);
        gridView.setAdapter(gridCoursesAdapter);
        followerRecyclerView = (RecyclerView)rootView.findViewById(yackeen.education.ta3allam.R.id.followers_list);
        addListener();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        followerRecyclerView.setLayoutManager(manager);
        followerRecyclerView.hasFixedSize();
        followersAdapter = new FollowersAdapter(getContext());
        feachDataFromApi();
        followerRecyclerView.setAdapter(followersAdapter);
        return rootView;
    }
    public void addListener(){
        followerNumTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent= FriendsActivity.newFriendsIntent(getContext(),UserHelper.getUserId(getContext()));
                startActivity(intent);
            }
        });
        floatingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent=  EditProfileActivity.newEditProfileActivtyIntent(getContext(),profileResponse.getUserPictureURL(),profileResponse.getUserName(),profileResponse.getAbout());
            getContext().startActivity(intent);
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserHelper.removeFromSharedPreferences(UserHelper.USER_ID);
                UserHelper.removeFromSharedPreferences(UserHelper.USER_TYPE);
                UserHelper.removeFromSharedPreferences(UserHelper.security_token);
                getContext().startActivity(new Intent(getContext(), LoginActivity.class));

            }
        });
        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AllBooksActivity.newِAllBooksIntent(getContext(),userBooksList);
                startActivity(intent);
            }
        });
    }
    public void feachDataFromApi(){
        FollowerRequest body = new FollowerRequest();
        body.setUserID(UserHelper.getUserId(getContext()));
        body.setSelectedUserID(UserHelper.getUserId(getContext()));
        API.getUserAPIs().getUserProfileDetail(body,getProfileDataListener(),
                getProfileDataFailedListener(),getContext());
    }
    public void addView(FollwerResponse response){
        name.setText(response.getUserName());
//        level.setText(response.getUserType());
        UserHelper.saveStringInSharedPreferences(UserHelper.User_name,response.getUserName());
        UserHelper.saveStringInSharedPreferences(UserHelper.Photo_url,response.getUserPictureURL());
        about.setText(response.getAbout());
        Picasso.with(getContext()).load(response.getUserPictureURL()).placeholder(R.drawable.default_emam_larg
        ).error(R.drawable.default_emam_larg).into(userImage);

    }

    //network response
    private Response.Listener<FollwerResponse> getProfileDataListener(){
        return new Response.Listener<FollwerResponse>() {
            @Override
            public void onResponse(FollwerResponse response) {
                Log.e(TAG, "network_response:" + response.followers.size());
                addView(response);
                profileResponse = response;
                List<Follower> followers = new ArrayList<>();
                 userBooksList = new ArrayList<>();
                if (response.userBooksList.size() > 4){
                    showAll.setEnabled(true);
                    showAll.setVisibility(View.VISIBLE);
                    for (int i=0;i <4 ;i++){
                    userBooksList.add(response.userBooksList.get(i));
                }
                }else{
                    userBooksList.addAll(response.userBooksList);
                }
                if (response.getFollowersNumber()>6){
                    followerNumTextView.setVisibility(View.VISIBLE);
                    addListener();
                    int followernum = response.getFollowersNumber()-6;
                    followerNumTextView.setText("+"+ followernum);
                    for (int i=0;i <6 ;i++){
                    followers.add(response.followers.get(i));
                }
                }else{
                    followers.addAll(response.followers);
                }
                followersAdapter.addAll(followers);
                gridCoursesAdapter.addAll(userBooksList);
            }
        };
    }
    private Response.ErrorListener getProfileDataFailedListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));
                Toast.makeText(getActivity(), yackeen.education.ta3allam.R.string.network_error, Toast.LENGTH_SHORT).show();

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
