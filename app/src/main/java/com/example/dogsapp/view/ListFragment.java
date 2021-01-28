package com.example.dogsapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dogsapp.R;
import com.example.dogsapp.databinding.FragmentListBinding;
import com.example.dogsapp.model.DogBreed;
import com.example.dogsapp.viewvmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class ListFragment extends Fragment {

    private ListViewModel viewModel;
    private DogListAdapter dogListAdapter = new DogListAdapter(new ArrayList<>());
    FragmentListBinding binding;
    RecyclerView dogList;
    TextView listError;
    ProgressBar loadingView;
    SwipeRefreshLayout refreshLayout;
    NavDirections action;

    public ListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_list, container, false);
        binding = FragmentListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*action = ListFragmentDirections.actionListFragmentToDetailFragment();
        Navigation.findNavController(getView()).navigate(action);*/


        dogList =binding.dogList;
        listError = binding.listError;
        loadingView = binding.loadingView;
        refreshLayout = binding.refreshLayout;

        viewModel = new ViewModelProvider(getActivity()).get(ListViewModel.class);
        viewModel.refresh("abc");

        dogList.setLayoutManager(new LinearLayoutManager(getContext()));
        dogList.setAdapter(dogListAdapter);

        observerViewModel();
    }

    private void observerViewModel(){

        viewModel.dogs.observe(getViewLifecycleOwner(), new Observer<List<DogBreed>>() {
            @Override
            public void onChanged(List<DogBreed> dog) {
                if (dog != null && dog instanceof List){
                    dogList.setVisibility(View.VISIBLE);
                    dogListAdapter.updateDogLit(dog);
                }

            }
        });

        viewModel.dogLoadError.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError!=null&& isError instanceof Boolean){
                    listError.setVisibility(isError?View.VISIBLE:View.GONE);
                }
            }
        });

        viewModel.loading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null && isLoading instanceof Boolean){
                    loadingView.setVisibility(isLoading?View.VISIBLE:View.GONE);

                    if (isLoading){
                        listError.setVisibility(View.GONE);
                        dogList.setVisibility(View.GONE
                        );
                    }
                }
            }
        });
    }
}