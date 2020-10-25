package com.e.tbreview.ui

data class UiMessage(
    val message: String,
    val uiMessageType: UIMessageType
)

sealed class UIMessageType{
    class Toast : UIMessageType()

    class Dialog: UIMessageType()

    class AreYouSureDialog(
        val callback: AreYouSureCallback
    ): UIMessageType()

    class None: UIMessageType()
}