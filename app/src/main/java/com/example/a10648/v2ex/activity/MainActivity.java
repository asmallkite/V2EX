package com.example.a10648.v2ex.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.fragment.EyeFragment;

public class MainActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    private long exitTime = 0; //再按一次退出程序用的

    private int ic_item_No;// DrawerLayout中菜单中的索引号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        init();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new EyeFragment())
                .commit();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("浏览");
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        setSupportActionBar(toolbar);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_menu_view :
                        if (ic_item_No != 1) {

                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, new EyeFragment())
                                    .commit();
                            item.setChecked(true);
                            drawerLayout.closeDrawers();
                            toolbar.setTitle("浏览");
                            ic_item_No = 1;
                        } else {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, new EyeFragment())
                                    .commit();
                            drawerLayout.closeDrawers();
                        }
                        break;
                    case R.id.ic_menu_about :
                        if (2 != ic_item_No) {
                            item.setChecked(true);
                            drawerLayout.closeDrawers();
                            toolbar.setTitle("关于");
                            ic_item_No = 2;
                        }
                        Toast.makeText(MainActivity.this, "版本 ： 1.22.21", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.ic_menu_all_node :
                        if (ic_item_No != 3) {

                        startActivity(new Intent(MainActivity.this, AllNodeActivity.class));
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        ic_item_No = 3;
                    } else {
                            startActivity(new Intent(MainActivity.this, AllNodeActivity.class));

                        drawerLayout.closeDrawers();
                    }
                        break;

                }

                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_all_node_search:
                //跳转到搜索的活动
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);


        }
        return true;
    }



    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
           Toast.makeText(MainActivity.this, getString(R.string.main_exitapp_hint),Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
