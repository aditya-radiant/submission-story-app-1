package com.dicoding.picodiploma.submission_story_app.ui.story

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.submission_story_app.R
import com.dicoding.picodiploma.submission_story_app.data.response.ListStoryItem
import com.dicoding.picodiploma.submission_story_app.databinding.ActivityStoryBinding
import com.dicoding.picodiploma.submission_story_app.ui.adapter.StoryAdapter
import com.dicoding.picodiploma.submission_story_app.model.LoginModel
import com.dicoding.picodiploma.submission_story_app.model.UserPreferences
import com.dicoding.picodiploma.submission_story_app.ui.ViewModelFactory
import com.dicoding.picodiploma.submission_story_app.ui.detail.DetailStoryActivity
import com.dicoding.picodiploma.submission_story_app.ui.login.LoginActivity
import com.dicoding.picodiploma.submission_story_app.ui.login.LoginViewModel
import com.dicoding.picodiploma.submission_story_app.ui.postStory.PostStoryActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
class StoryActivity : AppCompatActivity() {
    private val storyViewModel: StoryViewModel by viewModels()
    private lateinit var adapter: StoryAdapter
    private lateinit var login: LoginModel

    private val binding: ActivityStoryBinding by lazy {
        ActivityStoryBinding.inflate(layoutInflater)
    }

    companion object {
        const val USER_DATA= "user"
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.story)

        adapter = StoryAdapter()
        adapter.notifyDataSetChanged()

        storyViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        storyViewModel.isHaveData.observe(this) {
            showHaveDataOrNot(it)
        }

        login = intent.getParcelableExtra(USER_DATA)!!

        setListStory()

        binding.apply {
            rvUserStory.layoutManager = LinearLayoutManager(this@StoryActivity)
            rvUserStory.adapter = adapter
            rvUserStory.setHasFixedSize(true)
        }

        adapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListStoryItem) {
                Intent(this@StoryActivity, DetailStoryActivity::class.java).also {
                    it.putExtra(DetailStoryActivity.EXTRA_STORY, data)
                    startActivity(it)
                }
            }
        })

        binding.addStory.setOnClickListener{
            val intent = Intent(this, PostStoryActivity::class.java)
            intent.putExtra(PostStoryActivity.EXTRA_DATA, login)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[LoginViewModel::class.java]
        if (item.itemId == R.id.settings) {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }else if (item.itemId == R.id.logout) {
            loginViewModel.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        setListStory()
    }

    private fun setListStory() {
        storyViewModel.showListStory(login.token)
        storyViewModel.itemStory.observe(this) {
            adapter.setListStory(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showHaveDataOrNot(isHaveData: Boolean) {
        binding.rvUserStory.visibility = if (isHaveData) View.VISIBLE else View.GONE

    }

}