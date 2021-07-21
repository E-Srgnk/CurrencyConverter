package com.srgnk.currencyconverter.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.srgnk.currencyconverter.R
import com.srgnk.currencyconverter.RatesState
import com.srgnk.currencyconverter.commons.*
import com.srgnk.currencyconverter.databinding.FragmentConverterBinding
import com.srgnk.currencyconverter.viewmodels.ConverterViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ConverterFragment : Fragment() {

    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ConverterViewModel by viewModels()

    private var rates: Array<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConverterBinding.inflate(inflater, container, false)

        subscribeUI()

        binding.apply {
            clearAll.setOnClickListener { viewModel.clearAll() }
            clearLastSymbol.setOnClickListener { viewModel.clearLast() }

            comma.setOnClickListener { viewModel.addSymbol(",") }
            one.setOnClickListener { viewModel.addSymbol("1") }
            two.setOnClickListener { viewModel.addSymbol("2") }
            tree.setOnClickListener { viewModel.addSymbol("3") }
            four.setOnClickListener { viewModel.addSymbol("4") }
            five.setOnClickListener { viewModel.addSymbol("5") }
            six.setOnClickListener { viewModel.addSymbol("6") }
            seven.setOnClickListener { viewModel.addSymbol("7") }
            eight.setOnClickListener { viewModel.addSymbol("8") }
            nine.setOnClickListener { viewModel.addSymbol("9") }
            zero.setOnClickListener { viewModel.addSymbol("0") }

            refreshCurrencyRate.setOnClickListener {
                viewModel.fetchRates()
                binding.refreshCurrencyRate.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }

            secondCurrencyLayout.setOnClickListener {
                val bundle = Bundle()
                rates?.let {
                    bundle.putStringArray(RATES, it)
                }
                findNavController().navigate(
                    R.id.action_ConverterFragment_to_SelectCurrencyFragment,
                    bundle
                )
            }
        }

        getNavigationResult()
            ?.observe(viewLifecycleOwner) { result ->
                viewModel.selectedCurrency(result)
            }

        return binding.root
    }

    private fun subscribeUI() {
        viewModel.enteredValue.observe(viewLifecycleOwner) {
            binding.firstCurrencyValue.text = it.toString()
        }

        viewModel.ratesState.observe(viewLifecycleOwner) { state ->
            state?.let {
                when (it) {
                    is RatesState.Success -> {
                        rates = it.exchangeRates.rates.keys.toTypedArray()
                        binding.dateLastRefresh.text = Date().formattedDate()
                    }
                    is RatesState.Error -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
                binding.progressBar.visibility = View.GONE
                binding.refreshCurrencyRate.visibility = View.VISIBLE
            }
        }

        viewModel.currencyName.observe(viewLifecycleOwner) {
            binding.secondCurrency.text = it
        }

        viewModel.calculatedValue.observe(viewLifecycleOwner) {
            binding.secondCurrencyValue.text = it
        }

        viewModel.ratio.observe(viewLifecycleOwner) {
            binding.ratioOfCurrencies.text = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.refreshCurrencyRate.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

        viewModel.fetchRates()

        val prefs = context?.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val currency = prefs?.getString(SELECTED_CURRENCY, "USD") ?: "USD"

        viewModel.selectedCurrency(currency)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}