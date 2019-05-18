package com.example.coderswag.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.coderswag.Adapters.CategoryAdapter
import com.example.coderswag.Adapters.CategoryRecycleAdapter
import com.example.coderswag.Model.Category
import com.example.coderswag.R
import com.example.coderswag.Services.DataService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //lateinit var adapter: ArrayAdapter<Category>
    //lateinit var adapter: CategoryAdapter
    lateinit var adapter: CategoryRecycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, DataService.categories)
        //adapter = CategoryAdapter(this, DataService.categories)
        adapter = CategoryRecycleAdapter(this, DataService.categories /*, ::itemClick */) { category ->
            Toast
                .makeText(this, "This is click on ${category.title} category", Toast.LENGTH_SHORT)
                .show()
        }
        categoryListView.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        categoryListView.layoutManager = layoutManager
        categoryListView.hasFixedSize()
    }

    private fun itemClick(category: Category) {
        Toast
            .makeText(this, "This is click on ${category.title} category", Toast.LENGTH_SHORT)
            .show()
    }
}
