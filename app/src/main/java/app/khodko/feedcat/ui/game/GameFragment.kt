package app.khodko.feedcat.ui.game

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import app.khodko.feedcat.App
import app.khodko.feedcat.R
import app.khodko.feedcat.ShareTextInterface
import app.khodko.feedcat.core.extension.getViewModelExt
import app.khodko.feedcat.core.extension.navigateExt
import app.khodko.feedcat.databinding.FragmentGameBinding
import app.khodko.feedcat.preferences.UserPreferences

class GameFragment : Fragment() {

    private var isAnimateBtn1 = false
    private var isAnimateBtn2 = false
    private var isAnimateBtn3 = false
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
        gameViewModel.animateNumber.observe(viewLifecycleOwner) {
            when (it) {
                0 -> animateBtn1()
                1 -> animateBtn2()
                2 -> animateBtn3()
            }
        }
    }

    private fun initListeners() {
        binding.btnFeed1.setOnClickListener { feed(isAnimateBtn1) }
        binding.btnFeed2.setOnClickListener { feed(isAnimateBtn2) }
        binding.btnFeed3.setOnClickListener { feed(isAnimateBtn3) }
        binding.btnStop.setOnClickListener {
            gameViewModel.save()
            navigateExt(R.id.nav_home)
        }
    }

    private fun feed(flag: Boolean) {
        if (flag) {
            animateFeedCat(1.5f)
            if (gameViewModel.feed()) animateCat()
        } else {
            animateFeedCat(0.7f)
            gameViewModel.take()
        }
    }

    private fun animateBtn1() {
        binding.btnFeed1.animate().apply {
            duration = 1200
            rotationYBy(360f)
            isAnimateBtn1 = true
        }.withEndAction {
            isAnimateBtn1 = false
        }
    }

    private fun animateBtn2() {
        binding.btnFeed2.animate().apply {
            duration = 1200
            rotationYBy(360f)
            isAnimateBtn2 = true
        }.withEndAction {
            isAnimateBtn2 = false
        }
    }

    private fun animateBtn3() {
        binding.btnFeed3.animate().apply {
            duration = 1200
            rotationYBy(360f)
            isAnimateBtn3 = true
        }.withEndAction {
            isAnimateBtn3 = false
        }
    }

    private fun animateCat() {
        binding.imageCat.animate().apply {
            duration = 1000
            rotationYBy(360f)
            scaleX(1.0f)
            scaleY(1.0f)
        }
    }

    private fun animateFeedCat(scale: Float) {
        binding.imageCat.animate().apply {
            duration = 200
            scaleX(scale)
            scaleY(scale)
        }.withEndAction {
            binding.imageCat.animate().apply {
                duration = 200
                scaleX(1.0f)
                scaleY(1.0f)
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
                val str = "Application: Feed the Cat\n" +
                        "User: ${gameViewModel.user?.name}\n" +
                        "Result: satiety = ${gameViewModel.satiety.value}\n"
                val shareTextInterface = requireActivity() as ShareTextInterface
                shareTextInterface.shareText(str)
                true
            }
            else -> false
        }

    override fun onStop() {
        super.onStop()
        gameViewModel.stopTimer()
    }

    override fun onResume() {
        super.onResume()
        gameViewModel.resumeTimer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}