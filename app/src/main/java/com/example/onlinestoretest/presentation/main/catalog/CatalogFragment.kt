package com.example.onlinestoretest.presentation.main.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.onlinestoretest.R
import com.example.onlinestoretest.databinding.FragmentCatalogBinding
import com.example.onlinestoretest.domain.models.Product
import com.example.onlinestoretest.presentation.main.common.ProductAdapter
import com.google.android.material.chip.Chip

class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CatalogViewModel
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this@CatalogFragment)[CatalogViewModel::class.java]

        productAdapter = ProductAdapter(requireContext())
        productAdapter.onItemClickListener = { productId ->
            val action = CatalogFragmentDirections.actionCatalogFragmentToProductFragment(productId)
            view.findNavController().navigate(action)
        }

        productAdapter.favoriteItemClickListener =
            object : ProductAdapter.FavoriteItemClickListener {
                override fun onToggleFavorite(productId: String) {
                    viewModel.toggleFavorite(productId)
                }
            }

        binding.recyclerView.apply {
            setItemViewCacheSize(10)

            val gridLayoutManager = GridLayoutManager(context, 2)
            layoutManager = gridLayoutManager

            adapter = productAdapter
        }

        viewModel.products.observe(viewLifecycleOwner) { products ->
            showData(products)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showError()
        }

        showLoading()
        viewModel.loadProducts()

        initSpinner()
        initChips()
    }

    private fun initSpinner() {
        val sortOptions = arrayOf(
            requireContext().resources.getString(R.string.sort_popularity),
            requireContext().resources.getString(R.string.sort_price_descending),
            requireContext().resources.getString(R.string.sort_price_ascending)
        )

        val adapter = CustomSpinnerAdapter(requireContext(), sortOptions)
        binding.sortSpinner.adapter = adapter

        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedSortOption = sortOptions[position]
                productAdapter.sortBy(selectedSortOption)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun initChips() {
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            var selectedTag = "watch_all"

            val chip = group.findViewById<Chip>(checkedId)
            chip?.let { selectedTag = chip.tag.toString() }
            productAdapter.filterByTag(selectedTag)
        }

        binding.watchAllChip.setOnCheckedChangeListener { compoundButton, checked ->
            setUpChip(
                compoundButton,
                checked
            )
        }

        binding.faceChip.setOnCheckedChangeListener { compoundButton, checked ->
            setUpChip(
                compoundButton,
                checked
            )
        }

        binding.bodyChip.setOnCheckedChangeListener { compoundButton, checked ->
            setUpChip(
                compoundButton,
                checked
            )
        }

        binding.suntanChip.setOnCheckedChangeListener { compoundButton, checked ->
            setUpChip(
                compoundButton,
                checked
            )
        }

        binding.masksChip.setOnCheckedChangeListener { compoundButton, checked ->
            setUpChip(
                compoundButton,
                checked
            )
        }
    }

    private fun setUpChip(compoundButton: CompoundButton, checked: Boolean) {
        val chip = compoundButton as Chip
        chip.isCloseIconVisible = checked
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading() {
        binding.errorLayout.visibility = View.INVISIBLE
        binding.loadingLayout.visibility = View.VISIBLE
        binding.dataLayout.visibility = View.INVISIBLE
    }

    private fun showData(products: List<Product>) {
        binding.errorLayout.visibility = View.INVISIBLE
        binding.loadingLayout.visibility = View.INVISIBLE
        binding.dataLayout.visibility = View.VISIBLE

        productAdapter.setData(products)
        productAdapter.sortBy(requireContext().resources.getString(R.string.sort_popularity))
    }

    private fun showError() {
        binding.errorLayout.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.INVISIBLE
        binding.dataLayout.visibility = View.INVISIBLE

        binding.retryButton.setOnClickListener {
            showLoading()
            viewModel.loadProducts()
        }
    }
}