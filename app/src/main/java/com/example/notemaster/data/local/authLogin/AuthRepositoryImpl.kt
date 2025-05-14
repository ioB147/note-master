package com.example.notemaster.data.local.authLogin

import com.example.notemaster.data.local.authLogin.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(firebaseAuth.currentUser!!)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            firebaseAuth.currentUser?.let {
                // Update the user's profile with the name
                val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()
                it.updateProfile(profileUpdates).await()
            }
            Resource.Success(firebaseAuth.currentUser!!)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}
