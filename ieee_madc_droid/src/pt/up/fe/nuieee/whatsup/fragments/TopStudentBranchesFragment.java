package pt.up.fe.nuieee.whatsup.fragments;

import java.util.ArrayList;
import java.util.List;

import pt.up.fe.nuieee.whatsup.R;
import pt.up.fe.nuieee.whatsup.adapter.TopSBListAdapter;
import pt.up.fe.nuieee.whatsup.api.AsyncTaskHandler;
import pt.up.fe.nuieee.whatsup.api.FetchAsyncTask;
import pt.up.fe.nuieee.whatsup.api.ServerAPI;
import pt.up.fe.nuieee.whatsup.models.TopItemModel;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

public class TopStudentBranchesFragment extends ListFragment {
	
	ArrayList<TopItemModel> mTopItems;
	TopSBListAdapter mAdapter;
	
	public TopStudentBranchesFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		
		getListView().setDividerHeight(0);
		
		
		mTopItems = new ArrayList<TopItemModel>();
		
		mAdapter = new TopSBListAdapter(getActivity(), mTopItems);
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
						mTopItems.addAll(result);
						mAdapter.notifyDataSetChanged();
					}
				}
				);
		fetchActivitiesTask.execute(ServerAPI.Actions.getTopSBs);
	}


	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		MenuInflater inflater = this.getActivity().getMenuInflater();
		inflater.inflate(R.menu.action_context_event, menu);
	}
	
}
