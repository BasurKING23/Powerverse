package com.angel.powerverse

class SuperheroResponse (
    val response: String,
    val result: List<Superhero>
){
}
class Superhero (
    val id: String,
    val name: String
    ){
}