package com.happyshoper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class TestFragmentAdapter extends FragmentPagerAdapter {
	private String notification;
	protected static final String[] CONTENT_LOW_BALANCE = new String[] {
			"Page1", "Page2", };
	protected static final String[] CONTENT_MONEY_INCOMING = new String[] {
			"Page1", "Page2", "Page3", };

	public TestFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		if (notification.equalsIgnoreCase("lowBalance")) {
			return TestFragment.newInstance(
					CONTENT_LOW_BALANCE[position % CONTENT_LOW_BALANCE.length]+notification);
		} else if (notification.equalsIgnoreCase("moneyIncoming")) {
			return TestFragment.newInstance(
					CONTENT_MONEY_INCOMING[position
							% CONTENT_MONEY_INCOMING.length]+notification);
		} else {
			return TestFragment.newInstance("");
		}
	}

	@Override
	public int getCount() {
		if (notification.equalsIgnoreCase("lowBalance")) {
			return CONTENT_LOW_BALANCE.length;
		} else if (notification.equalsIgnoreCase("moneyIncoming")) {
			return CONTENT_MONEY_INCOMING.length;
		} else {
			return 0;
		}
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return TestFragmentAdapter.CONTENT_MONEY_INCOMING[position];
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}
}