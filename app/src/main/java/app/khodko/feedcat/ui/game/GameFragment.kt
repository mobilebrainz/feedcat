package app.khodko.feedcat.ui.game

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import app.khodko.feedcat.App
import app.khodko.feedcat.R
import app.khodko.feedcat.core.extension.getViewModelExt
import app.khodko.feedcat.core.extension.navigateExt
import app.khodko.feedcat.databinding.FragmentGameBinding
import app.khodko.feedcat.preferences.UserPreferences

class GameFragment : Fragment() {

    private var btnId = -1
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var gameViewModel: GameViewModel

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
                0 -> animateBtn(binding.btnFeed1)
                1 -> animateBtn(binding.btnFeed2)
                2 -> animateBtn(binding.btnFeed3)
            }
        }
    }

    private fun initListeners() {
        binding.btnFeed1.setOnClickListener {
            feed(btnId == R.id.btn_feed1)
        }
        binding.btnFeed2.setOnClickListener {
            feed(btnId == R.id.btn_feed2)
        }
        binding.btnFeed3.setOnClickListener {
            feed(btnId == R.id.btn_feed3)
        }
        binding.btnGameOver.setOnClickListener {
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

    private fun animateBtn(button: Button) {
        button.animate().apply {
            duration = 750
            translationYBy(-25f)
            button.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.md_light_green_A700)
            )
            btnId = button.id
        }.withEndAction {
            button.animate().apply {
                duration = 50
                translationYBy(25f)
            }
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            btnId = 0
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
        inflater.inflate(R.menu.home_fragment_options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
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