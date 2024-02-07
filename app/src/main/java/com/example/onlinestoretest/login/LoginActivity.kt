package com.example.onlinestoretest.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.onlinestoretest.main.MainActivity
import com.example.onlinestoretest.R
import com.example.onlinestoretest.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        // Валидация полей ввода
        binding.nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.validateName(p0.toString())
            }
        })

        viewModel.nameValid.observe(this) { isValid ->
            if (isValid) {
                binding.nameLayout.error = null;
            } else {
                binding.nameLayout.error = "Ошибка"
            }
        }

        binding.surnameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.validateSurname(p0.toString())
            }
        })

        viewModel.surnameValid.observe(this) { isValid ->
            if (isValid) {
                binding.surnameLayout.error = null;
            } else {
                binding.surnameLayout.error = "Ошибка"
            }
        }

        binding.phoneEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.validatePhone(p0.toString())
            }
        })

        viewModel.phoneValid.observe(this) { isValid ->
            if (isValid) {
                binding.phoneLayout.error = null;
            } else {
                binding.phoneLayout.error = "Ошибка"
            }
        }

        viewModel.loginButtonEnabled.observe(this) { isEnabled ->
            binding.loginButton.isEnabled = isEnabled
        }

        // Обработка нажатия на кнопку "Войти"
        binding.loginButton.setOnClickListener {
            viewModel.onLoginButtonClick()
        }

        viewModel.navigateToMain.observe(this, Observer { navigateToMain ->
            if (navigateToMain) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }
}