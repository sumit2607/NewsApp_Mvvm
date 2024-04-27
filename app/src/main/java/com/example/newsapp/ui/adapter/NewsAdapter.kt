package com.example.newsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.NewsItrmLayoutBinding
import com.example.newsapp.remote.model.ResponseModel
import com.example.newsapp.ui.DetailsFragment

class NewsAdapter(private var context: Context, private val dataList: List<ResponseModel.Article> , private var fragmentMange : FragmentManager) :
    RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    private lateinit var detailsFragment: DetailsFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsItrmLayoutBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(private val binding: NewsItrmLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ResponseModel.Article) {
            binding.tvCardDesc.text = data.description
            //  binding.tvCardSource.text = data.source.toString()
            binding.tvCardDate.text = data.publishedAt

            // Load image using Glide (you may need to adjust the URL or method based on your actual image loading mechanism)
            Glide.with(binding.root)
                .load(data.urlToImage)
                .into(binding.iCard)

            binding.cardlayout.setOnClickListener {
                detailsFragment = DetailsFragment(context, data)
                detailsFragment.show(fragmentMange , "adapter")
            }

        }
    }
}


