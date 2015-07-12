package net.icepc.vertretungsplan;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {
    //MaterialTabHost tabHost;
    ViewPager pager;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        /*
        tabHost = (MaterialTabHost) this.findViewById(R.id.materialTabHost);
        tabHost.setAnimation(null);
        */

        SlidingTabLayout tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        pager = (ViewPager) this.findViewById(R.id.viewpager);

        // init view pager
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accentColor);
            }
        });
        tabs.setViewPager(pager);


    }

}
