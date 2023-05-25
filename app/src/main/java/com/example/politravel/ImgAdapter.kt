package com.example.politravel

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImgAdapter (private val context: Context,
                  private val paquete: Paquete):
    RecyclerView.Adapter<ImgAdapter.ImgViewHolder>(), View.OnClickListener{
    private val layout = R.layout.img_item
    private var clickListener: View.OnClickListener? = null

    class ImgViewHolder (val view: View): RecyclerView.ViewHolder(view) {
        var imgPaquete: ImageView

        init {
            imgPaquete = view.findViewById(R.id.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return ImgViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {
        val image = paquete.imgCiudad.get(position)
        bindImage(holder, image)
    }

    fun bindImage (holder: ImgViewHolder, imgPaq: String) {

        val imgPath = context.getFilesDir().toString() + "/img/" + imgPaq
        val bitmap = BitmapFactory.decodeFile(imgPath)
        holder.imgPaquete?.setImageBitmap(bitmap)


    }

    fun setOnClickListener (listener: View.OnClickListener) {
        clickListener = listener
    }

    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

    override fun getItemCount() = paquete.imgCiudad.size
}