/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package advent2023

import advent2023.day1.day1FirstStar

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    println(App().greeting)
    println(day1FirstStar())
}
