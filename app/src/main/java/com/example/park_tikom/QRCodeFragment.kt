package com.example.park_tikom

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.OnBackPressedCallback
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class QRCodeFragment : Fragment() {
    private lateinit var tokenId : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_q_r_code, container, false)
        val qrCodePesanan = view.findViewById<ImageView>(R.id.qrCodePesanan)
        val writer = QRCodeWriter()
        tokenId = arguments?.getString("idQR").toString()
        println("=================$tokenId===============")
        try {
            val bitMatrix = writer.encode(tokenId, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width){
                for (y in 0 until height){
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            qrCodePesanan.setImageBitmap(bmp)
        } catch (e: WriterException){
            e.printStackTrace()
        }

        view.findViewById<RelativeLayout>(R.id.layoutBackgroundQR).setOnClickListener {
            activity?.onBackPressed()
        }

        return view
    }


//
//    companion object{
//
//        @JvmStatic
//        fun passQR(idQR: String) = QRCodeFragment().apply {
//            arguments = Bundle().apply {
//                putString("idQR", idQR)
//            }
//        }
//    }

}