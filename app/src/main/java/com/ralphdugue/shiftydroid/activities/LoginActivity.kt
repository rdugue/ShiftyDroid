package com.ralphdugue.shiftydroid.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import org.jetbrains.anko.*;
import org.jetbrains.anko.sdk25.coroutines.onClick

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scrollView {
            isFillViewport = true
            verticalLayout {
                padding = dip(30)
                val company = editText {
                    hint = "Company Id"
                    textSize = 24f
                }
                val  user = editText {
                    hint = "Username"
                    textSize = 24f
                }
                val  password = editText {
                    hint = "Password"
                    textSize = 24f
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
                button("Login") {
                    textSize = 26f
                }
                textView("Click here to register") {
                    gravity = Gravity.CENTER_HORIZONTAL
                    onClick { startActivity(intentFor<RegisterActivity>().singleTop()) }
                }
            }
        }
    }
}
