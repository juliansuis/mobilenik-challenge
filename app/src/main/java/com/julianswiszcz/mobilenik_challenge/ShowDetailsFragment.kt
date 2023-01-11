package com.julianswiszcz.mobilenik_challenge

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.julianswiszcz.mobilenik_challenge.databinding.FragmentShowDetailsBinding
import com.squareup.picasso.Picasso

class ShowDetailsFragment : Fragment(R.layout.fragment_show_details), ShowAdapter.CallBack {

    private var param1: Int? = null

    private lateinit var binding: FragmentShowDetailsBinding
    private lateinit var viewModel: ShowDetailsViewModel
    private val adapter: ShowAdapter = ShowAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentShowDetailsBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.adapter = adapter

        val repository = ShowsRepository()
        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(repository)
        )[ShowDetailsViewModel::class.java]

        param1?.let {
            viewModel.setShowId(it)
        }

        viewModel.show.observe(viewLifecycleOwner) {
            binding.txtTitle.text = it?.name
            it?.image?.image?.let { img ->
                Picasso.get().load(img).into(binding.img)
            }
            @Suppress("DEPRECATION")
            it.summary?.let { summary ->
                binding.txtDescription.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(summary, 0)
                } else {
                    Html.fromHtml(summary)
                }
            }
        }

        viewModel.episodes.observe(viewLifecycleOwner) {
            adapter.submitList(
                it.map { show ->
                    ShowsResponse(show)
                }
            )
        }
    }

    companion object {
        private const val ARG_PARAM1 = "param1"

        fun newInstance(param1: Int) =
            ShowDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }

    override fun onShowClick(showId: Int) {
        Toast.makeText(requireContext(), "No preview available", Toast.LENGTH_SHORT).show()
    }
}
