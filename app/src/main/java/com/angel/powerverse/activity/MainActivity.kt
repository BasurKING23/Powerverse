package com.angel.powerverse.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.angel.powerverse.R
import com.angel.powerverse.adapter.SuperheroAdapter
import com.angel.powerverse.data.Superhero
import com.angel.powerverse.data.SuperheroResponse
import com.angel.powerverse.data.SuperheroService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SuperheroAdapter
    lateinit var searchView: SearchView


    var superheroList: List<Superhero> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.superrecyclerview)

        adapter = SuperheroAdapter(superheroList)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        getretrofit("a")
    }

    private fun getretrofit(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://superheroapi.com/api/7606f1c6a2b57ea22ca4bdf432bd1f9c/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SuperheroService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val result = service.findSuperheroesByName(query)

            superheroList = result.results

            CoroutineScope(Dispatchers.Main).launch {
                adapter.items = superheroList
                adapter.notifyDataSetChanged()

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_menu, menu)

        val menuItem = menu?.findItem(R.id.search)
        val searchView = menuItem?.actionView as SearchView

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            getretrofit(query)
            return false
        }

        override fun onQueryTextChange(query: String): Boolean {
            return false
        }
    })
        return true
    }
}

