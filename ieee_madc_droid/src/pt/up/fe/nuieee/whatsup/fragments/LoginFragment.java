package pt.up.fe.nuieee.whatsup.fragments;

import pt.up.fe.nuieee.whatsup.R;
import pt.up.fe.nuieee.whatsup.api.AsyncTaskHandler;
import pt.up.fe.nuieee.whatsup.api.FetchAsyncTask;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {
	
	private EditText et_username;
	private EditText et_password;
	private Button bt_submit;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
		 View rootView = inflater.inflate(R.layout.fragment_intro_login, container, false);
	        et_password = (EditText) rootView.findViewById(R.id.et_password);
	        et_username = (EditText) rootView.findViewById(R.id.et_username);
	        bt_submit = (Button) rootView.findViewById(R.id.bt_validate_login);
	        
	        bt_submit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (et_password.getText().toString().isEmpty()) {
						et_password.setError("Password is missing...");
						return;
					}
					if (et_username.getText().toString().isEmpty()) {
						et_username.setError("Username is missing...");
						return;
					}
					
					String username = et_username.getText().toString();
					String password = et_password.getText().toString();
					
					//validate login
					new FetchAsyncTask<Boolean>(new AsyncTaskHandler<Boolean>() {

						@Override
						public void onSuccess(Boolean result) {
							if(result) {
								Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
							} else {
								Toast.makeText(getActivity(), "Wrong username or password", Toast.LENGTH_LONG).show();
							}
						}

						@Override
						public void onFailure(Exception error) {
							Toast.makeText(getActivity(), "Wrong username or password", Toast.LENGTH_LONG).show();
							Log.e("Login",error.getMessage());
							
						}
					}, username, password).execute(Boolean.class);
				}
			});
        return rootView;
    }

}
