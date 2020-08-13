package com.example.mylogin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.mylogin.Dialog_components_Fragment
import com.example.mylogin.R
import com.example.mylogin.data.AuthListener
import com.github.loadingview.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.main_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MainFragment : Fragment() , AuthListener, KodeinAware {

    override val kodein by kodein()
    private val factory: MainViewModelFactory by instance()

    companion object {
        fun newInstance() = MainFragment()
    }
    lateinit var auth: FirebaseAuth

    private lateinit var viewModel: MainViewModel
    private lateinit var database: DatabaseReference
    private var pizza: String? = null
    private var buger: String? = null
    private var cafe: String? = null
    private var status: Boolean? = null

    private lateinit var  dialog : LoadingDialog




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,factory).get(MainViewModel::class.java)
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

//        dialog = LoadingDialog[MainActivity()].show()
        gravar()

    }

    fun gravar(){

        button.setOnClickListener {
            var totalAmount: Int = 0
            val result = StringBuilder()

            result.append("Pedidos Selecionados")
            if (checkBox1.isChecked) {
                result.append("\nPizza  ---> 16,90Rs")
                pizza = "pizza"
            }
            if (checkBox2.isChecked) {
                result.append("\nCafé  ---> 5,00Rs")
                cafe = "café"
                totalAmount += 50
            }
            if (checkBox3.isChecked) {
                result.append("\nHambuger   ---> 39,800Rs")
                buger = "Hambuger"

                totalAmount += 120
            }
            result.append("\nTotal: " + totalAmount + "Rs")
            Toast.makeText(activity, result.toString(), Toast.LENGTH_SHORT).show()

            viewModel.writeData(result.toString(),"pedido",auth.currentUser?.uid?:"",auth.currentUser?.email?:"",21)
        }

    }

    override fun onStarted() {
        TODO("Not yet implemented")
    }

    override fun onSuccess() {
        Toast.makeText(activity, "sucesso ao gravar", Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(message: String) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.container, Dialog_components_Fragment.newInstance(message,false))
            ?.addToBackStack(null)
            ?.commit()
    }

    fun status(){
        if (status == true){
            textStatus.text = "pronto a retirar ao cliente"
        }else
        textStatus.text = "processando"
    }

}

