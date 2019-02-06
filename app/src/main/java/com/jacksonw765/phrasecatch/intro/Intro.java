package com.jacksonw765.phrasecatch.intro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.jacksonw765.phrasecatch.MainActivity;


public class Intro extends AppIntro {
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intro_One introOne = new Intro_One();
        Intro_Two introTwo = new Intro_Two();
        Intro_Three introThree = new Intro_Three();

        addSlide(introOne);
        addSlide(introTwo);
        addSlide(introThree);

        //SliderPage sliderPage = new SliderPage();
        //sliderPage.setTitle("Welcome to PhraseCatch");
        //sliderPage.setBgColor(R.color.colorPrimary);
        showSkipButton(true);
        setProgressButtonEnabled(true);
        //sliderPage.setImageDrawable(R.drawable.logo_main);
        //addSlide(AppIntroFragment.newInstance(sliderPage));
        setDepthAnimation();

        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
