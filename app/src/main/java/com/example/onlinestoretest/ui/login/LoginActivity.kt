package com.example.onlinestoretest.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.onlinestoretest.CenteredTitleToolbar
import com.example.onlinestoretest.R
import com.example.onlinestoretest.databinding.ActivityLoginBinding
import com.example.onlinestoretest.ui.main.MainActivity
import com.example.onlinestoretest.utils.dpToPx

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
            val name = binding.nameEditText.text.toString()
            val surname = binding.surnameEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            viewModel.onLoginButtonClick(name, surname, phone)
        }

        viewModel.navigateToMain.observe(this, Observer { navigateToMain ->
            if (navigateToMain) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
        }

        supportActionBar?.let { actionBar ->
            actionBar.title = resources.getString(R.string.login)

            val centeredTitleToolbar = binding.toolbar as CenteredTitleToolbar
            centeredTitleToolbar.titleTextView?.let { titleTextView ->
                titleTextView.typeface = ResourcesCompat.getFont(this, R.font.sf_pro_display_medium)
                titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                titleTextView.setTextColor(Color.BLACK)

                val layoutParams = titleTextView.layoutParams as Toolbar.LayoutParams
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
                layoutParams.bottomMargin = 10.dpToPx(this)
                titleTextView.layoutParams = layoutParams
            }
        }
    }
}