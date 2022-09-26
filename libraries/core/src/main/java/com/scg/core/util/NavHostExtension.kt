package com.scg.core.util

import androidx.navigation.dynamicfeatures.fragment.DynamicNavHostFragment
import androidx.navigation.fragment.NavHostFragment
import com.scg.core.ui.fragment.navhost.FieldProperty
import com.scg.core.viewmodel.NavControllerViewModel

var DynamicNavHostFragment.viewModel: NavControllerViewModel by FieldProperty {
    NavControllerViewModel()
}

var NavHostFragment.viewModel: NavControllerViewModel by FieldProperty {
    NavControllerViewModel()
}
