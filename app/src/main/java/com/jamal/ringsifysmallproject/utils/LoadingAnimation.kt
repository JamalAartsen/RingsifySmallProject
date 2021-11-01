package com.jamal.ringsifysmallproject.utils

import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi

class LoadingAnimation {
    companion object {
        @RequiresApi(Build.VERSION_CODES.M)
        fun loadingRingAnimation(drawableRingLoading: AnimatedVectorDrawable, avd: AnimatedVectorDrawable) {
            drawableRingLoading.registerAnimationCallback(object : Animatable2.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    super.onAnimationEnd(drawable)

                    avd.start()
                }
            })
        }
    }
}