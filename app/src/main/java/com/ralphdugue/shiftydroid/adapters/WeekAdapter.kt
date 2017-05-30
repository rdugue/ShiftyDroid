package com.ralphdugue.shiftydroid.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ralphdugue.shiftydroid.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by Ralph on 5/29/2017.
 */

class WeekAdapter(private val days: List<String>) : RecyclerView.Adapter<WeekDayViewHolder>() {
    val onClickSubject = PublishSubject.create<String>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WeekDayViewHolder {
        return WeekDayViewHolder(
                DayUI().createView(AnkoContext.create(parent!!.context, parent)))
    }

    override fun onBindViewHolder(holder: WeekDayViewHolder?, position: Int) {
        holder?.bind(days[position])
        holder?.itemView?.onClick { onClickSubject.onNext(days[position]) }
    }

    override fun getItemCount(): Int = days.size

    fun getPositionClicks(): Observable<String> = onClickSubject
}

class WeekDayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.find(R.id.day_name)

    fun bind(day: String) { name.text = day }

}

class DayUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        linearLayout {
            padding = dip(5)
            lparams {
                width = wrapContent
                height = wrapContent
            }
            cardView {
                textView {
                    height = matchParent
                    width = matchParent
                    textSize = 26f
                    textColor = Color.BLACK
                    id = R.id.day_name
                    text = "TESTING"
                    gravity = Gravity.CENTER
                }
            }.lparams {
                width = dip(200)
                height = dip(200)
                gravity = Gravity.CENTER
            }
        }

    }
}