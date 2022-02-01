package app.khodko.feedcat.ui.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.khodko.feedcat.App
import app.khodko.feedcat.core.extension.getViewModelExt
import app.khodko.feedcat.databinding.FragmentResultsBinding
import app.khodko.feedcat.preferences.UserPreferences

class ResultsFragment : Fragment() {

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    private lateinit var resultsViewModel: ResultsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val user = UserPreferences.getInstance(requireContext()).getUser()
        resultsViewModel =
            getViewModelExt { ResultsViewModel(App.instance.gameResultRepository, user) }
        _binding = FragmentResultsBinding.inflate(inflater, container, false)

        initRecycler()

        return binding.root
    }

    private fun initRecycler() {
        val recyclerView = binding.recyclerView
        val adapter = ResultListAdapter()
        recyclerView.adapter = adapter

        resultsViewModel.gameResults.observe(viewLifecycleOwner) {
            it.let { adapter.submitList(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}