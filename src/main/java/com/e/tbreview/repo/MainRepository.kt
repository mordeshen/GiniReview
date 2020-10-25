package com.e.tbreview.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.e.tbreview.api.ApiService
import com.e.tbreview.api.response.ItemResponse
import com.e.tbreview.ui.main.state.MainViewState
import com.e.tbreview.model.ItemModel
import com.e.tbreview.persistence.MainDao
import com.e.tbreview.session.SessionManager
import com.e.tbreview.ui.DataState
import com.e.tbreview.util.ApiSuccessResponse
import com.e.tbreview.util.GenericApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    val apiService: ApiService,
    val mainDao: MainDao,
    val sessionManager: SessionManager
) : JobManager("MainRepository") {

    private  val TAG = "MainRepository"
    fun getNumbers(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<ItemResponse>, List<ItemModel>,MainViewState>(
            sessionManager.isConnectedToInternet(),
            true,
            true,
            false
        ) {

            override fun createCall(): LiveData<GenericApiResponse<List<ItemResponse>>> {
                return apiService.getListFromApi()
            }

            override fun setJob(job: Job) {
                addJob("getNumbers",job)
            }

//            fun loadFromProvider():List<ModelContentProvider>{
////                return providerDao.searchAll()
//            }

            override fun loadFromCache(): LiveData<MainViewState> {

                return mainDao.getAllBlogPosts()
                    .switchMap {
                        Log.e(TAG, "loadFromCache: $it" )
                        object : LiveData<MainViewState>() {
                            override fun onActive() {
                                super.onActive()
                                value = MainViewState(
                                    MainViewState.ListFields(
                                        fullList = it
                                    )
                                )
                            }
                        }
                    }
            }

            override suspend fun createCacheRequestAndReturn() {
                withContext(Dispatchers.Main) {
                    result.addSource(loadFromCache()) { viewState ->
                        onCompleteJob(DataState.data(viewState, null))
                        Log.e(TAG, "createCacheRequestAndReturn: $viewState" )
                    }

                }

            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<List<ItemResponse>>) {
                Log.e(TAG, "handleApiSuccessResponse: ${response.body.toString()}")


                var itemList: ArrayList<ItemModel> = ArrayList()
                var counter: Int = 0
                for (item in response.body){
                    itemList.add(
                        ItemModel(
                            pk = counter,
                            cellKind = 1,
                            name = item.name,
                            thumbnail = item.thumbnail,
                            description = item.description
                        )
                    )
                    counter++
                    if (counter == 2|| counter==9){
                        counter++
                    }
                }

                for (i in 0..9) {
                    when(i){
                        2 -> {
                            itemList.add(
                                ItemModel(2,2,"","","")
                            )
                        }
                        9 -> {
                            itemList.add(
                                ItemModel(9,3,"","","")
                            )
                        }

                        else -> {

                        }
                    }
                }

                 itemList

                Log.e(TAG, "handleApiSuccessResponse@@@@@@: $itemList" )
                updateLocalDb(itemList)
                createCacheRequestAndReturn()
            }

            override suspend fun updateLocalDb(cacheObject: List<ItemModel>?) {
                Log.e(TAG, "updateLocalDb: $cacheObject" )
                if (cacheObject != null) {
                    withContext(Dispatchers.IO) {

//                        try {
//                            launch { mainDao.deleteAll() }
//                        }
//                        catch (e:Exception){
//                            Log.e(TAG, "updateLocalDb: error in deleting all " )
//                        }

                        for (item in cacheObject) {
                            try {
                                launch {
                                    Log.d(TAG, "updateLocalDb: inserting item: $item")
                                    mainDao.insert(item)
                                }

                            } catch (e: Exception) {
                                Log.e(
                                    TAG, "updateLocalDb: error updating cache" +
                                            "on item with name: ${item.name}"
                                )
                            }
                        }
                    }
                }
            }

        }.asLiveData()
    }
}