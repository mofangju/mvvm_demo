/**
 * Copyright (C) 2018 Bill Bao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.billbao.sample.di

import android.app.Application
import android.content.Context

import com.billbao.core.di.CoreModule
import com.billbao.data.di.DataModule
import com.billbao.sample.DemoApp

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, AppModule::class, CoreModule::class, DataModule::class, ActivityBuilder::class))
interface AppComponent {
    fun inject(app: DemoApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}


@Module
internal class AppModule {
    @Provides
    @Singleton
    fun providesContext(application: Application): Context {
        return application
    }
}