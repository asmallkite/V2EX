package com.example.a10648.v2ex.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a10648.v2ex.R;

/**
 * Created by 10648 on 2016/7/16 0016.
 */
public class UserPageFragment extends Fragment {

    public static final String Hot_URL = "https://www.v2ex.com/api/members/show.json?id=1";

    public UserPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View user_page_view = LayoutInflater.from(getActivity()).inflate(R.layout.eye_user_layout, container, false);
        return user_page_view;
    }
}
