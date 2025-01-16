package com.macc.androidkotlin.discountapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.macc.androidkotlin.R

class DiscountActivity : AppCompatActivity() {

    // Text Views
    private lateinit var txtPrecio: TextView
    private lateinit var txtCantidad: TextView
    private lateinit var txtPorcentaje: TextView
    private lateinit var txtTotal: TextView
    private lateinit var txtBTotal: TextView
    private lateinit var txtImporte: TextView
    private lateinit var txtBImporte: TextView
    private lateinit var txtIVA: TextView
    private lateinit var txtIEPS: TextView
    private lateinit var txtDiva: TextView
    private lateinit var txtDieps: TextView
    private lateinit var txtResultado: TextView
    private lateinit var txtDCiva: TextView
    private lateinit var txtDCieps: TextView

    // Input Fields
    private lateinit var tbxPrecio: EditText
    private lateinit var tbxCantidad: EditText
    private lateinit var tbxPorcentaje: EditText

    // Radio Group and Buttons
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioIVA: RadioButton
    private lateinit var radioIEPS: RadioButton

    // Button
    private lateinit var btnDiscount: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discount)
        initComponents()
        initTaxes()

        btnDiscount.setOnClickListener {
            calculateDiscount()
        }
    }

    companion object {
        const val IVA_RATE = 0.16
        const val IEPS_RATE = 0.08
    }

    private fun calculateDiscount() {
        val precioUnit = getDoubleFromInput(tbxPrecio)
        val cantidad = getIntFromInput(tbxCantidad)
        val porcentaje = getDoubleFromInput(tbxPorcentaje) / 100
        val taxRate = getSelectedRadioButtonValue()

        if (precioUnit == 0.0 || cantidad == 0 || porcentaje == 0.0) {
            txtResultado.text = "Error: Ingrese valores válidos."
            return
        }

        // Calculations
        val total = precioUnit * cantidad
        val importe = total / (1 + taxRate)
        val bonificacionTotal = total * porcentaje
        val bonificacionImporte = importe * porcentaje

        var importeIva = 0.0
        var ivaDescuento = 0.0
        var importeIeps = 0.0
        var iepsDescuento = 0.0

        if (taxRate == IEPS_RATE) {
            importeIeps = importe * taxRate
            iepsDescuento = importeIeps * porcentaje
        } else if (taxRate == IVA_RATE) {
            importeIva = importe * taxRate
            ivaDescuento = importeIva * porcentaje
        }

        val resultado = total - bonificacionTotal
        val ivaConDescuento = importeIva - ivaDescuento
        val iepsConDescuento = importeIeps - iepsDescuento

        // Display results
        txtTotal.text = "Total: $%.6f".format(total)
        txtImporte.text = "Importe: $%.6f".format(importe)
        txtBTotal.text = "Bonif. Total: $%.6f".format(bonificacionTotal)
        txtBImporte.text = "Bonif. Importe: $%.6f".format(bonificacionImporte)
        txtIVA.text = "IVA: $%.6f".format(importeIva)
        txtDiva.text = "Bonif. IVA: $%.6f".format(ivaDescuento)
        txtIEPS.text = "IEPS: $%.6f".format(importeIeps)
        txtDieps.text = "Bonif. IEPS: $%.6f".format(iepsDescuento)
        txtDCiva.text = "IVA con Descuento: $%.6f".format(ivaConDescuento)
        txtDCieps.text = "IEPS con Descuento: $%.6f".format(iepsConDescuento)
        txtResultado.text = "Resultado: $%.6f".format(resultado)
    }

    private fun getSelectedRadioButtonValue(): Double {
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId
        if (selectedRadioButtonId == -1) {
            return 0.0 // Default value if no radio button is selected
        }
        val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
        return when (selectedRadioButton.id) {
            R.id.radioIVA -> IVA_RATE
            R.id.radioIEPS -> IEPS_RATE
            else -> 0.0
        }
    }

    private fun getDoubleFromInput(input: EditText): Double {
        val text = input.text.toString()
        return text.toDoubleOrNull() ?: 0.0
    }

    private fun getIntFromInput(input: EditText): Int {
        val text = input.text.toString()
        return text.toIntOrNull() ?: 0
    }

    private fun initTaxes() {
        radioGroup.check(R.id.radioIVA) // Default selection
    }

    private fun initComponents() {
        // Text Views
        txtPrecio = findViewById(R.id.txtPrecio)
        txtCantidad = findViewById(R.id.txtCantidad)
        txtPorcentaje = findViewById(R.id.txtPorcentaje)
        txtTotal = findViewById(R.id.txtTotal)
        txtBTotal = findViewById(R.id.txtBTotal)
        txtImporte = findViewById(R.id.txtImporte)
        txtBImporte = findViewById(R.id.txtBImporte)
        txtIVA = findViewById(R.id.txtIVA)
        txtIEPS = findViewById(R.id.txtIEPS)
        txtDiva = findViewById(R.id.txtDiva)
        txtDieps = findViewById(R.id.txtDieps)
        txtResultado = findViewById(R.id.txtResultado)
        txtDCieps = findViewById(R.id.txtDCieps)
        txtDCiva = findViewById(R.id.txtDCiva)

        // Input Fields
        tbxPrecio = findViewById(R.id.tbxPrecio)
        tbxCantidad = findViewById(R.id.tbxCantidad)
        tbxPorcentaje = findViewById(R.id.tbxPorcentaje)

        // Radio Group and Buttons
        radioGroup = findViewById(R.id.radioGroup)
        radioIVA = findViewById(R.id.radioIVA)
        radioIEPS = findViewById(R.id.radioIEPS)

        // Button
        btnDiscount = findViewById(R.id.btnDescuento)
    }
}
