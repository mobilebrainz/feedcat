package app.khodko.feedcat.ui.results

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
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

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean {
                return false
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                resultsViewModel.delete(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}