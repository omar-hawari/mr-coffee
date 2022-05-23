package com.omarhawari.mrcoffee.presentation.size_picker.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omarhawari.mrcoffee.BR
import com.omarhawari.mrcoffee.R
import com.omarhawari.mrcoffee.domain.models.Size

class SizesAdapter(
    private val sizes: ArrayList<Size>,
    private var onItemClick: ((index: Int) -> Any)? = null,
    private var selectedItem: Int? = null,
) :
    RecyclerView.Adapter<BindableViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_size,
            parent,
            false
        )
        return BindableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        holder.bind(sizes[position], onItemClick, selectedItem)
    }

    override fun getItemCount() = sizes.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<Size> = arrayListOf()) {
        sizes.clear()
        sizes.addAll(items)
        notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setOnItemClick(onItemClick: ((index: Int) -> Any)?) {
        this.onItemClick = onItemClick
        notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setSelectedItem(selectedItem: Int) {
        this.selectedItem = selectedItem
        if (selectedItem != -1)
            notifyItemChanged(selectedItem)
    }

}

class BindableViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(size: Size, onItemClick: ((index: Int) -> Any)?, selectedItem: Int?) {
        binding.setVariable(BR.size, size)
        binding.setVariable(BR.isSelected, selectedItem == adapterPosition)
        binding.root.setOnClickListener {
            onItemClick?.let { onItemClick(adapterPosition) }
        }
    }
}

@BindingAdapter("sizes")
fun bindSizes(recyclerView: RecyclerView, sizes: List<Size>) {
    updateOrCreateAdapter(recyclerView, sizes = sizes)
}


@BindingAdapter("onSizeClicked")
fun bindOnSizeClicked(recyclerView: RecyclerView, onItemClick: ((index: Int) -> Any)) {
    updateOrCreateAdapter(recyclerView, onItemClick = onItemClick)
}

@BindingAdapter("selectedSize")
fun bindSelectedSize(recyclerView: RecyclerView, selectedItem: Int) {
    updateOrCreateAdapter(recyclerView, selectedItem = selectedItem)
}


private fun updateOrCreateAdapter(
    recyclerView: RecyclerView,
    sizes: List<Size>? = null,
    onItemClick: ((index: Int) -> Any)? = null,
    selectedItem: Int? = null,
): SizesAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is SizesAdapter) {
        (recyclerView.adapter as SizesAdapter).apply {
            sizes?.let {
                updateItems(sizes)
            }
            onItemClick?.let {
                setOnItemClick(onItemClick)
            }
            selectedItem?.let {
                setSelectedItem(selectedItem)
            }
        }
    } else {
        val adapter =
            SizesAdapter(
                sizes = ArrayList(sizes ?: emptyList()),
                onItemClick = onItemClick,
                selectedItem = selectedItem
            )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        adapter
    }
}

