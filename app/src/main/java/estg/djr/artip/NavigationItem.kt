package estg.djr.artip

sealed class NavigationItem(var route: String, var icon: Int, var title: String){
    object Map : NavigationItem("map", R.drawable.ic_launcher_background, "Map")
    object Feed : NavigationItem("feed", R.drawable.ic_launcher_background, "Feed")
    object Camera : NavigationItem("camera", R.drawable.ic_launcher_background, "Camera")
    object Profile : NavigationItem("profile", R.drawable.ic_launcher_background, "Profile")
    object Settings : NavigationItem("settings", R.drawable.ic_launcher_background, "Settings")

}
