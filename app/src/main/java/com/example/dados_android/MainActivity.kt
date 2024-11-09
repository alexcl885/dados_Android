package com.example.dados_android

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dados_android.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

/**
 * El juego consiste en tocar el vaso de los dados y..:
 *  1. Si son 2/3 el mismo numero ganas 1 punto
 *  2. Si son 3/3 el mismo numero ganas 2 puntos
 *  3. Si no son del mismo numero pues sumas una tirada,
 *      ENTONCES:
 *      si llegas a las tres tiradas pierdes y si llegas a 3 puntos ganas
 * */
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var fichero_compartido : SharedPreferences
    private lateinit var handler: Handler

    //VARIABLES QUE NECESITAMOS PARA EL JUEGO
    private var tiradas : Int = 0
    private var puntos : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inicarPreferenciasCompartidas()

        handler = Handler(Looper.getMainLooper())

        binding.ivVaso.setOnClickListener {
            programarTirada()
        }
        binding.btReiniciar.setOnClickListener {
            reiniciarJuego()
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }
    /**Iniciamos las preferencias compartidas*/
    private fun inicarPreferenciasCompartidas(){
        val nombreFicheroCompartido = getString(R.string.nombre_fichero_preferencia_compartida)
        this.fichero_compartido = getSharedPreferences(nombreFicheroCompartido, MODE_PRIVATE)
    }


    private  fun programarTirada(){
        if (tiradas < 3) {
            val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.animacion_vaso)
            binding.ivVaso.startAnimation(animation)

            val executor1 = Executors.newSingleThreadScheduledExecutor()
            executor1.schedule({
                val animationDados: Animation = AnimationUtils.loadAnimation(this, R.anim.animacion_dados)

                binding.ivDadoUno.startAnimation(animationDados)
                binding.ivDadoDos.startAnimation(animationDados)
                binding.ivDadoTres.startAnimation(animationDados)
                handler.post {
                    realizarTirada()
                }
            }, 1, TimeUnit.SECONDS)
        }
    }


    /**Metodo que programa la tirada y ejecuta un hilo*/
private fun realizarTirada() {
    val animation = AnimationUtils.loadAnimation(this, R.anim.animacion_vaso)
    binding.ivVaso.startAnimation(animation)
    cambiarDados()
    habilitarTirada(true)
    verificarFinDelJuego()
}
    /**Metodo que calcula el numero y llama a otro metodo para cambiar los datos*/
    private fun cambiarDados() {

    val valor1  = (Math.random()*6).roundToInt()
    val valor2 =  (Math.random()*6).roundToInt()
    val valor3 =  (Math.random()*6).roundToInt()

    if (valor1 == valor2 && valor2 == valor3) {
        puntos += 2
    } else if (valor1 == valor2 || valor2 == valor3 || valor1 == valor3) {
        puntos += 1
    } else {
        tiradas++
    }
    binding.ivDadoUno.setImageResource(imagenDado(valor1))
    binding.ivDadoDos.setImageResource(imagenDado(valor2))
    binding.ivDadoTres.setImageResource(imagenDado(valor3))

    actualizarPuntuacion()
    }
    private fun actualizarPuntuacion() {
        binding.tvNumeroPuntos.text = puntos.toString()
        binding.tvNumeroTiradas.text = tiradas.toString()
    }
    /**Metodo que cambia la imagen del dado*/
    private fun imagenDado(value: Int): Int {
        return when (value) {
            1 -> R.drawable.dado1
            2 -> R.drawable.dado2
            3 -> R.drawable.dado3
            4 -> R.drawable.dado4
            5 -> R.drawable.dado5
            else -> R.drawable.dado6
        }
    }

    /**Metodo para verificar si es fin del juego*/
    private fun verificarFinDelJuego() {
        if (puntos >= 3) {
            Toast.makeText(this, "HAS GANADO!!!", Toast.LENGTH_LONG).show()
            habilitarTirada(false)
        } else if (tiradas >= 3) {
            Toast.makeText(this, "HAS PERDIDO!!!", Toast.LENGTH_LONG).show()
            habilitarTirada(false)
        }
    }

    /**Metodo que devuelve si se puede tirar o no el dado*/
    private fun habilitarTirada(habilitar: Boolean) {
        binding.ivVaso.isEnabled = habilitar
    }
    /**Metodo que reinicia el juego*/
    private fun reiniciarJuego() {
        tiradas = 0
        puntos = 0
        binding.tvNumeroPuntos.text = tiradas.toString()
        binding.tvNumeroTiradas.text = puntos.toString()
        Toast.makeText(this, "Juego reiniciado", Toast.LENGTH_SHORT).show()
        habilitarTirada(true)
    }
}
