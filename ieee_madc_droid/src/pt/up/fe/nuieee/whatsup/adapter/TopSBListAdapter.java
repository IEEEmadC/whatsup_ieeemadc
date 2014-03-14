package pt.up.fe.nuieee.whatsup.adapter;

import java.util.List;

import pt.up.fe.nuieee.whatsup.R;
import pt.up.fe.nuieee.whatsup.models.TopItemModel;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TopSBListAdapter  extends ArrayAdapter<TopItemModel> {

	private final Activity context;
	private final List<TopItemModel> topItems;

	static class ViewHolder {
		public TextView studentBranch;
		public TextView score;
		public TextView ranking;
		public ImageView type;
	}

	public TopSBListAdapter(Activity context, List<TopItemModel> topItems) {
		super(context, R.layout.top_item, topItems);
		this.context = context;
		this.topItems = topItems;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		// reuse views
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.top_item, null);
			// configure view holder
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.studentBranch = (TextView) rowView.findViewById(R.id.top_item_title);
			viewHolder.type = (ImageView) rowView.findViewById(R.id.top_item_icon);
			viewHolder.score = (TextView) rowView.findViewById(R.id.top_item_score);
			viewHolder.ranking = (TextView) rowView.findViewById(R.id.top_item_ranking);
			rowView.setTag(viewHolder); 
		}

		// fill data
		ViewHolder holder = (ViewHolder) rowView.getTag();


		holder.ranking.setText("#" + (position + 1) );
		holder.score.setText("" + topItems.get(position).getPoints());
		holder.studentBranch.setText(topItems.get(position).getStudenBranchName());

		holder.type.setImageResource(R.drawable.ic_communities);

		return rowView;
	}
}
