package pt.up.fe.nuieee.whatsup.fragments;

import java.util.ArrayList;
import java.util.List;

import pt.up.fe.nuieee.whatsup.R;
import pt.up.fe.nuieee.whatsup.adapter.FeedListAdapter;
import pt.up.fe.nuieee.whatsup.api.AsyncTaskHandler;
import pt.up.fe.nuieee.whatsup.api.FetchAsyncTask;
import pt.up.fe.nuieee.whatsup.api.ServerAPI;
import pt.up.fe.nuieee.whatsup.models.EventModel;
import pt.up.fe.nuieee.whatsup.settings.Settings;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyStudentBranchFragment extends Fragment implements AsyncTaskHandler<List<EventModel>> {
	
	TextView mName;
	TextView mPoints;
	ListView mActivities;

	List<EventModel> mEventItems;
	FeedListAdapter mFeedListAdapter;
	
	public MyStudentBranchFragment() {
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_my_sb, container, false);
        
        mName = (TextView) rootView.findViewById(R.id.my_student_branch_title);
        mPoints = (TextView) rootView.findViewById(R.id.my_sb_points);
        mActivities = (ListView) rootView.findViewById(R.id.my_sb_activities);

        mEventItems = new ArrayList<EventModel>();
		mFeedListAdapter = new FeedListAdapter(getActivity(), mEventItems);
		mFeedListAdapter.setNotifyOnChange(true);
		mActivities.setAdapter(mFeedListAdapter);
        //TODO: fetch sb details and show them
        
        new FetchAsyncTask<List<EventModel>>(this, Settings.getStudentBranchName(getActivity()))
        	.execute(ServerAPI.Actions.getEventsOfSB);
        
        return rootView;
    }

	@Override
	public void onSuccess(List<EventModel> result) {
		mFeedListAdapter.clear();
		mFeedListAdapter.addAll(result);		
	}

	@Override
	public void onFailure(Exception error) {
		Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();
	}
}
