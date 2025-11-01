package com.alaa.alaagallo.view.onboarding

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.alaa.alaagallo.R
import com.alaa.alaagallo.base.INavigation
import com.alaa.alaagallo.base.helpers.LaunchingActivity
import com.alaa.alaagallo.base.helpers.extensions.openActivity
import com.alaa.alaagallo.view.registration.LoginActivity


@Suppress("DEPRECATION")
class OnboardingActivity : AppCompatActivity(), INavigation {

    private var currentFragment = 0

    private val onboardingViewpager:ViewPager get() = findViewById(R.id.onboarding_viewpager)
    private val onBoardingImage: ConstraintLayout get() = findViewById(R.id.onboarding_image)
    private val next: Button get() = findViewById(R.id.btn_next)
    private val start : Button get() =  findViewById(R.id.btn_start)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_onboarding)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*
       Application is opened -- disable first launch
        */
        LaunchingActivity.getInstance(applicationContext).setIsFirstLaunchToFalse()
        onboardingViewpager.adapter = OnBoardingPagerAdapter(supportFragmentManager)

        handleViewPager()
        handleNavigation()
    }

   override  fun handleNavigation() {
        next.setOnClickListener {
            currentFragment++
            onboardingViewpager.setCurrentItem(currentFragment, true)
        }

        start.setOnClickListener {
            //openActivity(this,SignUpActivity::class.java )
            openActivity(this,LoginActivity::class.java )
            finishAffinity()
        }

    }

  override  fun handleViewPager() {
        onboardingViewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                currentFragment = position
                handleNavView(currentFragment)
            }

            override fun onPageSelected(position: Int) {
                updateBackground(position)

            }
        })
    }
    private fun updateBackground(position: Int) {
        when (position) {
            0 -> onBoardingImage.setBackgroundResource(R.drawable.onboarding_image)
            1 -> onBoardingImage.setBackgroundResource(R.drawable.img_on_board_2)
            else -> onBoardingImage.setBackgroundResource(R.drawable.onboarding_image_three)
        }
    }
    override fun handleNavView(id: Int) {
        when (currentFragment) {
            0 -> {
                start.visibility = View.GONE
                next.visibility = View.VISIBLE
            }
            1 -> {
                start.visibility = View.GONE
                next.visibility = View.VISIBLE
            }
            else -> {
                start.visibility = View.VISIBLE
                next.visibility = View.GONE
            }
        }
    }
}