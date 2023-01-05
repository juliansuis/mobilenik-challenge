package com.julianswiszcz.mobilenik_challenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.julianswiszcz.mobilenik_challenge.databinding.ListItemShowBinding
import com.squareup.picasso.Picasso

class ShowAdapter(
    private val callBack: CallBack,
) : ListAdapter<Show, ShowAdapter.ViewHolder>(ShowDiffCallback) {

    interface CallBack {
        fun onShowClick(showId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ListItemShowBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            callBack
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = R.layout.list_item_show

    class ViewHolder(
        private val binding: ListItemShowBinding,
        val callBack: CallBack,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Show) {
            binding.txtTitle.text = item.name
            binding.txtDescription.text = item.summary
            item.image?.let {
                Picasso.get().load(it.smallImage).into(binding.img)
            }

            itemView.setOnClickListener {
                callBack.onShowClick(item.id)
            }
        }
    }
}

object ShowDiffCallback : DiffUtil.ItemCallback<Show>() {

    override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean =
        oldItem == newItem
}
