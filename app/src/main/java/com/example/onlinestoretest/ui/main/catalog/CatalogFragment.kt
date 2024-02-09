package com.example.onlinestoretest.ui.main.catalog

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.onlinestoretest.databinding.FragmentCatalogBinding
import com.example.onlinestoretest.domain.Product
import com.google.android.material.chip.Chip

class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CatalogViewModel
    private val catalogAdapter = CatalogAdapter()

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

            adapter = catalogAdapter

            viewModel = ViewModelProvider(this@CatalogFragment)[CatalogViewModel::class.java]

            viewModel.products.observe(viewLifecycleOwner, Observer { products ->
                catalogAdapter.setData(products)
                catalogAdapter.sortBy("По популярности")
            })
            viewModel.loadProducts()
        }

        initSpinner()
        initChips()
    }

    private fun initSpinner() {
        val sortOptions = arrayOf("По популярности", "По уменьшению цены", "По возрастанию цены")

        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, sortOptions)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.sortSpinner.adapter = adapter

        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedSortOption = sortOptions[position]
                // Вызов метода в адаптере для выполнения сортировки
                catalogAdapter.sortBy(selectedSortOption)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Обработка случая, когда ничего не выбрано
            }
        }
    }

    private fun initChips() {
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            var selectedTag = "watch_all"

            val chip = group.findViewById<Chip>(checkedId)
            chip?.let { selectedTag = chip.tag.toString() }
            catalogAdapter.filterByTag(selectedTag)
        }

        binding.watchAllChip.setOnCheckedChangeListener { compoundButton, checked ->
            setChipCloseButtonVisibility(
                compoundButton,
                checked
            )
        }

        binding.faceChip.setOnCheckedChangeListener { compoundButton, checked ->
            setChipCloseButtonVisibility(
                compoundButton,
                checked
            )
        }

        binding.bodyChip.setOnCheckedChangeListener { compoundButton, checked ->
            setChipCloseButtonVisibility(
                compoundButton,
                checked
            )
        }

        binding.suntanChip.setOnCheckedChangeListener { compoundButton, checked ->
            setChipCloseButtonVisibility(
                compoundButton,
                checked
            )
        }

        binding.masksChip.setOnCheckedChangeListener { compoundButton, checked ->
            setChipCloseButtonVisibility(
                compoundButton,
                checked
            )
        }
    }

    private fun setChipCloseButtonVisibility(compoundButton: CompoundButton, checked: Boolean) {
        val chip = compoundButton as Chip
        chip.isCloseIconVisible = checked
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}