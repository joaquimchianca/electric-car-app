package com.example.eletriccarapp.presentation

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccarapp.R
import com.example.eletriccarapp.data.CarService
import com.example.eletriccarapp.domain.Car
import com.example.eletriccarapp.presentation.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarFragment: Fragment() {

    private lateinit var fabRedirect: FloatingActionButton
    private lateinit var listaCarros: RecyclerView
    private lateinit var carService: CarService
    private lateinit var progress: ProgressBar
    private lateinit var noInternetImage: ImageView
    private lateinit var noInternetText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRetrofit()
        setupViews(view)
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        if (checkNetwork(context)) {
            getAllCars()
        } else {
            emptyState()
        }
    }

    // checks if cellphone is connected to internet
    private fun checkNetwork(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        return if (activeNetwork == null) {
            false
        } else {
            val netCapabilities = cm.getNetworkCapabilities(activeNetwork)
            (netCapabilities != null
                    && netCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    && netCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
        }
    }

    private fun emptyState() {
        progress.isVisible = false
        listaCarros.isVisible = false
        noInternetImage.isVisible = true
        noInternetText.isVisible = true
    }



    private fun setupViews(view: View) {
        view.apply {
            fabRedirect = findViewById(R.id.fab_calcular)
            listaCarros = findViewById(R.id.rv_lista_carros)
            progress = findViewById(R.id.pb_loader)
            noInternetImage = findViewById(R.id.iv_empty_state)
            noInternetText = findViewById(R.id.tv_wifi_off)
        }
    }

    private fun setupListeners() {
        fabRedirect.setOnClickListener{
            startActivity(Intent(context, CalculaAutonomiaActivity::class.java))
        }
    }

    private fun setupList(cars: List<Car>) {
        val carAdapter = CarAdapter(cars)
        listaCarros.apply {
            isVisible = true
            adapter = carAdapter
        }
        carAdapter.carItemListener = { carro ->
            val bateria = carro.bateria
        }
    }

    private fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://igorbag.github.io/cars-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        carService = retrofit.create(CarService::class.java)
    }

    private fun getAllCars() {
        carService.getAllCars().enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if (response.isSuccessful) {
                    progress.isVisible = false
                    noInternetImage.isVisible = false
                    noInternetText.isVisible = false
                    response.body()?.let {
                        setupList(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
            }

        })
    }



}

