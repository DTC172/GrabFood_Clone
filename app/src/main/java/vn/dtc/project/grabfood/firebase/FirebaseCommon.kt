package vn.dtc.project.grabfood.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import vn.dtc.project.grabfood.data.CartFood

class FirebaseCommon(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private val cartCollection = firestore.collection("user").document(auth.uid!!).collection("cart")

    fun addFoodToCart(cartFood: CartFood, onResult: (CartFood?, Exception?) -> Unit){
       cartCollection.document().set(cartFood)
           .addOnSuccessListener {
               onResult(cartFood, null)
           } .addOnFailureListener {
               onResult(null, it)
           }
    }

    fun increaseQuantity(documentId: String, onResult: (String?, Exception?) -> Unit){
        firestore.runTransaction{ transition ->
            val documentRef = cartCollection.document(documentId)
            val document = transition.get(documentRef)
            val foodObject = document.toObject(CartFood::class.java)
            foodObject?.let { cartFood ->
                val newQuantity = cartFood.quantity + 1
                val newFoodObject = cartFood.copy(quantity = newQuantity)
                transition.set(documentRef, newFoodObject)
            }
        }.addOnSuccessListener {
            onResult(documentId, null)
        }.addOnFailureListener {
            onResult(null, it)
        }
    }

    fun decreaseQuantity(documentId: String, onResult: (String?, Exception?) -> Unit){
        firestore.runTransaction{ transition ->
            val documentRef = cartCollection.document(documentId)
            val document = transition.get(documentRef)
            val foodObject = document.toObject(CartFood::class.java)
            foodObject?.let { cartFood ->
                val newQuantity = cartFood.quantity - 1
                val newFoodObject = cartFood.copy(quantity = newQuantity)
                transition.set(documentRef, newFoodObject)
            }
        }.addOnSuccessListener {
            onResult(documentId, null)
        }.addOnFailureListener {
            onResult(null, it)
        }
    }

    enum class QuantityChanging{
        INCREASE, DECREASE
    }
}