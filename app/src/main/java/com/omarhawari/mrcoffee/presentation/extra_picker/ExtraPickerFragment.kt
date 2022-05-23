package com.omarhawari.mrcoffee.presentation.extra_picker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.omarhawari.mrcoffee.R
import com.omarhawari.mrcoffee.databinding.FragmentExtraPickerBinding
import com.omarhawari.mrcoffee.presentation.core.MainEvent
import com.omarhawari.mrcoffee.presentation.core.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExtraPickerFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentExtraPickerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_extra_picker, container, false
        )
        val view: View = binding.root.apply {
            findViewById<FloatingActionButton>(R.id.next).setOnClickListener {
                viewModel.onEvent(MainEvent.ExtrasToCheckout)
            }
        }
        binding.viewModel = viewModel
        return view
    }
}