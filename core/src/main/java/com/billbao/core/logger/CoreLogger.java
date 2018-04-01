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

public class CoreLogger {
    // App Logger uses static member. Hence it's not possible to use Dagger to inject.
    // Use timer logger, the customized  other logger can be used to replace the default logger.
    static Logger sLogger = new TimberLogger();

    public static void d(String s, Object... objects) {
        sLogger.d(s, objects);
    }

    public static void d(Throwable throwable, String s, Object... objects) {
        sLogger.d(throwable, s, objects);
    }

    public static void i(String s, Object... objects) {
        sLogger.i(s, objects);
    }

    public static void i(Throwable throwable, String s, Object... objects) {
        sLogger.i(throwable, s, objects);
    }

    public static void w(String s, Object... objects) {
        sLogger.w(s, objects);
    }

    public static void w(Throwable throwable, String s, Object... objects) {
        sLogger.w(throwable, s, objects);
    }

    public static void e(String s, Object... objects) {
        sLogger.e(s, objects);
    }

    public static void e(Throwable throwable, String s, Object... objects) {
        sLogger.e(throwable, s, objects);
    }
}