package cz.eman.kaalsample.data.common.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 * This class provides auxiliary functions used by DAO classes.
 *
 * **Be Aware**:
 * This DAO can handle only one concrete database entity [T]
 *
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
interface BaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg entities: T)

    @Update
    suspend fun update(entity: T)

    @Update
    suspend fun update(vararg entities: T)

    @Delete
    suspend fun delete(entity: T)

}