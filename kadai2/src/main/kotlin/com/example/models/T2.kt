package com.example.models

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object T2 : IntIdTable() {
    val name = varchar("name", 255)
    val age = integer("age")
}
class TT(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TT>(T2)

    var name by T2.name
    var age by T2.age
}
fun main(args: Array<String>) {
    Database.connect("jdbc:mysql://127.0.0.1/test", "com.mysql.cj.jdbc.Driver","root","")
    transaction {
        transaction {
            val gumimin = TT.find {T2.name eq "gumimin"}.single()
            gumimin.name = "choco"
            println(gumimin.name)
        }
    }
}