package com.happyshoper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class TestFragmentAdapter extends FragmentPagerAdapter {
	private String notification;

	protected static final String[] TABS = new String[] {
			"partners", "home", "advices", };

    private Context context;

	public TestFragmentAdapter(FragmentManager fm, Context context) {
		super(fm);
        this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
//		if (notification.equalsIgnoreCase("lowBalance")) {
			return TestFragment.newInstance(
					TABS[position], context);
//		} else if (notification.equalsIgnoreCase("moneyIncoming")) {
//			return TestFragment.newInstance(
//					TABS[position
//							% TABS.length]+notification);
//		} else {
//			return TestFragment.newInstance("");
//		}
	}

	@Override
	public int getCount() {
//		if (notification.equalsIgnoreCase("lowBalance")) {
//			return CONTENT_LOW_BALANCE.length;
//		} else if (notification.equalsIgnoreCase("moneyIncoming")) {
			return TABS.length;
//		} else {
//			return 0;
//		}
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return TestFragmentAdapter.TABS[position];
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}
}