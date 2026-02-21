package com.example.myproject.service

import com.example.myproject.domain.Product
import com.example.myproject.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getAllProducts(): List<Product> = productRepository.findAll()

    fun addProduct(product: Product) {
        productRepository.save(product)
    }
    fun searchByTitle(query: String): List<Product> {
        return if (query.isBlank()) productRepository.findAll()
        else productRepository.findByTitleContaining(query)
    }
}