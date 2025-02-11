package com.botaoap.appnews.customview

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.botaoap.appnews.R
import com.botaoap.appnews.databinding.ViewDebtSupportFooterBinding
import com.botaoap.appnews.ui.extension.collapse
import com.botaoap.appnews.ui.extension.expand
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class DebtSupportFooter(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(context, attrs) {

    private val binding =
        ViewDebtSupportFooterBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.DebtSupportFooterDetail,
            0,
            0
        ).apply {
            try {
                initDefaultView(
                    colorBackground = getColor(
                        R.styleable.DebtSupportFooterDetail_backgroundColor,
                        ContextCompat.getColor(context, R.color.white)
                    ),
                    outlineStrokeColor = getColor(
                        R.styleable.DebtSupportFooterDetail_outlineStrokeColor,
                        ContextCompat.getColor(context, R.color.dark_low_16)
                    ),
                )
                initViewXml(
                    data = DebtSupportModel(
                        title = getString(R.styleable.DebtSupportFooterDetail_title) ?: "",
                        description = getString(R.styleable.DebtSupportFooterDetail_description)
                            ?: "",
                        progressPercentage = getInt(
                            R.styleable.DebtSupportFooterDetail_progressBarStyle,
                            0
                        ),
                        isOpen = false,
                        sectionHide = SectionHideModel(
                            title = "",
                            description = ""
                        )
                    )
                )
            } finally {
                recycle()
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initAnimation() {
        binding.groupDebtSupportView.visibility = VISIBLE
        measure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.UNSPECIFIED)
        )
        visibility = GONE
        val targetHeight = measuredHeight

        val step = targetHeight / (100 / 16)

        GlobalScope.launch(Dispatchers.Main) {
            var initialHeight = 0
            visibility = VISIBLE
            while (initialHeight < targetHeight) {
                initialHeight += step
                if (initialHeight > targetHeight) {
                    initialHeight = targetHeight
                }
                layoutParams.height = initialHeight
                requestLayout()
                delay(16)
            }
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            requestLayout()
        }
    }

    fun initView(data: DebtSupportModel) {
        setBackgroundColor(ContextCompat.getColor(context, R.color.athens_gray))

        initAnimation()

        clickOnFooter()
        setupSectionHide(data.sectionHide, data.isOpen)
        setupHeader(data.title)
        setupDescription(data.description)
        setupProgressBar(data.progressPercentage)
    }

    private fun initViewXml(data: DebtSupportModel) {
        clickOnFooter()
        setupSectionHide(data.sectionHide, data.isOpen)
        setupHeader(data.title)
        setupDescription(data.description)
        setupProgressBar(data.progressPercentage)
    }

    private fun initDefaultView(colorBackground: Int, outlineStrokeColor: Int) {
        this.setBackgroundColor(colorBackground)
        binding.mcvDebtSupportFooterCard.apply {
            setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
            strokeColor = outlineStrokeColor
            strokeWidth = 2
        }
    }

    private fun clickOnFooter() {
        binding.clDebtSupportHeaderContainer.setOnClickListener {
            binding.clDebtSupportFooterSectionHideContainer.apply {
                if (isVisible) {
                    collapse()
                    setupArrow(false)
                } else {
                    expand()
                    setupArrow(true)
                }
            }
        }
    }

    private fun setupHeader(data: String) {
        binding.tvDebtSupportFooterTitle.text = data
    }

    private fun setupArrow(isUp: Boolean) {
        binding.stbDebtSupportArrow.apply {
            if (isUp) {
                animate().rotation(-180f)
            } else {
                animate().rotation(0f)
            }
        }
    }

    private fun setupDescription(data: String) {
        binding.tvDebtSupportFooterDescription.text = data.parseAsHtml()
    }

    private fun setupProgressBar(data: Int) {
        binding.lpiDebtSupportFooterProgress.apply {
            max = 100 * 1000
            interpolator = FastOutSlowInInterpolator()
            ObjectAnimator.ofInt(this, "progress", data * 1000)
                .setDuration(2000)
                .start()
        }
    }

    private fun setupSectionHide(data: SectionHideModel, isOpen: Boolean) {
        binding.clDebtSupportFooterSectionHideContainer.let {
            if (isOpen) {
                it.expand()
                setupArrow(true)
            } else {
                it.collapse()
                setupArrow(false)
            }
        }
        binding.tvDebtSupportFooterSectionHideTitle.text = data.title
        binding.tvDebtSupportFooterSectionHideDescription.text = data.description.parseAsHtml()
    }
}