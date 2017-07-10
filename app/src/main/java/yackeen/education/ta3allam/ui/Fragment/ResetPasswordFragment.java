package yackeen.education.ta3allam.ui.Fragment;

import android.content.Intent;
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

import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.model.dto.request.ResetPasswordRequest;
import yackeen.education.ta3allam.model.dto.request.ResetPasswordRequest2;
import yackeen.education.ta3allam.model.dto.response.ResetPasswordResponse;
import yackeen.education.ta3allam.model.dto.response.ResetPasswordResponse2;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.ui.activity.Home;
import yackeen.education.ta3allam.ui.activity.PasswordResetActivity;
import yackeen.education.ta3allam.util.TextHelper;
import yackeen.education.ta3allam.util.UserHelper;

public class ResetPasswordFragment extends Fragment {
    private final String TAG = "resetPhaseTwoFragment";

    private Button finishButton;
    private EditText codeEditText;
    private EditText passwordEditText;
    private EditText confermPasswordEditText;
    public static final String EMAILARG ="email";
    public String email;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(yackeen.education.ta3allam.R.layout.fragment_pasword_reset_phase_two, container, false);
        finishButton = (Button) view.findViewById(R.id.btn_finish);
        codeEditText = (EditText) view.findViewById(R.id.edit_text_code);
        passwordEditText =(EditText) view.findViewById(R.id.edit_text_password);
        confermPasswordEditText = (EditText) view.findViewById(R.id.edit_text_confirm);
        if (getArguments() != null){
            email = getArguments().getString(EMAILARG);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(yackeen.education.ta3allam.R.id.toolbar);
        ((PasswordResetActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((PasswordResetActivity) getActivity()).switchFragment(new NewPasswordFragment());
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextHelper.isEditTextEmpty(new EditText[]{codeEditText})
                        && TextHelper.isEditTextEmpty(new EditText[]{passwordEditText})&& TextHelper.isEditTextEmpty(new EditText[]{confermPasswordEditText})){
                    UserHelper.showProgressDialog(getContext(),getString(R.string.send),getString(R.string.sendingCodenow));
                    ResetPasswordRequest2 body = new ResetPasswordRequest2();
                    body.setEmail(email);
                    body.setConfirmPassword(confermPasswordEditText.getText().toString());
                    body.setPassword(passwordEditText.getText().toString());
                    body.setVerifyCode(codeEditText.getText().toString());

                    API.getUserAPIs().verifyPasswordFor(
                            body,
                            getListener(),
                            getErrorListener());
                }
            }
        });
    }
    private Response.Listener<ResetPasswordResponse2> getListener() {
        return new Response.Listener<ResetPasswordResponse2>() {
            @Override
            public void onResponse(ResetPasswordResponse2 response) {

                UserHelper.dismissProgressDialog();

                if (response.isSuccess()){
                    getContext().startActivity(new Intent(getContext(), Home.class));
                }
                else Toast.makeText(getContext(), response.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }
    private Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));

                UserHelper.dismissProgressDialog();

                Toast.makeText(getActivity(), yackeen.education.ta3allam.R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        };
    }

}
