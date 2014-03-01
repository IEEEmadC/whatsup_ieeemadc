package pt.up.fe.nuieee.whatsup.fragments;

import java.util.ArrayList;

import pt.up.fe.nuieee.whatsup.adapter.TopSBListAdapter;
import pt.up.fe.nuieee.whatsup.models.TopItemModel;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;

public class TopStudentBranchesFragment extends ListFragment {
	
	ArrayList<TopItemModel> topItems;
	
	public TopStudentBranchesFragment() {
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		topItems = new ArrayList<TopItemModel>();
		TopSBListAdapter mAdapter = new TopSBListAdapter(getActivity(), topItems);
	}
	
	
}
