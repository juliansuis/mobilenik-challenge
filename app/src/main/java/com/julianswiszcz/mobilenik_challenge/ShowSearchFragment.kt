package com.julianswiszcz.mobilenik_challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
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

        val retrofit = APIService.getInstance()
        val repository = ShowsRepository(retrofit)
        viewModel = ViewModelProvider(this, MyViewModelFactory(repository)).get(
            ShowSearchViewModel::class.java
        )
        binding.recycler.adapter = adapter
        binding.search.setOnQueryTextListener(this)

        viewModel.showsList.observe(viewLifecycleOwner) {
            adapter.submitList(it.showsList)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            viewModel.getAllShows(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
    override fun onShowClick(showId: Int) {
        childFragmentManager.commit {
            replace(R.id.container, ShowDetailsFragment.newInstance(showId))
        }
    }
}
