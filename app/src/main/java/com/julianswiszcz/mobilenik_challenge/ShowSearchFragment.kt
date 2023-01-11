package com.julianswiszcz.mobilenik_challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.julianswiszcz.mobilenik_challenge.databinding.FragmentShowSearchBinding

class ShowSearchFragment : Fragment(R.layout.fragment_show_search),
    SearchView.OnQueryTextListener, ShowAdapter.CallBack {

    private lateinit var viewModel: ShowSearchViewModel

    private lateinit var binding: FragmentShowSearchBinding
    private val adapter: ShowAdapter = ShowAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentShowSearchBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = ShowsRepository()
        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(repository)
        )[ShowSearchViewModel::class.java]
        binding.recycler.adapter = adapter
        binding.search.setOnQueryTextListener(this)

        viewModel.showList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            viewModel.setQuery(query)
        }
        binding.search.clearFocus()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onShowClick(showId: Int) {
        parentFragmentManager.commit {
            replace(R.id.container, ShowDetailsFragment.newInstance(showId))
        }
    }
}
