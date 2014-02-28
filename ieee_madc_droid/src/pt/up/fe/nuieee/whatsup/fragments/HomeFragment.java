package pt.up.fe.nuieee.whatsup.fragments;

import java.util.Date;

import pt.up.fe.nuieee.whatsup.R;
import pt.up.fe.nuieee.whatsup.adapter.FeedListAdapter;
import pt.up.fe.nuieee.whatsup.model.EventModel;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends ListFragment {
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    EventModel[] items = new EventModel[2];
	    EventModel item = new EventModel();
	    item.setDate(new Date());
	    item.setPoints(500);
	    item.setStudentBranch("UP");
	    item.setTitle("coisa magnifica");
	    item.setType("workshop");

	    items[0] = item;
	    items[1] = item;
	    
	    FeedListAdapter adapter = new FeedListAdapter(getActivity(), items);
	    setListAdapter(adapter);
	  }
}
