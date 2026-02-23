package com.example.myproject.web

import com.example.myproject.domain.Product
import com.example.myproject.service.ProductService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class ProductController(private val productService: ProductService) {

    @GetMapping("/")
    fun mainPage(
        @RequestParam(required = false) loadProducts: String?,
        model: Model
    ): String {
        if (loadProducts != null) {
            model.addAttribute("products", productService.getAllProducts())
            model.addAttribute("showProductsTable", true)
        }
        return "index"
    }

    @GetMapping("/products/load")
    fun loadProducts(
        @RequestParam(required = false) sort: String?,
        model: Model
    ): String {
        val products = when (sort) {
            "price_asc" -> productService.getProductsSortedByPriceAsc()
            "price_desc" -> productService.getProductsSortedByPriceDesc()
            else -> productService.getAllProducts()
        }
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
        return "product_search"   // was "search"
    }

    @GetMapping("/products/search")
    fun searchProducts(
        @RequestParam(required = false, defaultValue = "") q: String,
        model: Model
    ): String {
        model.addAttribute("products", productService.searchByTitle(q))
        return "fragments/products :: productsTable"
    }

    @GetMapping("/products/{id}/edit")
    fun editProductPage(@PathVariable id: Long, model: Model): String {
        val product = productService.getProductById(id)
            ?: return "redirect:/"
        model.addAttribute("product", product)
        return "product-edit"
    }

    @PostMapping("/products/{id}/edit")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestParam title: String,
        @RequestParam vendor: String,
        @RequestParam productType: String,
        @RequestParam price: Double
    ): String {
        val product = Product(id, title, vendor, productType, price)
        productService.updateProduct(product)
        return "redirect:/?loadProducts=1"
    }

    @PostMapping("/products/{id}/delete")
    fun deleteProduct(
        @PathVariable id: Long,
        @RequestParam(required = false, defaultValue = "main") target: String,
        model: Model
    ): String {
        productService.deleteProduct(id)
        model.addAttribute("products", productService.getAllProducts())
        return if (target == "search")
            "fragments/products :: productsTable"
        else
            "fragments/products :: productsTableWithButton"
    }
}