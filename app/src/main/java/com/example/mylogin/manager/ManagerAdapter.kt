package com.example.mylogin.manager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mylogin.Pedido
import com.example.mylogin.R
import kotlinx.android.synthetic.main.item_view_recicler_manager.view.*
import kotlinx.android.synthetic.main.main_fragment.view.*

class ManagerAdapter(val list: MutableList<Pedido>) : RecyclerView.Adapter<CustomViewHolder>(){

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_view_recicler_manager, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val post = list.get(position)
        holder.view.textTitle.text = post.title.toString()
        holder.view.textDescription.text = post.body.toString()
        holder.view.textName.text = post.author.toString()
        holder.view.textUiid.text = post.uid.toString()
        holder.view.status.text = "Status = processando"

//        if (post.status == "true"){
//            holder.view.status.text = "Status = Pronto pra retirada no balcão"
//        }else
//            holder.view.status.text = "Status = processando"
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

}