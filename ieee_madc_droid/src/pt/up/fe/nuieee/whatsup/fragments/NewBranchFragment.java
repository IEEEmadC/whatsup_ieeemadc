package pt.up.fe.nuieee.whatsup.fragments;

import java.util.ArrayList;
import java.util.List;

import pt.up.fe.nuieee.whatsup.R;
import pt.up.fe.nuieee.whatsup.api.AsyncTaskHandler;
import pt.up.fe.nuieee.whatsup.api.FetchAsyncTask;
import pt.up.fe.nuieee.whatsup.api.ServerAPI;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewBranchFragment extends Fragment implements AsyncTaskHandler<Boolean> {
	
	EditText mName; 
	EditText mLocation;
	EditText mWebpage;
	EditText mEmail;
	EditText mPassword;
	Button   bt_submit;
	
	public NewBranchFragment() { }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_edit_student_branch_register, container, false);
        mName 		= (EditText) rootView.findViewById(R.id.create_student_branch_register_student_branch_name);
        mLocation 	= (EditText) rootView.findViewById(R.id.create_student_branch_register_location);
        mWebpage 	= (EditText) rootView.findViewById(R.id.create_student_branch_register_webpage);
        mEmail 		= (EditText) rootView.findViewById(R.id.create_student_branch_register_email);
        mPassword	= (EditText) rootView.findViewById(R.id.create_student_branch_register_password);
        bt_submit	= (Button)	 rootView.findViewById(R.id.bt_create_student_branch);
        
        final List<EditText> editTexts = new ArrayList<EditText>();
        editTexts.add(mName);
        editTexts.add(mLocation);
        editTexts.add(mWebpage);
        editTexts.add(mEmail);
        editTexts.add(mPassword);
        
        bt_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				for(EditText et : editTexts) {
					if(et.getText().toString().isEmpty()) {
						et.setError("This field is required");
					} else {
						new FetchAsyncTask<Boolean>(NewBranchFragment.this, 
								mName.getText().toString(),
								mLocation.getText().toString(),
								mEmail.getText().toString(),
								mPassword.getText().toString(),
								mWebpage.getText().toString()).execute(ServerAPI.Actions.newStudentBranch);
					}
				}
			}
		});
        
        return rootView;
    }

	@Override
	public void onSuccess(Boolean result) {
		if(result) {
			Toast.makeText(getActivity(), "Successfully created!" + result, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getActivity(), "FOI CO CARALHO", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onFailure(Exception error) {
		// TODO Auto-generated method stub
		
	}

}
