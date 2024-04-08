package com.example.interviewpractice

import android.content.ContentResolver
import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.Tag
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.util.Date
import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.interviewpractice.controller.HistoryController
import com.example.interviewpractice.controller.NotificationController
import com.example.interviewpractice.controller.QuestionController
import com.example.interviewpractice.controller.ReviewController
import com.example.interviewpractice.controller.UserController
import com.example.interviewpractice.types.AnsweredQuestion
import com.example.interviewpractice.types.CatastrophicException
import com.example.interviewpractice.types.ErrorType
import com.example.interviewpractice.types.FetchType
import org.mockito.kotlin.whenever
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.Review
import com.example.interviewpractice.types.UIError
import com.example.interviewpractice.types.UserException
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.tasks.Tasks
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.exceptions.base.MockitoException
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import java.io.File
import java.util.UUID
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.tasks.await
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.inOrder
import java.io.ByteArrayInputStream
import java.io.InputStream

@ExperimentalCoroutinesApi
class AuthModelTest {
    lateinit var authmodel: AuthModel
    lateinit var mainModel: MainModel
    lateinit var authController: AuthController

    private lateinit var authModel: AuthModel
    private val mockAuth: FirebaseAuth = Mockito.mock(FirebaseAuth::class.java)
    private val mockDb: FirebaseFirestore = Mockito.mock(FirebaseFirestore::class.java)
    private val mockStorage: FirebaseStorage = Mockito.mock(FirebaseStorage::class.java)

    @Before
    fun setup() {
        authModel = AuthModel(mockAuth,mockDb)
        mainModel = MainModel(mockAuth,mockDb,mockStorage)
        authController = AuthController(mainModel,authModel)
        Dispatchers.setMain(Dispatchers.Unconfined)

    }

    //Main Model
    @Test
    fun invalidateAll_updatesCacheCorrectly() = runTest {
        mainModel.invalidateAll()
        FetchType.values().forEach {
            assertFalse(mainModel.isCached[it]!!)
        }
    }



}