package com.naranjo.dishcovery.ui.screens.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.naranjo.dishcovery.ui.screens.base.BaseFragment

class MapFragment: BaseFragment<Void>() {

    private val args: MapFragmentArgs by navArgs()

    override fun setContent(): @Composable () -> Unit = {
        val location = LatLng(args.latitude.toDouble(), args.longitude.toDouble())

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(location, 10f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = location),
                title = "Source of this dish"
            )
        }
    }

}