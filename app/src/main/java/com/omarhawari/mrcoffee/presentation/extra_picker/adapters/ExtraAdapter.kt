package com.omarhawari.mrcoffee.presentation.extra_picker.adapters

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
import com.omarhawari.mrcoffee.domain.models.relations.ExtraWithSubSelectionsSelected

class ExtraAdapter(
    private val extras: ArrayList<ExtraWithSubSelectionsSelected>,
    private var onItemClick: ((extraId: String, index: Int) -> Any)? = null,
) :
    RecyclerView.Adapter<BindableViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_extra,
            parent,
            false
        )
        return BindableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        holder.bind(extras[position], onItemClick)
    }

    override fun getItemCount() = extras.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<ExtraWithSubSelectionsSelected> = arrayListOf()) {
        extras.clear()
        extras.addAll(items)
        notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setOnItemClick(onItemClick: ((extraId: String, index: Int) -> Any)?) {
        this.onItemClick = onItemClick
        notifyDataSetChanged()
    }
}

class BindableViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        extra: ExtraWithSubSelectionsSelected,
        onItemClick: ((extraId: String, index: Int) -> Any)?
    ) {
        binding.setVariable(BR.extra, extra)
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
                extra.subSelections.forEachIndexed { index, subSelection ->
                    val ssBinding: ViewDataBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(context),
                        R.layout.rv_subselection,
                        this as ViewGroup,
                        false
                    )
                    ssBinding.root.setOnClickListener {
                        onItemClick?.let { onItemClick(extra.extra.extraId, index) }
                    }
                    ssBinding.setVariable(BR.subSelection, subSelection)
                    ssBinding.setVariable(BR.isSelected, index == extra.selectedIndex)
                    ssBinding.setVariable(BR.isOneSelected, extra.selectedIndex != -1)
                    addView(ssBinding.root)
                }
            }
        else
            subSelectionsLayout.children.forEachIndexed { index, view ->
                val ssBinding = DataBindingUtil.getBinding<ViewDataBinding>(view)!!
                ssBinding.setVariable(BR.isSelected, index == extra.selectedIndex)
                ssBinding.setVariable(BR.isOneSelected, extra.selectedIndex != -1)
            }

        binding.setVariable(BR.isOneSelected, extra.selectedIndex != -1)
    }
}

@BindingAdapter("extras")
fun bindExtras(recyclerView: RecyclerView, extras: List<ExtraWithSubSelectionsSelected>) {
    updateOrCreateAdapter(recyclerView, extras = extras)
}


@BindingAdapter("onSubSelectionClicked")
fun bindOnSubSelectionClicked(
    recyclerView: RecyclerView,
    onItemClick: ((extraId: String, index: Int) -> Any)
) {
    updateOrCreateAdapter(recyclerView, onItemClick = onItemClick)
}

private fun updateOrCreateAdapter(
    recyclerView: RecyclerView,
    extras: List<ExtraWithSubSelectionsSelected>? = null,
    onItemClick: ((extraId: String, index: Int) -> Any)? = null,
): ExtraAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is ExtraAdapter) {
        (recyclerView.adapter as ExtraAdapter).apply {
            extras?.let {
                updateItems(extras)
            }
            onItemClick?.let {
                setOnItemClick(onItemClick)
            }
        }
    } else {
        val adapter =
            ExtraAdapter(
                extras = ArrayList(extras ?: emptyList()),
                onItemClick = onItemClick,
            )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        adapter
    }
}

