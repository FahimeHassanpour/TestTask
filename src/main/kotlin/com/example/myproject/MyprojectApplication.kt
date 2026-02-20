package com.example.myproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class MyprojectApplication

fun main(args: Array<String>) {
	runApplication< MyprojectApplication>(*args)
}