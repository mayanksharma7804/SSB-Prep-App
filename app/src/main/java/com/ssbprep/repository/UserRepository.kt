package com.ssbprep.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getUserPaymentStatus(): Flow<Boolean> = callbackFlow {
        val userId = auth.currentUser?.uid ?: run {
            trySend(false)
            return@callbackFlow
        }
        
        val docRef = db.collection("users").document(userId)
        val registration = docRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                val isPaid = snapshot.getBoolean("isPaid") ?: false
                trySend(isPaid)
            } else {
                trySend(false)
            }
        }
        
        awaitClose { registration.remove() }
    }
    
    fun getUserId(): String? = auth.currentUser?.uid
}
