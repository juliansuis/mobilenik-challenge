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
) : ListAdapter<QueryResponse, ShowAdapter.ViewHolder>(ShowDiffCallback) {

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
        private val callBack: CallBack,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: QueryResponse) {
            binding.txtTitle.text = item.show.name
            binding.txtDescription.text = item.show.summary
            item.show.image?.let {
                Picasso.get().load(it.smallImage).into(binding.img)
            }

            itemView.setOnClickListener {
                callBack.onShowClick(item.show.id)
            }
        }
    }
}

object ShowDiffCallback : DiffUtil.ItemCallback<QueryResponse>() {

    override fun areItemsTheSame(oldItem: QueryResponse, newItem: QueryResponse): Boolean =
        oldItem.show.id == newItem.show.id

    override fun areContentsTheSame(oldItem: QueryResponse, newItem: QueryResponse): Boolean =
        oldItem == newItem
}
