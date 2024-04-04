package com.example.interviewpractice.controller

import android.util.Log
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.ErrorType
import com.example.interviewpractice.types.FetchType
import com.example.interviewpractice.types.UIError
import com.example.interviewpractice.types.UserException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

open class Controller(protected val mm: MainModel, protected val am: AuthModel, protected val TAG: String) {

    fun fetchData(ft: FetchType) {
        handler("fetchData.${ft}",mm.noCache(ft)) {
            when (ft) {
                FetchType.PROFILE->mm.getCurrentUserData()
                FetchType.LEADERBOARD->mm.getLeaderBoardData()
                FetchType.SEARCH->mm.refresh()
                FetchType.RECOMMENDATION-> {
                    if (mm.homePageRecommendations == null) {
                        mm.searchQuestion("",self=true)
                    }

                    mm.notifySubscribers()
                }
                FetchType.RESETUSER->mm.reset()
                FetchType.NOTIFICATION->mm.getNotificationData()
                FetchType.HISTORY->{}
            }
        }
    }
    protected fun handler(functionName: String, requiresLoad: Boolean = false, func: suspend () -> Unit) {
        MainScope().launch {
            try {
                if (requiresLoad) am.loading = true else mm.localLoading = true
                func()
            } catch (ex: FirebaseAuthException) {
                //Represents user errors that are caught by Firebase Auth
                am.error = UIError(ex.message!!,ErrorType.USER)
                Log.w(TAG,"$functionName:userException[${ex.errorCode}] --> ${ex.message}")

            } catch (ex: FirebaseFirestoreException) {
                //Represents user errors that are caught by Firestore
                am.error = UIError(ex.message!!, ErrorType.USER)
                Log.w(TAG, "$functionName:userException --> ${ex.message}")

            } catch (ex: FirebaseException) {
                //Represents all remaining (system) errors that are caught by Firebase
                am.error = UIError(ex.message!!, ErrorType.SYSTEM)
                Log.e(TAG, "$functionName:systemException --> ${ex.message}")
            } catch (ex: UserException) {
                //Represents all user errors that are caught by us
                am.error = UIError(ex.message!!, ErrorType.USER)
                Log.w(TAG, "$functionName:userException -> ${ex.message}")
            } catch (ex: Exception) {
                //Represents all remaining errors that weren't caught by Firebase or us
                //If we reach here, something very bad has happened
                am.error = UIError(ex.toString(), ErrorType.CATASTROPHIC)
                Log.wtf(TAG, "$functionName:catastrophicFailure", ex)

                //Log user out to prevent further damage
                am.logout()

            } finally {
                //Stop loading after we finished our task
                if (requiresLoad) am.loading = false else mm.localLoading = false

            }
        }
    }
}