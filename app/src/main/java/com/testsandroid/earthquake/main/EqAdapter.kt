package com.testsandroid.earthquake.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.testsandroid.earthquake.Earthquake
import com.testsandroid.earthquake.R
import com.testsandroid.earthquake.databinding.EqListItemBinding

private val TAG = EqAdapter::class.java.simpleName

class EqAdapter(private val context: Context): ListAdapter<Earthquake, EqAdapter.EqViewHolder>(
    DiffCallback
) {
    companion object DiffCallback : DiffUtil.ItemCallback<Earthquake>(){
        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem == newItem

        }

    }
    lateinit var onItemClickListener: (Earthquake) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EqViewHolder {

        val binding = EqListItemBinding.inflate(LayoutInflater.from(parent.context))

        return EqViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EqViewHolder, position: Int) {
        val earthquake = getItem(position)
        holder.bind(earthquake)

    }
    inner class EqViewHolder(private val binding: EqListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(earthquake: Earthquake){
            binding.magnitudText.text = context.getString(R.string.magnitude_format, earthquake.magnitud)

            binding.eqPlaceTxt.text = earthquake.place

            binding.executePendingBindings()

            binding.root.setOnClickListener{
                if(::onItemClickListener.isInitialized){
                    onItemClickListener(earthquake)
                }else{
                    Log.i(TAG, "OnItemCLickListener not initialized")
                }


            }

        }
    }
}