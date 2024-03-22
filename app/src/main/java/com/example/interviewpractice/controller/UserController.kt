package com.example.interviewpractice.controller

import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.FetchType

class UserController(mm: MainModel, am: AuthModel): Controller(mm,am,TAG) {

    companion object {
        private const val TAG = "ProfileController"
    }
}