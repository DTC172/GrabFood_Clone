package vn.dtc.project.grabfood.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import vn.dtc.project.grabfood.R
import vn.dtc.project.grabfood.adapters.BestFoodAdapter
import vn.dtc.project.grabfood.databinding.FragmentBaseCategoryBinding

open class BaseCategoryFragment: Fragment(R.layout.fragment_base_category) {
    private lateinit var binding: FragmentBaseCategoryBinding
    private lateinit var offerAdapter: BestFoodAdapter
    private lateinit var bestFoodAdapter: BestFoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOfferRv()
        setupBestFoodRv()
    }

    private fun setupBestFoodRv() {
        bestFoodAdapter = BestFoodAdapter()
        binding.rvBestfood.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = bestFoodAdapter
        }
    }

    private fun setupOfferRv() {
        offerAdapter = BestFoodAdapter()
        binding.rvBestfood.apply {
            LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
            adapter = offerAdapter
        }
    }

}
