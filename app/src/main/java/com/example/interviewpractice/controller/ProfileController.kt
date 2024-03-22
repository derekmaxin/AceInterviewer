package com.example.interviewpractice.controller

import android.util.Log
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.ErrorType
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.Tag
import com.example.interviewpractice.types.UIError
import com.example.interviewpractice.types.UserException
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ProfileController(mm: MainModel, am: AuthModel): Controller(mm,am,TAG) {

    public override fun fetchData() {
        handler("fetchData") {
            mm.getCurrentUserData()
        }
    }

    companion object {
        private const val TAG = "ProfileController"
    }
}