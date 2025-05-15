package com.example.moviedbapplication.common.extensions

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

fun NavController.navigateSafe(
    directions: NavDirections,
    navOptions: NavOptions? = null
) {
    try {
        val action =
            currentDestination?.getAction(directions.actionId) ?: graph.getAction(directions.actionId)
        if (action != null && currentDestination?.id != action.destinationId) {
            navigate(directions, navOptions)
        }
    } catch (ex: java.lang.Exception) {
        Log.d("navigateSafe", "Exception : $ex")
    }
}