package com.e.tbreview.ui.main.state

import com.e.tbreview.model.ItemModel


data class MainViewState(
    var listField:ListFields = ListFields()
) {
    data class ListFields(
        var apiList: List<ItemModel> = ArrayList(),
        var fullList:List<ItemModel> = ArrayList(),
        var taboolaWidgetList: ItemModel= ItemModel(9,3,"","",""),
        var taboolaFeedList:ItemModel =ItemModel(2,2,"","","")
    )
}