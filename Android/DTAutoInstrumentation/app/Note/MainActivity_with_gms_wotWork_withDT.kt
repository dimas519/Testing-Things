package com.example.dt_autoinstrumentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.dynatrace.android.agent.Dynatrace
import com.dynatrace.android.agent.ModifiableUserAction
import com.example.dt_autoinstrumentation.ui.theme.DTAutoInstrumentationTheme

//untuk location
import com.google.android.gms.location.LocationServices
import android.os.Build
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority

class MainActivity2 : ComponentActivity(){
    private lateinit var locationManager: LocationManager

    private lateinit var fusedLocationClient: FusedLocationProviderClient //untuk gms


    override fun onCreate(savedInstanceState: Bundle?) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DTAutoInstrumentationTheme {


                Scaffold(content = { paddingValues ->
                    Row(
                        modifier = Modifier.fillMaxSize() ,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Greeting(
                            
                            name = "Android",
                            modifier = Modifier.padding(paddingValues)

                        )
                    }

                    Row(modifier = Modifier.fillMaxSize() ,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        LocationButton("Singapore",fusedLocationClient,
                            modifier=Modifier.padding(paddingValues),baseContext)
                        LocationButton("Malaysia",fusedLocationClient,
                            modifier=Modifier.padding(paddingValues),baseContext)
                        LocationButton("Japan",fusedLocationClient,
                            modifier=Modifier.padding(paddingValues),baseContext)
                        LocationButton("Current",fusedLocationClient,
                            modifier=Modifier.padding(paddingValues),baseContext)

                    }

                    Row(
                        modifier = Modifier.fillMaxSize() ,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Greeting(

                            name = "Android",
                            modifier = Modifier.padding(paddingValues)

                        )
                    }



                })
//






            }
        }






        //check location
        this.locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }else{
            Log.d("Konci", "onCreate: permission failed")
        }




    }







}




@SuppressLint("MissingPermission")
fun getLocation(name:String,fusedLocationClient:FusedLocationProviderClient, context: Context ): Location {
    val loc = Location("staticProvider")


    if(name=="Japan"){
        loc.latitude=35.652832;
        loc.longitude=139.839478;
        return loc
    }else if(name=="Malaysia"){
        loc.latitude=3.140853;
        loc.longitude=101.693207;
        return loc
    }else if(name=="Singapore"){
        loc.latitude=1.290270;
        loc.longitude=103.851959;
        return loc
    }else {

        val priority = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Priority.PRIORITY_HIGH_ACCURACY
        } else {
            LocationRequest.PRIORITY_HIGH_ACCURACY
        }


        fusedLocationClient.getCurrentLocation(priority,null)
            .addOnSuccessListener { location: Location ->
                if (location != null) {
                    Toast.makeText(
                        context,
                        "Lat: ${location.latitude}, Lon: ${location.longitude}",
                        Toast.LENGTH_LONG
                    ).show()
                }


            }
        return loc
    }



}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifier
    )
}



@Composable
fun LocationButton(city:String,fusedLocationClient:FusedLocationProviderClient,modifier: Modifier = Modifier, context: Context){
    Button(onClick = {
        Dynatrace.modifyUserAction { userAction: ModifiableUserAction ->
            userAction.actionName = "Change Location"

            val loc:Location=getLocation( city,fusedLocationClient,context )
            Toast.makeText(context,"Changed to "+city+" "+loc.latitude+","+loc.longitude,Toast.LENGTH_SHORT).show()

            Dynatrace.setGpsLocation(loc)

//            Dynatrace.setGpsLocation (loc)
            userAction.reportValue("City", city)
            userAction.reportValue("latitude", loc.latitude)
            userAction.reportValue("longitude", loc.longitude)
        }
//    }, modifier = modifier, enabled = true, shape = , elevation = TODO(), interactionSource = TODO()) {
    }, modifier = modifier, enabled = true) {
        Text(city)

    }



}