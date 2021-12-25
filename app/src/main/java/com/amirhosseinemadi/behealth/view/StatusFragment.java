package com.amirhosseinemadi.behealth.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.amirhosseinemadi.behealth.R;
import com.amirhosseinemadi.behealth.databinding.FragmentStatusBinding;
import com.amirhosseinemadi.behealth.viewmodel.StatusVm;

public class StatusFragment extends Fragment {


    public StatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentStatusBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_status,container,false);
        StatusVm viewModel = new StatusVm();
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

}