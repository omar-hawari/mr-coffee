package com.omarhawari.mrcoffee.presentation.type_picker.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.omarhawari.mrcoffee.BR
import com.omarhawari.mrcoffee.R
import com.omarhawari.mrcoffee.domain.models.Type


class TypesAdapter(
    private val types: ArrayList<Type>,
    private var onItemClick: ((index: Int) -> Any)? = null,
    private var selectedItem: Int? = null,
) :
    RecyclerView.Adapter<BindableViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_type,
            parent,
            false
        )
        return BindableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        holder.bind(types[position], onItemClick, selectedItem)
    }

    override fun getItemCount() = types.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<Type> = arrayListOf()) {
        types.clear()
        types.addAll(items)
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

    fun bind(type: Type, onItemClick: ((index: Int) -> Any)?, selectedItem: Int?) {
        binding.setVariable(BR.type, type)
        binding.setVariable(BR.isSelected, selectedItem == adapterPosition)
        binding.root.setOnClickListener {
            onItemClick?.let { onItemClick(adapterPosition) }
        }
    }
}

@BindingAdapter("types")
fun bindTypes(recyclerView: RecyclerView, types: List<Type>) {
    updateOrCreateAdapter(recyclerView, types = types)
}


@BindingAdapter("onTypeClicked")
fun bindOnTypeClicked(recyclerView: RecyclerView, onItemClick: ((index: Int) -> Any)) {
    updateOrCreateAdapter(recyclerView, onItemClick = onItemClick)
}

@BindingAdapter("selectedType")
fun bindSelectedType(recyclerView: RecyclerView, selectedItem: Int) {
    updateOrCreateAdapter(recyclerView, selectedItem = selectedItem)
}


private fun updateOrCreateAdapter(
    recyclerView: RecyclerView,
    types: List<Type>? = null,
    onItemClick: ((index: Int) -> Any)? = null,
    selectedItem: Int? = null,
): TypesAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is TypesAdapter) {
        (recyclerView.adapter as TypesAdapter).apply {
            if (selectedItem != null) {
                setSelectedItem(selectedItem)
            } else {
                types?.let {
                    updateItems(types)
                }
                onItemClick?.let {
                    setOnItemClick(onItemClick)
                }
            }
        }
    } else {
        val bindableRecyclerAdapter =
            TypesAdapter(
                types = ArrayList(types ?: emptyList()),
                onItemClick = onItemClick,
                selectedItem = selectedItem
            )
        recyclerView.adapter = bindableRecyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)

        bindableRecyclerAdapter
    }
}

