package com.srgnk.currencyconverter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.srgnk.currencyconverter.databinding.CurrencyBinding

class CurrencyAdapter(
    private val values: Array<String>?,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun clickListener(currency: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyAdapter.ViewHolder {
        val itemBinding =
            CurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        values?.let {
            holder.currency.text = it[position]
        }
    }

    override fun getItemCount() = values?.size ?: 0

    inner class ViewHolder(
        binding: CurrencyBinding,
        private val listenerItem: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        var currency = binding.currency

        init {
            binding.currency.setOnClickListener {
                listenerItem.clickListener((it as TextView).text.toString())
            }
        }
    }
}