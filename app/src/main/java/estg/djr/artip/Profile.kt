package estg.djr.artip

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.skydoves.landscapist.glide.GlideImage
import estg.djr.artip.dataclasses.PostData
import estg.djr.artip.ui.theme.ArtipTheme
import org.intellij.lang.annotations.JdkConstants
import kotlin.math.round

private var bool = true;
public var myProfile = true;
public var Artista = true;
var Posts: List<Int> = arrayListOf(1,1,1,1,1)
var Favoritos: List<Int> = arrayListOf(1,1,1,1,1,1)

private var  db = FirebaseFirestore.getInstance();
private var mAuth = FirebaseAuth.getInstance()


class Profile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtipTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ProfileCampo(true, myProfile)
                }
            }
        }
    }
}
fun Seguir(seguido: Boolean): String{
    if(seguido){
        return "Seguido"
    }
    return "Seguir"
}

fun Ligar(Numero: String, context: Context){
    val intent = Intent(Intent.ACTION_DIAL);
    intent.data = Uri.parse("tel:$Numero")
    startActivity(context, intent, null)
}

@Composable
fun ProfileCampo(visible: Boolean, myProfile: Boolean) {
    var text = remember { mutableStateOf(Seguir(bool)) }
    var postsArr = remember {mutableStateListOf<String>()}

    val context = LocalContext.current

    if(visible) {


      //  val docRef = db.collection("users").document(mAuth.currentUser?.uid!!.toString()).collection("favoritos")



        val docRef = db.collection("posts")
            .whereEqualTo("user_uid", mAuth.currentUser?.uid!!.toString())
            .orderBy("createdAt", Query.Direction.DESCENDING).limit(50)

        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            if (snapshot != null) {
                DataProvider.DataProvider.postList.clear()
                val documents = snapshot.documents
                if (documents.size != 0) {
                    for (document in documents) {
                        var userPost = document.get("data")
                        postsArr.add(userPost.toString())
                        Log.d("LISTA", postsArr.toString())
                    }
                }
            }
        }



        if(Artista){
            Text(text = "Artista",modifier = Modifier.padding(start = 10.dp, top = 4.dp), fontWeight = FontWeight.Bold)
        }else{
            Text(text = "Espectador", modifier = Modifier.padding(start = 10.dp, top = 4.dp), fontWeight = FontWeight.Bold)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .border(1.dp, Color(100, 244, 244))){
            Box(modifier = Modifier.padding(10.dp)) {
                GlideImage(
                    imageModel = mAuth.currentUser?.photoUrl!!,
                    modifier = Modifier
                        .size(150.dp, 150.dp)
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(2.dp, Color.LightGray, CircleShape),
                    error = ImageBitmap.imageResource(R.drawable.profile_icon)
                )
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.Center,modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)){
                Text(mAuth.currentUser?.displayName!!, fontSize = 30.sp)
                Text("Porto, Portugal", fontSize = 20.sp)
            }
            if(!myProfile) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row{
                        Button(
                            onClick = {
                                bool = !bool
                                text.value = Seguir(bool)
                            },
                            modifier = Modifier.size(110.dp, 40.dp)
                        ) {
                            Text(text.value)
                        }
                        Button(
                            onClick = { Ligar(context = context, Numero = "916002758")},
                            modifier = Modifier
                                .padding(horizontal = 13.dp)
                                .size(120.dp, 40.dp)
                        ) {
                            Text("Ligar")
                        }
                        Button(onClick = { /*TODO*/ }, modifier = Modifier.size(110.dp, 40.dp)) {
                            Text("Donation")
                        }
                    }
                }
            }else{
                Column(
                    Modifier
                        .background(Color(230, 155, 155))
                        .heightIn(max = 150.dp)
                        .padding(bottom = 7.dp, start = 10.dp, end = 10.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(text = "Artistas Favoritos:")
                    LazyColumn(
                    ){
                        this.items(Favoritos){
                            Favoritos("João Freitas")
                        }
                    }
                }
            }
            if(Artista || !myProfile) {
                Text(text = "Publicações:",modifier = Modifier.padding(top = 10.dp))
                LazyColumn(
                    Modifier.padding(
                        bottom = 90.dp
                    )
                ) {
                    items(postsArr) {l ->
                            Posts(
                                mAuth.currentUser?.displayName!!, l
                            )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    ArtipTheme {
        ProfileCampo(true, myProfile)
    }
}

@Composable
fun Posts(username: String, userMensage: String){
    Column (modifier = Modifier
        .padding(horizontal = 10.dp, vertical = 5.dp)
        .clip(shape = RoundedCornerShape(10.dp))
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(216, 216, 216, 255))
                .padding(horizontal = 10.dp, vertical = 3.dp)
        ){

            GlideImage(
                imageModel = mAuth.currentUser?.photoUrl!!,
                modifier = Modifier
                    .size(40.dp, 40.dp)
                    .fillMaxSize()
                    .clip(CircleShape),
                error = ImageBitmap.imageResource(R.drawable.profile_icon)
            )


            Text(text = username, Modifier.padding(start = 10.dp) )

        }
        Text(
            text = userMensage,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(154, 199, 184, 255))
                .heightIn(min = 100.dp)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
    }
}

@Composable
fun Favoritos(username: String) {
    Column(modifier = Modifier
        .padding(vertical = 2.dp)
        .border(1.dp, Color(231, 151, 151))
        .clip(shape = RoundedCornerShape(6.dp))) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(216, 216, 216, 255))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ){


            GlideImage(
                imageModel = mAuth.currentUser?.photoUrl!!,
                modifier = Modifier
                    .size(40.dp, 40.dp)
                    .fillMaxSize()
                    .clip(CircleShape),
                error = ImageBitmap.imageResource(R.drawable.profile_icon)
            )

            Text(text = username, Modifier.padding(start = 10.dp))
        }
    }
}