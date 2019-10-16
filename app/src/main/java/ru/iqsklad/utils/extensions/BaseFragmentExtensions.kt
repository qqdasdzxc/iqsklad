package ru.iqsklad.utils.extensions

import ru.iqsklad.ui.base.fragment.BaseFragment

fun BaseFragment<*>.safeNavigate(thisNavID: Int, actionNav: () -> Unit) {
    if (navController.currentDestination?.id == thisNavID) {
        actionNav.invoke()
    }
}