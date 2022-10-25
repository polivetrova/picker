package com.pet.picker

import android.view.View
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun View.showSnackBarWithAction(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}