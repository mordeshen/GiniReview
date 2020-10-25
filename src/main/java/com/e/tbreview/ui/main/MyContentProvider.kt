package com.e.tbreview.ui.main

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.e.tbreview.persistence.AppDatabase
import com.e.tbreview.persistence.ProviderDao

class MyContentProvider : ContentProvider() {
    private val TAG = "MyContentProvider"
    //    @Inject
    lateinit var providerDao :ProviderDao

    companion object {
        // defining authority so that other application can access it
        const val PROVIDER_NAME = "com.e.tbcontentprovider"

        // defining content URI
        const val URL = "content://$PROVIDER_NAME/tb_color"

        // parsing the content URI
        val CONTENT_URI = Uri.parse(URL)
        const val pk = "pk"
        const val color = "color"
        const val uriCode = 1
        var uriMatcher: UriMatcher? = null
        private val values: HashMap<String, String>? = null



        init {

            // to match the content URI
            // every time user access table under content provider
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            // to access whole table
            uriMatcher!!.addURI(
                PROVIDER_NAME,
                "tb_color",
                uriCode
            )

            // to access a particular row
            // of the table
            uriMatcher!!.addURI(
                PROVIDER_NAME,
                "tb_color/*",
                uriCode
            )

        }
    }


    override fun onCreate(): Boolean {
        if (context != null) {
            val appDatabase = AppDatabase.getDatabase(context!!)
            providerDao = appDatabase.getProviderDao()
        }
        return true
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        if (contentValues != null) {
            when (uriMatcher?.match(uri)) {
                uriCode -> {
                    providerDao.insertOrUpdateSearch(
                        contentValues.getAsInteger("pk"),
                        contentValues.getAsString("color")
                    )


                }
            }
        }
        return null
    }


    override fun query(
        uri: Uri,
        selection: Array<out String>?,
        where: String?,
        whereValues: Array<out String>?,
        orderBy: String?
    ): Cursor? {
        when (uriMatcher?.match(uri)) {
            uriCode -> {
                return providerDao.getModelListCursor()
            }
        }
        return null
    }


    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(p0: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
