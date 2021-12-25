package com.amirhosseinemadi.behealth.view;

import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.amirhosseinemadi.behealth.R;
import com.amirhosseinemadi.behealth.common.Application;
import com.amirhosseinemadi.behealth.common.PrefManager;
import com.amirhosseinemadi.behealth.databinding.FragmentStepBinding;
import com.amirhosseinemadi.behealth.util.StepService;
import com.amirhosseinemadi.behealth.util.Calculator;
import com.amirhosseinemadi.behealth.viewmodel.StepVm;

import java.text.DecimalFormat;

public class StepFragment extends Fragment {

    private StepVm viewModel;
    public static int isRunning;
    private PrefManager prefManager;
    private Calculator calculator;
    private DecimalFormat decimalFormat;

    public StepFragment() {
        // Required empty public constructor
        isRunning = 1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentStepBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step, container, false);

        prefManager = Application.dComponent.prefManager();
        calculator = Application.dComponent.calculator();
        decimalFormat = new DecimalFormat("#.#");
        LayerDrawable progressBackground = (LayerDrawable) binding.progress.getBackground();
        //progressBackground.setLevel(260);
        progressBackground.getDrawable(0).setLevel(7500);
        progressBackground.getDrawable(1).setLevel(7500);

        viewModel = new StepVm(getViewLifecycleOwner());
        binding.setViewModel(viewModel);


        return binding.getRoot();

    }


    @Override
    public void onResume() {
        super.onResume();
            StepService.timeLiveData.observe(getViewLifecycleOwner(), new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer != 0) {
                        String str = integer / 60 + "'";
                        viewModel.setTime(str);
                    }
                }
            });


        StepService.stepLiveData.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                String str = String.valueOf(integer);
                viewModel.setSteps(str);
                viewModel.setProgress(integer);
                viewModel.setDistance(decimalFormat.format((calculator.calculateDistance(prefManager.getStep(),prefManager.getStride()))/1000));
                viewModel.setCalories(String.valueOf(calculator.calculateCalories(prefManager.getBmr(),calculator.calculateMet(calculator.calculateDistance(prefManager.getStep(),prefManager.getStride()),prefManager.getTime()),prefManager.getTime())));
            }
        });
        }

    }


