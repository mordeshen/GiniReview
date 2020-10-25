package com.e.tbreview.ui.main.state

sealed class MainStateEvent {

    class GetItemsEvent : MainStateEvent()

    class None : MainStateEvent()


}