package com.example.a10648.v2ex.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.fragment.EyeFragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private int ic_item_No;// DrawerLayout中菜单中的索引号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_menu_view :
                        Toast.makeText(MainActivity.this, " 系统维护中·····", Toast.LENGTH_SHORT).show();
                        if (ic_item_No != 1) {

                            getSupportFragmentManager().beginTransaction()
                                    .add(R.id.container, new EyeFragment())
                                    .commit();
                            item.setChecked(true);
                            drawerLayout.closeDrawers();
                            toolbar.setTitle("浏览");
                            ic_item_No = 1;
                        } else drawerLayout.closeDrawers();
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
                }

                return false;
            }

        });



    }
}
