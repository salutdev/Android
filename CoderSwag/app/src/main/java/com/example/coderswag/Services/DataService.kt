package com.example.coderswag.Services

import com.example.coderswag.Model.Category
import com.example.coderswag.Model.Product

object DataService {
    val categories = listOf(
        Category("SHIRTS", "shirtimage"),
        Category("HOODIES", "hoodieimage"),
        Category("HATS", "hatimage"),
        Category("DIGITAL", "digitalgoodsimage")
    )

    val hats = listOf(
        Product("Graphic hat", "$18", "hat1"),
        Product("Black hat", "$25", "hat2"),
        Product("White hat", "$30", "hat3"),
        Product("Spanback hat", "$55", "hat4")
    )

    val hoodies = listOf(
        Product("Hoodie grey", "$34", "hoodie1"),
        Product("Red hoodie", "$36", "hoodie2"),
        Product("Grey hoodie", "$13", "hoodie3"),
        Product("Black hoodie", "$64", "hoodie4")
    )

    val shirts = listOf(
        Product("Shirt black shirt", "$88", "shirt1"),
        Product("Light grey shirt", "$33", "shirt2"),
        Product("Red shirt", "$32", "shirt3"),
        Product("Hustle shirt", "$12", "shirt4"),
        Product("Studios shirt", "$17", "shirt4")
    )
}