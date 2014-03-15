package pt.up.fe.nuieee.whatsup.fragments;

import pt.up.fe.nuieee.whatsup.R;
import pt.up.fe.nuieee.whatsup.activities.MainActivity;
import pt.up.fe.nuieee.whatsup.models.EventModel;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

public class EventDetailsFragment extends Fragment {
	
	TextView tv_title;
	TextView tv_description;
	ImageView iv_icon;
	
	private EventModel mEvent;
	
	public EventDetailsFragment() {
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_event_details, container, false);
		tv_title = (TextView) rootView.findViewById(R.id.tv_title);
		tv_description = (TextView) rootView.findViewById(R.id.tv_details);
		iv_icon = (ImageView) rootView.findViewById(R.id.iv_event_icon);
        
        return rootView;
    }

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
        mEvent = new Gson().fromJson(getArguments().getString("event"), EventModel.class);
        tv_title.setText(mEvent.getTitle());
        tv_description.setText(mEvent.getDescription());
        
        ((MainActivity) getActivity()).getDrawerToggle().setDrawerIndicatorEnabled(false);
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
        ((MainActivity) getActivity()).getDrawerToggle().setDrawerIndicatorEnabled(true);
	}
	


}
