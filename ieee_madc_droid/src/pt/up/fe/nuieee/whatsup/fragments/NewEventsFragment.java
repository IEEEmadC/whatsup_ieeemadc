package pt.up.fe.nuieee.whatsup.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pt.up.fe.nuieee.whatsup.R;
import pt.up.fe.nuieee.whatsup.activities.MainActivity;
import pt.up.fe.nuieee.whatsup.api.AsyncTaskHandler;
import pt.up.fe.nuieee.whatsup.api.FetchAsyncTask;
import pt.up.fe.nuieee.whatsup.api.ServerAPI;
import pt.up.fe.nuieee.whatsup.models.EventModel;
import pt.up.fe.nuieee.whatsup.models.EventType;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

public class NewEventsFragment extends Fragment implements AsyncTaskHandler<Boolean> {

	public static final String BUNDLE_MODEL_JSON = "model_json";
	public static final String BUNDLE_CREATE_NEW = "create_new";


	EditText mTitle;
	Spinner mType;
	EditText mTags;
	Button mDate;
	EditText mDescription;
	Button mSubmit;

	EventModel mModel;
	List<EditText> mEventList;

	public NewEventsFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			if (getArguments().getBoolean(BUNDLE_CREATE_NEW))
			{
				mModel = new EventModel();
			}
			else
			{
				String json = getArguments().getString(BUNDLE_MODEL_JSON);
				mModel = new Gson().fromJson(json, EventModel.class);
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_edit_event_model, container, false);

		mTitle = (EditText) rootView.findViewById(R.id.create_event_model_title);
		mType = (Spinner) rootView.findViewById(R.id.create_event_model_event_type);
		mTags = (EditText)  rootView.findViewById(R.id.create_event_model_tags);
		mDescription = (EditText)  rootView.findViewById(R.id.create_event_model_description);
		mDate = (Button) rootView.findViewById(R.id.create_event_model_date);
		mSubmit = (Button) rootView.findViewById(R.id.create_event_model_submit);
		
		mEventList = new ArrayList<EditText>();
		mEventList.add(mTitle);
		mEventList.add(mDescription);

		mType.setAdapter(new ArrayAdapter<EventType>(getActivity(), android.R.layout.simple_spinner_item, EventType.values()));

		mDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				Calendar calendar = Calendar.getInstance();
				try {
					calendar.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(mModel.getDate()));
				} catch (ParseException e) {
					Log.e("DATE", e.getMessage());
				}
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);

				new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						Calendar calendar = Calendar.getInstance();
						calendar.set(Calendar.YEAR, year);
						calendar.set(Calendar.MONTH, monthOfYear);
						calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
						mModel.setDate(DateFormat.format("dd-MM-yyyy", calendar.getTime()).toString());
						mDate.setText(mModel.getDate());
					}
				}, year, month, day).show();
			}
		});
 
		mSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for(EditText et : mEventList) {
					if (et.getText().toString().isEmpty()) {
						et.setError("Required field");
						return;
					}
				}
				String eventTitle = mTitle.getText().toString();
				String eventDescription = mDescription.getText().toString();
				String eventSB = getActivity().getSharedPreferences(getResources().getString(R.string.app_name), 0).getString("studentBranch", "");
				String eventDate = mDate.getText().toString();
				String eventTags = mTags.getText().toString();
				String eventType = mType.getSelectedItem().toString();
				
				new FetchAsyncTask<Boolean>(NewEventsFragment.this, 
						eventTitle, eventSB, eventType, eventTags, eventDate, eventDescription)
							.execute(ServerAPI.Actions.newEvent);

			}
		});

		return rootView;
	}

	@Override
	public void onSuccess(Boolean result) {
		if(result) {
			Toast.makeText(getActivity(), "Event successfully created!", Toast.LENGTH_SHORT).show();
			
			((MainActivity) getActivity()).displayView(0);
		} else {
			Toast.makeText(getActivity(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onFailure(Exception error) {
		Toast.makeText(getActivity(), "Something went wrong, please try again.", Toast.LENGTH_SHORT).show();
		Log.e("Event", error.getMessage());
	}
}
