package com.example.dogsapp.viewvmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dogsapp.model.DogApiService;
import com.example.dogsapp.model.DogBreed;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import retrofit2.Retrofit;

public class ListViewModel extends AndroidViewModel {

    // we need to specify the type this MutableLiveData handles
    public MutableLiveData<List<DogBreed>> dogs = new MutableLiveData<List<DogBreed>>();
    // this is for provide any information if any error is coming from server api
    public MutableLiveData<Boolean> dogLoadError = new MutableLiveData<Boolean>();
    // this to provide information whether data is loaded in background
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    private CompositeDisposable disposable = new CompositeDisposable();

    private DogApiService apiService = new DogApiService();

    public ListViewModel(@NonNull Application application) {
        super(application);

    }

    public void refresh(String abc) {

        fetchFromRemote();
    }

    private void fetchFromRemote() {

        disposable.add(
                apiService.getDogs().subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<DogBreed> dogBreeds) {

                                dogs.setValue(dogBreeds);
                                dogLoadError.setValue(false);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                dogLoadError.setValue(true);
                                loading.setValue(false);
                            }
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
