package com.practicum.studyandroid.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.annotations.SerializedName
import com.practicum.studyandroid.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

class WeatherLocation : AppCompatActivity() {

    private val forecaBaseUrl = "https://pfa.foreca.com/"

    private var token = ""

    private val retrofit = Retrofit.Builder()
        .baseUrl(forecaBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val forecaService = retrofit.create(ForecaApi::class.java)

    private val locations = ArrayList<ForecastLocation>()
    private val adapter = LocationsAdapter {
        showWeather(it)
    }

    private lateinit var searchButton: Button
    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var locationsList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_location)

        placeholderMessage = findViewById(R.id.placeholderMessage)
        placeholderMessage = findViewById(R.id.placeholderMessage)
        searchButton = findViewById(R.id.searchButton)
        queryInput = findViewById(R.id.queryInput)
        locationsList = findViewById(R.id.locations)

        adapter.locations = locations

        locationsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        locationsList.adapter = adapter

        searchButton.setOnClickListener {
            if (queryInput.text.isNotEmpty()) {
                if (token.isEmpty()) {
                    authenticate()
                } else {
                    search()
                }
            }
        }
    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            placeholderMessage.visibility = View.VISIBLE
            locations.clear()
            adapter.notifyDataSetChanged()
            placeholderMessage.text = text
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            placeholderMessage.visibility = View.GONE
        }
    }
    private fun authenticate() {
        forecaService.authenticate(ForecaAuthRequest("freeman", "F5dJBBflMkPT"))
            .enqueue(object : Callback<ForecaAuthResponse> {
                override fun onResponse(call: Call<ForecaAuthResponse>,
                                        response: Response<ForecaAuthResponse>) {
                    if (response.code() == 200) {
                        token = response.body()?.token.toString()
                        search()
                    } else {
                        showMessage(getString(R.string.something_went_wrong), response.code().toString())
                    }
                }

                override fun onFailure(call: Call<ForecaAuthResponse>, t: Throwable) {
                    showMessage(getString(R.string.something_went_wrong), t.message.toString())
                }

            })
    }

    private fun search() {
        forecaService.getLocations("Bearer $token", queryInput.text.toString())
            .enqueue(object : Callback<LocationsResponse> {
                override fun onResponse(call: Call<LocationsResponse>,
                                        response: Response<LocationsResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            if (response.body()?.locations?.isNotEmpty() == true) {
                                locations.clear()
                                locations.addAll(response.body()?.locations!!)
                                adapter.notifyDataSetChanged()
                                showMessage("", "")
                            } else {
                                showMessage(getString(R.string.nothing_found), "")
                            }

                        }
                        401 -> authenticate()
                        else -> showMessage(getString(R.string.something_went_wrong), response.code().toString())
                    }

                }

                override fun onFailure(call: Call<LocationsResponse>, t: Throwable) {
                    showMessage(getString(R.string.something_went_wrong), t.message.toString())
                }

            })
    }

    private fun showWeather(location: ForecastLocation) {
        forecaService.getForecast("Bearer $token", location.id)
            .enqueue(object : Callback<ForecastResponse> {
                override fun onResponse(call: Call<ForecastResponse>,
                                        response: Response<ForecastResponse>) {
                    if (response.body()?.current != null) {
                        val message = "${location.name} t: ${response.body()?.current?.temperature}\n(Ощущается как ${response.body()?.current?.feelsLikeTemp})"
                        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

            })
    }
}

class LocationViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
        .inflate(R.layout.location_item, parent, false)) {

    var name: TextView = itemView.findViewById(R.id.locationName)

    fun bind(location: ForecastLocation) {
        name.text = "${location.name} (${location.country})"
    }
}

class LocationsAdapter(val clickListener: LocationClickListener) : RecyclerView.Adapter<LocationViewHolder>() {

    var locations = ArrayList<ForecastLocation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder = LocationViewHolder(parent)

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(locations.get(position))
        holder.itemView.setOnClickListener { clickListener.onLocationClick(locations.get(position)) }
    }

    override fun getItemCount(): Int = locations.size

    fun interface LocationClickListener {
        fun onLocationClick(location: ForecastLocation)
    }
}


class ForecaAuthRequest(val user: String, val password: String)

class ForecaAuthResponse(@SerializedName("access_token") val token: String)

data class ForecastLocation(val id: Int,
                            val name: String,
                            val country: String)

class LocationsResponse(val locations: ArrayList<ForecastLocation>)

data class CurrentWeather(val temperature: Float,
                          val feelsLikeTemp: Float)

class ForecastResponse(val current: CurrentWeather)

interface ForecaApi {

    @POST("/authorize/token?expire_hours=-1")
    fun authenticate(@Body request: ForecaAuthRequest): Call<ForecaAuthResponse>

    @GET("/api/v1/location/search/{query}")
    fun getLocations(@Header("Authorization") token: String, @Path("query") query: String): Call<LocationsResponse>

    @GET("/api/v1/current/{location}")
    fun getForecast(@Header("Authorization") token: String, @Path("location") locationId: Int): Call<ForecastResponse>
}

