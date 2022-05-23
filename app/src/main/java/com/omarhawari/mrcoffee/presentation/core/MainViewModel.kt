package com.omarhawari.mrcoffee.presentation.core

import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.omarhawari.mrcoffee.R
import com.omarhawari.mrcoffee.core.Resource
import com.omarhawari.mrcoffee.data.data_source.remote.dto.toType
import com.omarhawari.mrcoffee.domain.models.Size
import com.omarhawari.mrcoffee.domain.models.Type
import com.omarhawari.mrcoffee.domain.models.relations.ExtraWithSubSelectionsSelected
import com.omarhawari.mrcoffee.domain.models.relations.toExtraWithSubSelectionsSelected
import com.omarhawari.mrcoffee.domain.use_cases.MasterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val masterUseCase: MasterUseCase
) :
    ViewModel() {

    lateinit var navController: NavController

    private val machineId = "60ba1ab72e35f2d9c786c610"

    val state = ObservableField(MainState())

    private val currentType: Type?
        get() = state.get()?.let {
            if (it.selectedType == -1) null
            else it.types[it.selectedType]
        }

    private val currentSize: Size?
        get() = state.get()?.let {
            if (it.selectedSize == -1) null
            else it.sizes[it.selectedSize]
        }

    public val checkout: List<Any>
        get() {
            return arrayListOf(
                currentType!!,
                currentSize!!,
            ).apply {
                addAll(state.get()!!.extras.filter { it.selectedIndex != -1 })
            }
        }


    init {
        getAll()
    }


    /**
     * Handling UI events
     * */

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.TypesToSizes -> {
                navController.navigate(R.id.action_typePickerFragment_to_sizePickerFragment)
            }
            is MainEvent.SizesToExtras -> {
                navController.navigate(R.id.action_sizePickerFragment_to_extraPickerFragment)
            }
            is MainEvent.ExtrasToCheckout -> {
                navController.navigate(R.id.action_extraPickerFragment_to_checkoutFragment)
            }
            is MainEvent.CheckoutToTypes -> {
                navController.navigate(R.id.action_checkoutFragment_to_typePickerFragment)
            }
            is MainEvent.CheckoutToSizes -> {
                navController.navigate(R.id.action_checkoutFragment_to_sizePickerFragment)
            }
            is MainEvent.CheckoutToExtras -> {
                navController.navigate(R.id.action_checkoutFragment_to_extraPickerFragment)
            }
            is MainEvent.TypeSelected -> {
                updateStateWith(
                    selectedType = if (state.get()!!.selectedType == event.typeIndex)
                        -1 else event.typeIndex, selectedSize = -1
                )
                if (state.get()!!.selectedType != -1) {
                    getSizesForType()
                    getExtrasForType()
                }
            }
            is MainEvent.SizeSelected -> {
                updateStateWith(
                    selectedSize = if (state.get()!!.selectedSize == event.sizeIndex)
                        -1 else event.sizeIndex
                )
            }
            is MainEvent.SubSelectionSelected -> {
                updateStateWith(
                    extras = state.get()!!.extras.onEach {
                        if (it.extra.extraId == event.extraId) {
                            it.selectedIndex =
                                if (event.subSelectionIndex == it.selectedIndex) -1 else event.subSelectionIndex
                        }
                    }
                )
            }
        }
    }

    /**
     * Receiving UI events.
     * */

    fun onRefresh() {
        getAll()
    }

    fun onTypeClicked(position: Any): Boolean {
        onEvent(
            MainEvent.TypeSelected(position as Int)
        )
        return true
    }

    fun onSizeClicked(position: Any): Boolean {
        onEvent(
            MainEvent.SizeSelected(position as Int)
        )
        return true
    }

    fun onSubSelectionClicked(extraId: Any, position: Any): Boolean {
        onEvent(
            MainEvent.SubSelectionSelected(extraId as String, position as Int)
        )
        return true
    }

    fun onEdit(item: Any): Boolean {
        when (item) {
            is Type -> onEvent(MainEvent.CheckoutToTypes)
            is Size -> onEvent(MainEvent.CheckoutToSizes)
            is ExtraWithSubSelectionsSelected -> onEvent(MainEvent.CheckoutToExtras)
        }
        return true
    }


    /**
     * Getting the data from the database or the API.
     * */

    private fun getAll() {
        masterUseCase.getAllUseCase(machineId).onEach { result ->
            state.set(
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { masterUseCase.saveAllUseCase(it) }
                        MainState(types = result.data?.types?.map { it.toType() } ?: emptyList())
                    }
                    is Resource.Error -> {
                        val types = masterUseCase.loadTypes()

                        if (types.isEmpty())
                            MainState(error = result.message ?: "Unexpected error.")
                        else {
                            Toast.makeText(navController.context, "Loaded from cache.", Toast.LENGTH_SHORT).show()
                            MainState(types = types)
                        }
                    }


                    is Resource.Loading -> MainState(isLoading = true)
                }
            )
        }.launchIn(viewModelScope)
    }

    private fun getSizesForType() {
        masterUseCase.loadSizesForType(currentType!!.typeId).onEach { result ->
            updateStateWith(sizes = result)
        }.launchIn(viewModelScope)
    }

    private fun getExtrasForType() {
        masterUseCase.loadExtrasForType(currentType!!.typeId).onEach { result ->
            val extras = arrayListOf<ExtraWithSubSelectionsSelected>()

            result.fold(extras) { acc, extra ->
                acc.add(
                    masterUseCase.loadSubsSelectionsForExtra(extra.extraId)
                        .toExtraWithSubSelectionsSelected()
                )
                acc
            }

            updateStateWith(extras = extras)
        }.launchIn(viewModelScope)
    }


    /**
     * Updating the state with specific values.
     * */

    private fun updateStateWith(
        isLoading: Boolean = state.get()!!.isLoading,
        selectedType: Int = state.get()!!.selectedType,
        selectedSize: Int = state.get()!!.selectedSize,
        types: List<Type> = state.get()!!.types,
        sizes: List<Size> = state.get()!!.sizes,
        extras: List<ExtraWithSubSelectionsSelected> = state.get()!!.extras,
        error: String = state.get()!!.error,
    ) {
        state.set(
            state.get()!!.copyWith(
                isLoading = isLoading,
                selectedType = selectedType,
                selectedSize = selectedSize,
                types = types,
                sizes = sizes,
                extras = extras,
                error = error,
            )
        )
    }
}