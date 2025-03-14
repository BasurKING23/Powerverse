package com.angel.powerverse.data

class SuperheroResponse (
    val results: List<Superhero>
){
}
class Superhero (
    val name: String,
    val image: Image
    ){
}

class Image (val url: String)