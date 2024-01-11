package com.example.eletriccarapp.data

import com.example.eletriccarapp.domain.Car

object CarFactory {

    val list = listOf(
        Car(
            id = 1,
            preco = "R$ 300.000,00",
            bateria = "320 kWh",
            potencia = "150 cv",
            recarga = "23 min",
            urlPhoto = "www.google.com",
            isFavorite = false
        ),
        Car(
            id = 2,
            preco = "R$ 200.000,00",
            bateria = "200 kWh",
            potencia = "200 cv",
            recarga = "12 min",
            urlPhoto = "www.google.com",
            isFavorite = false
        )
    )
}