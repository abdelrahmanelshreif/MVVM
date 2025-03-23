package com.example.mvvm.favProducts

import android.os.Bundle
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.allproducts.AllProductViewModelFactory
import com.example.mvvm.allproducts.AllProductsViewModel
import com.example.mvvm.allproducts.ui.theme.ProductItem
import com.example.mvvm.data.local.ProductDatabase
import com.example.mvvm.data.local.ProductLocalDataSource
import com.example.mvvm.data.models.Product
import com.example.mvvm.data.remote.ProductRemoteDataSource
import com.example.mvvm.data.remote.RetrofitHelper
import com.example.mvvm.favProducts.ui.theme.MVVMTheme
import com.example.mvvm.repo.ProductRepository

class FavProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = FavProductsViewModelFactory(
            ProductRepository.getInstance(
                ProductRemoteDataSource(RetrofitHelper.apiservice),
                localDataSource = ProductLocalDataSource(
                    ProductDatabase.getInstance(this@FavProductsActivity).getProductDao()
                )
            )
        )
        val favProductsViewModel =
            ViewModelProvider(
                this@FavProductsActivity,
                factory
            ).get(FavProductsViewModel::class.java)
        setContent {
            com.example.mvvm.allproducts.ui.theme.MVVMTheme {

                ProductListScreen(favProductsViewModel)
            }
        }
    }
}


@Composable
fun ProductListScreen(favProductsViewModel: FavProductsViewModel) {
    val productsState = favProductsViewModel.products.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        favProductsViewModel.getFavProducts()
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
                        favProductsViewModel.deleteProduct(product)
                        Toast.makeText(
                            context,
                            "Product is Deleted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }
}
