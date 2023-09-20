package vn.dtc.project.grabfood.fragments.shopping

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import vn.dtc.project.grabfood.R
import vn.dtc.project.grabfood.adapters.CartFoodAdapter
import vn.dtc.project.grabfood.databinding.FragmentCartBinding
import vn.dtc.project.grabfood.firebase.FirebaseCommon
import vn.dtc.project.grabfood.util.Resource
import vn.dtc.project.grabfood.util.VerticalItemDecoration
import vn.dtc.project.grabfood.viewmodel.CartViewModel

class CartFragment: Fragment(R.layout.fragment_cart) {
    private lateinit var biding: FragmentCartBinding
    private val cartAdapter by lazy { CartFoodAdapter() }
    private val viewModel by activityViewModels<CartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        biding = FragmentCartBinding.inflate(inflater)
        return biding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCartRv()

        lifecycleScope.launchWhenStarted {
            viewModel.foodPrice.collectLatest { price ->
                price?.let {
                    biding.tvTotalPrice.text = "$ $price"
                }
            }
        }

        cartAdapter.onFoodClick = {
            val b = Bundle().apply{ putParcelable("food", it.food) }
            findNavController().navigate(R.id.action_cartFragment_to_foodDetailsFragment, b)
        }

        cartAdapter.onPlusClick ={
            viewModel.changeQuantity(it, FirebaseCommon.QuantityChanging.INCREASE)
        }
        cartAdapter.onMinusClick = {
            viewModel.changeQuantity(it, FirebaseCommon.QuantityChanging.DECREASE)
        }
        lifecycleScope.launchWhenStarted {
            viewModel.deleteDialog.collectLatest {
                val alertDialog = AlertDialog.Builder(requireContext()).apply {
                    setTitle("Delete item from cart")
                    setMessage("Do you want to delete this item from your cart?")
                    setNegativeButton("Cancel"){dialog,_ ->
                        dialog.dismiss()
                    }
                    setPositiveButton("Yes"){ dialog,_ ->
                        viewModel.deleteCartFood(it)
                        dialog.dismiss()
                    }
                }
                alertDialog.create()
                alertDialog.show()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.cartFoodz.collectLatest {
                when(it){
                    is Resource.Loading ->{
                        biding.progressbarCart.visibility = View.VISIBLE
                    }
                    is Resource.Success ->{
                        biding.progressbarCart.visibility = View.INVISIBLE
                        if (it.data!!.isEmpty()){
                            showEmptyCart()
                            hideOtherView()
                        } else {
                            hideEmptyCart()
                            cartAdapter.differ.submitList(it.data)
                            showOtherView()
                        }
                    }
                    is Resource.Error ->{
                        biding.progressbarCart.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }



    private fun showOtherView() {
        biding.apply {
            rvCart.visibility = View.VISIBLE
            totalBoxContainer.visibility = View.VISIBLE
            buttonCheckout.visibility = View.VISIBLE
        }
    }

    private fun hideOtherView() {
        biding.apply {
            rvCart.visibility = View.GONE
            totalBoxContainer.visibility = View.GONE
            buttonCheckout.visibility = View.GONE
        }
    }

    private fun hideEmptyCart() {
        biding.apply {
            layoutCartEmpty.visibility = View.GONE
        }
    }

    private fun showEmptyCart() {
        biding.apply {
            layoutCartEmpty.visibility = View.VISIBLE
        }
    }

    private fun setupCartRv() {
        biding.rvCart.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = cartAdapter
            addItemDecoration(VerticalItemDecoration())
        }
    }
}