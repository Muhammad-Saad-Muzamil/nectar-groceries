package com.example.nectar.model
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nectar.R

class ProductViewModel: ViewModel(){
    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> get() = _productList

    private val allProducts = listOf(
        Product("1", "Organic Bananas", "","1dozen price ", "$4.99",R.drawable.banana,"Exclusive"),
        Product("2", "Red Apple","", "1kg price", "$4.9",R.drawable.apple,"Exclusive" ),
        Product("3", "Green Grapes","", "1kg price", "$4.99",R.drawable.banana,"Exclusive"),
        Product("4", "Mango","", "1kg price","$4.99", R.drawable.apple,"Best Selling"),
        Product("5","bell_pepper Red","","1kg price","$4.99",R.drawable.bell_pepper,"Best Selling"),
        Product("7","Ginger","","250mg priceg","$4.99",R.drawable.ginger,"Best Selling")
    )

    init {
        _productList.value = allProducts // Load initial list
    }

    fun searchProducts(query: String) {
        _productList.value = if (query.isEmpty()) {
            allProducts
        } else {
            allProducts.filter { it.name.contains(query, ignoreCase = true) }
        }
    }
}

