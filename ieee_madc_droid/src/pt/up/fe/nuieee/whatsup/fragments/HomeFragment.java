package pt.up.fe.nuieee.whatsup.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import pt.up.fe.nuieee.whatsup.R;
import pt.up.fe.nuieee.whatsup.adapter.FeedListAdapter;
import pt.up.fe.nuieee.whatsup.api.AsyncTaskHandler;
import pt.up.fe.nuieee.whatsup.api.FetchEventsTask;
import pt.up.fe.nuieee.whatsup.models.EventModel;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class HomeFragment extends ListFragment {

	private FeedListAdapter mFeedListAdapter;
	private ArrayList<EventModel> mEventItems;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mEventItems = new ArrayList<EventModel>();

		EventModel item = new EventModel();
		item.setDate("01-01-2013");
		item.setPoints(500);
		item.setStudentBranch("UP");
		item.setTitle("coisa magnifica");
		item.setDescription("este evento foi mesmo brutalmente fixe.");
		item.setType("workshop");

		mEventItems.add(item);
		mEventItems.add(item);

		mFeedListAdapter = new FeedListAdapter(getActivity(), mEventItems);
		mFeedListAdapter.setNotifyOnChange(true);
		setListAdapter(mFeedListAdapter);

		//getListView().setOnLongClickListener(l)

		registerForContextMenu(this.getListView());


		FetchEventsTask<List<EventModel>> fetchActivitiesTask = new FetchEventsTask<List<EventModel>>(
				new AsyncTaskHandler<List<EventModel>>() {
					@Override
					public void onFailure(Exception error) {
						Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
					}

					public void onSuccess(List<EventModel> result) {

						mEventItems.clear();
						mEventItems.addAll(result);
						mFeedListAdapter.notifyDataSetChanged();
					}
				}
				);
		fetchActivitiesTask.execute();

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

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		EventModel event = mEventItems.get(position);
		FragmentTransaction transaction = super.getFragmentManager().beginTransaction();
		transaction.setCustomAnimations(R.animator.slide_in, R.animator.slide_out);


		EventDetailsFragment eventDetails = new EventDetailsFragment();
		Bundle args = new Bundle();
		args.putString("event", new Gson().toJson(event));
		eventDetails.setArguments(args);
		transaction.replace(R.id.frame_container, eventDetails);
		transaction.addToBackStack(null);
		transaction.commit();
	}
}
