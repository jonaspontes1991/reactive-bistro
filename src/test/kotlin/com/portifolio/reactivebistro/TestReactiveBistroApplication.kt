package com.portifolio.reactivebistro

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<ReactiveBistroApplication>().with(TestcontainersConfiguration::class).run(*args)
}
