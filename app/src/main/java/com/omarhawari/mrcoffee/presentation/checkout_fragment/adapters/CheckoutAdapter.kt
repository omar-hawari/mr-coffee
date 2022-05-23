package com.omarhawari.mrcoffee.presentation.checkout_fragment.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omarhawari.mrcoffee.BR
import com.omarhawari.mrcoffee.R
import com.omarhawari.mrcoffee.domain.models.Extra
import com.omarhawari.mrcoffee.domain.models.Size
import com.omarhawari.mrcoffee.domain.models.Type
import com.omarhawari.mrcoffee.domain.models.relations.ExtraWithSubSelectionsSelected

class CheckoutAdapter(
    private val extras: ArrayList<Any>,
    private var onItemClick: ((any: Any) -> Any)? = null,
) :
    RecyclerView.Adapter<BindableViewHolder>() {

    companion object {
        const val ELEMENT_TYPE = 1
        const val ELEMENT_SIZE = 2
        const val ELEMENT_EXTRA = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            when (viewType) {
                ELEMENT_TYPE -> {
                    R.layout.rv_type
                }
                ELEMENT_SIZE -> {
                    R.layout.rv_size
                }
                ELEMENT_EXTRA -> {
                    R.layout.rv_extra
                }
                else -> R.layout.rv_type
            },
            parent,
            false
        )
        return BindableViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return when (extras[position]) {
            is Type -> ELEMENT_TYPE
            is Size -> ELEMENT_SIZE
            is ExtraWithSubSelectionsSelected -> ELEMENT_EXTRA
            else -> ELEMENT_TYPE
        }
    }

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        holder.bind(extras[position], onItemClick, viewType = getItemViewType(position))
    }

    override fun getItemCount() = extras.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<Any> = arrayListOf()) {
        extras.clear()
        extras.addAll(items)
        notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setOnItemClick(onItemClick: ((any: Any) -> Any)?) {
        this.onItemClick = onItemClick
        notifyDataSetChanged()
    }
}

class BindableViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: Any,
        onItemClick: ((any: Any) -> Any)?,
        viewType: Int
    ) {
        when (viewType) {
            CheckoutAdapter.ELEMENT_TYPE -> {
                binding.setVariable(BR.type, item as Type)
            }
            CheckoutAdapter.ELEMENT_SIZE -> {
                binding.setVariable(BR.size, item as Size)
            }
            CheckoutAdapter.ELEMENT_EXTRA -> {
                binding.setVariable(BR.extra, item as ExtraWithSubSelectionsSelected)
                val subSelectionsLayout = binding.root.findViewById<LinearLayout>(R.id.sub_selections)
                val separator = binding.root.findViewById<View>(R.id.separator)

                // Toggle sub selection visibility
                binding.root.setOnClickListener {
                    if (subSelectionsLayout.visibility == View.GONE) {
                        subSelectionsLayout.visibility = View.VISIBLE
                        separator.visibility = View.VISIBLE
                    } else {
                        subSelectionsLayout.visibility = View.GONE
                        separator.visibility = View.GONE
                    }
                }

                // Update sub selections or init them
                if (subSelectionsLayout.childCount == 0)
                    subSelectionsLayout.apply {

                        val subSelection = item.subSelections[item.selectedIndex]
                        val ssBinding: ViewDataBinding = DataBindingUtil.inflate(
                            LayoutInflater.from(context),
                            R.layout.rv_subselection,
                            this as ViewGroup,
                            false
                        )

                        ssBinding.setVariable(BR.subSelection, subSelection)
                        ssBinding.setVariable(BR.isSelected, true)
                        ssBinding.setVariable(BR.isOneSelected, true)
                        addView(ssBinding.root)
                    }
                else
                    subSelectionsLayout.children.first().apply {
                        val ssBinding = DataBindingUtil.getBinding<ViewDataBinding>(this)!!
                        ssBinding.setVariable(BR.isSelected, true)
                        ssBinding.setVariable(BR.isOneSelected, true)
                    }
            }
        }
        binding.setVariable(BR.isEdit, true)
        binding.setVariable(BR.isSelected, true)
        binding.setVariable(BR.isOneSelected, true)
        binding.root.findViewById<TextView>(R.id.edit).setOnClickListener {
            onItemClick!!(item)
        }
    }
}

@BindingAdapter("checkout")
fun bindCheckout(recyclerView: RecyclerView, checkout: List<Any>) {
    updateOrCreateAdapter(recyclerView, checkout = checkout)
}


@BindingAdapter("onEdit")
fun bindOnEdit(
    recyclerView: RecyclerView,
    onEdit: (extra: Any) -> Any
) {
    updateOrCreateAdapter(recyclerView, onEdit = onEdit)
}

private fun updateOrCreateAdapter(
    recyclerView: RecyclerView,
    checkout: List<Any>? = null,
    onEdit: ((type: Any) -> Any)? = null,
): CheckoutAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is CheckoutAdapter) {
        (recyclerView.adapter as CheckoutAdapter).apply {
            checkout?.let {
                updateItems(checkout)
            }
            onEdit?.let {
                setOnItemClick(onEdit)
            }
        }
    } else {
        val adapter =
            CheckoutAdapter(
                extras = ArrayList(checkout ?: emptyList()),
                onItemClick = onEdit,
            )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        adapter
    }
}

