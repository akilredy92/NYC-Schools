package com.nycschool.com.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nycschool.com.databinding.AdapterSchoolBinding
import com.nycschool.com.model.Schools

class MainAdapter: RecyclerView.Adapter<MainViewHolder>() {

    var schools = mutableListOf<Schools>()
    lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(item: Schools?)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSchoolList(movies: List<Schools>, listener: OnItemClickListener) {
        this.listener = listener
        this.schools = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterSchoolBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val school = schools[position]
        holder.binding.name.text = school.schoolName
        holder.binding.location.text = school.location
        holder.binding.contact.text = school.phoneNumber
        holder.itemView.setOnClickListener {
          listener.onItemClick(school)
        }
    }

    override fun getItemCount(): Int {
        return schools.size
    }
}

class MainViewHolder(val binding: AdapterSchoolBinding) : RecyclerView.ViewHolder(binding.root) {

}