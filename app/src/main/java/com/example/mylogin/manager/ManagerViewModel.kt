package com.example.mylogin.manager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylogin.Pedido
import com.example.mylogin.data.AuthListener
import com.example.mylogin.data.UserRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ManagerViewModel(private val repository: UserRepository): ViewModel() {

    private lateinit var database: DatabaseReference
    var authListener: AuthListener? = null
    private val disposables = CompositeDisposable()

    private val _state = MutableLiveData<ScreenState>()
    val state: LiveData<ScreenState>
        get() = _state


    private val _loadSucessdb = MutableLiveData<Pedido>()
    val loadSucessdb: LiveData<Pedido>
        get() = _loadSucessdb

    init {
        dumbMethod()
    }


    fun loadStatus(){
        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataValues in dataSnapshot.children) {
                    val post: Pedido? = dataValues.getValue(Pedido::class.java)
                    _loadSucessdb.postValue(post)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        database.addListenerForSingleValueEvent(menuListener)
    }

    fun dumbMethod(){
      //  _state.value = ScreenState.Loading
        _state.postValue(ScreenState.Loading)
    }

    fun managerResponse(status:String){
        val disposable = repository.managerResponse(status)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    sealed class ScreenState {
        object Empty : ScreenState()
        object Loading : ScreenState()
        object Sucess : ScreenState()

        data class Error(val message: CharSequence) : ScreenState()
    }


}