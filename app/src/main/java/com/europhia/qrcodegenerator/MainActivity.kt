package com.europhia.qrcodegenerator

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.europhia.qrcodegenerator.databinding.ActivityMainBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGeneratorQRCode.setOnClickListener {
            val data = binding.editQRCode.text.toString().trim()
            if(data.isEmpty()){
                Toast.makeText(this,"Enter some data", Toast.LENGTH_SHORT).show()
            } else {
                val writer = QRCodeWriter()
                try{
                    val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512,512)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bmp = Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565)
                    for (x in 0 until width){
                        for(y in 0 until height){
                            bmp.setPixel(x, y, if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                        }
                    }
                    binding.imgQRCode.setImageBitmap(bmp)
                }catch (e: WriterException){
                    e.printStackTrace()
                }
            }
        }
    }
}