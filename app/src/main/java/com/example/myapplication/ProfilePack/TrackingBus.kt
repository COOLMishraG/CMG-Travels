package com.example.myapplication.ProfilePack

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.Weather.WeatherApiService
import com.example.myapplication.Weather.WeatherResponse
import com.example.myapplication.databinding.ActivityTrackingBusBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import kotlin.math.log
import kotlin.math.pow
import kotlin.random.Random

class TrackingBus : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    private lateinit var binding: ActivityTrackingBusBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                getCurrentLocation()
            }
        }
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherApiService = retrofit.create(WeatherApiService::class.java)


    private lateinit var destinationLatLng: LatLng // Variable for destination location

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTrackingBusBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }
    private fun createCustomMarkerBitmap(layoutResId: Int): BitmapDescriptor {
        // Inflate the layout
        val view = LayoutInflater.from(this).inflate(layoutResId, null)
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun addMarkersForCoordinates() {
         val coordinatesList = listOf(
            LatLng(19.0760, 72.8777), // Mumbai
            LatLng(22.5726, 88.3639), // Kolkata
            LatLng(28.6139, 77.2090), // Delhi
            LatLng(13.0827, 80.2707), // Chennai
            LatLng(17.3850, 78.4867), // Hyderabad
            LatLng(12.9716, 77.5946), // Bangalore
            LatLng(26.9124, 75.7873), // Jaipur
            LatLng(22.3072, 73.1812), // Ahmedabad
            LatLng(30.7333, 76.7794), // Chandigarh
            LatLng(23.2599, 77.4126), // Bhopal
            LatLng(21.1458, 79.0882), // Nagpur
            LatLng(23.1762, 78.6569), // Gwalior
            LatLng(25.4376, 78.5722), // Sagar
            LatLng(19.2183, 72.9781),// Thane
             LatLng(22.7197, 75.8573) // Indore
        )

        for (coordinate in coordinatesList) {
            // Create the custom marker bitmap
            val customMarkerBitmap = createCustomMarkerBitmap(R.layout.stationmarker)

            googleMap.addMarker(
                MarkerOptions()
                    .position(coordinate)
                    .title("Marker at ${coordinate.latitude}, ${coordinate.longitude}")
                    .icon(customMarkerBitmap) // Use your custom icon
            )
        }
    }
    @SuppressLint("MissingInflatedId")
    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        setupMap(googleMap)
        googleMap.uiSettings.isZoomControlsEnabled = true // Enable zoom controls

        // Enable traffic layer
        googleMap.isTrafficEnabled = true

        // Request location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            getCurrentLocation()
        }

        // Add custom marker at a different location (e.g., India Gate)
        val customMarkerView = LayoutInflater.from(this).inflate(R.layout.marker_info_window, null)
        val markerText = customMarkerView.findViewById<TextView>(R.id.markerTitle)
        markerText.text = "MP-HTJI-689"
        val markerText2 = customMarkerView.findViewById<TextView>(R.id.markerTitle2)
        val customMarkerBitmap = createBitmapFromView(customMarkerView)

        // Set destination location (Example: India Gate)
       // destinationLatLng = LatLng(16.2469, 79.4479) // Change this LatLng to your destination
        val (randomLat, randomLng) = getRandomCoordinatesForCalifornia()
        Toast.makeText( this , "$randomLat.toString() $randomLng.toString()", Toast.LENGTH_SHORT).show()
        destinationLatLng = LatLng(randomLat, randomLng)
        // Add a marker for the destination location
        googleMap.addMarker(
            MarkerOptions()
                .position(destinationLatLng)
                .title("Destination Location")
                .icon(BitmapDescriptorFactory.fromBitmap(customMarkerBitmap))
        )
        addMarkersForCoordinates()
    }
    private fun getRandomCoordinatesForCalifornia(): Pair<Double, Double> {
        // California boundaries
        val minLat = 21.1600
        val maxLat = 26.3000
        val minLng = 74.0000
        val maxLng = 82.0000

        // Generate random latitude and longitude
        val randomLat = Random.nextDouble(minLat, maxLat)
        val randomLng = Random.nextDouble(minLng, maxLng)

        return Pair(randomLat, randomLng)
    }
    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val currentLatLng = LatLng(it.latitude, it.longitude)

                    // Enable the blue dot for the current location
                    googleMap.isMyLocationEnabled = true

                    // Move the camera to the current location
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))

                    // Draw a line (Polyline) between current location and destination
                    val points = drawLeftwardCurveBetweenLocations(currentLatLng, destinationLatLng)

                    // Create 5 equidistant locations for weather data
                    val weatherLocations = getWeatherLocations(points)

                    // Add weather markers
                    addWeatherMarkers(weatherLocations)

                    // Create a LatLngBounds to include both locations
                    val builder = LatLngBounds.Builder()
                    builder.include(currentLatLng) // Include current location
                    builder.include(destinationLatLng) // Include destination location
                   val distanceBetweenBoth = haversine(currentLatLng.latitude, currentLatLng.longitude, destinationLatLng.latitude, destinationLatLng.longitude)
                    //Adding the Distance between the source and the target
                    val customMarkerView = LayoutInflater.from(this).inflate(R.layout.marker_info_window, null)

                    val markerText2 = customMarkerView.findViewById<TextView>(R.id.markerTitle2)
                    markerText2.text = "${distanceBetweenBoth.toInt()} km"
                    val distanceInKm = distanceBetweenBoth.toInt()
                    val estimatedArrivalTimeInHours = distanceInKm / 55.0
                    val calendar = Calendar.getInstance()
                    val minutesToAdd = (estimatedArrivalTimeInHours * 60).toInt()
                    calendar.add(Calendar.MINUTE, minutesToAdd)
                    val arrivalHour = calendar.get(Calendar.HOUR_OF_DAY)
                    val arrivalMinute = calendar.get(Calendar.MINUTE)

                    binding.textView9.text = "  EAT: ${String.format("%02d:%02d", arrivalHour, arrivalMinute)} hrs  "
                    val customMarkerBitmap = createBitmapFromView(customMarkerView)
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(destinationLatLng)
                            .title("Destination Location")
                            .icon(BitmapDescriptorFactory.fromBitmap(customMarkerBitmap))
                    )
                    // Move the camera to fit both locations
                    val bounds = builder.build()
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100)) // 100 is padding in pixels
                }
            }
        }
    }

    // Function to create a smooth horizontal curve using quadratic Bézier
    private fun drawLeftwardCurveBetweenLocations(start: LatLng, end: LatLng): List<LatLng> {
        val pattern = listOf<PatternItem>(Dot(), Gap(10f)) // Dotted pattern

        // Calculate the midpoint between start and end
        val midLat = (start.latitude + end.latitude) / 2
        val midLng = (start.longitude + end.longitude) / 2

        // Curvature offset for leftward curve, adjust this to control the curvature intensity
        val curvatureOffset = -0.15 // Negative for leftward curvature

        // Apply horizontal curvature by offsetting the midpoint's longitude
        val midPoint = LatLng(midLat, midLng + curvatureOffset)

        // Create intermediate points for a smooth curve
        val points = mutableListOf<LatLng>()
        val numPoints = 100 // Higher number means smoother curve

        // Interpolate points for a quadratic Bézier curve
        for (i in 0..numPoints) {
            val t = i / numPoints.toFloat()

            // Calculate quadratic Bézier interpolation for a leftward curve
            val lat = (1 - t).pow(2) * start.latitude + 2 * (1 - t) * t * midLat + t.pow(2) * end.latitude
            val lng = (1 - t).pow(2) * start.longitude + 2 * (1 - t) * t * midPoint.longitude + t.pow(2) * end.longitude

            points.add(LatLng(lat, lng))
        }

        // Create a PolylineOptions object to customize the line
        val polylineOptions = PolylineOptions()
            .addAll(points)  // Add the interpolated points for a smooth curve
            .width(8f)       // Line width
            .color(0xFF000000.toInt()) // Black color for the line
            .pattern(pattern) // Apply dotted pattern

        // Add the polyline to the map
        googleMap.addPolyline(polylineOptions)

        return points // Return the polyline points
    }

    // Fetches weather data from OpenWeather API
    private fun fetchWeatherData(lat: Double, lon: Double, callback: (WeatherResponse) -> Unit) {
        val apiKey = getString(R.string.weather_api) // Your OpenWeather API key

        // Make the API call in a coroutine
        lifecycleScope.launch {
            try {
                val weatherData = weatherApiService.getWeatherData(lat, lon, apiKey)
                callback(weatherData) // Pass the data to the callback
            } catch (e: Exception) {
                e.printStackTrace() // Handle the error
            }
        }
    }

    // Add markers with weather data
    private fun addWeatherMarkers(locations: List<LatLng>) {
        for (location in locations) {
            fetchWeatherData(location.latitude, location.longitude) { weatherData ->
                val weatherIcon = getWeatherIcon(weatherData.weather[0].icon) // Get the weather icon from the response
                googleMap.addMarker(
                    MarkerOptions()
                        .position(location)
                        .title("Temp: ${weatherData.main.temp}°C") // Show temperature in title
                        .icon(BitmapDescriptorFactory.fromBitmap(getWeatherIcon(weatherData.weather[0].icon))) // Display weather icon
                )
            }
        }
    }
    private fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0 // Radius of the Earth in kilometers
        val dLat = Math.toRadians(lat2 - lat1) // Convert latitude difference to radians
        val dLon = Math.toRadians(lon2 - lon1) // Convert longitude difference to radians

        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)

        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return R * c // Distance in kilometers
    }
    // Calculate equidistant points for weather data along the polyline
    private fun getWeatherLocations(points: List<LatLng>, numLocations: Int = 6): List<LatLng> {
        val step = points.size / numLocations
        return List(numLocations) { i -> points[i * step] }
    }

    // Create bitmap from custom view
    private fun createBitmapFromView(view: android.view.View): Bitmap {
        view.measure(
            android.view.View.MeasureSpec.makeMeasureSpec(0, android.view.View.MeasureSpec.UNSPECIFIED),
            android.view.View.MeasureSpec.makeMeasureSpec(0, android.view.View.MeasureSpec.UNSPECIFIED)
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)

        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
    private fun getWeatherIcon(iconCode: String): Bitmap {
        // You can map icon codes from OpenWeather to your drawable resources
        val iconResId = when (iconCode) {
            "01d" -> R.drawable.sun // Clear sky day
            "01n" -> R.drawable.night // Clear sky night
            "02d", "02n" -> R.drawable.cloudyfew // Few clouds (day or night)
            "03d", "03n", "04d", "04n" -> R.drawable.cloudy // Cloudy or overcast
            "09d", "09n" -> R.drawable.cloudyrain // Shower rain
            "10d", "10n" -> R.drawable.heavyrain// Rain (day or night)
            "11d", "11n" -> R.drawable.thunder // Thunderstorm
            "13d", "13n" -> R.drawable.cmg // Snow
            "50d", "50n" -> R.drawable.cmg // Mist or fog
            else -> R.drawable.cmg // Default weather icon for unknown conditions
        }

        // Convert the drawable resource into a Bitmap to use as a marker icon
        val drawable = getDrawable(iconResId)!!
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        val sizeMultiplier = 0.05f // Change this value to resize (0.5f = 50% of original size)
        return Bitmap.createScaledBitmap(bitmap, (bitmap.width * sizeMultiplier).toInt(), (bitmap.height * sizeMultiplier).toInt(), false)
    }


    // Handle map lifecycle
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
    private fun setupMap(googleMap: GoogleMap) {
        // Set the map style to dark mode
        val mapStyleOptions = MapStyleOptions("""
        [
            {
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#212121"
                    }
                ]
            },
            {
                "elementType": "label.icon",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "elementType": "label.text.fill",
                "stylers": [
                    {
                        "color": "#757575"
                    }
                ]
            },
            {
                "elementType": "label.text.stroke",
                "stylers": [
                    {
                        "color": "#212121"
                    }
                ]
            },
            {
                "featureType": "administrative",
                "elementType": "geometry",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "poi",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#303030"
                    }
                ]
            },
            {
                "featureType": "road",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#3e3e3e"
                    }
                ]
            },
            {
                "featureType": "water",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#000000"
                    }
                ]
            }
        ]
    """.trimIndent())

        try {
            googleMap.setMapStyle(mapStyleOptions)
        } catch (e: Exception) {
            Log.e("MapStyle", "Failed to set map style: ", e)
        }
    }
}
