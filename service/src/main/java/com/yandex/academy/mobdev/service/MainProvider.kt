package com.yandex.academy.mobdev.service

import android.content.ContentProvider
import android.content.ContentValues
import android.database.MatrixCursor
import android.net.Uri

class MainProvider : ContentProvider() {

    private val cursor = MatrixCursor(arrayOf("_id", "name"))

    init {
        cursor.addRow(arrayOf(0L, "value #0"))
        cursor.addRow(arrayOf(1L, "value #1"))
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id = cursor.count.toLong()
        cursor.addRow(arrayOf(id, "value #$id"))
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ) = cursor

    override fun onCreate() = true

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw IllegalArgumentException()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw IllegalArgumentException()
    }

    override fun getType(uri: Uri) = null
}