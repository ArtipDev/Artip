package estg.djr.artip


import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import estg.djr.artip.data.SavePrefUserType
import estg.djr.artip.dataclasses.PostData
import estg.djr.artip.ui.theme.ArtipTheme
import estg.djr.artip.ui.theme.Artip_pink
import java.util.*


private var db = FirebaseFirestore.getInstance();
private var mAuth = FirebaseAuth.getInstance()
var showAlert = false;








class Feed : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ArtipTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }

    }
}

fun insertPost(context: Context, text: String) {
    val timestramp = FieldValue.serverTimestamp();
    val user = mAuth.currentUser!!
    val displaName =  user.displayName.toString()
    val photoUrl = user.photoUrl.toString()
    val uid = user.uid.toString()

    val postData = hashMapOf(
        "createdAt" to timestramp,
        "data" to text,
        "user_uid" to uid,
        "username" to displaName,
        "userPhoto" to photoUrl
    )

    Log.d("CLICOU", "Clicou")

    db.collection("posts")
        .add(postData)
        .addOnSuccessListener { documentReference ->
          Log.d("DATA", "ADDED")
            Toast.makeText(context, "Post criado com sucesso!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e -> Log.w("INSERIR", "Error adding document", e) }
}



@Composable
fun FeedCompo(visible: Boolean, postList: List<PostData>) {



    val context = LocalContext.current

    val posts = remember { mutableStateListOf(DataProvider.DataProvider.postList) }


    var l: Location?

    val listState = rememberLazyListState()

    val longitude = remember { mutableStateOf(12.0) }
    val latitude = remember { mutableStateOf(-14.0) }

    val dataUserType = SavePrefUserType(context)
    val savedUserType = dataUserType.getUserTypePref.collectAsState(initial = false)


    /*val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
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
        return
    }
        mFusedLocationClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            l = location
        }*/

    val geocoder = Geocoder(context, Locale.getDefault())

    val textState = remember { mutableStateOf(TextFieldValue()) }
    var lines = postList
    val welcomeText = remember { mutableStateOf("") }

    val k = geocoder.getFromLocation(41.6946, -8.83016, 1)

    welcomeText.value = k[0]!!.locality.toString()




    val docRef = db.collection("posts").orderBy("createdAt", Query.Direction.DESCENDING).limit(50)

    docRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            return@addSnapshotListener
        }
        if (snapshot != null) {
            DataProvider.DataProvider.postList.clear()
            val documents = snapshot.documents
            if(documents.size!=0) {
                for (document in documents) {
                    var documentId = document.id
                    var userPost = document.get("data")
                    var username = document.get("username")
                    var userPhoto = document.get("userPhoto")
                    DataProvider.DataProvider.postList.add(
                        PostData(
                            documentId.toString(),
                            username.toString(),
                            userPost.toString(),
                            userPhoto.toString()
                        )
                    )
                }
            } else {
                DataProvider.DataProvider.postList.add(PostData(
                    "xxxxxx",
                    "Sistema",
                    "De momento, não existem posts!",
                    "null"
                ))
            }
        } else {
            DataProvider.DataProvider.postList.add(PostData(
                "xxxxxx",
                "Sistema",
                "De momento, não existem posts!",
                "null"
            ))
        }
    }

    if (visible) {
        Scaffold(
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Artip_pink)
                        .padding(20.dp)
                ) {
                    Text(text = "Bem-vindo a " + welcomeText.value)
                }
                Spacer(Modifier.size(10.dp))
                Box(Modifier.fillMaxSize()) {
                    LazyColumn(
                    ) {
                        items(DataProvider.DataProvider.postList) { l ->
                            Feed_Entry(pd = l)
                        }
                    }

                    if(savedUserType.value!=false) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(0.dp, -80.dp)
                            .padding(20.dp)
                            .background(Artip_pink)
                    ) {
                        TextField(
                            value = textState.value,
                            onValueChange = { textState.value = it },
                            Modifier.padding(10.dp)
                        )
                        Spacer(Modifier.size(10.dp))


                        Box(
                            Modifier
                                .wrapContentSize()
                                .padding(0.dp, 20.dp, 0.dp, 20.dp)
                        ) {
                            Button(onClick = {

                                if (textState.value.text.length > 10) {
                                    showAlert = true
                                    insertPost(context, textState.value.text)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "O post tem de ter no mínimo 15 caracteres!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }, Modifier.align(Alignment.Center)) {
                                Text(text = ">")
                            }
                        }


                    }


                    }
                }
            }
        }
    }
}
