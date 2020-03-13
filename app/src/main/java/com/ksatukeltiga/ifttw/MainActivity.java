package com.ksatukeltiga.ifttw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private TabLayout tabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.println(Log.INFO, "MainActivityTag", "masuk ON_CREATE");
        tabs = findViewById(R.id.tabs);

        setTabAdapter();

        tabs.addTab(tabs.newTab().setText("Active"));
        tabs.addTab(tabs.newTab().setText("Inactive"));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        FloatingActionButton fab = findViewById(R.id.addFab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddRoutineActivity.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.println(Log.INFO, "MainActivityTag", "masuk ON_START");
    }

    @Override
    public void onResume() {
        super.onResume();

        setTabAdapter();
        Log.println(Log.INFO, "MainActivityTag", "masuk ON_RESUME");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.println(Log.INFO, "MainActivityTag", "masuk ON_PAUSE");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.println(Log.INFO, "MainActivityTag", "masuk ON_STOP");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.println(Log.INFO, "MainActivityTag", "masuk ON_DESTROY");
    }

    private void setTabAdapter()
    {
        final TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), tabs.getTabCount());
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.clearOnTabSelectedListeners();
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

}
