package com.ssbprep.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

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
    
    suspend fun updatePaymentStatus(isPaid: Boolean, transactionId: String? = null): Boolean {
        val userId = auth.currentUser?.uid ?: return false
        return try {
            val data = mutableMapOf<String, Any>(
                "isPaid" to isPaid,
                "updatedAt" to com.google.firebase.Timestamp.now()
            )
            if (transactionId != null) {
                data["lastTransactionId"] = transactionId
                data["paymentTimestamp"] = com.google.firebase.Timestamp.now()
            }

            db.collection("users").document(userId)
                .set(data, SetOptions.merge())
                .await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    fun getUserId(): String? = auth.currentUser?.uid
}
