package com.example.dados_android

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dados_android.databinding.ActivityMainBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var fichero_compartido : SharedPreferences
    private lateinit var handler: Handler

    private  var resultadoDados : Int = 0





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inicarPreferenciasCompartidas()
        handler = Handler(Looper.getMainLooper())
        programarTirada(1)


    }


    /**Iniciamos las preferencias compartidas*/
    private fun inicarPreferenciasCompartidas(){
        val nombreFicheroCompartido = getString(R.string.nombre_fichero_preferencia_compartida)
        this.fichero_compartido = getSharedPreferences(nombreFicheroCompartido, MODE_PRIVATE)
    }


/**Metodo que programa la tirada y ejecuta un hilo*/
    private fun programarTirada(tiempo: Long) {

        binding.ivVaso.setOnClickListener{
            val animation : Animation = AnimationUtils.loadAnimation(this, R.anim.animacion_vaso)
            binding.ivVaso.startAnimation(animation)

            val executor1 = Executors.newSingleThreadScheduledExecutor() //creamos un hilo
            executor1.schedule({
                //amimacion para los dados al ejecutar el hilo
                val animationDados : Animation = AnimationUtils.loadAnimation(this, R.anim.animacion_dados)
                binding.ivDadoUno.startAnimation(animationDados)
                binding.ivDadoDos.startAnimation(animationDados)
                binding.ivDadoTres.startAnimation(animationDados)
                handler.post {
                    for(i in 1..3){
                        cambiarDados()
                    }
                }
            }, tiempo, TimeUnit.SECONDS)
        }

    }
/**Metodo que cambia los dados*/
    private fun cambiarDados() {


        //val valores = arrayOf(valor1, valor2, valor3)


    val valor1 : Int = (Math.random()*6).roundToInt()
    val valor2 =  (Math.random()*6).roundToInt()
    val valor3 =  (Math.random()*6).roundToInt()

    resultadoDados  = valor1 + valor2 + valor3
    binding.tvResultado.text = resultadoDados.toString()

    when(valor1){
        1 -> binding.ivDadoUno.setImageResource(R.drawable.dado1)
        2 -> binding.ivDadoUno.setImageResource(R.drawable.dado2)
        3 -> binding.ivDadoUno.setImageResource(R.drawable.dado3)
        4 -> binding.ivDadoUno.setImageResource(R.drawable.dado4)
        5 -> binding.ivDadoUno.setImageResource(R.drawable.dado5)
        6 -> binding.ivDadoUno.setImageResource(R.drawable.dado6)
    }
    when(valor2){
        1 -> binding.ivDadoDos.setImageResource(R.drawable.dado1)
        2 -> binding.ivDadoDos.setImageResource(R.drawable.dado2)
        3 -> binding.ivDadoDos.setImageResource(R.drawable.dado3)
        4 -> binding.ivDadoDos.setImageResource(R.drawable.dado4)
        5 -> binding.ivDadoDos.setImageResource(R.drawable.dado5)
        6 -> binding.ivDadoDos.setImageResource(R.drawable.dado6)
    }
    when(valor3){
        1 -> binding.ivDadoTres.setImageResource(R.drawable.dado1)
        2 -> binding.ivDadoTres.setImageResource(R.drawable.dado2)
        3 -> binding.ivDadoTres.setImageResource(R.drawable.dado3)
        4 -> binding.ivDadoTres.setImageResource(R.drawable.dado4)
        5 -> binding.ivDadoTres.setImageResource(R.drawable.dado5)
        6 -> binding.ivDadoTres.setImageResource(R.drawable.dado6)
    }



    }


}