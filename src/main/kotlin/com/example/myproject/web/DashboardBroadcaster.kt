package com.example.myproject.web

import com.example.myproject.event.ProductChangedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.CopyOnWriteArrayList

@Component
class DashboardBroadcaster {

    private val emitters = CopyOnWriteArrayList<SseEmitter>()

    fun subscribe(): SseEmitter {
        val emitter = SseEmitter(0L)
        emitters.add(emitter)
        emitter.onCompletion { emitters.remove(emitter) }
        emitter.onTimeout { emitters.remove(emitter) }
        emitter.onError { emitters.remove(emitter) }
        return emitter
    }

    @EventListener
    fun onProductChanged(@Suppress("UNUSED_PARAMETER") event: ProductChangedEvent) {
        val dead = mutableListOf<SseEmitter>()
        emitters.forEach { emitter ->
            try {
                emitter.send(SseEmitter.event().name("refresh").data("products-changed").build())
            } catch (e: Exception) {
                dead.add(emitter)
            }
        }
        dead.forEach { emitters.remove(it) }
    }
}
