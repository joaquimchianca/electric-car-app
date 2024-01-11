package com.example.eletriccarapp.presentation

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.eletriccarapp.R

class CalculaAutonomiaActivity : ComponentActivity() {

    private lateinit var preco: EditText
    private lateinit var percurso: EditText
    private lateinit var resultado: TextView
    private lateinit var btnCalcular: Button
    private lateinit var btnClose: ImageView

    private fun setupView() {
        preco = findViewById(R.id.et_preco_kwh)
        percurso = findViewById(R.id.et_km_percorrido)
        resultado = findViewById(R.id.tv_resultado)
        btnCalcular = findViewById(R.id.btn_calcular)
        btnClose = findViewById(R.id.iv_close)
    }

    private fun setupListeners() {
        btnCalcular.setOnClickListener{
            calcular()
        }
        btnClose.setOnClickListener{
            finish()
        }
    }

    private fun calcular() {
        val preco = preco.text.toString().toDouble()
        val km = percurso.text.toString().toDouble()
        val r = preco / km
        resultado.text = "O carro faz $ $r por km rodado"
        saveSharedPref(r)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcula_autonomia)
        setupView()
        setupListeners()
        setupCachedResult()
    }

    private fun setupCachedResult() {
        val valorCalculado = getSharedPref()
        resultado.text = valorCalculado.toString()
    }

    // armazena o resultado numa shared preference
    fun saveSharedPref(resultado: Double) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putFloat(getString(R.string.saved_calc), 1f)
            apply()
        }
    }

    // recupera valor salvo no mapeamento local do shared preferences
    fun getSharedPref(): Float {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getFloat(getString(R.string.saved_calc), 0.0f)
    }
}