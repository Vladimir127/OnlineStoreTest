package com.example.onlinestoretest.presentation.main.product

import android.os.Bundle
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.onlinestoretest.R
import com.example.onlinestoretest.databinding.FragmentProductBinding
import com.example.onlinestoretest.domain.models.Product
import com.example.onlinestoretest.presentation.main.common.ImageAdapter
import com.example.onlinestoretest.utils.ImageMapUtil.Companion.imageMap
import com.example.onlinestoretest.utils.dpToPx
import com.google.android.material.tabs.TabLayoutMediator

private const val ARG_PARAM1 = "productId"

class ProductFragment : Fragment() {
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private var product: Product? = null
    private var productId: String? = ""

    private var isDescriptionVisible = true
    private var areIngredientsExpanded = false

    private lateinit var viewModel: ProductViewModel

    private val imageAdapter = ImageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productId = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this@ProductFragment)[ProductViewModel::class.java]

        viewModel.product.observe(viewLifecycleOwner) {
            this.product = it
            showData()
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showError()
        }

        showLoading()
        productId?.let {
            viewModel.loadProduct(productId!!)
        }
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

    private fun showData() {
        binding.errorLayout.visibility = View.INVISIBLE
        binding.loadingLayout.visibility = View.INVISIBLE
        binding.dataLayout.visibility = View.VISIBLE

        initViewPager()

        initFavoriteButton()

        binding.titleTextView.text = product?.title
        binding.subtitleTextView.text = product?.subtitle

        val availableCount = product?.available ?: 0
        val availableText = resources.getQuantityString(R.plurals.pieces, availableCount, availableCount)
        binding.countAvailableTextView.text = resources.getString(R.string.available, availableText)

        binding.ratingBar.rating = product?.feedback?.rating?.toFloat()!!
        val reviewsCount = product?.feedback?.count ?: 0
        val reviewsText = resources.getQuantityString(R.plurals.reviews, reviewsCount, reviewsCount)
        binding.countReviewsTextView.text = resources.getString(R.string.rating, product?.feedback?.rating?.toFloat()!!, reviewsText)

        binding.priceTextView.text = resources.getString(R.string.price_with_unit, product?.price?.priceWithDiscount, product?.price?.unit)
        binding.oldPriceTextView.text = resources.getString(R.string.price_with_unit, product?.price?.price, product?.price?.unit)
        binding.discountTextView.text = resources.getString(R.string.discount, product?.price?.discount)

        initDescription()

        initInfoList()

        initIngredients()

        binding.priceTextView1.text = resources.getString(R.string.price_with_unit, product?.price?.priceWithDiscount, product?.price?.unit)
        binding.oldPriceTextView1.text = resources.getString(R.string.price_with_unit, product?.price?.price, product?.price?.unit)
    }

    private fun initViewPager() {
        binding.imageViewPager.adapter = imageAdapter
        imageAdapter.setData(imageMap[product?.id] ?: emptyList())
        TabLayoutMediator(binding.tabLayout, binding.imageViewPager) { tab, position ->}.attach()
    }

    private fun initFavoriteButton() {
        setFavoriteButtonIcon()

        binding.addToFavoriteButton.setOnClickListener {
            product?.let {
                viewModel.toggleFavorite(product!!.id)
                product!!.isFavorite = !product!!.isFavorite
                setFavoriteButtonIcon()
            }
        }
    }

    private fun setFavoriteButtonIcon() {
        if (product?.isFavorite == true) {
            binding.addToFavoriteButton.setImageResource(R.drawable.ic_heart_filled)
        } else {
            binding.addToFavoriteButton.setImageResource(R.drawable.ic_heart_stroke)
        }
    }

    /**
     * Отображает бренд и описание, а также инициализирует TextView для их сворачивания/разворачивания
     */
    private fun initDescription() {
        binding.brandTextView.text = product?.title
        binding.descriptionTextView.text = product?.description
        binding.hideDescriptionTextView.setOnClickListener {
            if (isDescriptionVisible) {
                binding.brandLayout.visibility = View.GONE
                binding.descriptionTextView.visibility = View.GONE
                binding.hideDescriptionTextView.text = resources.getString(R.string.read_more)
            } else {
                binding.brandLayout.visibility = View.VISIBLE
                binding.descriptionTextView.visibility = View.VISIBLE
                binding.hideDescriptionTextView.text = resources.getString(R.string.hide)
            }

            isDescriptionVisible = !isDescriptionVisible
        }
    }

    /**
     * Отображает таблицу характеристик
     */
    private fun initInfoList() {
        val infoLinearLayout = binding.characteristicsTable
        val infoList = product?.info ?: emptyList()

        for (info in infoList) {
            val frameLayout = FrameLayout(requireContext())
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                32.dpToPx(requireContext())
            )

            frameLayout.layoutParams = params

            val infoNameTextView = TextView(requireContext())
            val nameParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            nameParams.gravity = Gravity.START or Gravity.BOTTOM
            nameParams.bottomMargin = 4.dpToPx(requireContext())
            infoNameTextView.layoutParams = nameParams

            val typeface = ResourcesCompat.getFont(requireContext(), R.font.sf_pro_display_regular)
            infoNameTextView.typeface = typeface
            infoNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)

            infoNameTextView.text = info.title

            val infoValueTextView = TextView(requireContext())
            val valueParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            valueParams.gravity = Gravity.END or Gravity.BOTTOM
            valueParams.bottomMargin = 4.dpToPx(requireContext())
            infoValueTextView.layoutParams = valueParams
            infoValueTextView.typeface = typeface
            infoValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)

            infoValueTextView.text = info.value

            val divider = View(requireContext())
            val dividerParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                1.dpToPx(requireContext())
            )
            dividerParams.gravity = Gravity.BOTTOM
            divider.background = ResourcesCompat.getDrawable(resources, R.color.light_gray, null)
            divider.layoutParams = dividerParams

            frameLayout.addView(infoNameTextView)
            frameLayout.addView(infoValueTextView)
            frameLayout.addView(divider)
            infoLinearLayout.addView(frameLayout)
        }
    }

    /**
     * Отображает ингредиенты (состав), а также инициализирует TextView для их сворачивания/разворачивания
     */
    private fun initIngredients() {
        binding.ingredientsTextView.text = product?.ingredients

        // Определяем количество строк в составе: если более 2, то сворачиваем до 2 строк,
        // отображаем многоочие и TextView для их сворачивания/разворачивания
        binding.ingredientsTextView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.ingredientsTextView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val layout = binding.ingredientsTextView.layout
                val lineCount = layout.lineCount

                if (lineCount > 2) {
                    binding.ingredientsTextView.maxLines = 2
                    binding.ingredientsTextView.ellipsize = TextUtils.TruncateAt.END
                    binding.hideIngredientsTextView.visibility = View.VISIBLE
                }
            }
        })

        // Обработчик нажатия на ссылку для сворачивания/разворачивания
        binding.hideIngredientsTextView.setOnClickListener {
            if (areIngredientsExpanded) {
                binding.ingredientsTextView.maxLines = 2
                binding.ingredientsTextView.ellipsize = TextUtils.TruncateAt.END
                binding.hideIngredientsTextView.text = resources.getString(R.string.read_more)
            } else {
                binding.ingredientsTextView.maxLines = Int.MAX_VALUE
                binding.ingredientsTextView.ellipsize = null
                binding.hideIngredientsTextView.text = resources.getString(R.string.hide)
            }

            areIngredientsExpanded = !areIngredientsExpanded
        }
    }

    private fun showError() {
        binding.errorLayout.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.INVISIBLE
        binding.dataLayout.visibility = View.INVISIBLE

        binding.retryButton.setOnClickListener {
            showLoading()
            productId?.let {
                viewModel.loadProduct(productId!!)
            }
        }
    }
}