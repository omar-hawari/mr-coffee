package com.omarhawari.mrcoffee.presentation.size_picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.omarhawari.mrcoffee.R
import com.omarhawari.mrcoffee.databinding.FragmentSizePickerBinding
import com.omarhawari.mrcoffee.presentation.core.MainEvent
import com.omarhawari.mrcoffee.presentation.core.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SizePickerFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentSizePickerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_size_picker, container, false
        )
        val view: View = binding.root.apply {
            findViewById<FloatingActionButton>(R.id.next).setOnClickListener {
                viewModel.onEvent(MainEvent.SizesToExtras)
            }
        }
        binding.viewModel = viewModel
        return view
    }

}