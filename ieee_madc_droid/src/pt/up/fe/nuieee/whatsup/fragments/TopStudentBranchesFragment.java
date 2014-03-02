package pt.up.fe.nuieee.whatsup.fragments;

import java.util.ArrayList;
import java.util.List;

import pt.up.fe.nuieee.whatsup.R;
import pt.up.fe.nuieee.whatsup.adapter.TopSBListAdapter;
import pt.up.fe.nuieee.whatsup.api.AsyncTaskHandler;
import pt.up.fe.nuieee.whatsup.api.FetchAsyncTask;
import pt.up.fe.nuieee.whatsup.models.TopItemModel;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
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
						
						TopItemModel topItem = new TopItemModel();
						topItem.setRanking(1);
						topItem.setStudentBranchName("NuIEEE");
						topItem.setScore(1000);
						List<TopItemModel> r2 = new ArrayList<TopItemModel>();
						r2.add(topItem);
						
						TopItemModel topItem2 = new TopItemModel();
						topItem2.setRanking(2);
						topItem2.setStudentBranchName("ISCTE");
						topItem2.setScore(753);
						r2.add(topItem2);
						
						TopItemModel topItem3 = new TopItemModel();
						topItem3.setRanking(3);
						topItem3.setStudentBranchName("Aveiro");
						topItem3.setScore(253);
						r2.add(topItem3);
						
						mTopItems.addAll(r2);
						mAdapter.notifyDataSetChanged();
						Toast.makeText(getActivity(), "added sample" + mTopItems.size(), Toast.LENGTH_LONG).show();
					}
				}
				);
		fetchActivitiesTask.execute(TopItemModel.class);
	}


	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		MenuInflater inflater = this.getActivity().getMenuInflater();
		inflater.inflate(R.menu.action_context_event, menu);
	}
	
}
