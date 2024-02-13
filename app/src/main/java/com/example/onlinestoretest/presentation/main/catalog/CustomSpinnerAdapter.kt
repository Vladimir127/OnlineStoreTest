package com.example.onlinestoretest.presentation.main.catalog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.onlinestoretest.R

class CustomSpinnerAdapter(context: Context, items: Array<String>) :
    ArrayAdapter<String>(context, R.layout.item_spinner, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val row = LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false)

        val textView = row.findViewById<TextView>(R.id.text_view)

        textView.text = getItem(position)

        return row
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val row = LayoutInflater.from(context).inflate(R.layout.item_spinner_dropdown, parent, false)

        val textView = row.findViewById<TextView>(R.id.text_view)
        textView.text = getItem(position)

        return row
    }
}