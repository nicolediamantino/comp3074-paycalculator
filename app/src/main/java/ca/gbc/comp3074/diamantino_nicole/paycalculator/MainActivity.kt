package ca.gbc.comp3074.diamantino_nicole.paycalculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.Locale
import android.content.Intent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //hide actionBar on calculator screen
        supportActionBar?.hide()

        // Input fields
        val etHours = findViewById<EditText>(R.id.etHours)
        val etRate = findViewById<EditText>(R.id.etRate)
        val etTax = findViewById<EditText>(R.id.etTax)

        // Result fields
        val tvPay = findViewById<TextView>(R.id.tvPay)
        val tvOvertime = findViewById<TextView>(R.id.tvOvertime)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val tvTax = findViewById<TextView>(R.id.tvTax)
        val tvNetPay = findViewById<TextView>(R.id.tvNetPay)

        // Buttons
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val btnAbout = findViewById<Button>(R.id.btnAbout)

        btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
        btnCalculate.setOnClickListener {

            // Read input
            val hours = etHours.text.toString().toDoubleOrNull()
            val rate = etRate.text.toString().toDoubleOrNull()
            val taxRate = etTax.text.toString().toDoubleOrNull()

            // Validate input
            if (hours == null || rate == null || taxRate == null) {
                Toast.makeText(
                    this,
                    "Please enter valid numbers",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (hours < 0 || rate < 0 ||
                taxRate < 0 || taxRate > 100) {
                Toast.makeText(
                    this,
                    "Please check your input values",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Calculate regular and overtime pay
            var pay: Double
            var overtimePay = 0.0

            if (hours <= 40) {
                pay = hours * rate
            } else {
                pay = 40 * rate
                overtimePay = (hours - 40) * rate * 1.5
            }

            // Calculate total pay
            val totalPay = pay + overtimePay

            // Convert tax percentage to decimal
            val tax = pay * (taxRate / 100)

            // Calculate pay after tax
            val netPay = totalPay - tax

            // Display results
            tvPay.text = String.format(
                Locale.CANADA, "Pay: $%.2f", pay
            )

            tvOvertime.text = String.format(
                Locale.CANADA, "Overtime Pay: $%.2f", overtimePay
            )

            tvTotal.text = String.format(
                Locale.CANADA, "Total Pay: $%.2f", totalPay
            )

            tvTax.text = String.format(
                Locale.CANADA, "Tax: $%.2f", tax
            )

            tvNetPay.text = String.format(
                Locale.CANADA, "Total Pay - Tax: $%.2f", netPay
            )
        }
    }
}
