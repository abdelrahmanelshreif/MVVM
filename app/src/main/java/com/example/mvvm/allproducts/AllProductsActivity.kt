package com.example.mvvm.allproducts

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.allproducts.ui.theme.MVVMTheme
import com.example.mvvm.allproducts.ui.theme.ProductItem
import com.example.mvvm.data.local.ProductDatabase
import com.example.mvvm.data.local.ProductLocalDataSource
import com.example.mvvm.data.models.Product
import com.example.mvvm.data.remote.ProductRemoteDataSource
import com.example.mvvm.data.remote.RetrofitHelper
import com.example.mvvm.repo.ProductRepository
import android.content.Context
import androidx.compose.ui.platform.LocalContext

class AllProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = AllProductViewModelFactory(
            ProductRepository.getInstance(
                ProductRemoteDataSource(RetrofitHelper.apiservice),
                localDataSource = ProductLocalDataSource(
                    ProductDatabase.getInstance(this@AllProductsActivity).getProductDao()
                )
            )
        )
        val allProductsViewModel =
            ViewModelProvider(
                this@AllProductsActivity,
                factory
            ).get(AllProductsViewModel::class.java)
        setContent {
            MVVMTheme {

                ProductListScreen(allProductsViewModel)
            }
        }
    }
}


@Composable
fun ProductListScreen(allProductsViewModel: AllProductsViewModel) {
    val productsState = allProductsViewModel.products.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        allProductsViewModel.getProducts()
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        if (productsState.value?.isEmpty() != false) {
            Text(text = "No products available")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(productsState.value ?: listOf<Product>()) { product ->
                    ProductItem(product = product) {
                        allProductsViewModel.insertProduct(product)
                        Toast.makeText(
                            context,
                            "Product is Added to Favourite",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }
}

