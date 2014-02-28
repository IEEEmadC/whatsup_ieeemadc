package pt.up.fe.nuieee.whatsup.fragments;

import pt.up.fe.nuieee.whatsup.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewActivityFragment extends Fragment {
	
	public NewActivityFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_new_activity, container, false);
         
        return rootView;
    }
}
