package com.example.onlinestoretest.presentation.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.onlinestoretest.R
import com.example.onlinestoretest.databinding.ActivityLoginBinding
import com.example.onlinestoretest.presentation.main.MainActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        viewModel.checkUserData()

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
                binding.nameLayout.error = getString(R.string.error)
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
                binding.surnameLayout.error = getString(R.string.error)
            }
        }

        binding.phoneEditText.setText("+7 ")
        binding.phoneEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                (view as? TextInputEditText)?.let { editText ->
                    editText.setSelection(editText.text?.length ?: 0)
                }
            }
        }

        binding.phoneEditText.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false
            private val digitPattern = "\\d"

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (isFormatting) {
                    return
                }
                isFormatting = true

                if (p0.toString().length <= 3) {
                    p0?.replace(0, p0.length, "+7 ")
                }

                val formattedText = formatPhoneNumber(p0.toString().substring(3))
                p0?.replace(3, p0.length, formattedText)

                isFormatting = false

                viewModel.validatePhone(p0.toString())
            }

            private fun formatPhoneNumber(phoneNumber: String): String {
                val sb = StringBuilder()
                //sb.append("+7 ")

                var digitCount = 0

                for (c in phoneNumber) {
                    if (c.toString().matches(digitPattern.toRegex())) {
                            if (digitCount == 3) {
                            sb.append(" ")
                        } else if (digitCount == 6 || digitCount == 8) {
                            sb.append("-")
                        }

                        sb.append(c)
                        digitCount++
                    }
                }

                return sb.toString()
            }
        })

        viewModel.phoneValid.observe(this) { isValid ->
            if (isValid) {
                binding.phoneLayout.error = null;
            } else {
                binding.phoneLayout.error = getString(R.string.error)
            }
        }

        viewModel.loginButtonEnabled.observe(this) { isEnabled ->
            binding.loginButton.isEnabled = isEnabled
        }

        // Обработка нажатия на кнопку "Войти"
        binding.loginButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val surname = binding.surnameEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            viewModel.onLoginButtonClick(name, surname, phone)
        }

        viewModel.navigateToMain.observe(this) { navigateToMain ->
            if (navigateToMain) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        initConditionsTextView()
    }

    private fun initConditionsTextView() {
        val text = resources.getString(R.string.terms_and_conditions)
        val spannableString = SpannableString(text)
        spannableString.setSpan(UnderlineSpan(), 39, text.length, 0)
        binding.conditionsTextView.text = spannableString
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)

        // Убираем границу между StatusBar и ActionBar, а также устаналиваем белый цвет
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))

        // Устанавливаем текст заголовка
        supportActionBar?.let { actionBar ->
            actionBar.title = resources.getString(R.string.login)
        }

        // Устанавливаем заголовок посередине
        val centeredTitleToolbar = binding.toolbar
        centeredTitleToolbar.titleTextView?.let { titleTextView ->
            titleTextView.gravity = Gravity.CENTER
        }
    }
}