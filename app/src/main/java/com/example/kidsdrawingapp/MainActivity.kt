package com.example.kidsdrawingapp

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get

class MainActivity : AppCompatActivity() {
    private var drawingView :DrawingView?=null
    private var mImageButtonCurrentPaint:ImageButton? = null

    val requestPermission: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                val perMissionName = it.key
                val isGranted = it.value
                //Todo 3: if permission is granted show a toast and perform operation
                if (isGranted ) {
                    Toast.makeText(
                        this@MainActivity,
                        "Permission granted now you can read the storage files.",
                        Toast.LENGTH_LONG
                    ).show()
                    //perform operation
                } else {
                    //Todo 4: Displaying another toast if permission is not granted and this time focus on
                    //    Read external storage
                    if (perMissionName == Manifest.permission.READ_EXTERNAL_STORAGE)
                        Toast.makeText(
                            this@MainActivity,
                            "Oops you just denied the permission.",
                            Toast.LENGTH_LONG
                        ).show()
                }
            }}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawingView = findViewById(R.id.drawing_view)
        drawingView!!.setSizeForBrush(4.toFloat())

        val linearlayoutPaintColor = findViewById<LinearLayout>(R.id.paint_colors)
        mImageButtonCurrentPaint = linearlayoutPaintColor[1] as ImageButton
        mImageButtonCurrentPaint!!.setImageDrawable(
            ContextCompat.getDrawable(this,R.drawable.pallate_pressed)
        )

        val brushBtn:ImageButton = findViewById(R.id.brushBtn)
        brushBtn.setOnClickListener {
            showBrushSizeChooserDialog()
        }

        val ibGallery : ImageButton = findViewById(R.id.btnBackgroundImage)
        ibGallery.setOnClickListener {
            requestStoragePermission()

        }

    }

    private fun showBrushSizeChooserDialog(){
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dilog_brush_size)
        brushDialog.setTitle("Brush size : ")
        val smallBtn : ImageButton = brushDialog.findViewById(R.id.small_brush)
        smallBtn.setOnClickListener {
            drawingView?.setSizeForBrush(10.toFloat())
            brushDialog.dismiss()
        }
        val mediumBtn : ImageButton = brushDialog.findViewById(R.id.medium_brush)
        mediumBtn.setOnClickListener {
            drawingView?.setSizeForBrush(15.toFloat())
            brushDialog.dismiss()
        }
        val largeBtn : ImageButton = brushDialog.findViewById(R.id.large_brush)
        largeBtn.setOnClickListener {
            drawingView?.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        }

        brushDialog.show()
    }

    fun paintClicked(view: View){
        //Toast.makeText(this,"clicked paint",Toast.LENGTH_LONG).show()
        if (view!==mImageButtonCurrentPaint){
            val imageButton = view as ImageButton
            val colorTag = imageButton.tag.toString()
            drawingView?.setColor(colorTag)
           imageButton.setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.pallate_pressed)
            )
            mImageButtonCurrentPaint?.setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.pallate_normal)
            )
            mImageButtonCurrentPaint = view
        }
    }
    private fun requestStoragePermission(){
        //Todo 6: Check if the permission was denied and show rationale
        if (
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        ){
            //Todo 9: call the rationale dialog to tell the user why they need to allow permission request
            showRationaleDialog("Kids Drawing App","Kids Drawing App " +
                    "needs to Access Your External Storage")
        }
        else {
            // You can directly ask for the permission.
            // Todo 7: if it has not been denied then request for permission
            //  The registered ActivityResultCallback gets the result of this request.
            requestPermission.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }}

    private fun showRationaleDialog(
        title: String,
        message: String,
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }
}