package com.example.mvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mvvm.allproducts.AllProductsActivity
import com.example.mvvm.favProducts.FavProductsActivity
import com.example.mvvm.favProducts.FavProductsViewModel
import com.example.mvvm.ui.theme.MVVMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVVMTheme {
                Options()
            }
        }
    }

    @Composable
    fun Options() {
        MVVMTheme {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    val intent = Intent(this@MainActivity, AllProductsActivity::class.java)
                    startActivity(intent)
                }) {
                    Text("All Products")
                }
                Button(onClick = {
                    val intent = Intent(this@MainActivity, FavProductsActivity::class.java)
                    startActivity(intent)
                }) {

                    Text("Favourite Products")

                }
                Button(onClick = { finish() }) {
                    Text("Exit")
                }
            }
        }
    }
}


