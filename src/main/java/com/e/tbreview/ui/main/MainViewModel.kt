package com.e.tbreview.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.e.tbreview.model.ItemModel
import com.e.tbreview.repo.MainRepository
import com.e.tbreview.session.SessionManager
import com.e.tbreview.ui.BaseViewModel
import com.e.tbreview.ui.DataState
import com.e.tbreview.ui.main.state.MainStateEvent
import com.e.tbreview.ui.main.state.MainViewState
import com.e.tbreview.util.AbsentLiveData
import javax.inject.Inject

class MainViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val repository: MainRepository,
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) : BaseViewModel<MainStateEvent, MainViewState>() {



    override fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        println("DEBUG: New StateEvent detected: $stateEvent")
        when (stateEvent) {

            is MainStateEvent.GetItemsEvent -> {
                return repository.getNumbers()
            }

            is MainStateEvent.None -> {
                return AbsentLiveData.create()
            }
        }
    }

    fun setApiListData(items: List<ItemModel>) {
        val update = getCurrentViewStateOrNew()
        update.listField.apiList = items
        _viewState.value = update
    }

    fun getWholeList():List<ItemModel>{
        getCurrentViewStateOrNew().let {
            return it.listField.fullList?.let {fullList->
                return fullList
            } ?: getDummyList()
        }
    }
    fun getDummyList(): List<ItemModel> {
        var dummyList = ArrayList<ItemModel>(10)
        return dummyList
    }

    fun cancelActiveJobs() {
        repository.cancelActiveJobs()
        handlePendingData()
    }

    private fun handlePendingData() {
        setStateEvent(MainStateEvent.None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

    override fun initNewViewState(): MainViewState {
        return MainViewState()
    }

}













