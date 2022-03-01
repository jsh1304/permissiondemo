package jj.appproject.permissiondemo

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {

    private val cameraResultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
                isGranted ->
                if (isGranted) {
                    Toast.makeText(this,
                        "카메라에 대한 접근 승인", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this,
                        "카메라에 대한 접근 거부", Toast.LENGTH_LONG).show()
                }
            }


    private val cameraAndLocationResultLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){
            permissions ->
            permissions.entries.forEach{
                val permissionName = it.key
                val isGranted = it.value
                if(isGranted) {
                    if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                        Toast.makeText(
                            this,
                            "위치 정보에 대한 접근 승인", Toast.LENGTH_LONG
                        ).show()
                    }
                    else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION) {
                        Toast.makeText(
                            this,
                            "대략적 위치 정보에 대한 접근 승인", Toast.LENGTH_LONG
                        ).show()
                    }
                    else {

                        Toast.makeText(
                            this,
                            "카메라에 대한 접근 승인", Toast.LENGTH_LONG
                        ).show()
                    }
                }
                else{
                    if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                        Toast.makeText(
                            this,
                            "위치 정보에 대한 접근 거부", Toast.LENGTH_LONG
                        ).show()
                    }
                    else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION) {
                        Toast.makeText(
                            this,
                            "대략적 위치 정보에 대한 접근 거부", Toast.LENGTH_LONG
                        ).show()
                    }
                    else {

                        Toast.makeText(
                            this,
                            "카메라에 대한 접근 거부", Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCameraPermission : Button = findViewById(R.id.btnCameraPermission)
        btnCameraPermission.setOnClickListener {
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M &&
                    shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                showRationaleDialog("Permission Demo는 카메라 접근이 필요합니다.",
                    "카메라 접근이 거부되어서 카메라를 사용할 수 없습니다.")
            }
            else{
                cameraAndLocationResultLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    private fun showRationaleDialog(
        title: String,
        message: String,
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("cancel"){dialog, _->
                dialog.dismiss()
            }
        builder.create().show()
    }


}