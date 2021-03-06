package pt.up.fe.nuieee.whatsup.activities;

import java.util.Locale;

import pt.up.fe.nuieee.whatsup.R;
import pt.up.fe.nuieee.whatsup.fragments.IntroPage1Fragment;
import pt.up.fe.nuieee.whatsup.fragments.IntroPage2Fragment;
import pt.up.fe.nuieee.whatsup.fragments.LoginFragment;
import pt.up.fe.nuieee.whatsup.fragments.LoginOrRegisterStudentBranchFragment;
import pt.up.fe.nuieee.whatsup.fragments.LoginOrRegisterStudentBranchFragment.LoginOrRegisterSucessHandler;
import pt.up.fe.nuieee.whatsup.fragments.NewBranchFragment;
import pt.up.fe.nuieee.whatsup.settings.Settings;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class IntroActivity
	extends ProgressActivity
	implements OnPageChangeListener, LoginOrRegisterSucessHandler {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	
	private boolean mIsRegistering;

	private LoginOrRegisterStudentBranchFragment mFragment;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private boolean mIsLastPage = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_intro);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
		
		if (Settings.isFirstTime(this) == false)
		{
			skipToMainActivity();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (mIsLastPage == false)
		{
			return false;
		}
		
		if (mIsRegistering) {
			getMenuInflater().inflate(R.menu.login, menu);
		} else {
			getMenuInflater().inflate(R.menu.new_sb, menu);	
		}
		 
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new IntroPage1Fragment();
			case 1:
				return new IntroPage2Fragment();
			case 2:
				mFragment = new LoginOrRegisterStudentBranchFragment();
				Bundle fragmentArguments = new Bundle();
				fragmentArguments.putBoolean(LoginOrRegisterStudentBranchFragment.BUNDLE_IS_REGISTERING, false);
				mFragment.setArguments(fragmentArguments);
				return mFragment;
			default:

				Fragment fragment = new DummySectionFragment();
				Bundle args = new Bundle();
				args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			}
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_new_sb:
			mIsRegistering = true;
			mFragment.getArguments()
				.putBoolean(LoginOrRegisterStudentBranchFragment.BUNDLE_IS_REGISTERING, mIsRegistering);
			mViewPager.setAdapter(mSectionsPagerAdapter);
			mViewPager.setCurrentItem(2);
			return true;
		case R.id.action_login:
			mIsRegistering = false;
			mFragment.getArguments()
				.putBoolean(LoginOrRegisterStudentBranchFragment.BUNDLE_IS_REGISTERING, mIsRegistering);
			mViewPager.setAdapter(mSectionsPagerAdapter);
			mViewPager.setCurrentItem(2);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		} 
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_intro_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);

			if (getArguments().getInt(ARG_SECTION_NUMBER) != 4)
			{
				dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			}
			return rootView;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		setProgress((int) (position * 100f / 2f), true);
		
		invalidateOptionsMenu();
		mIsLastPage = 2 == position;

		if (position == 3)
		{
			// the last position launches the main activity.
			skipToMainActivity();
		}
	}

	@Override
	public void onLoginOrRegisterSucess() {
		skipToMainActivity();
	}
	
	private void skipToMainActivity() {

		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		Settings.setFirstTimeToFalse(this);
	}

}
