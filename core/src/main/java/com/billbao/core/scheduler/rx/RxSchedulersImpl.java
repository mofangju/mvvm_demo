/**
 *  Copyright (C) 2018 Bill Bao
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.billbao.core.scheduler.rx;

import com.billbao.core.scheduler.executor.AppExecutors;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulersImpl implements RxSchedulers {
    private Scheduler mDiskScheduler;
    private Scheduler mNetworkScheduler;
    private Scheduler mMainScheduler;

    public RxSchedulersImpl(AppExecutors executors) {
        mDiskScheduler = Schedulers.from(executors.diskIO());
        mNetworkScheduler = Schedulers.from(executors.networkIO());
        mMainScheduler = Schedulers.from(executors.mainThread());
    }

    @Override
    public Scheduler diskIO() {
        return mDiskScheduler;
    }

    @Override
    public Scheduler networkIO() {
        return mNetworkScheduler;
    }

    @Override
    public Scheduler mainThread() {
        return mMainScheduler;
    }

}
