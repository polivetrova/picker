package com.pet.picker

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBarWithAction(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}
