package com.example.newsapp.ui

import android.R
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.FragmentDetailsBinding
import com.example.newsapp.remote.model.ResponseModel


class DetailsFragment(private val context: Context, private var data: ResponseModel.Article) :
    DialogFragment() {

    private lateinit var bindingOnDetailsFragment: FragmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //mcontext =context
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingOnDetailsFragment =
            FragmentDetailsBinding.inflate(inflater, container, false)
        return bindingOnDetailsFragment.root
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(bindingOnDetailsFragment.ivImageDetails).load(data.urlToImage)
            .into(bindingOnDetailsFragment.ivImageDetails)
        bindingOnDetailsFragment.tvDescription.text = data.description
        bindingOnDetailsFragment.tvTitle.text = data.content
        bindingOnDetailsFragment.tvCardDate.text = data.publishedAt
    }

}