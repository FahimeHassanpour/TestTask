package com.example.myproject.client

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class ProductClient {

    private val restClient = RestClient.create()

    fun getProducts(): List<ProductApi> {
        val response = restClient.get().uri("https://famme.no/products.json?limit=30")
            .retrieve().body<ProductResponse>()
        return response!!.products
    }
}

data class ProductResponse(val products: List<ProductApi>)

@JsonIgnoreProperties(ignoreUnknown = true)
data class VariantApi(
    val price: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ProductApi(
    val id: Long,
    val title: String,
    val vendor: String,
    @JsonProperty("product_type")
    val productType: String,
    val variants: List<VariantApi>? = null
)