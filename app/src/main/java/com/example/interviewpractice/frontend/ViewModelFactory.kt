package com.example.interviewpractice.frontend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.interviewpractice.frontend.views.auth.login.LoginViewModel
import com.example.interviewpractice.frontend.views.auth.register.RegisterViewModel
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel


//This is cringe

//class MMViewModelFactory(private val model: MainModel) : ViewModelProvider.Factory {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
////        return if (modelClass === LoginViewModel::class.java) {
////            LoginViewModel(model) as T
////        } else if (modelClass === RegisterViewModel::class.java) {
////            RegisterViewModel(model) as T
////        }
//        return super.create(modelClass)
//    }
//}
//
//class AMViewModelFactory(private val model: AuthModel) : ViewModelProvider.Factory {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return if (modelClass === LoginViewModel::class.java) {
//            LoginViewModel(model) as T
//        } else (modelClass === RegisterViewModel::class.java) {
//            RegisterViewModel(model) as T
//        }
//    }
//}