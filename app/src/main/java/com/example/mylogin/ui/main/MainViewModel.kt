package com.example.mylogin.ui.main

import androidx.lifecycle.ViewModel
import com.example.mylogin.AuthListener
import com.example.mylogin.data.UserRepository
import com.github.loadingview.LoadingDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val repository: UserRepository): ViewModel() {

    //auth listener
    var authListener: AuthListener? = null

    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()

    val user by lazy {
        repository.currentUser()
    }

    val logout by lazy {
        repository.logout()
    }


    fun writeData(){
        val disposable = repository.saveData(body = "café com leite",title = "mesa 3",userId = user?.uid?:"001",username = "josé")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun responseDb(){
        val disposable = repository.responseDb(user = "",userId = user?.uid?:"001")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun exit(){
        logout
    }

    //disposing the disposables
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}