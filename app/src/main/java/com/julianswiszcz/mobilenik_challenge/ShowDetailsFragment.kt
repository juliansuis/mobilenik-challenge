package com.julianswiszcz.mobilenik_challenge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.julianswiszcz.mobilenik_challenge.databinding.FragmentShowDetailsBinding
import com.squareup.picasso.Picasso

class ShowDetailsFragment : Fragment(R.layout.fragment_show_details) {

    private var param1: Int? = null

    private lateinit var binding: FragmentShowDetailsBinding
    private val viewModel: ShowSearchViewModel by viewModels({ requireParentFragment() })

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

        param1?.let {
            viewModel.setShowId(it)
        }

        viewModel.show.observe(viewLifecycleOwner) {
            binding.txtTitle.text = it.show.name
            Picasso.get().load(it.show.image?.image).into(binding.img)
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
}
