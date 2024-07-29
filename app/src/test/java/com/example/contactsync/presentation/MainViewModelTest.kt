package com.example.contactsync.presentation


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.contactsync.domain.usecase.SyncContactsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MainViewModel
    private var syncContactsUseCase = mockk<SyncContactsUseCase>()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(syncContactsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `syncContacts success updates status to Success`() = runTest {
        // Given
        coEvery { syncContactsUseCase.invoke() } returns Unit

        // When
        viewModel.syncContacts()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(SyncStatus.Success, viewModel.syncStatus.value)
    }

    @Test
    fun `syncContacts failure updates status to Error`() = runTest {
        // Given
        val errorMessage = "Sync failed"
        coEvery { syncContactsUseCase.invoke() } throws RuntimeException(errorMessage)

        // When
        viewModel.syncContacts()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val status = viewModel.syncStatus.value
        assertTrue(status is SyncStatus.Error)
        assertEquals(errorMessage, (status as SyncStatus.Error).message)
    }
}