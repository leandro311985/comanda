package com.example.mylogin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.mylogin.Description
import com.example.mylogin.Pedido
import com.example.mylogin.R
import com.github.loadingview.LoadingDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var database: DatabaseReference
    private var pizza: String? = null
    private var buger: String? = null
    private var cafe: String? = null
    private lateinit var  dialog : LoadingDialog




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        database = FirebaseDatabase.getInstance().reference

        //dialog = LoadingDialog[MainActivity()].show()




        gravar()


    }

    fun gravar(){
       // dialog.show()
        button.setOnClickListener {
            var totalAmount: Int = 0
            val result = StringBuilder()

            result.append("Selected Items")
            if (checkBox1.isChecked) {
                result.append("\nPizza 100Rs")
                pizza = "pizza"
            }
            if (checkBox2.isChecked) {
                result.append("\nCoffee 50Rs")
                cafe = "café"
                totalAmount += 50
            }
            if (checkBox3.isChecked) {
                result.append("\nBurger 120Rs")
                buger = "Hambuger"

                totalAmount += 120
            }
            result.append("\nTotal: " + totalAmount + "Rs")
            var pedidos = pizza
            Toast.makeText(activity, result.toString(), Toast.LENGTH_SHORT).show()
            pedidoFirebase(pedidos?:"","20,00","1")
            pedidoFirebase(cafe?:"","5,00","1")

         //   Thread.sleep(5000)
        //    dialog.hide()


        }

    }

    private fun pedidoFirebase(tipo: String, preco: String, quantidade: String?) {
        val pedido = Pedido(pedido = Description(tipo,preco,quantidade?:""))
        database.child("users").child("001").setValue(pedido)
    }


}

