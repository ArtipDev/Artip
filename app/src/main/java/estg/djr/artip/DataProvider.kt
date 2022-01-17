package estg.djr.artip

import androidx.compose.runtime.mutableStateListOf
import estg.djr.artip.dataclasses.PostData

class DataProvider {

    object DataProvider {

        var postList = mutableStateListOf<PostData>(
            PostData("Diogo", "Olá"),
            PostData("Ricardo", "Adeus"),
            PostData("João", "Olá outra vez!")
        )

    }

}