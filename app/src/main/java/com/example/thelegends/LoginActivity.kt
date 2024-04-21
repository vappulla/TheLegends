package com.example.thelegends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp // Add this import

class LoginActivity : AppCompatActivity() {

    // Declare variables
    private lateinit var firebaseAuth: FirebaseAuth
    private val signUpBtn: Button get() = findViewById(R.id.signUpButton)
    private val loginBtn: Button get() = findViewById(R.id.loginButton)
    private val usernameText: EditText get() = findViewById(R.id.usernameInput)
    private val passwordText: EditText get() = findViewById(R.id.passwordInput)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Instantiate firebase
        firebaseAuth = FirebaseAuth.getInstance()

        // Sign up functionality when sign up btn is tapped
        signUpBtn.setOnClickListener(){
            // Grab email and password input
            val email = usernameText.text.toString()
            val pass = passwordText.text.toString()

            // Check if email and pass are empty, if they are show error msg
            if (email.isNotEmpty() && pass.isNotEmpty()){

                // Create new user from email and password input
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{

                    // If sign up is successful, go to next activity, otherwise show error msg
                    if (it.isSuccessful){
                        Log.d("Sign Up", "Account Creation Successful")
                        launchMainActivity()
                    }else {
                        Toast.makeText(this , it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else {
                Toast.makeText(this , "Invalid email or password", Toast.LENGTH_SHORT).show()
            }


        }

        // Login functionality once login btn is tapped
        loginBtn.setOnClickListener(){
            // Grab email and password input
            val email = usernameText.text.toString()
            val pass = passwordText.text.toString()

            // Check if email and pass are empty, if they are show error msg
            if(email.isNotEmpty() && pass.isNotEmpty()) {

                // Login existing user
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener{

                    // If login is successful, go to next activity, otherwise show error msg
                    if (it.isSuccessful){
                        Log.d("Login", "Login Successful")
                        launchMainActivity()
                    }else {
                        Toast.makeText(this , it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this , "Invalid email or password", Toast.LENGTH_SHORT).show()
            }

        }
    }

    // Function to take user to next activity
    private fun launchMainActivity() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}
