package com.example.dt_autoinstrumentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row

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
import android.location.Location
import android.location.LocationListener //ini must untuk gps
import android.location.LocationManager
import android.os.Build
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.ViewModel
import com.dynatrace.android.agent.conf.DataCollectionLevel
import com.dynatrace.android.agent.conf.UserPrivacyOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class MainActivity : ComponentActivity(),LocationListener {
    private lateinit var locationManager: LocationManager
    private val msgInfo: MainViewModel by viewModels()
    private val LOCATION_PERMISSION_CODE = 100 //gps part



    override fun onCreate(savedInstanceState: Bundle?) {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager //gps part

        Dynatrace.applyUserPrivacyOptions(
            UserPrivacyOptions.builder()
                .withDataCollectionLevel(DataCollectionLevel.USER_BEHAVIOR)
                .withCrashReplayOptedIn(true)
                .withScreenRecordOptedIn(false)
                .withCrashReportingOptedIn(false)








                .build()
        )


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DTAutoInstrumentationTheme {
                val msgGPS by msgInfo.msgGPS.collectAsState()
                Scaffold(content = { paddingValues ->
                    Column(
                        modifier = Modifier.wrapContentHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(

                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Greeting(

                                name = "DT GPS Testing",
                                modifier = Modifier.padding(paddingValues),

                                )
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            LocationButton(
                                "Singapore", locationManager,
                                modifier = Modifier.padding(paddingValues), baseContext
                            )
                            LocationButton(
                                "Malaysia", locationManager,
                                modifier = Modifier.padding(paddingValues), baseContext
                            )
                            LocationButton(
                                "Japan", locationManager,
                                modifier = Modifier.padding(paddingValues), baseContext
                            )


                        }
                        Row(

                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            LocationButton(
                                "Auto Loc", locationManager,
                                modifier = Modifier.padding(paddingValues), baseContext
                            )
                            LocationButton(
                                "Network Based", locationManager,
                                modifier = Modifier.padding(paddingValues), baseContext
                            )
                            LocationButton(
                                "GPS Based", locationManager,
                                modifier = Modifier.padding(paddingValues), baseContext
                            )
                        }
                        Row(

                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text("Location GPS:")
                            Text(msgGPS )
//                            Text(msgGPS)
                        }
                    }


                })
//


            }
        }


        //check location
        this.locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {


//untuk execute pop up req access location
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_CODE
            )


        } else {
            Log.d("Konci", "onCreate: already have permission")
        }


    }

