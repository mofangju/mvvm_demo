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

package com.billbao.data.di;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.billbao.core.scheduler.executor.AppExecutors;
import com.billbao.data.NBARepository;
import com.billbao.data.NBARepositoryImpl;
import com.billbao.data.local.AppDatabase;
import com.billbao.data.local.LocalNBADataSource;
import com.billbao.data.remote.RemoteNBADataSource;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {
    private final static String DATABASE_NAME = "app-db";

    @Provides
    @Singleton
    public AppDatabase providesAppDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, DATABASE_NAME)
                .build();
    }

    @Provides
    @Singleton
    public LocalNBADataSource providesLocalNBADataSource(AppDatabase appDatabase, AppExecutors appExecutors) {
        return new LocalNBADataSource(appDatabase, appExecutors);
    }

    @Provides
    @Singleton
    public RemoteNBADataSource providesRemoteNBADataSource(Context context, AppExecutors appExecutors) {
        return new RemoteNBADataSource(context, appExecutors);
    }

    @Provides
    @Singleton
    public NBARepository providesNBARepository(LocalNBADataSource localNBADataSource,
                                               RemoteNBADataSource remoteNBADataSource) {
        return new NBARepositoryImpl(localNBADataSource, remoteNBADataSource);
    }
}
