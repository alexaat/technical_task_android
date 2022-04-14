package com.alexaat.technical_task_android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexaat.technical_task_android.model.User
import com.alexaat.technical_task_android.network.Status
import com.alexaat.technical_task_android.network.UsersApiService
import com.alexaat.technical_task_android.utils.token
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UsersViewModel
@Inject
constructor( private val usersApiService: UsersApiService): ViewModel(){

    val users: LiveData<List<User>>
        get() = _users
    private val _users = MutableLiveData<List<User>>()

    private val _status = MutableLiveData(Status.LOADING)
    val status: LiveData<Status>
        get() = _status

    val response: LiveData<Response<User>>
        get() = _response
    private val _response = MutableLiveData<Response<User>>()

    fun getUsers(page: Int){
        viewModelScope.launch {
            _status.value = Status.LOADING
            try{
                _users.value = usersApiService.getUsers(page = page, token = token)
                _status.value = Status.SUCCESS
            }catch(e: Exception){
                _status.value = Status.ERROR
            }
        }
    }

    fun postUser(user: User){
        viewModelScope.launch {
            _response.value = usersApiService.postUser(token, user)
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch {
            _response.value = usersApiService.deleteUser(user.id, token)
        }
    }
}
