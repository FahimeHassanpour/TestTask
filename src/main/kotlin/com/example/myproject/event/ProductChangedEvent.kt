package com.example.myproject.event

import org.springframework.context.ApplicationEvent

class ProductChangedEvent(source: Any) : ApplicationEvent(source)
