package com.example.models

import io.ktor.server.routing.*
import io.ktor.http.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.Properties
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SchemaUtils.create


//テーブルの枠
object UsersDAO:IntIdTable("Users"){
    //val number = integer("id").uniqueIndex()
    val name = varchar("name",50)
}
//テーブルにクラス
class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(UsersDAO)
    //var number by UsersDAO.number
    var name by UsersDAO.name
}
fun Application.usersdao() {
    Database.connect("jdbc:mysql://127.0.0.1/test", "com.mysql.cj.jdbc.Driver", "root", "")
    routing {
        //transaction {
        //create(UsersDAO)
        /*UsersDAO.insert {
            it[id]=3
            it[name]="テスト3さん"
        }*/
        /*val search = User.findById(1)
        if (search != null) {
            println(search.name)
        }*/

        get("/users/{id}") {
            var data: String? =null
            transaction {
                val sid = call.parameters["id"]
                val i: Int = Integer.parseInt(sid)
                val userdata = User.findById(i)
                if (userdata != null) {
                    data=userdata.name
                }
            }
            call.respondText("$data")
                /*if (userdata != null) {
                    println(userdata.name)
                }*/
                //call.respondText("こんにちは$sid+さん")


        }
    }
}
    /*routing {
        get("/users/{id}") {
            var sid = call.parameters["id"]
            val i : Int = Integer.parseInt(sid)
            val userdata=User.findById(i)
            if (userdata != null) {
                println(userdata.name)
            }
            call.respondText("こんにちは$sid+さん")
            /*if (userdata != null) {
                call.respondText("${userdata.name}")
            }*/
        }
    }*/
