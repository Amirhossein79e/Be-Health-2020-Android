package com.amirhosseinemadi.behealth.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.amirhosseinemadi.behealth.R;
import com.amirhosseinemadi.behealth.callback.ChangeVmCallback;
import com.amirhosseinemadi.behealth.databinding.FragmentChangeBinding;
import com.amirhosseinemadi.behealth.viewmodel.ChangeVm;
import com.google.android.material.snackbar.Snackbar;


public class ChangeFragment extends Fragment implements ChangeVmCallback {

    FragmentChangeBinding binding;

    public ChangeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change,container,false);
        ChangeVm viewModel = new ChangeVm(this);
        binding.setViewModel(viewModel);


        return binding.getRoot();
    }

    @Override
    public void onSubmit() {

        Snackbar.make(getActivity().findViewById(R.id.layout_main),"Changed",Snackbar.LENGTH_SHORT).setAnchorView(R.id.bottom_navigation_main).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,new StatusFragment()).commit();

    }
}