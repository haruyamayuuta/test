package com.example.models

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.sql.*
import io.ktor.server.routing.*
import io.ktor.http.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.Properties
//テーブルの枠
object Student:IntIdTable("Studen"){
    //val number = integer("id").uniqueIndex()
    val name = varchar("name",50)
}
//テーブルにクラス
class SS(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SS>(Student)
    //var number by UsersDAO.number
    var name by Student.name
}
