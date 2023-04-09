package now.example.hopen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import now.example.hopen.MainActivity.Companion.EXTRA_NAME
import now.example.hopen.databinding.ActivityGoogleSignInBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import android.widget.EditText

import android.content.ContentValues.TAG

import android.util.Log
import android.widget.Button
import now.example.hopen.R
import now.example.hopen.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp.getInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth



class GoogleSignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGoogleSignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        myRef.setValue(string)



    }
}