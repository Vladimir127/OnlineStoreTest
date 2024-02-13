package com.example.onlinestoretest.presentation.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.onlinestoretest.R
import com.example.onlinestoretest.databinding.FragmentProfileBinding
import com.example.onlinestoretest.presentation.login.LoginActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this@ProfileFragment)[ProfileViewModel::class.java]

        // Отобажаем персональные данные пользователя
        viewModel.userData.observe(viewLifecycleOwner) { userData ->
            binding.fullNameTextView.text =
                resources.getString(R.string.full_name, userData.name, userData.surname)
            binding.phoneTextView.text = userData.phoneNumber
        }
        viewModel.loadData()

        // Отображаем количество товаров в избранном
        viewModel.favoritesCount.observe(viewLifecycleOwner) { count ->
            if (count == 0) {
                binding.favCountTextView.visibility = View.GONE
            } else {
                binding.favCountTextView.visibility = View.VISIBLE
                val favoritesCountText =
                    resources.getQuantityString(R.plurals.products, count, count)
                binding.favCountTextView.text = favoritesCountText
            }
        }
        viewModel.loadFavoritesCount()

        // Обработка нажатия на кнопку "Избранное" - открываем FavoritesFragment
        binding.favoritesLayout.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_favoritesFragment)
        }

        // Обработка нажатия на кнопку "Выйти" - запускаем LoginActivity и завершаем текущую
        viewModel.navigateToLogin.observe(viewLifecycleOwner) { navigateToLogin ->
            if (navigateToLogin) {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}