package estg.djr.artip

import android.content.ContentProvider
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.android.gms.common.util.CollectionUtils.listOf
import estg.djr.artip.dataclasses.PostData
import estg.djr.artip.ui.theme.ArtipTheme
import estg.djr.artip.ui.theme.Artip_pink
import java.util.*
import kotlin.collections.ArrayList

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

@Composable
fun FeedCompo(visible: Boolean, postList: List<PostData>) {

    val textState = remember { mutableStateOf(TextFieldValue()) }
    var lines = postList


    if (visible) {
        Scaffold(
            bottomBar = { Box(modifier = Modifier.padding(10.dp)) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(value = textState.value, onValueChange = {textState.value = it},
                modifier = Modifier
                    .offset(0.dp, (-80).dp))
            Button(onClick = {
                {/**TODO**/}
            },
                Modifier
                    .offset(0.dp, (-80).dp)
                    .width(60.dp)
                    .background(color = Artip_pink))

            {
                Text(text = ">",
                    fontSize = 30.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(0.dp)
                )
            }
        }
    }
            } }
        ) {
            LazyColumn(){
                items(lines){ l ->
                        Feed_Entry(pd = l)
                    }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    val ls:MutableList<PostData> = Arrays.asList(PostData("JoelDoe", "Hello Everyone!"))
    ArtipTheme {
        FeedCompo(true, ls)
    }
}

@Composable
fun InputMessage(pl: MutableList<PostData>) {


    
}