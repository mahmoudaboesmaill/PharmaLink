package com.pharma.link.app.data.repositories

import com.pharma.link.app.data.entities.NotificationEntity
import com.pharma.link.app.data.local.dao.NotificationDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val notificationDao: NotificationDao
) {
    suspend fun insertNotification(notification: NotificationEntity) = notificationDao.insertNotification(notification)

    suspend fun getNotificationById(notificationId: Int): NotificationEntity? = notificationDao.getNotificationById(notificationId)

    fun getAllNotifications(): Flow<List<NotificationEntity>> = notificationDao.getAllNotifications()

    fun getUnreadNotifications(): Flow<List<NotificationEntity>> = notificationDao.getUnreadNotifications()

    suspend fun markNotificationAsRead(notificationId: Int) = notificationDao.markNotificationAsRead(notificationId)

}