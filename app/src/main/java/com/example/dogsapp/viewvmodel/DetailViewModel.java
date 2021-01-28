package com.example.dogsapp.viewvmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dogsapp.model.DogBreed;

public class DetailViewModel extends ViewModel {

    public MutableLiveData<DogBreed> dogLiveData = new MutableLiveData<DogBreed>();

    public void fetch(){
        DogBreed dogB1 = new DogBreed("1","corgi","15 year","","companianon","Frendly","");
        dogLiveData.setValue(dogB1);


    }
}
