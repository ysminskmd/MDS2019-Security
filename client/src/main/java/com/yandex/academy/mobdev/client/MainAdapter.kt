package com.yandex.academy.mobdev.client

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

class MainAdapter(context: Context, c: Cursor?) : CursorAdapter(context, c, 0) {

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.item_main, parent, false)
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        (view as? TextView)?.text = cursor.getString(cursor.getColumnIndex("name"))
    }
}