package com.omarhawari.mrcoffee.domain.use_cases

class MasterUseCase(
    val getAllUseCase: GetAllUseCase,
    val loadSizesForType: LoadSizesForType,
    val saveAllUseCase: SaveAllUseCase,
    val loadExtrasForType: LoadExtrasForType,
    val loadSubsSelectionsForExtra: LoadSubSelectionsForExtra,
    val loadTypes: LoadTypes,
)