package app.khodko.feedcat.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.khodko.feedcat.App
import app.khodko.feedcat.R
import app.khodko.feedcat.core.extension.getViewModelExt
import app.khodko.feedcat.core.extension.navigateExt
import app.khodko.feedcat.database.entity.User
import app.khodko.feedcat.databinding.FragmentHomeBinding
import app.khodko.feedcat.preferences.UserPreferences

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = getViewModelExt {
            HomeViewModel(App.instance.userRepository, App.instance.gameResultRepository)
        }
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        userPreferences = UserPreferences.getInstance(requireContext())

        initObservers()
        initListeners()

        val user = userPreferences.getUser()
        user?.let {
            homeViewModel.setUser(it)
        }

        return binding.root
    }

    private fun initObservers() {
        homeViewModel.existUser.observe(viewLifecycleOwner) {
            if (it) {
                binding.textError.visibility = View.VISIBLE
                binding.textError.text = getString(R.string.registr_error_exists)
            } else {
                binding.textError.visibility = View.GONE
            }
        }
        homeViewModel.loginError.observe(viewLifecycleOwner) {
            if (it) {
                binding.textError.visibility = View.VISIBLE
                binding.textError.text = getString(R.string.login_error)
            } else {
                binding.textError.visibility = View.GONE
            }
        }
        homeViewModel.user.observe(viewLifecycleOwner) {
            it?.let {
                binding.layoutLogin.visibility = View.GONE
                binding.layoutMain.visibility = View.VISIBLE
                binding.textWelcome.text = getString(R.string.text_welcome, it.name)

                userPreferences.saveUser(it)
                homeViewModel.loadLastGameResult()
            }
        }
        homeViewModel.lastGameResult.observe(viewLifecycleOwner) {
            binding.layoutResult.visibility = View.VISIBLE
            binding.textSatiety.text = getString(R.string.text_satiety, it.satiety)
            binding.textDate.text = getString(R.string.text_date, it.datetime)
        }
    }

    private fun initListeners() {
        binding.btnLogin.setOnClickListener { login() }
        binding.btnRegistr.setOnClickListener { registr() }
        binding.btnPlay.setOnClickListener { navigateExt(R.id.nav_game) }
        binding.btnResults.setOnClickListener { navigateExt(R.id.nav_results) }
        binding.btnLogout.setOnClickListener { logout() }
    }

    private fun validate(): User? {
        val name = binding.editTextName.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        when {
            name.isEmpty() -> {
                binding.editTextName.requestFocus()
                binding.editTextName.error = getString(R.string.name_field_error)
            }
            password.isEmpty() -> {
                binding.editTextPassword.requestFocus()
                binding.editTextPassword.error = getString(R.string.password_field_error)
            }
            else -> {
                return User(name, password)
            }
        }
        return null
    }

    private fun registr() {
        validate()?.let { homeViewModel.save(it) }
    }

    private fun login() {
        validate()?.let { homeViewModel.login(it) }
    }

    private fun logout() {
        binding.layoutLogin.visibility = View.VISIBLE
        binding.layoutMain.visibility = View.GONE
        homeViewModel.logout()
        userPreferences.removeUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}