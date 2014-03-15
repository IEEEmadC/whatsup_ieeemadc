package pt.up.fe.nuieee.whatsup.fragments;

import pt.up.fe.nuieee.whatsup.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class MyStudentBranchFragment extends Fragment {
	
	TextView mName;
	TextView mPoints;
	ListView mActivities;
	
	public MyStudentBranchFragment() {
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_my_sb, container, false);
        
        mName = (TextView) rootView.findViewById(R.id.my_student_branch_title);
        mPoints = (TextView) rootView.findViewById(R.id.my_sb_points);
        mActivities = (ListView) rootView.findViewById(R.id.my_sb_activities);

        //TODO: fetch sb details and show them
        return rootView;
    }
}
