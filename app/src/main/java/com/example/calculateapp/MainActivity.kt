package com.example.calculateapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculateapp.databinding.ActivityMainBinding
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }


        val inputText = binding.root.findViewById<TextView>(R.id.inputText)
        val resultText = binding.root.findViewById<TextView>(R.id.resultText)


        val buttons = listOf(
            binding.cButton, binding.twoButton, binding.threeButton, binding.fourButton,
            binding.yediButton, binding.sekizButton, binding.dokuzButton, binding.xButton,
            binding.dortButton, binding.besButton, binding.altiButton, binding.eksiButton,
            binding.birButton, binding.ikiButton, binding.ucButton, binding.toplaButton,
            binding.noktaButton, binding.sifirButton, binding.esittirButton
        )

        buttons.forEach { button ->
            button.setOnClickListener { onButtonClick(button, inputText, resultText) }
        }
    }

    private fun onButtonClick(button: Button, inputText: TextView, resultText: TextView) {
        val buttonText = button.text.toString()
        when (buttonText) {
            "C" -> clearInput(inputText, resultText)
            "=" -> calculateResult(inputText, resultText)
            else -> appendToInput(buttonText, inputText)
        }
    }

    private fun appendToInput(text: String, inputText: TextView) {
        inputText.text = "${inputText.text}$text"
    }

    private fun clearInput(inputText: TextView, resultText: TextView) {
        inputText.text = ""
        resultText.text = ""
    }

    private fun calculateResult(inputText: TextView, resultText: TextView) {
        val expressionText = inputText.text.toString()
        if (expressionText.isNotEmpty()) {
            try {
                val expression = Expression(expressionText)
                val result = expression.calculate()
                if (result.isNaN()) {
                    Toast.makeText(this, "Geçersiz ifade", Toast.LENGTH_SHORT).show()
                } else {
                    resultText.text = result.toString()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Hata: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

