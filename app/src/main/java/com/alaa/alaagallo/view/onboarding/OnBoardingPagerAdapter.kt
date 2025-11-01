package com.alaa.alaagallo.view.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.alaa.alaagallo.view.splash.BlankFragment
import com.alaa.alaagallo.view.onboarding.onboardingFragments.FirstOnboardingFragment
import com.alaa.alaagallo.view.onboarding.onboardingFragments.SecondOnBoardingFragment
import com.alaa.alaagallo.view.onboarding.onboardingFragments.ThirdOnBoardingFragment

@Suppress("DEPRECATION")
class OnBoardingPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm , BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)  {

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FirstOnboardingFragment()
            1 -> SecondOnBoardingFragment()
            2 -> ThirdOnBoardingFragment()
            else -> BlankFragment()
        }
    }
}