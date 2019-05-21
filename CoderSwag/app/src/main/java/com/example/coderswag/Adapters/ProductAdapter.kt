package com.example.coderswag.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.coderswag.Model.Product
import com.example.coderswag.R

class ProductAdapter(private val context: Context, private val products: List<Product>, private val itemClick: (Product) -> Unit) : RecyclerView.Adapter<ProductAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return products.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindProduct(products[position], context)
    }

    inner class Holder(itemView: View, val itemClick: (Product) -> Unit) : RecyclerView.ViewHolder(itemView) {

        val productImage = itemView.findViewById<ImageView>(R.id.productDetailImage)
        val productName = itemView.findViewById<TextView>(R.id.productDetailName)
        val productPrice = itemView.findViewById<TextView>(R.id.productPrice)

        fun bindProduct(product: Product, context: Context) {
            val resourceId = context.resources.getIdentifier(product.image, "drawable", context.packageName)
            productImage?.setImageResource(resourceId)
            productName?.text = product.title
            productPrice?.text = product.price
            itemView.setOnClickListener { itemClick(product) }
        }
    }
}