/*
 * Copyright 2023 Fragula contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fragula2.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StartupBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startupXmlCompilationNone() = startup(AppVariant.XML, CompilationMode.None())

    @Test
    fun startupXmlCompilationPartial() = startup(AppVariant.XML, CompilationMode.Partial())

    @Test
    fun startupComposeCompilationNone() = startup(AppVariant.COMPOSE, CompilationMode.None())

    @Test
    fun startupComposeCompilationPartial() = startup(AppVariant.COMPOSE, CompilationMode.Partial())

    private fun startup(appVariant: AppVariant, compilationMode: CompilationMode) {
        benchmarkRule.measureRepeated(
            packageName = "com.fragula2.sample",
            metrics = listOf(StartupTimingMetric()),
            compilationMode = compilationMode,
            startupMode = StartupMode.COLD,
            iterations = 5,
        ) {
            pressHome()
            startActivityAndWait(appVariant)
        }
    }
}