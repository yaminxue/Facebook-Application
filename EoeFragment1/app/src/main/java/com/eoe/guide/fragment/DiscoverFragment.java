package com.eoe.guide.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eoe.guide.R;
import com.eoe.guide.view.TitleView;
import com.eoe.guide.view.TitleView.OnLeftButtonClickListener;

/**
 * @author yamin
 */
public class DiscoverFragment extends Fragment {
	
	private View mParent;
	
	private FragmentActivity mActivity;

	private TitleView mTitle;

	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	public static DiscoverFragment newInstance(int index) {
		DiscoverFragment f = new DiscoverFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);

		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_discover, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mParent = getView();
		mActivity = getActivity();

		mTitle = (TitleView) mParent.findViewById(R.id.title);
		mTitle.setTitle(R.string.title_discover);
		mTitle.setLeftButton(R.string.back, new OnLeftButtonClickListener() {

			@Override
			public void onClick(View button) {
			}
		});
		mTitle.hiddenLeftButton();
		mTitle.hiddenRightButton();

	}

}
