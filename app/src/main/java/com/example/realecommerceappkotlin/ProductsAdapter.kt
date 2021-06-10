package com.example.realecommerceappkotlin

import android.app.Activity
import android.content.Context
import android.graphics.Picture
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_item.view.*

class ProductsAdapter(var context: Context, var arrayList : ArrayList<Product>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val productView = LayoutInflater.from(context).inflate(R.layout.rv_item,parent,false)
        return myViewHolder(productView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as myViewHolder).initializeRowUiComponent(arrayList[position].id,arrayList[position].name,
            arrayList[position].price,arrayList[position].picture)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
    inner class myViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        fun initializeRowUiComponent(id : Int, name : String , price : Int , picture: String) {
            itemView.txtId.text = id.toString()
            itemView.txtName.text = name
            itemView.txtPrice.text = price.toString()
            Picasso.get().load(picture).into(itemView.imgProduct)
            itemView.imgAdd.setOnClickListener {
                Person.productId = id
                val amountFragment = AmountFragment()
                val fragmentManager = (itemView.context as FragmentActivity).supportFragmentManager
                amountFragment.show(fragmentManager,"TAG")
            }
        }
    }
}