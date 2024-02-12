package com.example.onlinestoretest.ui.main.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.onlinestoretest.databinding.FragmentFavoritesBinding
import com.example.onlinestoretest.domain.Product
import com.example.onlinestoretest.ui.main.common.ProductAdapter

class FavoritesFragment : Fragment(), ProductAdapter.FavoriteItemClickListener {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var productAdapter: ProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this@FavoritesFragment)[FavoritesViewModel::class.java]

        productAdapter = ProductAdapter(requireContext())
        productAdapter.onItemClickListener = object : ProductAdapter.OnItemClickListener {
            override fun onItemClick(productId: String) {
                val action = FavoritesFragmentDirections.actionFavoritesFragmentToProductFragment(productId)
                view.findNavController().navigate(action)
            }
        }

        productAdapter.favoriteItemClickListener = object : ProductAdapter.FavoriteItemClickListener {
            override fun onToggleFavorite(productId: String) {
                viewModel.toggleFavorite(productId)
            }
        }

        binding.recyclerView.apply {
            val gridLayoutManager = GridLayoutManager(context, 2)
            layoutManager = gridLayoutManager

            adapter = productAdapter
        }

        viewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProducts ->
            showData(favoriteProducts)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showError()
        }

        showLoading()
        viewModel.loadFavorites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onToggleFavorite(productId: String) {

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
    }

    private fun showError() {
        binding.errorLayout.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.INVISIBLE
        binding.dataLayout.visibility = View.INVISIBLE

        binding.retryButton.setOnClickListener {
            showLoading()
            viewModel.loadFavorites()
        }
    }
}