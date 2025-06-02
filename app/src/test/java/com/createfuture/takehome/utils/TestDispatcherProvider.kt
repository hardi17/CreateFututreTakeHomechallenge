package com.createfuture.takehome.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

/**
 * TestDispatcherProvider is used to replace standard coroutine dispatchers
 * (Main, IO, Default) with UnconfinedTestDispatcher during unit testing.
 *
 * This allows coroutine-based code to run in a controlled
 * avoiding threading issues in unit tests.
 */

@ExperimentalCoroutinesApi
class TestDispatcherProvider : DispatcherProvider {

    private val testDispatcherProvider = UnconfinedTestDispatcher()

    override val main: CoroutineDispatcher
        get() = testDispatcherProvider

    override val io: CoroutineDispatcher
        get() = testDispatcherProvider

    override val default: CoroutineDispatcher
        get() = testDispatcherProvider
}