package com.example.myproject.web

import com.example.myproject.repository.ProductRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Controller
class DashboardController(
    private val productRepository: ProductRepository,
    private val dashboardBroadcaster: DashboardBroadcaster
) {

    @GetMapping("/dashboard")
    fun dashboardPage(model: Model): String {
        model.addAttribute("pageTitle", "Dashboard")
        addStatsToModel(model)
        return "dashboard"
    }

    @GetMapping("/dashboard/stats")
    fun dashboardStats(model: Model): String {
        addStatsToModel(model)
        return "fragments/dashboard-stats :: dashboardStats"
    }

    @GetMapping(value = ["/dashboard/stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun dashboardStream(): SseEmitter {
        return dashboardBroadcaster.subscribe()
    }

    private fun addStatsToModel(model: Model) {
        model.addAttribute("productCount", productRepository.count())
        model.addAttribute("averagePrice", productRepository.averagePrice())
    }
}
