package com.example.streaminganimes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.models.ModelHistorico

class AdapterContinueAssistindo(private val episodioLista: ArrayList<ModelHistorico>) : RecyclerView.Adapter<AdapterContinueAssistindo.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterContinueAssistindo.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_continue_assistindo, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterContinueAssistindo.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return episodioLista.size
    }
}