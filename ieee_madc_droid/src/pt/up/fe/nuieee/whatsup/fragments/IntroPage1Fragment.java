package pt.up.fe.nuieee.whatsup.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pt.up.fe.nuieee.whatsup.R;

public class IntroPage1Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_intro_page_1, container, false);
        return rootView;
    }
}
