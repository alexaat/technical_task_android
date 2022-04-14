package com.alexaat.technical_task_android.ui

import com.alexaat.technical_task_android.model.User

interface UserLongClickEvent {
    fun onItemLongClick(user: User)
}