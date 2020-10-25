package com.e.tbreview.persistence

import android.database.Cursor
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.e.tbreview.model.ItemModel
import com.e.tbreview.model.ModelContentProvider


@Dao
interface ProviderDao{

    @Query("SELECT * FROM tb_color")
    fun searchAll():Cursor


    @Insert(onConflict = REPLACE)
    abstract fun insert(itemModel: ModelContentProvider): Long

    @Insert(onConflict = REPLACE)
    abstract fun insert(list: List<ModelContentProvider>)


    @Transaction
    open fun insertOrUpdateSearch(
        pk: Int,
        color: String
    ) {

        val model = getModelContent(pk)

        if (model == null) {

            val listAll = getModelList()
            if (listAll.size > 10) {
                deleteModelByPk(listAll.get(10).pk)
            }
            insert(
                ModelContentProvider(
                    pk,
                    color
                )
            )
        } else {
            updateModel(color, pk)
        }


    }

    @Query("select * from tb_color order by pk asc")
    abstract fun getModelList(): List<ModelContentProvider>

    @Query("select * from tb_color order by pk asc")
    abstract fun getModelListCursor(): Cursor

    @Query("select * from tb_color where pk=:pk ")
    abstract fun getModelContent(
        pk: Int
    ): ModelContentProvider?

    @Query("update tb_color set color=:color where pk=:pk")
    abstract fun updateModel(
        color: String,
        pk: Int
    )

    @Query("delete from tb_color where pk= :pk")
    abstract fun deleteModelByPk(pk: Int)

    @Query("DELETE FROM tb_color WHERE pk =:pk")
    fun delete(pk:Int): Int

    @Update
    fun update(modelContentProvider: ModelContentProvider) : Int
}