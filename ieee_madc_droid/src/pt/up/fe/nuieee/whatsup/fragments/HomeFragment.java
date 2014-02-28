package pt.up.fe.nuieee.whatsup.fragments;

import java.util.ArrayList;
import java.util.Date;

import pt.up.fe.nuieee.whatsup.R;
import pt.up.fe.nuieee.whatsup.adapter.FeedListAdapter;
import pt.up.fe.nuieee.whatsup.model.EventModel;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ShareActionProvider;

public class HomeFragment extends ListFragment {
	
	ArrayList<EventModel> mEventItems;
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    mEventItems = new ArrayList<EventModel>();
	    
	    EventModel item = new EventModel();
	    item.setDate(new Date());
	    item.setPoints(500);
	    item.setStudentBranch("UP");
	    item.setTitle("coisa magnifica");
	    item.setDescription("este evento foi mesmo brutalmente fixe.");
	    item.setType("workshop");

	    mEventItems.add(item);
	    mEventItems.add(item);
	    
	    FeedListAdapter adapter = new FeedListAdapter(getActivity(), mEventItems.toArray(new EventModel[0]));
	    setListAdapter(adapter);
	    
	    //getListView().setOnLongClickListener(l)

	    registerForContextMenu(this.getListView());
	  }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);

	    MenuInflater inflater = this.getActivity().getMenuInflater();
	    inflater.inflate(R.menu.action_context_event, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

    	EventModel event = mEventItems.get(info.position);
    	
	    switch (item.getItemId()) {

	        case R.id.action_share:
	        	Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
	        	shareIntent.setType("text/plain");
	        	shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, event.getStudentBranch() + ": " + event.getTitle());
	        	shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, event.getDescription());
	        	
	        	startActivity(Intent.createChooser(shareIntent, "Share with ..."));
	            return true;

	        default:
	            return super.onContextItemSelected(item);
	    }
	}
}
