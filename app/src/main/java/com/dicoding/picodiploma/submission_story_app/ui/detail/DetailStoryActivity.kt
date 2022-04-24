package com.dicoding.picodiploma.submission_story_app.ui.detail


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.submission_story_app.data.response.ListStoryItem
import com.dicoding.picodiploma.submission_story_app.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var story: ListStoryItem
    private lateinit var binding: ActivityDetailStoryBinding
    private val vm: DetailStoryViewModel by viewModels()

    companion object {
        const val EXTRA_STORY = "story"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        story = intent.getParcelableExtra(EXTRA_STORY)!!
        vm.setDetailStory(story)
        displayResult()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun displayResult() {
        with(binding){
            tvUserName.text = vm.storyItem.name
            tvCaption.text = vm.storyItem.description
            Glide.with(imgPost)
                .load(vm.storyItem.photoUrl)
                .into(imgPost)
        }
    }
}