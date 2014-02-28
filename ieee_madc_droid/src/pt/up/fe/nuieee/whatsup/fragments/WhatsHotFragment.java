package pt.up.fe.nuieee.whatsup.fragments;

import java.util.Date;

import pt.up.fe.nuieee.whatsup.adapter.FeedListAdapter;
import pt.up.fe.nuieee.whatsup.model.EventModel;
import android.app.ListFragment;
import android.os.Bundle;

public class WhatsHotFragment extends ListFragment {
	
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    EventModel[] items = new EventModel[1];
	    EventModel item = new EventModel();
	    item.setDate(new Date());
	    item.setPoints(500);
	    item.setStudentBranch("UP");
	    item.setTitle("coisa magnifica");
	    item.setType("workshop");
	    
	    items[0] = item;
	    
	    FeedListAdapter adapter = new FeedListAdapter(getActivity(), items);
	    setListAdapter(adapter);
	  }
}
