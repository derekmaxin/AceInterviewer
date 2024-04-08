package com.example.interviewpractice.controller

import android.content.Context
import android.net.Uri
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.FetchType

class UserController(mm: MainModel, am: AuthModel): Controller(mm,am,TAG) {

    companion object {
        private const val TAG = "ProfileController"
    }


    fun addUserPfp (uri: Uri, context: Context) {
        handler("addUserPfp",false) {
            mm.addUserPfp(am.getUserID(), uri, context)
            this.fetchData(FetchType.PROFILE)
            this.fetchData(FetchType.HISTORY)
        }
    }

}