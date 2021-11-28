package estg.djr.artip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import estg.djr.artip.ui.theme.ArtipTheme


class MainActivity : AppCompatActivity(){

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        val user = mAuth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if(user != null) {
                //dashboard screen
                mAuth.signOut()
            } else {
                val signInIntent = Intent(this, LoginActivity::class.java)
                startActivity(signInIntent)
                finish()
            }
        }, 2000)

    }

}