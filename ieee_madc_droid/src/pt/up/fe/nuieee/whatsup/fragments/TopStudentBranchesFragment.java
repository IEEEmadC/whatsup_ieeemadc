package pt.up.fe.nuieee.whatsup.fragments;

import java.util.ArrayList;
import java.util.List;

import pt.up.fe.nuieee.whatsup.adapter.TopSBListAdapter;
import pt.up.fe.nuieee.whatsup.api.AsyncTaskHandler;
import pt.up.fe.nuieee.whatsup.api.FetchAsyncTask;
import pt.up.fe.nuieee.whatsup.models.EventModel;
import pt.up.fe.nuieee.whatsup.models.TopItemModel;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class TopStudentBranchesFragment extends ListFragment {
	
	ArrayList<TopItemModel> mTopItems;
	
	public TopStudentBranchesFragment() {
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		mTopItems = new ArrayList<TopItemModel>();
		
		final TopSBListAdapter mAdapter = new TopSBListAdapter(getActivity(), mTopItems);
		mAdapter.setNotifyOnChange(true);
		setListAdapter(mAdapter);
		
		FetchAsyncTask<List<TopItemModel>> fetchActivitiesTask = new FetchAsyncTask<List<TopItemModel>>(
				new AsyncTaskHandler<List<TopItemModel>>() {
					@Override
					public void onFailure(Exception error) {
						Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
					}

					public void onSuccess(List<TopItemModel> result) {

						mTopItems.clear();
						
						TopItemModel topItem = new TopItemModel();
						topItem.setRanking(1);
						topItem.setStudentBranchName("NuIEEE");
						topItem.setScore(100);
						List<TopItemModel> r2 = new ArrayList<TopItemModel>();
						r2.add(topItem);
						
						mTopItems.addAll(r2);
						mAdapter.notifyDataSetChanged();
					}
				}
				);
		fetchActivitiesTask.execute(TopItemModel.class);
	}
	
	
}
