package com.ralphdugue.shiftydroid.activities

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.ralphdugue.shiftydroid.api.ShiftyAPI
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import com.ralphdugue.shiftydroid.api.UserManager
import com.ralphdugue.shiftydroid.models.User
import com.ralphdugue.shiftydroid.utils.AUTH_TOKEN
import com.ralphdugue.shiftydroid.utils.PREFS_NAME
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.sdk25.coroutines.textChangedListener

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RegisterUI().setContentView(this)
    }
}

class RegisterUI : AnkoComponent<RegisterActivity> {
    lateinit var first: EditText
    lateinit var last: EditText
    lateinit var email: EditText
    lateinit var company: EditText
    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var confirm_password: EditText
    lateinit var register: Button

    override fun createView(ui: AnkoContext<RegisterActivity>) = with(ui) {
        scrollView {
            isFillViewport = true
            verticalLayout {
                padding = dip(30)
                linearLayout {
                    first = editText {
                        hint = "First Name"
                        textSize = 24f
                    }.lparams() { weight = 1f }
                    last = editText {
                        hint = "Last Name"
                        textSize = 24f
                    }.lparams() { weight = 1f }
                }
                linearLayout {
                    email = editText {
                        hint = "Email"
                        textSize = 24f
                    }.lparams() { weight = 1f }
                    company = editText {
                        hint = "Company Id"
                        textSize = 24f
                    }.lparams() { weight = 1f }
                }
                username = editText {
                    hint = "Username"
                    textSize = 24f
                }
                password = editText {
                    hint = "Password"
                    textSize = 24f
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
                confirm_password = editText {
                    hint = "Confirm password"
                    textSize = 24f
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
                register = button("Register") {
                    textSize = 26f
                    onClick {
                        //handleRegistration(ctx)
                        Snackbar.make(this@verticalLayout, "Registration Handled",
                                Snackbar.LENGTH_SHORT).show()
                    }
                }
                textView("Already registered?  Click here to login.") {
                    gravity = Gravity.CENTER_HORIZONTAL
                    onClick { ctx.startActivity(intentFor<LoginActivity>().singleTop()) }
                }

                val fields = listOf<EditText>(
                        first, last, email, company,
                        username, password, confirm_password
                )
                fields.forEach {
                    it.textChangedListener {
                        onTextChanged { _, _, _, _ -> register.isClickable = isFullForm() }
                        beforeTextChanged { _, _, _, _ -> register.isClickable = isFullForm() }
                        afterTextChanged { _ -> register.isClickable = isFullForm() }
                    }
                }
            }
        }
    }

    private fun isFullForm(): Boolean {
       return when {
            first.editableText.isNullOrBlank() or
            last.editableText.isNullOrBlank() or
            email.editableText.isNullOrBlank() or
            company.editableText.isNullOrBlank() or
            username.editableText.isNullOrBlank() or
            password.editableText.isNullOrBlank() or
            confirm_password.editableText.isNullOrBlank() -> false
            password.editableText !== confirm_password.editableText -> false
            else -> true
        }
    }

    private fun handleRegistration(ctx: Context) {
        val userManager = UserManager(ShiftyAPI())
        userManager.handleRegisterData(
                User(
                        username.text.toString(),
                        company.text.toString(),
                        password.text.toString(),
                        first.text.toString(),
                        last.text.toString(),
                        email.text.toString()
                )
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            userResponses ->
                            val user = userResponses.data
                            user.save()
                            val sharedPrefs = ctx.getSharedPreferences(
                                    PREFS_NAME, Context.MODE_PRIVATE)
                            val editor = sharedPrefs.edit()
                            editor.putString(AUTH_TOKEN, userResponses.token)
                            editor.commit()
                            ctx.startActivity(ctx.intentFor<WeekActivity>().singleTop())
                        },
                        {
                            Snackbar.make(ctx.scrollView(), it.message ?: "",
                                    Snackbar.LENGTH_LONG).show()
                        }
                )
    }
}