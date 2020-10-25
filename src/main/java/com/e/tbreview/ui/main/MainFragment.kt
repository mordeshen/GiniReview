package com.e.tbreview.ui.main

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.tbreview.R
import com.e.tbreview.model.ItemModel
import com.e.tbreview.ui.DataStateChangeListener
import com.e.tbreview.ui.main.state.MainStateEvent
import com.e.tbreview.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseMainFragment(), MainRecyclerAdapter.Interaction {


    lateinit var mainRecyclerAdapter: MainRecyclerAdapter
    private var fromProviderList = ArrayList<ItemModel>(10)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        initListFromProvider()
        initRecyclerView()

       initUi()
    }

    override fun onResume() {
        super.onResume()
        initUi()
    }

    private fun initUi() {
        getAndCheckDataFromProvider()
        subscribeObservers()
        triggerGetItemsEvent()
    }

    private fun initListFromProvider() {
        for (i in 0..9){
            fromProviderList.add(i,
            ItemModel()
            )
        }

    }

    private fun getAndCheckDataFromProvider(){

        val contentValues = ContentValues(10)


//        context?.contentResolver?.insert(MyContentProvider.CONTENT_URI, contentValues)
        Handler().post {
            val cursor =
                context?.contentResolver?.query(MyContentProvider.CONTENT_URI, null, null, null, null)
            if (cursor != null) {
//                var row =""
                cursor.moveToFirst()
                do {
                    fromProviderList.add((cursor.getInt(cursor.getColumnIndex("pk"))),
                        ItemModel(
                            pk =  (cursor.getInt(cursor.getColumnIndex("pk"))),
                            color = cursor.getString(cursor.getColumnIndex("color")
                        )
                    )
//                    row += "\n\n ============Record============"
//                    row += "\n pk: "+ cursor.getString(cursor.getColumnIndex("pk"))
//                    row += "\n color: "+ cursor.getString(cursor.getColumnIndex("color"))
//
//                    Log.i("row",row
                    )
                } while (cursor.moveToNext())
                cursor.close()

//                tv_output.text = row
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycler_view.adapter = null
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainFragment.context)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            removeItemDecoration(topSpacingDecorator) // does nothing if not applied already
            addItemDecoration(topSpacingDecorator)
            mainRecyclerAdapter = MainRecyclerAdapter(this@MainFragment,requestManager)
            adapter = mainRecyclerAdapter
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            Log.e(TAG, "subscribeObservers: $dataState")
            // Handle Loading and Message
            dataStateHandler.onDataStateChange(dataState)

            // handle Data<T>
            dataState?.data?.let { data ->
                data?.data.let { event ->
                    event?.getContentIfNotHandled().let {
                        it?.listField?.fullList?.let {
                            viewModel.setApiListData(it)
                            considerProviderInList(it)

                        }
                    }

                }
            }
        })


//        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
//            Log.d(TAG, "subscribeObservers: $viewState")
//            if(viewState!=null){
//                mainRecyclerAdapter.apply {
//                    submitList(list = viewState.listField.fullList)
//                }
//            }
//
//        })
    }

    private fun considerProviderInList(list: List<ItemModel>) {
        val considerList = ArrayList<ItemModel>()

        for (i in 0..list.lastIndex){
            considerList.add(i,list[i])
            if (fromProviderList[i].pk == considerList[i].pk){
                considerList[i].color = fromProviderList[i].color
            }
        }


        mainRecyclerAdapter.submitList(considerList.toList())
    }



    fun triggerGetItemsEvent() {
        viewModel.setStateEvent(MainStateEvent.GetItemsEvent())
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_get_numbers -> triggerGetItemsEvent()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateHandler = context as DataStateChangeListener
        } catch (e: ClassCastException) {
            println("$context must implement DataStateListener")
        }

    }


    override fun onItemSelected(position: Int, item: ItemModel) {
        Toast.makeText(context, "${item.name} selected", Toast.LENGTH_SHORT).show()
    }

}