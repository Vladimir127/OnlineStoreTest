package com.example.onlinestoretest.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.onlinestoretest.R
import com.example.onlinestoretest.databinding.FragmentProfileBinding
import com.example.onlinestoretest.ui.login.LoginActivity
import com.example.onlinestoretest.ui.main.MainActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = ViewModelProvider(this@ProfileFragment)[ProfileViewModel::class.java]

        profileViewModel.userData.observe(viewLifecycleOwner) { userData ->
            binding.fullNameTextView.text =
                resources.getString(R.string.full_name, userData.name, userData.surname)
            binding.phoneTextView.text = userData.phoneNumber
        }
        profileViewModel.loadData()

        profileViewModel.navigateToLogin.observe(viewLifecycleOwner) { navigateToLogin ->
            if (navigateToLogin) {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
        binding.logoutButton.setOnClickListener{
            profileViewModel.logout()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}