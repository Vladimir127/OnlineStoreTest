package com.example.onlinestoretest.ui.main.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.onlinestoretest.databinding.FragmentCatalogBinding
import com.example.onlinestoretest.domain.Product

class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            val gridLayoutManager = GridLayoutManager(context, 2)
            layoutManager = gridLayoutManager

            val catalogAdapter = CatalogAdapter()
            adapter = catalogAdapter

            val viewModel = ViewModelProvider(this@CatalogFragment)[CatalogViewModel::class.java]

            viewModel.products.observe(viewLifecycleOwner, Observer { products ->
                catalogAdapter.setData(products)
            })
            viewModel.loadProducts()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}