package now.example.hopen
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import now.example.hopen.MainActivity.Companion.EXTRA_NAME
import now.example.hopen.databinding.ActivityGoogleSignInBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import now.example.hopen.R
import now.example.hopen.MainActivity.Companion.EXTRA_NAME
import now.example.hopen.R.id.blow
import now.example.hopen.databinding.ActivityAnotherBinding
import com.google.firebase.auth.ktx.auth
import com.squareup.picasso.Picasso
import org.json.JSONObject

class AnotherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnotherBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_another)

        binding = ActivityAnotherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val load: Button = findViewById(blow)
        load.setOnClickListener {
            blow()
        }
    }

    // Read from the database
    fun blow() {
        val url2 = "https://hopen-f6b75-default-rtdb.firebaseio.com/"
        val database2 = FirebaseDatabase.getInstance(url2)

        var usertoget: EditText = findViewById(R.id.user)
        var thetext = usertoget.text
        val Ref2 = database2.getReference(thetext.toString())

        // Read from the database
        Ref2.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                val value2 = JSONObject(value)

                val status = value2.getString("status")
                val location = value2.getString("location")
                val longitude = value2.getString("longitude")
                val latitude = value2.getString("latitude")
                val moment = value2.getString("timestamp")
                val personPhoto = value2.getString("personPhoto")



                Log.d(TAG, "Value is: $value")

                var string2: String = binding.openview.toString()
                var openview: TextView = findViewById(R.id.openview)
                openview.text = thetext.toString() + " says: \n" + status+ "\n"+ moment

                val mapView = findViewById<MapView>(R.id.map)
                val location2 = LatLng(latitude.toDouble(), longitude.toDouble())


                var photo : ImageView = findViewById(R.id.personPhoto)

                Log.v(TAG, photo.toString());

                //val imageUri = "https://i.imgur.com/tGbaZCY.jpg"
                Picasso.get().load(personPhoto).into(photo)

                // Add the missing curly brace here




                    // Initialize the MapView
                    mapView.onCreate(Bundle())
                    mapView.onResume()
                    mapView.getMapAsync { googleMap ->
                        // Add a marker at the specified location
                        val markerOptions = MarkerOptions()
                            .position(location2)
                            .title("corresponding from here")
                            .snippet("Marker Snippet")
                        googleMap.addMarker(markerOptions)

                        // Move the camera to the marker location
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location2, 15f))
                    }






            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}
