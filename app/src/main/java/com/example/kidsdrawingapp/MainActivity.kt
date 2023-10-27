package com.example.kidsdrawingapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get

class MainActivity : AppCompatActivity() {
    private var drawingView :DrawingView?=null
    private var mImageButtonCurrentPaint:ImageButton? = null



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
   //Hii this is rupam bhakta
}