package com.ralphdugue.shiftydroid.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.InputType
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ralphdugue.shiftydroid.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scrollView {
            isFillViewport = true
            verticalLayout {
                lparams(
                        width = LinearLayout.LayoutParams.MATCH_PARENT,
                        height =  LinearLayout.LayoutParams.WRAP_CONTENT
                )
                padding = dip(30)
                linearLayout {
                    lparams(
                            width = LinearLayout.LayoutParams.MATCH_PARENT,
                            height =  LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    val first = editText {
                        hint = "First Name"
                        textSize = 24f
                    }
                    val last = editText {
                        hint = "Last Name"
                        textSize = 24f
                    }
                }
                linearLayout {
                    lparams(
                            width = LinearLayout.LayoutParams.MATCH_PARENT,
                            height =  LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    val email = editText {
                        hint = "Email"
                        textSize = 24f
                    }
                    val company = editText {
                        hint = "Company Id"
                        textSize = 24f
                    }
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
                val  confirm_password = editText {
                    hint = "Confirm password"
                    textSize = 24f
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
                button("Register") {
                    textSize = 26f
                }
                textView("Already registered?  Click here to login.") {
                    gravity = Gravity.CENTER_HORIZONTAL
                    onClick { startActivity(intentFor<LoginActivity>().singleTop()) }
                }
            }
        }
    }

}
