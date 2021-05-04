package com.sandalen.advanceddemo.glideSth;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.sandalen.advanceddemo.R;

@GlideExtension
public class MyGlideExtension {
    private MyGlideExtension(){

    }

    @GlideOption
    public static BaseRequestOptions<?> injectOptions(BaseRequestOptions<?> options){
        return options.placeholder(R.mipmap.loading)
                .error(R.mipmap.loader_error)
                .circleCrop();
    }
}
