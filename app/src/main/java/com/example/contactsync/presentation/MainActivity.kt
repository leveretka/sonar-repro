package com.example.contactsync.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.contactsync.presentation.MainViewModel
import com.example.contactsync.presentation.SyncStatus
import com.example.contactsync.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.syncButton.setOnClickListener {
            viewModel.syncContacts()
        }
    }

    private fun observeViewModel() {
        viewModel.syncStatus.observe(this) { status ->
            when (status) {
                is SyncStatus.Idle -> {
                    binding.statusTextView.text = "Status: Not synced"
                    binding.syncProgressBar.visibility = View.GONE
                    binding.syncButton.isEnabled = true
                }

                is SyncStatus.Syncing -> {
                    binding.statusTextView.text = "Status: Syncing..."
                    binding.syncProgressBar.visibility = View.VISIBLE
                    binding.syncButton.isEnabled = false
                }

                is SyncStatus.Success -> {
                    binding.statusTextView.text = "Status: Sync successful"
                    binding.syncProgressBar.visibility = View.GONE
                    binding.syncButton.isEnabled = true
                }

                is SyncStatus.Error -> {
                    binding.statusTextView.text = "Status: Sync failed - ${status.message}"
                    binding.syncProgressBar.visibility = View.GONE
                    binding.syncButton.isEnabled = true
                }
            }
        }
    }
}
