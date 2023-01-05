package com.julianswiszcz.mobilenik_challenge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.julianswiszcz.mobilenik_challenge.databinding.FragmentShowSearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShowSearchFragment : Fragment(R.layout.fragment_show_search),
    SearchView.OnQueryTextListener, ShowAdapter.CallBack {

    private lateinit var binding: FragmentShowSearchBinding
    private val showsList = mutableListOf<Show>()
    private lateinit var adapter: ShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_show_search)

        adapter = ShowAdapter(this)
        binding.recycler.adapter = adapter
        binding.searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            getShowById(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun getRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.tvmaze.com/shows/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun getShowById(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<ShowsResponse> =
                getRetrofit().create(APIService::class.java).getShows(query)
            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    val shows: ShowsResponse = call.body() ?: ShowsResponse(emptyList())
                    showsList.clear()
                    showsList.addAll(shows.showsList)
                    adapter.submitList(showsList)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Oops", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onShowClick(showId: Int) {
        childFragmentManager.beginTransaction().replace(R.id.fragment_container, ShowDetailsFragment())
            .commit()
    }
}
