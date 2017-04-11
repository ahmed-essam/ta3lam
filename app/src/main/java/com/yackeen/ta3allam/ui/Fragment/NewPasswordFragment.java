package com.yackeen.ta3allam.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.model.dto.request.ResetPasswordRequest;
import com.yackeen.ta3allam.model.dto.response.ResetPasswordResponse;
import com.yackeen.ta3allam.server.api.API;
import com.yackeen.ta3allam.ui.activity.PasswordResetActivity;
import com.yackeen.ta3allam.util.TextHelper;
import com.yackeen.ta3allam.util.UserHelper;

public class NewPasswordFragment extends Fragment{

    //Tags
    private final String TAG = "resetPhaseOneFragment";

    //Views
    private EditText emailEditText;

    //Data
    private PasswordResetActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pasword_reset_phase_one, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeLayoutElements();
    }

    //Initialization
    private void initializeLayoutElements() {

        activity = (PasswordResetActivity) getActivity();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((PasswordResetActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().finish();
            }
        });

        emailEditText = (EditText) getActivity().findViewById(R.id.edit_text_email);

        Button nextButton = (Button) getActivity().findViewById(R.id.btn_next);
        nextButton.setOnClickListener(nextButtonClickListener());
    }

    //Listeners
    private View.OnClickListener nextButtonClickListener(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextHelper.isEditTextEmpty(new EditText[]{emailEditText})
                        && TextHelper.isEmail(emailEditText)){

                    ResetPasswordRequest body = new ResetPasswordRequest();
                    body.email = emailEditText.getText().toString().trim();

                    API.getUserAPIs().resetPasswordFor(
                            body,
                            getListener(),
                            getErrorListener());
                }
            }
        };
    }
    private Response.Listener<ResetPasswordResponse> getListener() {
        return new Response.Listener<ResetPasswordResponse>() {
            @Override
            public void onResponse(ResetPasswordResponse response) {

                UserHelper.getInstance().dismissProgressDialog();

                if (response.isSuccessful) activity.switchFragment(new ResetPasswordFragment());
                else Toast.makeText(activity, response.errorMessage, Toast.LENGTH_SHORT).show();
            }
        };
    }
    private Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));

                UserHelper.getInstance().dismissProgressDialog();

                Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        };
    }
}
