package com.julianswiszcz.mobilenik_challenge

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.julianswiszcz.mobilenik_challenge.databinding.ListItemShowBinding
import com.squareup.picasso.Picasso

class ShowAdapter(
    private val callBack: CallBack,
) : ListAdapter<ShowsResponse, ShowAdapter.ViewHolder>(ShowDiffCallback) {

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

    @Suppress("DEPRECATION")
    class ViewHolder(
        private val binding: ListItemShowBinding,
        private val callBack: CallBack,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShowsResponse) {
            binding.txtTitle.text = item.show.name
            item.show.summary?.let {
                binding.txtDescription.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(it, 0)
                } else {
                    Html.fromHtml(it)
                }
            }

            item.show.image?.let {
                Picasso.get().load(it.image).into(binding.img)
            }

            itemView.setOnClickListener {
                callBack.onShowClick(item.show.id)
            }
        }
    }
}

object ShowDiffCallback : DiffUtil.ItemCallback<ShowsResponse>() {

    override fun areItemsTheSame(oldItem: ShowsResponse, newItem: ShowsResponse): Boolean =
        oldItem.show.id == newItem.show.id

    override fun areContentsTheSame(oldItem: ShowsResponse, newItem: ShowsResponse): Boolean =
        oldItem == newItem
}
