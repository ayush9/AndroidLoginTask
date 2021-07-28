package com.example.androidtestapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by lazy {
        getViewModelProvider(this, null).get(
            LoginViewModel::class.java
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<EditText>(R.id.input_email).addTextChangedListener(emailTextChangeWatcher)
        findViewById<EditText>(R.id.input_password).addTextChangedListener(passwordTextChangeWatcher)

        findViewById<EditText>(R.id.input_email).setOnTouchListener(closeButtonTouchListener)
        findViewById<EditText>(R.id.input_password).setOnTouchListener(closeButtonTouchListener)

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            onLoginClicked()
        }

        loginViewModel.loginStatus.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "LOGIN SUCCESS", Toast.LENGTH_LONG).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "LOGIN FAILED", Toast.LENGTH_LONG).show()
            }
        })

        findViewById<TextView>(R.id.link_signup).setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onLoginClicked() {
        hideVirtualKeyboard()
        if (!NetworkUtil.isNetworkAvailable(application)) {
            Toast.makeText(this, "NO INTERNET CONNECTION", Toast.LENGTH_LONG).show()
        }else {
            if (!EmailValidation.isValidEmail(findViewById<EditText>(R.id.input_email).text.toString()) ||
                findViewById<EditText>(R.id.input_password).text.toString().length < EmailValidation.PASSWORD_CHARCTER_LIMIT ||
                findViewById<EditText>(R.id.input_password).text.toString().contains(EmailValidation.PASSWORD_NULL_SPACE)
            ) {
                //invalid
                findViewById<TextView>(R.id.error_password).visibility = View.VISIBLE
            } else {
                //valid
                findViewById<TextView>(R.id.error_password).visibility = View.GONE
                loginViewModel.nativeLogin(findViewById<EditText>(R.id.input_email).text.toString(), findViewById<EditText>(R.id.input_password).text.toString())
            }
        }
    }


    /**
     * Hide virtual keyboard.
     */
    private fun hideVirtualKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun getViewModelProvider(
        fragment: LoginActivity,
        factory: ViewModelProvider.Factory?
    ): ViewModelProvider {
        return ViewModelProviders.of(fragment, factory)
    }

    /*
     This will make watch over the email text and Password Text, when length of characters>0
     then shows the clear (X) button in emailText and Password Text
    */
    private val emailTextChangeWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // Do Nothing
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // Do Nothing
        }

        override fun afterTextChanged(s: Editable) {
            if (s.isNotEmpty()) {
                findViewById<EditText>(R.id.input_email).setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_icon_cross_edit_text_grey,
                    0
                )
            } else {
                findViewById<EditText>(R.id.input_email).setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    0,
                    0
                )
            }

        }
    }

    private val passwordTextChangeWatcher = object : TextWatcher {

        /** {@inheritDoc}  */
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // Do Nothing
        }

        /** {@inheritDoc}  */
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // Do Nothing
        }

        override fun afterTextChanged(s: Editable) {
            if (s.isNotEmpty()) {
                findViewById<EditText>(R.id.input_password).setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_icon_cross_edit_text_grey,
                    0
                )
            } else {
                findViewById<EditText>(R.id.input_password).setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    0,
                    0
                )
            }
        }
    }

    private val DRAWABLE_RIGHT = 2
    @SuppressLint("ClickableViewAccessibility")
    private var closeButtonTouchListener: View.OnTouchListener = View.OnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            when (v.id) {
                R.id.input_email -> {
                    val mEmail = findViewById<EditText>(R.id.input_email)
                    mEmail.isCursorVisible = true
                    if (mEmail.text.isNotEmpty()) {
                        mEmail.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_icon_cross_edit_text_grey,
                            0
                        )
                        mEmail.setBackgroundDrawable(resources.getDrawable(R.drawable.edit_text_normal_background))
                        if (event.rawX >= mEmail.right - mEmail.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                            findViewById<EditText>(R.id.input_email).text.clear()
                            return@OnTouchListener true
                        }
                    }
                }
                R.id.input_password -> {
                    val mPassword = findViewById<EditText>(R.id.input_password)
                    mPassword.isCursorVisible = true
                    if (mPassword.text.isNotEmpty()) {
                        mPassword.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_icon_cross_edit_text_grey,
                            0
                        )
                        mPassword.setBackgroundDrawable(resources.getDrawable(R.drawable.edit_text_normal_background))
                        if (event.rawX >= mPassword.right - mPassword.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                            findViewById<EditText>(R.id.input_password).text.clear()
                            return@OnTouchListener true
                        }
                    }
                }
            }
        }
        false
    }
}