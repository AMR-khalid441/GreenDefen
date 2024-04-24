package com.example.greendefend.domin.useCase

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greendefend.data.repository.RemoteRepositoryImp
import com.example.greendefend.domin.model.forum.Comment
import com.example.greendefend.domin.model.forum.React
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpRetryException
import javax.inject.Inject

@HiltViewModel
class ForumViewModel @Inject constructor(val remoteRepositoryImp: RemoteRepositoryImp):ViewModel() {
    private val _fileName = MutableLiveData("")
    val fileName: LiveData<String>
        get() = _fileName

    // new added
    fun setFileName(name:String) {
        _fileName.value = name
    }
    fun rest(){
        remoteRepositoryImp.rest()
    }
    val serverResponse: LiveData<String> get() = remoteRepositoryImp.serverResponse
    val connectionError: LiveData<String> get() = remoteRepositoryImp.connectionError
    fun addPost(id: String,
                postValue: String,
                fileUri: Uri,
                fileRealPath: String

    )=viewModelScope.launch {

        viewModelScope.launch {
            try {
                val result= remoteRepositoryImp.addPost(id, postValue,fileUri,fileRealPath)

                if (result.isSuccessful){
                    remoteRepositoryImp.serverResponse.value=result.message()
                }
                else{
                    remoteRepositoryImp.connectionError.value="Password is irrcorect"
                }
            }
            catch (e: IOException)
            {
                e.printStackTrace()
                remoteRepositoryImp.connectionError.value="Internet is not connect"
            }
            catch (e: HttpRetryException){
                remoteRepositoryImp.connectionError.value="Server Not Response "
                e.printStackTrace()
            }
            catch (ex :Exception){
                remoteRepositoryImp.connectionError.value=ex.message

            }

        }

    }
    suspend fun addComment(comment: Comment) {
        viewModelScope.launch {
            try {
                val result=  remoteRepositoryImp.addComment(comment)

                if (result.isSuccessful){
                    remoteRepositoryImp.serverResponse.value=result.message()
                }
                else{
                    remoteRepositoryImp.connectionError.value="Password is irrcorect"
                }
            }
            catch (e: IOException)
            {
                e.printStackTrace()
                remoteRepositoryImp.connectionError.value="Internet is not connect"
            }
            catch (e: HttpRetryException){
                remoteRepositoryImp.connectionError.value="Server Not Response "
                e.printStackTrace()
            }
            catch (ex :Exception){
                remoteRepositoryImp.connectionError.value=ex.message

            }

        }

    }

    suspend fun addReact(react: React){
        viewModelScope.launch {
            try {
                val result=  remoteRepositoryImp.addReact(react)

                if (result.isSuccessful){
                    remoteRepositoryImp.serverResponse.value=result.message()
                }
                else{
                    remoteRepositoryImp.connectionError.value="Password is irrcorect"
                }
            }
            catch (e: IOException)
            {
                e.printStackTrace()
                remoteRepositoryImp.connectionError.value="Internet is not connect"
            }
            catch (e: HttpRetryException){
                remoteRepositoryImp.connectionError.value="Server Not Response "
                e.printStackTrace()
            }
            catch (ex :Exception){
                remoteRepositoryImp.connectionError.value=ex.message

            }

        }
    }


}