package app.khodko.feedcat.ui.game

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import app.khodko.feedcat.App
import app.khodko.feedcat.R
import app.khodko.feedcat.ShareTextInterface
import app.khodko.feedcat.core.extension.getViewModelExt
import app.khodko.feedcat.databinding.FragmentGameBinding
import app.khodko.feedcat.preferences.UserPreferences

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val user = UserPreferences.getInstance(requireContext()).getUser()
        gameViewModel = getViewModelExt { GameViewModel(App.instance.gameResultRepository, user) }
        _binding = FragmentGameBinding.inflate(inflater, container, false)

        initObservers()
        initListeners()

        return binding.root
    }

    private fun initObservers() {
        gameViewModel.satiety.observe(viewLifecycleOwner) {
            binding.textSatiety.text = getString(R.string.text_satiety, it)
        }
    }

    private fun initListeners() {
        binding.btnFeed.setOnClickListener {
            if (gameViewModel.feed()) animateCat()
        }
        binding.btnSave.setOnClickListener { gameViewModel.save() }
    }

    private fun animateCat() {
        binding.imageCat.animate().apply {
            duration = 1000
            rotationYBy(360f)
        }.withEndAction {
            binding.imageCat.animate().apply {
                duration = 1000
                rotationYBy(3600f)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.game_fragment_options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.share -> {
                val resultStr = "Result: 100"
                val shareTextInterface = requireActivity() as ShareTextInterface
                shareTextInterface.shareText(resultStr)
                true
            }
            else -> false
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}