package com.example.myproject.web

import com.example.myproject.domain.Product
import com.example.myproject.service.ProductService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class ProductController(private val productService: ProductService) {

    @GetMapping("/")
    fun mainPage(): String {
        return "index"
    }

    @GetMapping("/products/load")
    fun loadProducts(model: Model): String {
        val products = productService.getAllProducts()
        model.addAttribute("products", products)
        return "fragments/products :: productsTableWithButton"
    }

    @PostMapping("/products/add")
    fun addProduct(
        @RequestParam id: Long,
        @RequestParam title: String,
        @RequestParam vendor: String,
        @RequestParam productType: String,
        @RequestParam price: Double,
        model: Model
    ): String {
        val product = Product(id, title, vendor, productType, price)
        productService.addProduct(product)
        val products = productService.getAllProducts()
        model.addAttribute("products", products)
        return "fragments/products :: productsTableWithButton"
    }

    @GetMapping("/fragments/product-form")
    fun productForm(): String {
        return "fragments/product-form :: productForm"
    }

    @GetMapping("/products/list")
    fun productsListPage(model: Model): String {
        model.addAttribute("products", productService.getAllProducts())
        return "products-list"
    }

    @GetMapping("/search")
    fun searchPage(model: Model): String {
        model.addAttribute("products", productService.getAllProducts())
        return "search"
    }

    @GetMapping("/products/search")
    fun searchProducts(
        @RequestParam(required = false, defaultValue = "") q: String,
        model: Model
    ): String {
        model.addAttribute("products", productService.searchByTitle(q))
        return "fragments/products :: productsTable"
    }

}