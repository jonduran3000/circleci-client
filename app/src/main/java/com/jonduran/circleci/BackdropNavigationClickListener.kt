package com.jonduran.circleci

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.animation.Interpolator
import android.widget.ImageView

class BackdropNavigationClickListener @JvmOverloads internal constructor(
    private val activity: Activity,
    private val sheet: View,
    private val interpolator: Interpolator? = null,
    private val openIcon: Drawable? = null,
    private val closeIcon: Drawable? = null
) : View.OnClickListener {
    private val animatorSet = AnimatorSet()
    private val height: Int
    private var isShown: Boolean = false

    init {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels
    }

    override fun onClick(v: View?) {
        isShown = !isShown

        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()

        updateIcon(v)

        val translateY = height - activity.resources
            .getDimensionPixelSize(R.dimen.backdrop_reveal_height)
        val value = (if (isShown) translateY else 0).toFloat()

        val animator = ObjectAnimator.ofFloat(sheet, "translationY", value)
        animator.duration = 500
        if (interpolator != null) {
            animator.interpolator = interpolator
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                Log.d("Backdrop", "onAnimationRepeat")
            }

            override fun onAnimationEnd(animation: Animator?) {
                Log.d("Backdrop", "onAnimationEnd")
            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.d("Backdrop", "onAnimationCancel")
            }

            override fun onAnimationStart(animation: Animator?) {
                Log.d("Backdrop", "onAnimationStart")
            }
        })
        animatorSet.play(animator)
        animator.start()
    }

    private fun updateIcon(view: View?) {
        if (openIcon != null && closeIcon != null) {
            require(view is ImageView) { "updateIcon() must be called on an ImageView" }
            val icon = if (isShown) closeIcon else openIcon
            view.setImageDrawable(icon)
        }
    }
}