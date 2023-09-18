package vn.dtc.project.grabfood.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import vn.dtc.project.grabfood.R
import vn.dtc.project.grabfood.adapters.HomeViewpagerAdapter
import vn.dtc.project.grabfood.databinding.FragmentHomeBinding
import vn.dtc.project.grabfood.fragments.categories.MainCategoryFragment
import vn.dtc.project.grabfood.fragments.categories.cate_1_Fragment
import vn.dtc.project.grabfood.fragments.categories.cate_2_Fragment
import vn.dtc.project.grabfood.fragments.categories.cate_3_Fragment
import vn.dtc.project.grabfood.fragments.categories.cate_4_Fragment
import vn.dtc.project.grabfood.fragments.categories.cate_5_Fragment
import vn.dtc.project.grabfood.fragments.categories.cate_6_Fragment

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragments = arrayListOf(
            MainCategoryFragment(),
            cate_1_Fragment(),
            cate_2_Fragment(),
            cate_3_Fragment(),
            cate_4_Fragment(),
            cate_5_Fragment(),
            cate_6_Fragment()
        )

        binding.viewpagerHome.isUserInputEnabled = false

        val viewPager2Adapter =
            HomeViewpagerAdapter(categoriesFragments, childFragmentManager, lifecycle)
        binding.viewpagerHome.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewpagerHome) { tab, position ->
            when (position) {
                0 -> tab.text = "Main"
                1 -> tab.text = "C1"
                2 -> tab.text = "C2"
                3 -> tab.text = "C3"
                4 -> tab.text = "C4"
                5 -> tab.text = "C5"
                6 -> tab.text = "C6"
            }
        }.attach()
    }
}