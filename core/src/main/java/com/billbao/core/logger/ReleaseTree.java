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

import android.util.Log;

import timber.log.Timber;

public class ReleaseTree extends Timber.Tree {

    @Override
    protected void log(final int priority, final String tag, final String message, final Throwable throwable) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return;
        }

        // Hook here for 3rd party crash report lib.
        // The following is jsut a simple log for demo purpose.
        Timber.log(priority, message, throwable);
    }
}