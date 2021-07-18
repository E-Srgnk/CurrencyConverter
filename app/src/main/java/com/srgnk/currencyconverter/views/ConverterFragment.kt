package com.srgnk.currencyconverter.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.srgnk.currencyconverter.databinding.FragmentConverterBinding
import com.srgnk.currencyconverter.viewmodels.ConverterViewModel

class ConverterFragment : Fragment() {

    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!

    val viewModel: ConverterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConverterBinding.inflate(inflater, container, false)

        viewModel.enteredValue.observe(viewLifecycleOwner) {
            binding.firstCurrencyValue.text = it.toString()
        }

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
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}