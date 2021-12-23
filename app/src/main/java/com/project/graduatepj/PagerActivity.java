package com.project.graduatepj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class PagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private android.app.FragmentManager fragmentManager;
//    private Button bt;
//    private Button bt2;
    public static int lastPosition = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        Bundle bundleget = getIntent().getExtras();
        String patient = bundleget.getString("patient_num");
        String confirmman = bundleget.getString("confirm");
        String trans = bundleget.getString("rqno");

        Bundle bundle = new Bundle();
        tpr1Fragment fragment1 = new tpr1Fragment();
        tpr2Fragment fragment2 = new tpr2Fragment();
        tpr3Fragment fragment3 = new tpr3Fragment();

        bundle.putString("patient",patient);
        bundle.putString("confirmman",confirmman);
        bundle.putString("trans",trans);

        fragment1.setArguments(bundle);
//        fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.tpr1,tpr1Fragment);
//        fragmentTransaction.commit();

        fragment2.setArguments(bundle);
        fragment3.setArguments(bundle);

//        bt = findViewById(R.id.nextbt);
//        bt2 = findViewById(R.id.frontbt);
//
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(PagerActivity.this,blood_homeActivity.class);
//                startActivity(intent);
//            }
//        });
//        bt2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(PagerActivity.this,blood_homeActivity.class);
//                startActivity(intent);
//            }
//        });

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                lastPosition = position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        setupViewPager(mViewPager);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new tpr1Fragment(), "第一次TPR");
        adapter.addFragment(new tpr2Fragment(), "第二次TPR");
        adapter.addFragment(new tpr3Fragment(), "第三次TPR");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}