package estg.djr.artip

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.zxing.qrcode.encoder.QRCode
import kotlinx.android.synthetic.main.activity_qr.*

private const val CameraRequest = 101
var textQRCode = "bla...bla..."

class Qr : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        definirPermissoes()
        codeScanner()
    }

    private fun codeScanner(){

        codeScanner = CodeScanner(this, scanner_view)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    textQRCode = it.text
                }
            }
            //Se der erro
            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "Erro ${it.message}")
                }
            }
        }

        scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun definirPermissoes(){
        val permissoes = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA)

        if(permissoes != PackageManager.PERMISSION_GRANTED) {
            pedirPermissoes()
        }
    }

    private fun pedirPermissoes(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CameraRequest)
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            CameraRequest -> {
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "É necessario permissões para usar a camera!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun QRCode(visible: Boolean){
    Log.d("***text", ""+textQRCode)
    var text = remember {
        mutableStateOf(textQRCode)
    }



    Column() {
        Text(text = text.value)
    }
}