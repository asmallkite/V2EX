package com.example.a10648.v2ex.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.adapter.MyRecyclerViewAdapter2;
import com.example.a10648.v2ex.model.TopicModel;
import com.example.a10648.v2ex.model.UserModel;
import com.example.a10648.v2ex.net.HttpConnect;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 10648 on 2016/7/16 0016.
 */
public class UserPageFragment extends Fragment {

    public static final String Hot_URL = "https://www.v2ex.com/api/members/show.json?id=";

    String user_url;

    String edit_id;

   UserModel userModel = new UserModel();




    TextView user_name;
    TextView tagline;
    TextView bio;
    ImageView avatarLarge;
    public UserPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View user_page_view = LayoutInflater.from(getActivity()).inflate(R.layout.eye_user_layout, container, false);
        ButterKnife.bind(this, user_page_view);
        ImageButton searchButton = (ImageButton)user_page_view.findViewById(R.id.search_btn);
        user_name = (TextView)user_page_view.findViewById(R.id.username);
        tagline = (TextView)user_page_view.findViewById(R.id.tagline);
        bio = (TextView)user_page_view.findViewById(R.id.bio);
        avatarLarge = (ImageView)user_page_view.findViewById(R.id.avatar_large);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchEdit = (EditText) user_page_view.findViewById(R.id.search_edit);
                edit_id = searchEdit.getText().toString();
                user_url = Hot_URL + edit_id;
                new MyTask().execute();
            }
        });
        return user_page_view;
    }



    public class MyTask extends AsyncTask<UserModel, Integer, UserModel> {


        @Override
        protected UserModel doInBackground(UserModel... params) {
            String response = HttpConnect.sendRequestWithHttpURLConnection(user_url);
            praseJSONWithJSONObject(response);
            return userModel;
        }

        @Override
        protected void onPostExecute(UserModel models) {
            super.onPostExecute(models);
            user_name.setText("用户名： " + models.getUser_name());
            tagline.setText( "签名：  " +models.getTagline());
            bio.setText( "我的心情：  " + models.getBio());
            ImageLoader.getInstance().loadImage(models.getAvatar_large(), new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    avatarLarge.setImageBitmap(loadedImage);
                }
            });



        }

    }






    private void praseJSONWithJSONObject (String jsonData) {
        try {
                JSONObject jsonObject = new JSONObject(jsonData);
                String user_name = jsonObject.getString("username");
                String tagline = jsonObject.getString("tagline");
                String bio = jsonObject.getString("bio");

                String avatar = jsonObject.getString("avatar_large");
                if(avatar.startsWith("//")){
                    avatar = "http:" + avatar;
                }
                userModel.setUser_name(user_name);
                userModel.setTagline(tagline);
                userModel.setBio(bio);
                userModel.setAvatar_large(avatar);
        }catch (Exception e){
            e.printStackTrace();

        }
    }

}
