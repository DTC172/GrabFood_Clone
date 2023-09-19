package vn.dtc.project.grabfood.data

class Food(
    val id: String,
    val name: String,
    val category: String,
    val restaurant: String,
    val price: Float,
    val offerPercentage: Float? = null,
    val description: String? = null,
    val images: List<String>
) {
    constructor(): this("0","", "", "", 1f, images= emptyList())
}