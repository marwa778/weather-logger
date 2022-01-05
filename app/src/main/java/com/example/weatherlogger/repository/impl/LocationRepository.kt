package com.example.weatherlogger.repository.impl

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.example.weatherlogger.network.responses.Coordinates
import com.example.weatherlogger.repository.repositoryinterface.LocationRepositoryInterface
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.CancellationToken
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.google.android.gms.tasks.OnTokenCanceledListener
import javax.inject.Inject

const val REQUEST_LOCATION_PERMISSION_CODE = 100

class LocationRepository @Inject constructor(
    private val activity: Activity,
    private val fusedLocationClient: FusedLocationProviderClient) : LocationRepositoryInterface {

    private fun checkLocationPermissionNotGranted() =
        (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            REQUEST_LOCATION_PERMISSION_CODE
        )
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Coordinates = suspendCoroutine { cont ->
        if(!checkLocationPermissionNotGranted()) {
            val token = object : CancellationToken() {
                override fun isCancellationRequested() = false

                override fun onCanceledRequested(onTokenCanceledListener: OnTokenCanceledListener) = this
            }

            fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, token)
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        cont.resume(Coordinates(lat = location.latitude, lon = location.longitude))
                    } else {
                        cont.resume(Coordinates(lat = 31.0, lon = 30.0))
                    }
                }
        } else {
            requestLocationPermission()
        }
    }
}
