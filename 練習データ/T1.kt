package com.example.models
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction



object T1 : Table("T1") {
    val id = integer("id").uniqueIndex()
    val name = varchar("name", 50)
}

fun main(args: Array<String>) {
    Database.connect("jdbc:mysql://127.0.0.1/test", "com.mysql.cj.jdbc.Driver","root","")
    var T1data=""

    transaction {
        create(T1)
        /*T1.insert {
            it[id]=2
            it[name]="Bさん"
        }*/
        //val all =T1.selectAll()
        T1data= T1.select{T1.id eq 1}.toString()
    }
    println()
}