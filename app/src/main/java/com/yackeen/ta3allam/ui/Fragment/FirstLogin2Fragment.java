package com.yackeen.ta3allam.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.ui.activity.FirstLogin;
import com.yackeen.ta3allam.ui.activity.Home;

public class FirstLogin2Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first_login_2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button finishButton = (Button) getActivity().findViewById(R.id.btn_finish);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Home.class);
                startActivity(intent);


//                ((FirstLogin)getActivity()).switchFragment(new FirstLogin1Fragment());
            }
        });
    }
}
