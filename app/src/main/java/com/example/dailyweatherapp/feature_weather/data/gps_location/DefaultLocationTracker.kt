package com.example.dailyweatherapp.feature_weather.data.gps_location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.example.dailyweatherapp.feature_weather.domain.gps.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

@ExperimentalCoroutinesApi
class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
): LocationTracker {

    override suspend fun getCurrentLocation(): Location? {
        //Check for the android.permission.ACCESS_FINE_LOCATION in the manifest file.
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        //Check for the android.permission.ACCESS_COARSE_LOCATION in the manifest file.
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        //Get reference to location manager.
        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //Check permissions for gps location is enabled to retrieve location or return empty.
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isGpsEnabled) {
            return null
        }

        //If check permissions for gps is enabled, then we do this in coroutine.
        return suspendCancellableCoroutine { continuation ->
            locationClient.lastLocation.apply {
                if (isComplete) {
                    if(isSuccessful) {
                        continuation.resume(result)
                    } else {
                        continuation.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    continuation.resume(it)
                }
                addOnFailureListener {
                    continuation.resume(null)
                }
                addOnCanceledListener {
                    continuation.cancel()
                }
            }
        }
    }
}

private fun <T> CancellableContinuation<T>.resume(value: T?) {
    resume(value)
}