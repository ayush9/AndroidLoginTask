package com.example.androidtestapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class SignUpActivity : AppCompatActivity() {

    private val signUpViewModel: SignUpViewModel by lazy {
        getViewModelProvider(this, null).get(
            SignUpViewModel::class.java
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        findViewById<EditText>(R.id.input_email_signup).addTextChangedListener(emailTextChangeWatcher)
        findViewById<EditText>(R.id.input_password_signup).addTextChangedListener(passwordTextChangeWatcher)
        findViewById<EditText>(R.id.input_name).addTextChangedListener(inputNameTextChangeWatcher)
        findViewById<EditText>(R.id.input_reEnterPassword).addTextChangedListener(inputReEnterPasswordWatcher)

        findViewById<EditText>(R.id.input_email_signup).setOnTouchListener(closeButtonTouchListener)
        findViewById<EditText>(R.id.input_password_signup).setOnTouchListener(closeButtonTouchListener)
        findViewById<EditText>(R.id.input_name).setOnTouchListener(closeButtonTouchListener)
        findViewById<EditText>(R.id.input_reEnterPassword).setOnTouchListener(closeButtonTouchListener)

        findViewById<Button>(R.id.btn_signup).setOnClickListener {
            onSignUpClicked()
        }

        signUpViewModel.signUpStatus.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "SIGNUP SUCCESS", Toast.LENGTH_LONG).show()
//                val intent = Intent(this, HomeActivity::class.java)
  //              startActivity(intent)
            } else {
                Toast.makeText(this, "SIGNUP FAILED", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun onSignUpClicked() {
        hideVirtualKeyboard()
        if (!NetworkUtil.isNetworkAvailable(application)) {
            Toast.makeText(this, "NO INTERNET CONNECTION", Toast.LENGTH_LONG).show()
        }else {
            if (!EmailValidation.isValidEmail(findViewById<EditText>(R.id.input_email_signup).text.toString()) ||
                findViewById<EditText>(R.id.input_password_signup).text.toString().length < EmailValidation.PASSWORD_CHARCTER_LIMIT ||
                findViewById<EditText>(R.id.input_password_signup).text.toString().contains(EmailValidation.PASSWORD_NULL_SPACE)
            ) {
                //invalid
                findViewById<TextView>(R.id.error_signup).visibility = View.VISIBLE
            } else {
                //valid
                findViewById<TextView>(R.id.error_signup).visibility = View.GONE
                signUpViewModel.registerUser(findViewById<EditText>(R.id.input_email_signup).text.toString(), findViewById<EditText>(R.id.input_password_signup).text.toString(), findViewById<EditText>(R.id.input_name).text.toString())
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
        fragment: SignUpActivity,
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
                findViewById<EditText>(R.id.input_email_signup).setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_icon_cross_edit_text_grey,
                    0
                )
            } else {
                findViewById<EditText>(R.id.input_email_signup).setCompoundDrawablesWithIntrinsicBounds(
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
                findViewById<EditText>(R.id.input_password_signup).setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_icon_cross_edit_text_grey,
                    0
                )
            } else {
                findViewById<EditText>(R.id.input_password_signup).setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    0,
                    0
                )
            }
        }
    }

    private val inputNameTextChangeWatcher = object : TextWatcher {

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
                findViewById<EditText>(R.id.input_name).setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_icon_cross_edit_text_grey,
                    0
                )
            } else {
                findViewById<EditText>(R.id.input_name).setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    0,
                    0
                )
            }
        }
    }

    private val inputReEnterPasswordWatcher = object : TextWatcher {

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
                findViewById<EditText>(R.id.input_reEnterPassword).setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_icon_cross_edit_text_grey,
                    0
                )
            } else {
                findViewById<EditText>(R.id.input_reEnterPassword).setCompoundDrawablesWithIntrinsicBounds(
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
                R.id.input_email_signup -> {
                    val mEmail = findViewById<EditText>(R.id.input_email_signup)
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
                            findViewById<EditText>(R.id.input_email_signup).text.clear()
                            return@OnTouchListener true
                        }
                    }
                }
                R.id.input_password_signup -> {
                    val mPassword = findViewById<EditText>(R.id.input_password_signup)
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
                            findViewById<EditText>(R.id.input_password_signup).text.clear()
                            return@OnTouchListener true
                        }
                    }
                }
            }
        }
        false
    }

}
