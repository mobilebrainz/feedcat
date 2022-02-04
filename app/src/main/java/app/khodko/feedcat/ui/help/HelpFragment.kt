package app.khodko.feedcat.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.khodko.feedcat.R
import app.khodko.feedcat.core.extension.getViewModelExt
import app.khodko.feedcat.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {

    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding!!

    private lateinit var helpViewModel: HelpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        helpViewModel = getViewModelExt { HelpViewModel() }
        _binding = FragmentHelpBinding.inflate(inflater, container, false)

        //todo: заменить на массив строк
        val pages = listOf(getString(R.string.page_1), getString(R.string.page_2), getString(R.string.page_3))
        val adapter = HelpPagerAdapter(pages)
        binding.viewPager.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}