package com.example.politravel

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class SelectImgAdapter (private val context: Context,
                        private val imgs: ArrayList<Imagen>):
    RecyclerView.Adapter<SelectImgAdapter.PaqueteViewHolder>(), View.OnClickListener{
    private val layout = R.layout.select_img_item
    private var clickListener: View.OnClickListener? = null

    class PaqueteViewHolder (val view: View): RecyclerView.ViewHolder(view) {
        var imgSelect: ImageView
        var lblCiudadSelect: TextView
        var imgChecked: ImageView

        init {
            imgSelect = view.findViewById(R.id.imgSelect)
            lblCiudadSelect = view.findViewById(R.id.nombreCiudadSelect)
            imgChecked = view.findViewById(R.id.imgCheckedSelect)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaqueteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return PaqueteViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaqueteViewHolder, position: Int) {
        val image = imgs.get(position)
        bindImage(holder, image)
    }

    fun bindImage (holder: PaqueteViewHolder, img: Imagen) {

        holder.lblCiudadSelect.text = img.ciudad

        holder.imgChecked.isVisible = img.escogida

        val imgPath = context.getFilesDir().toString() + "/img/" + img.path
        val bitmap = BitmapFactory.decodeFile(imgPath)
        holder.imgSelect?.setImageBitmap(bitmap)



    }

    fun setOnClickListener (listener: View.OnClickListener) {
        clickListener = listener
    }

    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

    override fun getItemCount() = imgs.size
}