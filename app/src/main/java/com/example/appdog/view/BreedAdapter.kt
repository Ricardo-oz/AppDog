package com.example.appdog.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.appdog.databinding.ItemBreedBinding
import com.example.appdog.model.local.Dogs

class BreedAdapter : RecyclerView.Adapter<BreedAdapter.VH>() {
    private var listBreed = listOf<Dogs>()
    private val selectedBreed = MutableLiveData<Dogs>()

    fun update(list : List<Dogs>){
        listBreed =list
        notifyDataSetChanged()
    }

    fun selectedBreed(): LiveData<Dogs> = selectedBreed

    inner class VH(private val binding: ItemBreedBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener{
                fun bind(breed : Dogs){
                    binding.tvBreed.text = breed.breed
                    itemView.setOnClickListener(this)
                }

        override fun onClick(v: View?) {

            selectedBreed.value = listBreed[adapterPosition]
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemBreedBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int = listBreed.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(listBreed[position])
    }
}