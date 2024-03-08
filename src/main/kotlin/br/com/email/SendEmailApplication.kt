package br.com.email

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SendEmailApplication

fun main(args: Array<String>) {
	runApplication<SendEmailApplication>(*args)
}
