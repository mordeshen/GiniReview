package com.e.tbreview.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.e.tbreview.model.ItemModel

@Dao
interface MainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemModel: ItemModel): Long

    @Delete
    suspend fun delete(itemModel:ItemModel)

    @Query("DELETE FROM item_model")
    suspend fun deleteAll()

    @Query(
        """
        SELECT * FROM item_model
        ORDER BY pk ASC
        """
    )
    fun getAllBlogPosts(): LiveData<List<ItemModel>>

    @Query("""
        UPDATE item_model 
        SET cellKind = :kind,
        pk = :pk
        WHERE description = :description
    """)
    fun updateBlogPost(kind: Int, description: String, pk:Int)


}