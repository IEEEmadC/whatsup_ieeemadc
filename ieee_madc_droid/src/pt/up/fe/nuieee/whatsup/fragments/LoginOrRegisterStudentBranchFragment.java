package pt.up.fe.nuieee.whatsup.fragments;

import java.util.ArrayList;
import java.util.List;

import pt.up.fe.nuieee.whatsup.R;
import pt.up.fe.nuieee.whatsup.api.AsyncTaskHandler;
import pt.up.fe.nuieee.whatsup.api.FetchAsyncTask;
import pt.up.fe.nuieee.whatsup.api.ServerAPI;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginOrRegisterStudentBranchFragment extends Fragment {

	public static final String BUNDLE_IS_REGISTERING = "is_registering";

	private boolean mIsRegistering;

	private EditText mName;
	private EditText mPassword;
	private EditText mLocation;
	private EditText mWebpage;
	private EditText mEmail;
	private Button mSubmit;
	
	private AsyncTaskHandler<Boolean> mRegisterAsyncHandler;
	
	public LoginOrRegisterStudentBranchFragment() {
		mRegisterAsyncHandler = new AsyncTaskHandler<Boolean>() {

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					Toast.makeText(getActivity(), "Student Branch successfully created!", Toast.LENGTH_LONG).show();
					savePrefs();
					((LoginOrRegisterSucessHandler) getActivity()).onLoginOrRegisterSucess();
				} else {
					Toast.makeText(getActivity(), "This Student Branch is already registered.", Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(Exception error) {
				Toast.makeText(getActivity(), "Something went wrong. Please try again and make sure you have internet connectivity.", Toast.LENGTH_SHORT).show();
				Log.e("Login", error.getMessage());
			}
			
		};
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (getArguments() == null)
		{
			throw new RuntimeException("Must pass BUNDLE_IS_REGISTERING to fragments arguments.");
		}

		mIsRegistering = getArguments().getBoolean(BUNDLE_IS_REGISTERING);

		if (mIsRegistering) {

			View rootView = inflater.inflate(R.layout.fragment_edit_student_branch_register, container, false);
			mName 		= (EditText) rootView.findViewById(R.id.create_student_branch_register_student_branch_name);
			mLocation 	= (EditText) rootView.findViewById(R.id.create_student_branch_register_location);
			mWebpage 	= (EditText) rootView.findViewById(R.id.create_student_branch_register_webpage);
			mEmail 		= (EditText) rootView.findViewById(R.id.create_student_branch_register_email);
			mPassword	= (EditText) rootView.findViewById(R.id.create_student_branch_register_password);
			mSubmit	= (Button)	 rootView.findViewById(R.id.bt_create_student_branch);

			final List<EditText> editTexts = new ArrayList<EditText>();
			editTexts.add(mName);
			editTexts.add(mLocation);
			editTexts.add(mWebpage);
			editTexts.add(mEmail);
			editTexts.add(mPassword);

			mSubmit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					for(EditText et : editTexts) {
						if(et.getText().toString().isEmpty()) {
							et.setError("This field is required");
							return;
						}
					}
					new FetchAsyncTask<Boolean>(mRegisterAsyncHandler,
							mName.getText().toString(),
							mLocation.getText().toString(),
							mEmail.getText().toString(),
							mPassword.getText().toString(),
							mWebpage.getText().toString()).execute(ServerAPI.Actions.newStudentBranch);
				}
			});

			return rootView;

		} else {
			View rootView = inflater.inflate(R.layout.fragment_intro_login, container, false);
			mPassword = (EditText) rootView.findViewById(R.id.et_password);
			mName = (EditText) rootView.findViewById(R.id.et_username);
			mSubmit = (Button) rootView.findViewById(R.id.bt_validate_login);

			mSubmit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mPassword.getText().toString().isEmpty()) {
						mPassword.setError("Password is missing...");
						return;
					}
					if (mName.getText().toString().isEmpty()) {
						mName.setError("Username is missing...");
						return;
					}

					String username = mName.getText().toString();
					String password = mPassword.getText().toString();

					//validate login
					new FetchAsyncTask<Boolean>(new AsyncTaskHandler<Boolean>() {

						@Override
						public void onSuccess(Boolean result) {
							if (result) {
								Toast.makeText(getActivity(), "Successful login", Toast.LENGTH_LONG).show();
								savePrefs();
								((LoginOrRegisterSucessHandler) getActivity()).onLoginOrRegisterSucess();
							} else {
								Toast.makeText(getActivity(), "Wrong username or password", Toast.LENGTH_LONG).show();
							}
						}

						@Override
						public void onFailure(Exception error) {
							Toast.makeText(getActivity(), "Wrong username or password", Toast.LENGTH_LONG).show();
							Log.e("Login",error.getMessage());

						}
					}, username, password).execute(ServerAPI.Actions.authenticateStudentBranch);
				}
			});
			return rootView;
		}
	}
	
	private void savePrefs() {
		SharedPreferences mPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.app_name), 0);
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putString("studentBranch",mName.getText().toString());
		editor.putString("password", mPassword.getText().toString());
		editor.commit();
	}
	
	public static interface LoginOrRegisterSucessHandler {
		public void onLoginOrRegisterSucess();
	}

}
