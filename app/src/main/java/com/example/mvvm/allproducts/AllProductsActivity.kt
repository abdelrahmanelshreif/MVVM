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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mvvm.data.models.Response
import kotlinx.coroutines.flow.collect

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
    val uiState = allProductsViewModel.productsList.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        allProductsViewModel.toastEvent.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }


    when (uiState.value) {
        is Response.Loading -> {
            LoadingIndicator()
        }

        is Response.Failure -> {
            Text(
                "Can't Fetch Now",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(),
                fontSize = 22.sp
            )

        }

        is Response.Success -> Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val products = (uiState.value as Response.Success).data
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products ?: listOf<Product>()) { product ->
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


@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        CircularProgressIndicator()
    }
}

