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

package com.billbao.core.di;

import com.billbao.core.scheduler.executor.AppExecutors;
import com.billbao.core.scheduler.executor.AppExecutorsImpl;
import com.billbao.core.scheduler.executor.DiskIOThreadExecutor;
import com.billbao.core.scheduler.executor.MainThreadExecutor;
import com.billbao.core.scheduler.executor.NetIOThreadExecutor;
import com.billbao.core.scheduler.rx.RxSchedulers;
import com.billbao.core.scheduler.rx.RxSchedulersImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CoreModule {

    @Provides
    @Singleton
    public AppExecutors provideAppExecutors(DiskIOThreadExecutor diskIOThreadExecutor,
                                            NetIOThreadExecutor netIOThreadExecutor,
                                            MainThreadExecutor mainThreadExecutor) {
        return new AppExecutorsImpl(diskIOThreadExecutor, netIOThreadExecutor, mainThreadExecutor);
    }

    @Provides
    @Singleton
    RxSchedulers provideRxSchedulers(AppExecutors appExecutors) {
        return new RxSchedulersImpl(appExecutors);
    }
}
