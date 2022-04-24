package com.dicoding.picodiploma.submission_story_app.ui.detail

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.submission_story_app.data.response.ListStoryItem

class DetailStoryViewModel: ViewModel() {
    lateinit var storyItem: ListStoryItem

    fun setDetailStory(story: ListStoryItem) : ListStoryItem {
        storyItem = story
        return storyItem
    }
}