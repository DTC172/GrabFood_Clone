package vn.dtc.project.grabfood.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import vn.dtc.project.grabfood.R
import vn.dtc.project.grabfood.databinding.FragmentSearchBinding
import vn.dtc.project.grabfood.util.hideBottomNavigationView

class SearchFragment: Fragment() {
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideBottomNavigationView()
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }



}