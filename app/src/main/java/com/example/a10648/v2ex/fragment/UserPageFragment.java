package com.example.a10648.v2ex.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.activity.UserDetailActivity;
import com.example.a10648.v2ex.model.UserModel;
import com.example.a10648.v2ex.net.HttpConnect;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 10648 on 2016/7/16 0016.
 */
public class UserPageFragment extends Fragment  implements View.OnClickListener{

    public static final String Hot_URL = "https://www.v2ex.com/api/members/show.json?id=";

    String user_url; //根据id 和 Hot_URL组合的URL

    String edit_id; // 输入的值

    String  web_view_url; //获取的用户url,用于打开webvview

    UserModel userModel = new UserModel();


    TextView user_name;
    TextView tagline;
    TextView bio;
    ImageView avatarLarge;
    Button see_more_btn;

    View user_page_view;


    public UserPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        user_page_view = LayoutInflater.from(getActivity()).inflate(R.layout.eye_user_layout, container, false);


        ImageButton searchButton = (ImageButton) user_page_view.findViewById(R.id.search_btn);
        user_name = (TextView) user_page_view.findViewById(R.id.username);
        tagline = (TextView) user_page_view.findViewById(R.id.tagline);
        bio = (TextView) user_page_view.findViewById(R.id.bio);
        avatarLarge = (ImageView) user_page_view.findViewById(R.id.avatar_large);
        see_more_btn = (Button)user_page_view.findViewById(R.id.see_more);

        searchButton.setOnClickListener(this);
        see_more_btn.setOnClickListener(this);

        return user_page_view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.see_more:
//                Toast.makeText(getActivity(), "see more" + web_view_url, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getContext(), UserDetailActivity.class);
                intent.putExtra("url", web_view_url);

               startActivity(intent);
                break;
            case R.id.search_btn :
                EditText searchEdit = (EditText) user_page_view.findViewById(R.id.search_edit);
                edit_id = searchEdit.getText().toString();
                user_url = Hot_URL + edit_id;
                new MyTask().execute();
                break;
        }

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
            tagline.setText("签名：  " + models.getTagline());
            bio.setText("我的心情：  " + models.getBio());
            ImageLoader.getInstance().loadImage(models.getAvatar_large(), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    avatarLarge.setImageBitmap(loadedImage);
                }
            });


        }

    }


    /**
     * 对HttpConnect 中的sendRequestWithHttpURLConnection 返回的response 进行解析
     * 把解析得到的topicModel 添加到List 中
     *
     * @param jsonData 返回的response
     */

    private void praseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String user_name = jsonObject.getString("username");
            String tagline = jsonObject.getString("tagline");
            String bio = jsonObject.getString("bio");
            web_view_url = jsonObject.getString("url");
            String avatar = jsonObject.getString("avatar_large");
            if (avatar.startsWith("//")) {
                avatar = "http:" + avatar;
            }
            userModel.setUser_name(user_name);
            userModel.setTagline(tagline);
            userModel.setBio(bio);
            userModel.setAvatar_large(avatar);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
