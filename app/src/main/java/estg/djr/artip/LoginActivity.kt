package estg.djr.artip

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.RoundedCorner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import estg.djr.artip.ui.theme.ArtipTheme
import estg.djr.artip.ui.theme.NavButton
import estg.djr.artip.ui.theme.SelectedNavButton
import estg.djr.artip.ui.theme.bg_main
import org.intellij.lang.annotations.JdkConstants

class LoginActivity : ComponentActivity() {

    companion object{
        private const val RC_SIGN_IN = 120
    }

    private lateinit var mAuth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient
    var db = FirebaseFirestore.getInstance();

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtipTheme {
                LoginScreen({ signIn() })
            }
        }

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()

        googleSignInClient.signOut()
    }


    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception

            if(task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)

                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                }
            } else {
                Log.w("SignInActivity", exception.toString())
            }
        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val isNew = task.result.additionalUserInfo!!.isNewUser

                        //se for novo, adicionar ao firebase

                        if(isNew) {
                            val user = hashMapOf(
                                "nome" to mAuth.currentUser?.displayName.toString(),
                                "email" to mAuth.currentUser?.email.toString(),
                                "photoUrl" to mAuth.currentUser?.photoUrl.toString()
                            )

                            db.collection("users")
                                .add(user)
                                .addOnSuccessListener { documentReference ->
                                    Log.d("FIREBASE", "DocumentSnapshot added with ID: ${documentReference.id}")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("FIREBASE", "Error adding document", e)
                                }
                        }

                        val intent = Intent(this, Dashboard::class.java)
                        startActivity(intent)
                        finish()
                        overridePendingTransition( 0, R.anim.fade_out );

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignInActivity", "signInWithCredential:failure", task.exception)
                }
            }
    }

}


@Composable
fun LoginScreen(SignInFunc: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = bg_main)

    ) {
        Spacer(modifier = Modifier.size(40.dp))
        Image(painter = painterResource(R.drawable.artlogo), "Logov",
            Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .size(250.dp))
        Row(
            Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.welcome_message),
                fontFamily = FontFamily(Font(R.font.worksansregular)),
                fontSize = 30.sp,
                color = SelectedNavButton)
        }
        Spacer(modifier = Modifier.size(20.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = NavButton)
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(modifier = Modifier.padding(10.dp)){
                Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                    Text(text = stringResource(id = R.string.login_message),
                        fontFamily = FontFamily(Font(R.font.worksansregular)),
                    fontSize = 20.sp,
                    color = bg_main)
                    Spacer(modifier = Modifier.size(5.dp))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column {
                        Box(
                            Modifier
                                .padding(10.dp)
                                .clip(shape = RoundedCornerShape(50))
                                .clickable { SignInFunc()}
                        )
                        {
                            Image(painter = painterResource(id = R.drawable.g), contentDescription = "google"
                                ,
                                Modifier
                                    .size(80.dp)
                                    .background(color = bg_main)
                                    .padding(10.dp))

                        }
                    }
                }
                Spacer(modifier = Modifier.size(40.dp))

                //eliminado
            }

        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    ArtipTheme {
        LoginScreen({})
    }
}