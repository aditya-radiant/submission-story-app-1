package com.dicoding.picodiploma.submission_story_app.data.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.picodiploma.submission_story_app.data.response.ListStoryItem

class DiffCallBack(private val mOldStoryList: List<ListStoryItem>,
                   private val mNewStoryList: List<ListStoryItem>
                          ): DiffUtil.Callback() {
    override fun getOldListSize() = mOldStoryList.size

    override fun getNewListSize() = mNewStoryList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        mOldStoryList[oldItemPosition].id == mNewStoryList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldStoryList[oldItemPosition]
        val newEmployee = mNewStoryList[newItemPosition]
        return oldEmployee.id == newEmployee.id
    }
}