//
//    @SuppressLint("MissingPermission", "NewApi", "HardwareIds")
//    fun getImei(activity: MainActivity) {
//        val manager = activity!!.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//        if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_PHONE_STATE), 1000)
//            Log.d("konci1", "failed")
//            return
//
//        }
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
//            Log.d("konci1", "getImei: "+manager.imei)
//            //                    print(manager.deviceId)
//            Log.d("konci1","getImei: "+ manager.deviceId)
//        } else {
//            //                    print(manager.imei)
//            Log.d("konci12", "getImei: "+manager.imei)
//        }
//    }


    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("MissingPermission", "NewApi")
    fun getLocation(name: String, locationManager: LocationManager, context: Context): Location {
        val loc = Location("staticProvider")


        if (name == "Japan") {
            loc.latitude = 35.652832;
            loc.longitude = 139.839478;
        } else if (name == "Malaysia") {
            loc.latitude = 3.140853;
            loc.longitude = 101.693207;
        } else if (name == "Singapore") {
            loc.latitude = 1.290270;
            loc.longitude = 103.851959;
        } else {

            var provider= "Notype"
            if (name == "GPS Based") {
                provider=LocationManager.GPS_PROVIDER
            } else if (name == "Network Based"){
                provider = LocationManager.NETWORK_PROVIDER
            }else{
                provider = when {
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
                    else -> "NoType"
                }
            }



            if (provider == "NoType") {
                Toast.makeText(context, "No location provider enabled", Toast.LENGTH_SHORT).show()
//            return
            } else {

                Toast.makeText(context, provider.toString() , Toast.LENGTH_SHORT).show()
                Log.d(
                    "konci", "getLocation: " + locationManager.allProviders.toString()
                )

                locationManager.removeUpdates(this)
                locationManager.requestLocationUpdates(
                    provider,
                    2000L,
                    5f,
                    this
                )


                Log.d("konci", "getLocation: GNSS Year"+locationManager.gnssYearOfHardware)
                Log.d("konci", "getLocation: gnssAntennaInfos"+locationManager.gnssAntennaInfos)
                Log.d("konci", "getLocation: gnssCapabilities"+locationManager.gnssCapabilities)
//

            }


        }

        return loc;


    }


    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = name,
            modifier = modifier
        )
    }


    @Composable
    fun LocationButton(
        city: String,
        locationManager: LocationManager,
        modifier: Modifier = Modifier,
        context: Context
        ){
        Button(onClick = {

                msgInfo.updateMsgGPS("fetching")

                val loc: Location = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    getLocation(city, locationManager, context)
                } else {
                    TODO("VERSION.SDK_INT < P")
                }
                if (loc.latitude != 0.0) {
                    Dynatrace.modifyUserAction { userAction: ModifiableUserAction ->
//                userAction.actionName = "Change Location"
//            val userAction=Dynatrace.enterAction("Change Location")

                    msgInfo.updateMsgGPS("long:" + loc.longitude + "#lat:" + loc.latitude)
                    Toast.makeText(
                        context,
                        "Changed to " + city + " " + loc.latitude + "," + loc.longitude,
                        Toast.LENGTH_SHORT
                    ).show()


//

//                    Dynatrace.setGpsLocation(loc)
                    userAction.reportValue("City", city)
                    userAction.reportValue("latitude", loc.latitude)
                    userAction.reportValue("longitude", loc.longitude)
//                userAction.leaveAction()
                }
                    Dynatrace.setGpsLocation(loc)
            }
//    }, modifier = modifier, enabled = true, shape = , elevation = TODO(), interactionSource = TODO()) {
        }, modifier = modifier, enabled = true) {
            Text(city)
        }


    }

    override fun onLocationChanged(p0: Location) {
        val msg="long:"+p0.longitude+"#lat:"+p0.latitude+"#provider:"+p0.provider
        //debug showing
        Log.d("konci", "onLocationChanged: "+msg)
        Toast.makeText(this,"onLocationChanged: "+msg,Toast.LENGTH_SHORT).show()
        msgInfo.updateMsgGPS(msg)

        val typeLocation = if (p0.provider==LocationManager.GPS_PROVIDER) "GPS Location" else "Network Location"


        val userAction=Dynatrace.enterAction(typeLocation)
//        Dynatrace.modifyUserAction { userAction: ModifiableUserAction ->
//      userAction.actionName=typeLocation




//            Dynatrace.setGpsLocation (loc)
            userAction.reportValue("City", p0.provider)
            userAction.reportValue("kind", "dynamic")
            userAction.reportValue("latitude", p0.latitude)
            userAction.reportValue("longitude", p0.longitude)
            userAction.leaveAction()
//
//        }
        Dynatrace.setGpsLocation(p0) //p0 is not null because it is true on the report value result and text on the screen

    }


    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Log.d("konci", "onStatusChanged: "+provider+"#"+status.toString())
        msgInfo.updateMsgGPS(status.toString())
        Toast.makeText(this,status,Toast.LENGTH_SHORT).show();
    }
    override fun onProviderEnabled(provider: String) {
        Log.d("konci", "onProviderEnabled: "+provider)
    }
    override fun onProviderDisabled(provider: String) {
        Log.d("konci", "onProviderDisabled: "+provider)
    }






}







class MainViewModel : ViewModel() {
    private val _msgGPS = MutableStateFlow("Not Selected")
    val msgGPS:  StateFlow<String> = _msgGPS

    fun updateMsgGPS(newMsg: String) {
        _msgGPS.value = newMsg
    }
}

