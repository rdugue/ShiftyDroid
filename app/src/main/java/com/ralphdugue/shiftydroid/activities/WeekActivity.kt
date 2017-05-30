package com.ralphdugue.shiftydroid.activities

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.ralphdugue.shiftydroid.R
import com.ralphdugue.shiftydroid.adapters.WeekAdapter
import com.ralphdugue.shiftydroid.utils.Day
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView

class WeekActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WeekUI().setContentView(this)

        val toolbar: Toolbar = find(R.id.toolbar)
        setSupportActionBar(toolbar)
    }
}

class WeekUI : AnkoComponent<WeekActivity> {

    override fun createView(ui: AnkoContext<WeekActivity>) = with(ui) {
        coordinatorLayout {
            fitsSystemWindows = true
            lparams {
                width = matchParent
                height = matchParent
            }
            appBarLayout {
                toolbar {
                    setTitleTextColor(Color.WHITE)
                    id = R.id.toolbar
                    title = resources.getString(R.string.week_activity)
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }
            }.lparams(width = matchParent)
            recyclerView {
                backgroundColor = ContextCompat.getColor(context, R.color.colorAccent)
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(context, 2)
                val weekAdapter = WeekAdapter(Day.values().map { it.toString() })
                handleDayClick(this@recyclerView, weekAdapter)
                adapter = weekAdapter
            }.lparams {
                width = matchParent
                height = matchParent
                behavior = AppBarLayout.ScrollingViewBehavior()
            }
        }
    }

    fun handleDayClick(view: View, weekAdapter: WeekAdapter) {
        weekAdapter.getPositionClicks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Snackbar.make(view, "Clicked $it",
                                    Snackbar.LENGTH_LONG).show()
                        },
                        {
                            Snackbar.make(view, it.message ?: "",
                                    Snackbar.LENGTH_LONG).show()
                        }
                )
    }
}