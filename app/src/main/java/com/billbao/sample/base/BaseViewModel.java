/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.billbao.sample.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;


import com.billbao.core.scheduler.executor.AppExecutors;
import com.billbao.core.scheduler.rx.RxSchedulers;
import com.billbao.data.NBARepository;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

/**
 * NOTE: Adopt the code from: https://github.com/MindorksOpenSource/android-mvvm-architecture
 *
 * Besides {@link RxSchedulers}, {@link AppExecutors} is added as a separated thread executor for developer.
 * In fact, {@link RxSchedulers} is an wrapper to {@link AppExecutors} during Dagger2 injection.
 *
 * Created by amitshekhar on 07/07/17.
 */

public abstract class BaseViewModel<N> extends ViewModel {

    private final NBARepository mRepository;

    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);

    private RxSchedulers mRxSchedulers;

    private AppExecutors mAppExecutors;  // I add this for extension

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<N> mNavigator;  // I change this to avoid memory leak

    public BaseViewModel(NBARepository dataManager,
                         AppExecutors appExecutors
    ) {
        this.mRepository = dataManager;
        mAppExecutors = appExecutors;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    public BaseViewModel(NBARepository dataManager,
                         RxSchedulers rxSchedulers
    ) {
        this.mRepository = dataManager;
        this.mRxSchedulers = rxSchedulers;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public NBARepository getRepository() {
        return mRepository;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public RxSchedulers getRxSchedulers() {
        return mRxSchedulers;
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }
}
