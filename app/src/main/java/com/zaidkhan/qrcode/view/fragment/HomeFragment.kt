package com.zaidkhan.qrcode.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.zaidkhan.qrcode.MyApplication
import com.zaidkhan.qrcode.databinding.FragmentHomeBinding
import com.zaidkhan.qrcode.viewModel.MainViewModel
import javax.inject.Inject
import kotlin.math.min


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val mainViewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConvert.setOnClickListener {
            val data = binding.textToQRCode.text.toString().trim()
            val dimension = min(720, 720)
            if (data.isEmpty()) {
                Toast.makeText(requireContext(), "Enter Some Data", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    mainViewModel.getERGEncoder(data, dimension)
                    val action = HomeFragmentDirections.actionHomeFragmentToResultFragment()
                    Navigation.findNavController(it!!).navigate(action)
                } catch (e: java.lang.Exception) {
                    Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}