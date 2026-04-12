package com.pharma.link.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pharma.link.app.data.entities.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("SELECT * FROM notifications_table")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Query("SELECT * FROM notifications_table WHERE notificationId = :notificationId")
    suspend fun getNotificationById(notificationId: Int): NotificationEntity?

    @Query("SELECT * FROM notifications_table WHERE isRead = 0")
    fun getUnreadNotifications(): Flow<List<NotificationEntity>>


    @Query("UPDATE notifications_table SET isRead = 1 WHERE notificationId = :notificationId")
    suspend fun markNotificationAsRead(notificationId: Int)

}