package estg.djr.artip

import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.util.Log
import android.view.RoundedCorner
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import estg.djr.artip.dataclasses.MutableParamsNavbar
import estg.djr.artip.enums.NavItemsNumeration
import estg.djr.artip.ui.theme.ArtipTheme
import estg.djr.artip.ui.theme.NavButton
import estg.djr.artip.ui.theme.SelectedNavButton


class Navbar : ViewModel() {

    val tab: MutableState<Int> = mutableStateOf(0)
}


@Composable
fun BottomNavigationBar(tabChangeModel: Navbar = Navbar()) {

    val currentTab : Int = tabChangeModel.tab.value

    var bgcolor by remember {
        mutableStateOf(listOf(Color.Red, Color.Blue, Color.Red, Color.Red, Color.Red))
    }

    val l = mutableListOf(
        MutableParamsNavbar(4f, SelectedNavButton),
        MutableParamsNavbar(2f, NavButton),
        MutableParamsNavbar(2f, NavButton),
        MutableParamsNavbar(2f, NavButton),
        MutableParamsNavbar(2f, NavButton),
    )

    var nav by remember {
        mutableStateOf(l)
    }

    fun setMutation(x: Int) : MutableList<MutableParamsNavbar> {
        val presencen = 4f
        val presenced = 2f
        val colorPressed = SelectedNavButton
        val colorDefault = NavButton

        val defList = mutableListOf<MutableParamsNavbar>(
            MutableParamsNavbar(presenced, colorDefault),
            MutableParamsNavbar(presenced, colorDefault),
            MutableParamsNavbar(presenced, colorDefault),
            MutableParamsNavbar(presenced, colorDefault),
            MutableParamsNavbar(presenced, colorDefault),
        )

        when(x){
            0 -> { defList[0] = MutableParamsNavbar(presencen, colorPressed)}
            1 -> { defList[1] = MutableParamsNavbar(presencen, colorPressed)}
            2 -> { defList[2] = MutableParamsNavbar(presencen, colorPressed)}
            3 -> { defList[3] = MutableParamsNavbar(presencen, colorPressed)}
            4 -> { defList[4] = MutableParamsNavbar(presencen, colorPressed)}
        }
        Log.d("Nav", defList.toString())
        tabChangeModel.tab.value = x
        return defList


    }

    Row(horizontalArrangement = Arrangement.SpaceEvenly,
    modifier = Modifier
        .padding(10.dp)
        .clip(shape = RoundedCornerShape(30.dp))){
        Column(
            modifier = Modifier
                .background(color = nav[NavItemsNumeration.MAP.n].BG_Color)
                .weight(nav[NavItemsNumeration.MAP.n].Presence)
                .clickable { nav = setMutation(NavItemsNumeration.MAP.n) }
        ) {
            Box(modifier = Modifier
                .padding(10.dp, 20.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .align(alignment = CenterHorizontally)
                ){
                Spacer(Modifier.matchParentSize())
                Row() {
                    Column() {
                        Icon(painterResource(id = R.drawable.map), "Something")
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .background(color = nav[NavItemsNumeration.FEED.n].BG_Color)
                .weight(nav[NavItemsNumeration.FEED.n].Presence)
                .clickable { nav = setMutation(NavItemsNumeration.FEED.n) }
        ) {
            Box(modifier = Modifier
                .padding(10.dp, 20.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .align(alignment = CenterHorizontally)){
                Spacer(Modifier.matchParentSize())
                Icon(painterResource(id = R.drawable.newspaper_variant), "Something")
            }
        }
        Column(
            modifier = Modifier
                .background(color = nav[NavItemsNumeration.QR.n].BG_Color)
                .weight(nav[NavItemsNumeration.QR.n].Presence)
                .clickable { nav = setMutation(NavItemsNumeration.QR.n) }
        ) {
            Box(modifier = Modifier
                .padding(10.dp, 20.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .align(alignment = CenterHorizontally)){
                Spacer(Modifier.matchParentSize())
                Icon(painterResource(id = R.drawable.qrcode), "Camera")
            }
        }
        Column(
            modifier = Modifier
                .background(color = nav[NavItemsNumeration.PROFILE.n].BG_Color)
                .weight(nav[NavItemsNumeration.PROFILE.n].Presence)
                .clickable { nav = setMutation(NavItemsNumeration.PROFILE.n) }
        ) {
            Box(modifier = Modifier
                .padding(10.dp, 20.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .align(alignment = CenterHorizontally)){
                Spacer(Modifier.matchParentSize())
                Icon(painterResource(id = R.drawable.account), "Camera")
            }
        }
        Column(
            modifier = Modifier
                .background(color = nav[NavItemsNumeration.SETTINGS.n].BG_Color)
                .weight(nav[NavItemsNumeration.SETTINGS.n].Presence)
                .clickable { nav = setMutation(NavItemsNumeration.SETTINGS.n) }
        ) {
            Box(modifier = Modifier
                .padding(10.dp, 20.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .align(alignment = CenterHorizontally)
                ){
                Spacer(Modifier.matchParentSize())
                Icon(painterResource(id = R.drawable.cog), "Camera")
            }
        }
    }
}

@Composable
fun Greeting(name : String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArtipTheme {
        Greeting("Android")
    }
}