package com.example.onlinestoretest.utils

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import com.example.onlinestoretest.R

class CenteredTitleToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    var titleTextView: TextView? = null
    var backButton: ImageButton? = null
    var shareButton: ImageButton? = null

    init {
        initView()
    }

    private fun initView() {
        val backButtonLayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.START or Gravity.BOTTOM
            marginStart = 21.dpToPx(context)
            bottomMargin = (-2).dpToPx(context)
        }

        backButton = ImageButton(context).apply {
            setImageResource(R.drawable.ic_back_arrow)
            setBackgroundColor(Color.TRANSPARENT)
            layoutParams = backButtonLayoutParams
            visibility = View.GONE
        }

        addView(backButton)

        val shareButtonLayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.END or Gravity.BOTTOM
            marginEnd = 21.dpToPx(context)
            bottomMargin = (-2).dpToPx(context)
        }

        shareButton = ImageButton(context).apply {
            setImageResource(R.drawable.ic_share)
            setBackgroundColor(Color.TRANSPARENT)
            layoutParams = shareButtonLayoutParams
            visibility = View.GONE
        }

        addView(shareButton)

        titleTextView = TextView(context).apply {
            typeface = ResourcesCompat.getFont(context, R.font.sf_pro_display_medium)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER_VERTICAL or Gravity.START
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
                marginStart = 21.dpToPx(context)
                marginEnd = 21.dpToPx(context)
                bottomMargin = 21.dpToPx(context)
            }
        }
        addView(titleTextView)
    }

    override fun setTitle(title: CharSequence?) {
        titleTextView?.text = title
        ViewCompat.requestApplyInsets(this)
    }
}
