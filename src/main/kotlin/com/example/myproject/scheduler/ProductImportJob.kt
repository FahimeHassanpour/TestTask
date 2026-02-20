package com.example.myproject.scheduler

import com.example.myproject.client.ProductClient
import com.example.myproject.domain.Product
import com.example.myproject.repository.ProductRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ProductImportJob(
    private val productRepository: ProductRepository,
    private val productClient: ProductClient
) {

    @Scheduled(initialDelay = 0)
    fun importProducts() {

        val products = productClient.getProducts().map {
            Product(
                id = it.id,
                title = it.title,
                vendor = it.vendor,
                productType = it.productType,
                price = it.variants?.first()?.price?.toDouble() ?: 0.0,
            )
        }

        products.forEach {
            productRepository.save(it)
        }
    }
}