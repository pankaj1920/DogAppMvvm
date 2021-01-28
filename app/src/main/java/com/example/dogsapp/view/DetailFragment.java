package com.example.dogsapp.view;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dogsapp.R;
import com.example.dogsapp.databinding.FragmentDetailBinding;
import com.example.dogsapp.databinding.FragmentListBinding;
import com.example.dogsapp.model.DogBreed;
import com.example.dogsapp.viewvmodel.DetailViewModel;

public class DetailFragment extends Fragment {

    private DetailViewModel detailViewModel;
    FragmentDetailBinding binding;
    ImageView dogImg;
    TextView dogName,dogPurpose,dogTemp;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_detail, container, false);
        binding = FragmentDetailBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dogImg = binding.dogImg;
        dogName = binding.dogName;
        dogPurpose = binding.dogPurpose;
        dogTemp = binding.dogTemp;

        detailViewModel = new ViewModelProvider(getActivity()).get(DetailViewModel.class);

        detailViewModel.fetch();

        observeViewModel();
    }

    private void observeViewModel() {

        detailViewModel.dogLiveData.observe(getViewLifecycleOwner(), new Observer<DogBreed>() {
            @Override
            public void onChanged(DogBreed dogBreed) {
                if (dogBreed != null && dogBreed instanceof  DogBreed){
                    dogName.setText(dogBreed.dogBreed);
                    dogPurpose.setText(dogBreed.bredFor);
                    dogTemp.setText(dogBreed.temperament);

                }
            }
        });
    }
}