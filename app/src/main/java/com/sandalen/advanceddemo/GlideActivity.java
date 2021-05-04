package com.sandalen.advanceddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sandalen.advanceddemo.glideSth.GlideApp;
/*Glide.with(this)//此方法返回RequestManager对象
        .load(url)//返回RequestBuilder对象
        .apply(options)//设置Reauestion
        .thumbnail(Glide.with(this)//加载缩略图，如果目标图片先于缩略图加载，则缩略图停止加载
        .load(Thumbnailurl))
        .error(Glide.with(this)//加载缩略图，如果目标图片先于缩略图加载，则缩略图停止加载
        .load(errorImageUrl))//加载失败时，执行新的加载
        .transition(GenericTransitionOptions.<Drawable>with(R.anim.fade))//占位图到主图的的动画效果，比较损耗性能
        .listener(new RequestListener<Drawable>() {
        //监听图片加载
        返回fals表示没有被处理，会向下传递。
        返回true表示已经被处理，不会向下传递
@Override
public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
        //图片加载失败
        return false;
        }

@Override
public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        //图片加载成功
        return false;
        }
        })
        .into(imageView1);//设置目标容器*/

 /* RequestOptions options = new RequestOptions()
          .placeholder(R.mipmap.ic_launcher)//加载网络图片显示的占位图
          .error(R.mipmap.ic_launcher)//请求出错的时候显示的图片
          .circleCrop();//将图片变为圆形
          .timeout（5*1000）//图片加载超时时间
          .override（width，height）；*/


public class GlideActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        button = findViewById(R.id.glide_btn);
        imageView = findViewById(R.id.glide_iv);

        imageView.setImageResource(R.mipmap.loading);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPicByGenerate("http://res.lgdsunday.club/big_img.jpg");
            }
        });

    }

    private void loadingPicByGlide(String url) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.loader_error)
                .circleCrop();
        Glide.with(this).load(url).apply(requestOptions).into(imageView);
    }

    private void loadPicByGenerate(String url){
        GlideApp.with(this).load(url).injectOptions().into(imageView);
    }
}