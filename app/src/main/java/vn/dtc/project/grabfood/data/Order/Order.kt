package vn.dtc.project.grabfood.data.Order

import vn.dtc.project.grabfood.data.Address
import vn.dtc.project.grabfood.data.CartFood

data class Order(
    val orderStatus: String,
    val totalPrice: Float,
    val food: List<CartFood>,
    val address: Address
){

}
