package com.srgnk.currencyconverter.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.srgnk.currencyconverter.adapters.CurrencyAdapter
import com.srgnk.currencyconverter.commons.PREFS
import com.srgnk.currencyconverter.commons.RATES
import com.srgnk.currencyconverter.commons.SELECTED_CURRENCY
import com.srgnk.currencyconverter.commons.setNavigationResult
import com.srgnk.currencyconverter.databinding.FragmentSelectCurrencyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectCurrencyFragment : Fragment(), CurrencyAdapter.ItemClickListener {

    private var _binding: FragmentSelectCurrencyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rates = arguments?.getStringArray(RATES)

        binding.currenciesRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = CurrencyAdapter(rates, this@SelectCurrencyFragment)
        }

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun clickListener(currency: String) {
        setNavigationResult(currency)

        val prefs = context?.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val editor = prefs?.edit()
        editor?.putString(SELECTED_CURRENCY, currency)
        editor?.apply()

        findNavController().popBackStack()
    }
}