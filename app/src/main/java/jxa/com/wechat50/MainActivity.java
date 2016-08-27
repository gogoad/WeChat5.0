package jxa.com.wechat50;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jauker.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private FragmentPagerAdapter mFragmentPagerAdapter;
    private TextView mChatTextView;
    private TextView mFriendTextView;
    private TextView mContactTextView;
    private BadgeView mBadgeView;
    private LinearLayout mChatLinearLayout;

    private List<Fragment> mDatas;
    private ImageView mTabline;
    private int mScreen3_1;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabline();
        initView();
    }

    private void initTabline() {
        //获取屏幕的宽高
        mTabline = (ImageView) findViewById(R.id.id_iv_tabline);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        mScreen3_1 = outMetrics.widthPixels / 3;

        //获取图片的宽高
        ViewGroup.LayoutParams layoutParams = mTabline.getLayoutParams();
        layoutParams.width = mScreen3_1;
        mTabline.setLayoutParams(layoutParams);
    }

    //界面初始化
    private void initView() {
        mChatTextView = (TextView) findViewById(R.id.id_tv_chat);
        mFriendTextView = (TextView) findViewById(R.id.id_tv_friend);
        mContactTextView = (TextView) findViewById(R.id.id_tv_contact);
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mChatLinearLayout = (LinearLayout) findViewById(R.id.id_ll_chat);

        mDatas = new ArrayList<Fragment>();
        ChatMainFragment tab01 = new ChatMainFragment();
        FriendMainFragment tab02 = new FriendMainFragment();
        ContactMainFragment tab03 = new ContactMainFragment();

        mDatas.add(tab01);
        mDatas.add(tab02);
        mDatas.add(tab03);

        mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mDatas.get(position);
            }

            @Override
            public int getCount() {
                return mDatas.size();
            }

        };
        mViewPager.setAdapter(mFragmentPagerAdapter);

        /**
         * 滑动中改变字体颜色，使用badgeview消息提醒
         */
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTabline.getLayoutParams();
                layoutParams.leftMargin = position*mScreen3_1+(int)(positionOffset*mScreen3_1);
                Log.w("onPageScrolled: ",layoutParams.leftMargin+"");
                mTabline.setLayoutParams(layoutParams);
                Log.e("onPageScrolled: ",position+","+positionOffset+","+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                resetText();
                switch (position) {
                    case 0:
                        if (mBadgeView != null) {
                            mChatLinearLayout.removeView(mBadgeView);
                        }
                        mBadgeView = new BadgeView(MainActivity.this);
                        mBadgeView.setBadgeCount(20);
                        mChatLinearLayout.addView(mBadgeView);

                        mChatTextView.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 1:
                        mFriendTextView.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 2:
                        mContactTextView.setTextColor(Color.parseColor("#008000"));
                        break;
                }
            }



            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void resetText() {
        mFriendTextView.setTextColor(Color.BLACK);
        mChatTextView.setTextColor(Color.BLACK);
        mContactTextView.setTextColor(Color.BLACK);
    }
}
