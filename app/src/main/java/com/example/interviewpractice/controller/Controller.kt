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
import java.util.Calendar
import java.util.Date

open class Controller(protected val mm: MainModel, protected val am: AuthModel, protected val TAG: String) {

    fun fetchData(ft: FetchType) {
        if (ft == FetchType.LEADERBOARD) Log.d(TAG,"LEADERBOARD: ${mm.check(ft)}")
        handler("fetchData.${ft}",!mm.check(ft)) {
            when (ft) {
                FetchType.PROFILE->mm.getCurrentUserData()
                FetchType.LEADERBOARD->mm.getLeaderboardData()
                FetchType.SEARCH->mm.notifySubscribers()
                FetchType.RECOMMENDATION-> {
                    if (mm.homePageRecommendations == null) {
                        mm.searchQuestion("",self=true)
                    }

                    mm.notifySubscribers()
                }
                FetchType.INIT->mm.reset()
                FetchType.NOTIFICATION->mm.getNotificationData()
                FetchType.HISTORY->{
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.DATE, 1)
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);

                    val from: Date = calendar.time
                    mm.getHistoryData(from,Date(),am.getUserID())
                }
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

                //Invalidate caches to prevent damage
                mm.invalidateAll()

            } finally {
                //Stop loading after we finished our task
                if (requiresLoad) am.loading = false else mm.localLoading = false
            }
        }
    }
}