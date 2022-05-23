package com.omarhawari.mrcoffee.presentation.checkout_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.omarhawari.mrcoffee.R
import com.omarhawari.mrcoffee.databinding.FragmentCheckoutBinding
import com.omarhawari.mrcoffee.presentation.core.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCheckoutBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_checkout, container, false
        )
        val view: View = binding.root.apply {
            findViewById<View>(R.id.brew_coffee).setOnClickListener {
                Toast.makeText(
                    context,
                    "We don't have an api for that yet ¯\\_(ツ)_/¯.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.viewModel = viewModel
        return view
    }
}