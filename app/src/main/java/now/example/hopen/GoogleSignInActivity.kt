package now.example.hopen

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import now.example.hopen.MainActivity.Companion.EXTRA_NAME
import now.example.hopen.databinding.ActivityGoogleSignInBinding
import org.json.JSONObject
import java.net.URI
import java.net.URL
import java.sql.Timestamp
import java.util.concurrent.Executors


class GoogleSignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGoogleSignInBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var client_location = "null"

    override fun onCreate(savedInstanceState: Bundle?) {

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            var personName = acct.displayName
            var personGivenName = acct.givenName
            var personFamilyName = acct.familyName
            var personEmail = acct.email
            var personId = acct.id
            var personPhoto: Uri? = acct.photoUrl






        }




        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            val locationPermissionRequest = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        // Precise location access granted.
                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        // Only approximate location access granted.
                    } else -> {
                    // No location access granted.
                }
                }
            }

// ...

// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))

        }



        super.onCreate(savedInstanceState)
        binding = ActivityGoogleSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (acct != null) {
            var personName = acct.displayName
            var personGivenName = acct.givenName
            var personFamilyName = acct.familyName
            var personEmail = acct.email
            var personId = acct.id
            var personPhoto: Uri? = acct.photoUrl

            var photo : ImageView = findViewById(R.id.p_image)

            Log.v(TAG, photo.toString());

            //val imageUri = "https://i.imgur.com/tGbaZCY.jpg"
            Picasso.get().load(personPhoto).into(photo)



        }




        binding.textDisplayName.text = intent.getStringExtra(EXTRA_NAME)
        binding.logout.setOnClickListener {
            Firebase.auth.signOut()

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }




        binding.sendReport.setOnClickListener {

            sendReport()
        }


    }

    private fun sendReport() {


        var string: String = binding.editText.toString()
        var editText: EditText = findViewById(R.id.editText)
        var name = intent.getStringExtra(EXTRA_NAME)
        string = editText.text.toString()
        val url = "https://hopen-f6b75-default-rtdb.firebaseio.com/"
        val database = FirebaseDatabase.getInstance(url)
        val myRef = database.getReference(name.toString())
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->

                val latitude = location?.latitude
                val longitude = location?.longitude
                val currentTimeMillis = System.currentTimeMillis()
                val timestamp =  Timestamp(currentTimeMillis)
                // Got last known location. In some rare situations this can be null.
                val dataToSend = JSONObject()
                dataToSend.put("status", string)
                dataToSend.put("location", location)
                dataToSend.put("latitude",latitude)
                dataToSend.put("longitude", longitude)
                dataToSend.put("timestamp",timestamp)

                myRef.setValue(dataToSend.toString())


            //myRef.setValue(string + "\n  \n corresponding @ : \n \n" + location)

            }


        val intent = Intent(this, MainActivity::class.java)
        // start your next activity
        startActivity(intent)



    }





}