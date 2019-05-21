package com.example.coderswag.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.coderswag.Model.Product
import com.example.coderswag.R
import com.example.coderswag.Utilities.PRODUCT
import kotlinx.android.synthetic.main.activity_product_detail.productDetailName
import kotlinx.android.synthetic.main.product_list_item.*

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val product = intent.getParcelableExtra<Product>(PRODUCT)
        productDetailName.text = product.title
        productPrice.text = product.price

        val resourceId = this.resources.getIdentifier(product.image, "drawable", this.packageName)
        productDetailImage?.setImageResource(resourceId)
    }
}
