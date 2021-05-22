package com.recam.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.recam.R


class VagetableSpinnerAdapter(context: Context, val resource: Int, val objects: MutableList<String>):
    ArrayAdapter<String>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return  getCustomView(position, convertView, parent)!!
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)!!
    }

    fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val inflater: LayoutInflater =  LayoutInflater.from(parent!!.context)
        val layout: View = inflater.inflate(com.recam.R.layout.item_credit, parent, false)
        val tvLanguage = layout.findViewById<View>(R.id.tv_itemname) as TextView
        tvLanguage.setText(objects[position])
        return layout
    }

    override fun getCount(): Int {
        return super.getCount()
    }
}