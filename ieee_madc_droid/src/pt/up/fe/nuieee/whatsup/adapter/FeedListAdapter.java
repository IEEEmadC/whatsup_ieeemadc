package pt.up.fe.nuieee.whatsup.adapter;

import java.util.List;

import pt.up.fe.nuieee.whatsup.R;
import pt.up.fe.nuieee.whatsup.models.EventModel;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedListAdapter extends ArrayAdapter<EventModel> {
 
	private final Activity context;
	private final List<EventModel> events;

	static class ViewHolder {
		public TextView title;
		public TextView studentBranch;
		public TextView date;
		public ImageView type;
	}

	public FeedListAdapter(Activity context, List<EventModel> srcLogs) {
		super(context, R.layout.feed_item, srcLogs);
		this.context = context;
		this.events = srcLogs;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		// reuse views
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.feed_item, null);
			// configure view holder
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.title = (TextView) rowView.findViewById(R.id.feed_title);
			viewHolder.type = (ImageView) rowView.findViewById(R.id.feed_icon);
			viewHolder.studentBranch = (TextView) rowView.findViewById(R.id.feed_studentbranch);
			viewHolder.date = (TextView) rowView.findViewById(R.id.feed_date);
			rowView.setTag(viewHolder); 
		}

		// fill data
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.date.setText("" + events.get(position).getDate());
		holder.studentBranch.setText(events.get(position).getStudentBranch());
		holder.title.setText(events.get(position).getTitle());
		String eventType = events.get(position).getType().toLowerCase();
		if (eventType.equals("workshop")) {
			holder.type.setImageResource(R.drawable.ic_communities);
		} else if (eventType.equals("conference")) {
			holder.type.setImageResource(R.drawable.present);
		} else if (eventType.equals("talk")) {
			holder.type.setImageResource(R.drawable.talk);
		} else if (eventType.equals("generic")) {
			holder.type.setImageResource(R.drawable.ic_generic);
		} else {
			holder.type.setImageResource(R.drawable.ic_whats_hot);
		}

		return rowView;
	}
}
