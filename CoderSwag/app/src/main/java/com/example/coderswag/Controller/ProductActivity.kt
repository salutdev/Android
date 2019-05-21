package com.example.coderswag.Controller

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.coderswag.Adapters.ProductAdapter
import com.example.coderswag.R
import com.example.coderswag.Services.DataService
import com.example.coderswag.Utitities.EXTRA_CATEGERY
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity() {

    lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val categoryTitle = intent.getStringExtra(EXTRA_CATEGERY)
        val listOfProducts = DataService.getProducts(categoryTitle)

        productAdapter = ProductAdapter(this, listOfProducts) { product ->
            Toast.makeText(this, "${product.title} is clicked", Toast.LENGTH_SHORT).show()
        }

        productListView.adapter = productAdapter

        var colNum = 2
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            colNum = 3
        }

        val screenSize = resources.configuration.screenWidthDp
        if (screenSize > 720) {
            colNum = 3
        }

        val layoutManager = GridLayoutManager(this, colNum)
        productListView.layoutManager = layoutManager
        productListView.hasFixedSize()
    }
}
