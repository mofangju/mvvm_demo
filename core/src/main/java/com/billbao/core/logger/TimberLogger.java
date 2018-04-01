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

package com.billbao.core.logger;

import com.billbao.core.BuildConfig;
import javax.inject.Singleton;
import timber.log.Timber;

@Singleton
public class TimberLogger implements Logger {
    public TimberLogger() {
        Timber.Tree tree;
        if (BuildConfig.DEBUG) {
            tree = new Timber.DebugTree();
        } else {
            tree = new ReleaseTree();
        }
        Timber.plant(tree);
    }

    @Override
    public void d(String s, Object... objects) {
        Timber.d(s, objects);
    }

    @Override
    public void d(Throwable throwable, String s, Object... objects) {
        Timber.d(throwable, s, objects);
    }

    @Override
    public void i(String s, Object... objects) {
        Timber.i(s, objects);
    }

    @Override
    public void i(Throwable throwable, String s, Object... objects) {
        Timber.i(throwable, s, objects);
    }

    @Override
    public void w(String s, Object... objects) {
        Timber.w(s, objects);
    }

    @Override
    public void w(Throwable throwable, String s, Object... objects) {
        Timber.w(throwable, s, objects);
    }

    @Override
    public void e(String s, Object... objects) {
        Timber.e(s, objects);
    }

    @Override
    public void e(Throwable throwable, String s, Object... objects) {
        Timber.e(throwable, s, objects);
    }
}
