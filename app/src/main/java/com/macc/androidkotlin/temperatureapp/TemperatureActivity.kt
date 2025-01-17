package com.macc.androidkotlin.temperatureapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.macc.androidkotlin.R

class TemperatureActivity : AppCompatActivity() {
    private lateinit var spTemp1: Spinner
    private lateinit var spTemp2: Spinner
    private lateinit var tbxTemp1: EditText
    private lateinit var tbxTemp2: EditText
    private lateinit var btnConvert: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature)
        initComponents()
        setupSpinners()
        setupListeners()
    }

    private fun initComponents() {
        spTemp1 = findViewById(R.id.spTemp1)
        spTemp2 = findViewById(R.id.spTemp2)
        tbxTemp1 = findViewById(R.id.tbxTemp1)
        tbxTemp2 = findViewById(R.id.tbxTemp2)
        btnConvert = findViewById(R.id.btnConvert)
        tbxTemp2.isEnabled = false
    }

    private fun setupSpinners() {
        val items = resources.getStringArray(R.array.spinner_items_temp)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spTemp1.adapter = adapter
        spTemp2.adapter = adapter
    }

    private fun setupListeners() {
        btnConvert.setOnClickListener { convertTemps() }
    }

    private fun convertTemps() {
        val temp1 = getDoubleFromInput(tbxTemp1)
        if (temp1 == null) {
            showToast("Ingrese un numero valido.")
            return
        }

        val fromUnit = spTemp1.selectedItem?.toString()
        val toUnit = spTemp2.selectedItem?.toString()

        if (fromUnit == null || toUnit == null) {
            showToast("Seleccione una unidad de temperatura valida")
            return
        }

        val result = when (fromUnit to toUnit) {
            "Celsius" to "Fahrenheit" -> (temp1 * 9 / 5) + 32
            "Celsius" to "Kelvin" -> temp1 + 273.15
            "Fahrenheit" to "Celsius" -> (temp1 - 32) * 5 / 9
            "Fahrenheit" to "Kelvin" -> (temp1 + 459.67) * 5 / 9
            "Kelvin" to "Celsius" -> temp1 - 273.15
            "Kelvin" to "Fahrenheit" -> (temp1 * 9 / 5) - 459.67
            else -> {
                showToast("Conversion no soportada.")
                return
            }
        }
        tbxTemp2.setText(String.format("%.2f",result) + "°")
    }

    private fun getDoubleFromInput(input: EditText): Double? {
        val text = input.text.toString()
        return text.toDoubleOrNull()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
