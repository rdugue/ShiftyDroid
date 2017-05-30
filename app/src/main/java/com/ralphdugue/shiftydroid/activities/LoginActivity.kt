package com.ralphdugue.shiftydroid.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.InputType
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.ralphdugue.shiftydroid.api.ShiftyAPI
import com.ralphdugue.shiftydroid.api.UserManager
import com.ralphdugue.shiftydroid.models.User
import com.ralphdugue.shiftydroid.utils.AUTH_TOKEN
import com.ralphdugue.shiftydroid.utils.PREFS_NAME
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.textChangedListener

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginUI().setContentView(this)
    }
}

class LoginUI : AnkoComponent<LoginActivity> {
    lateinit var company: EditText
    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var login: Button

    override fun createView(ui: AnkoContext<LoginActivity>) = with(ui) {
        scrollView {
            isFillViewport = true
            verticalLayout {
                padding = dip(30)
                company = editText {
                    hint = "Company Id"
                    textSize = 24f
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
                login = button("Login") {
                    textSize = 26f
                    onClick {
                        //handleLogin(ctx)
                        Snackbar.make(this@verticalLayout, "Login Handled",
                                Snackbar.LENGTH_SHORT).show()
                        ctx.startActivity(intentFor<WeekActivity>().singleTop())
                    }
                }
                textView("Click here to register") {
                    gravity = Gravity.CENTER_HORIZONTAL
                    onClick { ctx.startActivity(intentFor<RegisterActivity>().singleTop()) }
                }

                val fields = listOf<EditText>(company, username, password)
                fields.forEach {
                    it.textChangedListener {
                        onTextChanged { _, _, _, _ -> login.isClickable = isFullForm() }
                        beforeTextChanged { _, _, _, _ -> login.isClickable = isFullForm() }
                        afterTextChanged { _ -> login.isClickable = isFullForm() }
                    }
                }
            }
        }
    }

    private fun isFullForm(): Boolean {
        return when {
            company.editableText.isNullOrBlank() or
            username.editableText.isNullOrBlank() or
            password.editableText.isNullOrBlank() -> false
            else -> true
        }
    }

    private fun handleLogin(ctx: Context) {
        val userManager = UserManager(ShiftyAPI())
        userManager.handleLoginData(
                User(username.text.toString(), company.text.toString(),password.text.toString()))
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
                            error ->
                            Snackbar.make(ctx.scrollView(), error.message ?: "",
                                    Snackbar.LENGTH_LONG).show()
                        }
                )
    }


}
