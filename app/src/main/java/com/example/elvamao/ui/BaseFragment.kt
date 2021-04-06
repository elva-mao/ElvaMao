package com.example.elvamao.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(getLayoutResId(), container, false)
        initViews(inflater, container)
        initViewModel()
        return root
    }

    /**
     * return the layout resource id of the fragment
     */
    abstract fun getLayoutResId() : Int

    /**
     * subclass init the viewmodel
     */
    abstract fun initViewModel()

    /**
     * init the views, click listeners etc
     */
    abstract fun initViews(inflater: LayoutInflater, container: ViewGroup?)
}
