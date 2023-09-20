package vn.dtc.project.grabfood.data

data class CartFood(
    val food: Food,
    val quantity: Int
){
    constructor(): this(Food(), 1)
}
