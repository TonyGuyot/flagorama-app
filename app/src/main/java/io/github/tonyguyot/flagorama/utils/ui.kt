package io.github.tonyguyot.flagorama.utils

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.setTitle(title: String) {
    activity?.let {
        if (it is AppCompatActivity) {
            it.supportActionBar?.title = title
        }
    }
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}