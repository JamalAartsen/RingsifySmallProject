package com.jamal.ringsifysmallproject

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImage {
    companion object {
        fun imageHolder(context: Context, image: Any, characterImage: ImageView) =
            Glide.with(context)
                .load(image)
                .fitCenter()
                .crossFade()
                .error(R.drawable.baseline_wifi_tethering_error_rounded_black_24dp)
                .into(characterImage)
    }
}