package com.angel.powerverse.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.angel.powerverse.R
import com.angel.powerverse.data.SuperheroService
import com.angel.powerverse.databinding.ActivityDetailsBinding
import com.example.powerverse.data.Superhero
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding

    lateinit var superhero: Superhero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getStringExtra("SUPERHERO_ID")!!
        getSuperheroById(id)

        binding.navigationBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_biography -> {
                    binding.appearanceContent.root.visibility = View.GONE
                    binding.statsContent.root.visibility = View.GONE
                    binding.biographyContent.root.visibility = View.VISIBLE
                }
                R.id.action_appearance -> {
                    binding.statsContent.root.visibility = View.GONE
                    binding.biographyContent.root.visibility = View.GONE
                    binding.appearanceContent.root.visibility = View.VISIBLE
                }
                R.id.action_stats -> {
                    binding.biographyContent.root.visibility = View.GONE
                    binding.appearanceContent.root.visibility = View.GONE
                    binding.statsContent.root.visibility = View.VISIBLE
                }
            }
            true
        }

        binding.navigationBar.selectedItemId = R.id.action_biography
    }

        private fun showSection(visibleView: View) {
            binding.biographyContent.root.visibility = View.GONE
            binding.appearanceContent.root.visibility = View.GONE
            binding.statsContent.root.visibility = View.GONE
            visibleView.visibility = View.VISIBLE
        }
    fun loadData() {
            Picasso.get().load(superhero.image.url).into(binding.pictureImageView)

            binding.toolbarTitleSuperHeroName.title = superhero.name
            supportActionBar?.title = superhero.name
            supportActionBar?.subtitle = superhero.biography.realName // No funciona con CollapsingActionBar

            // Biography
            binding.biographyContent.publisherTextView.text = superhero.biography.publisher
            binding.biographyContent.placeOfBirthTextView.text = superhero.biography.placeOfBirth
            binding.biographyContent.alignmentTextView.text = superhero.biography.alignment.replaceFirstChar { it.uppercase() }
            binding.biographyContent.alignmentTextView.setTextColor((getColor(superhero.getAlignmentColor())))
            binding.biographyContent.occupationTextView.text = superhero.work.occupation
            binding.biographyContent.baseTextView.text = superhero.work.base

            // Appearance
            binding.appearanceContent.raceTextView.text = superhero.appearance.race
            binding.appearanceContent.genderTextView.text = superhero.appearance.gender
            binding.appearanceContent.eyeColorTextView.text = superhero.appearance.eyeColor
            binding.appearanceContent.hairColorTextView.text = superhero.appearance.hairColor
            binding.appearanceContent.weightTextView.text = superhero.appearance.getWeightKg()
            binding.appearanceContent.heightTextView.text = superhero.appearance.getHeightCm()

            // Stats
            binding.statsContent.intelligence.progress = superhero.powerstats.intelligence.toIntOrNull() ?: 0
            binding.statsContent.strength.progress = superhero.powerstats.strength.toIntOrNull() ?: 0
            binding.statsContent.speed.progress = superhero.powerstats.speed.toIntOrNull() ?: 0
            binding.statsContent.durability.progress = superhero.powerstats.durability.toIntOrNull() ?: 0
            binding.statsContent.power.progress = superhero.powerstats.power.toIntOrNull() ?: 0
            binding.statsContent.combat.progress = superhero.powerstats.combat.toIntOrNull() ?: 0

        //stats points
        binding.statsContent.intelligencepoints.text = superhero.powerstats.intelligence
        binding.statsContent.strengthpoints.text = superhero.powerstats.strength
        binding.statsContent.speedpoints.text = superhero.powerstats.speed
        binding.statsContent.durabilitypoints.text = superhero.powerstats.durability
        binding.statsContent.powerpoints.text = superhero.powerstats.power
        binding.statsContent.combatpoints.text = superhero.powerstats.combat
        }

        fun getRetrofit(): SuperheroService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.superheroapi.com/api.php/7606f1c6a2b57ea22ca4bdf432bd1f9c/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(SuperheroService::class.java)
        }

        fun getSuperheroById(id: String) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val service = getRetrofit()
                    superhero = service.findSuperheroById(id)

                    CoroutineScope(Dispatchers.Main).launch {
                        loadData()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }