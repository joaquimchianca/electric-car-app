package com.example.eletriccarapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccarapp.R
import com.example.eletriccarapp.domain.Car

class CarAdapter(private val carros: List<Car>) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    var carItemListener : (Car) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.carro_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarAdapter.ViewHolder, position: Int) {
        holder.preco.text = carros[position].preco
        holder.bateria.text = carros[position].bateria
        holder.potencia.text = carros[position].potencia
        holder.recarga.text = carros[position].recarga
        holder.favorito.setOnClickListener {
            val carro = carros[position]
            carItemListener(carro)
            setupFavorite(carro, holder)
        }
    }

    private fun setupFavorite(
        carro: Car,
        holder: ViewHolder
    ) {
        carro.isFavorite = !carro.isFavorite

        if (carro.isFavorite)
            holder.favorito.setImageResource(R.drawable.ic_star_selected)
        else
            holder.favorito.setImageResource(R.drawable.ic_star)
    }

    override fun getItemCount(): Int = carros.size
        class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val preco: TextView
            val bateria: TextView
            val potencia: TextView
            val recarga: TextView
            val favorito: ImageView

            init {
                view.apply {
                    preco = findViewById(R.id.tv_preco_value)
                    bateria = findViewById(R.id.bateria_value)
                    potencia = findViewById(R.id.potencia_value)
                    recarga = findViewById(R.id.tv_recarga_value)
                    favorito = findViewById(R.id.iv_favorite)
                }
            }
        }
}
