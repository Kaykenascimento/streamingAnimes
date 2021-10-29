package com.example.streaminganimes.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object ConfFireBase {

    var firebaseFirestore: FirebaseFirestore? = null
        get() {
            if (field == null) {
                field = FirebaseFirestore.getInstance()
            }
            return field
        }

    var firebaseAuth: FirebaseAuth? = null
        get() {
            if (field == null) {
                field = FirebaseAuth.getInstance()
            }
            return field
        }

    /** public static StorageReference getFirebaseStorage(){
     * if(firebaseStorage==null){
     * firebaseStorage = FirebaseStorage.getInstance().getReference();
     * }
     * return firebaseStorage;
     * } */


}