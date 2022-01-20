package estg.djr.artip

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import estg.djr.artip.ui.theme.Artip_pink

class ArtistPreview {
}

@Preview
@Composable
fun artistPreview() {
    Column(Modifier.background(color = Artip_pink)){
        Row(){
            Text(text = "John Cena")
            Image(painter = painterResource(id = R.drawable.profile_icon), "test",
                modifier = Modifier
                    .size(90.dp)
                    .padding(10.dp)
                    .clip(shape = CircleShape))
        }
        Row(Modifier.padding(10.dp).align(CenterHorizontally)){
            Button(onClick = { /*TODO*/ },
            modifier = Modifier.absolutePadding(5.dp)) {
                Text(text = "Go To")
            }
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.absolutePadding(5.dp)) {
                Text(text = "Go Went")
            }
        }
    }
}