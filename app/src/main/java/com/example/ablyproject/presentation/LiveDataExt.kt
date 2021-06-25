package io.github.droidkaigi.confsched2018.util.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


inline fun <T> LiveData<T>.observe(
    owner: LifecycleOwner,
    crossinline observer: (T?) -> Unit
) {
    observe(owner, Observer<T> { v -> observer(v) })
}