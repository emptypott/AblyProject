package com.example.ablyproject.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        // ViewModel 클래스를 키로 하여, ViewModel 객체를 생성하는 Provider 를 가져온다.
        var creator : Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            //  클래스 키로 찾지 못했다면, 적당한 Provider가 있는지, 다시 맵에서 찾는다.
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom((key))) {
                    creator = value
                    break
                }
            }
        }

        if (creator == null) {
            throw IllegalArgumentException("unknown model class : $modelClass")
        }

        try {
            // Dagger 의 Provider로부터 ViewModel 객체 생성 및 변환
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch(e : Exception) {
            throw RuntimeException(e)
        }
    }
}