package example.duyal.mete.tablayoutinterpolation

import android.animation.ArgbEvaluator
import android.content.Context
import android.os.Build
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.widget.TextView

import android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING
import android.support.v4.view.ViewPager.SCROLL_STATE_IDLE

class TabLayoutInterpolationListener(tabLayout: TabLayout, context: Context) : ViewPager.OnPageChangeListener {
    //region Fields
    private var mTabLayout = tabLayout
    private var lastSumPosAndPosOffset = 0F
    private var mArgbEvaluator = ArgbEvaluator()
    private var mTabColor = 0
    private var mSelectedColor = ContextCompat.getColor(context, R.color.tab_active)
    private var mUnselectedColor = ContextCompat.getColor(context, R.color.tab_passive)
    private var mCurrentTextView: TextView? = null
    private var mNextTextView: TextView? = null
    private var mIsClicked = true
    private var mCurrentIndex = 0
    private var mNextIndex = 0
    private var mContext = context
    // CurrentIndex and nextIndex are wrong at first initialize.
    // mIsInitialized field added to get correct reference of current and next text view.
    private var mIsInitialized = false
    //endregion


    init {
        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mNextIndex = tab.position
                mNextTextView = getTextViewReference(mNextIndex)
                mCurrentTextView = getTextViewReference(mCurrentIndex)
                mCurrentTextView!!.setTextColor(mUnselectedColor)
                mNextTextView!!.setTextColor(mSelectedColor)
                checkInitialize()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                mCurrentIndex = tab.position
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }

    //region Listener
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (position == 0) {
            changeCurrentTabTextColor(position, positionOffset, mSelectedColor, mUnselectedColor)
            checkInitialize()
        }

        if (!mIsClicked) {
            if (position + positionOffset > lastSumPosAndPosOffset) {
                changeCurrentTabTextColor(position, positionOffset, mSelectedColor, mUnselectedColor)

                if (position != mTabLayout.tabCount - 1) {
                    changeNextTabTextColor(position + 1, 1 - positionOffset, mSelectedColor, mUnselectedColor)
                }
            } else {
                if (position != mTabLayout.tabCount - 1) {
                    changeCurrentTabTextColor(position + 1, 1 - positionOffset, mSelectedColor, mUnselectedColor)
                }

                changeNextTabTextColor(position, positionOffset, mSelectedColor, mUnselectedColor)
            }

            lastSumPosAndPosOffset = positionOffset + position
        }
    }

    override fun onPageSelected(position: Int) {}

    override fun onPageScrollStateChanged(state: Int) {
        if (state == SCROLL_STATE_DRAGGING) {
            mIsClicked = false
        } else if (state == SCROLL_STATE_IDLE) {
            mCurrentTextView = null
            mNextTextView = null
            mIsClicked = true
        }
    }

    //endregion

    //region Methods

    private fun checkInitialize() {
        if (!mIsInitialized) {
            mIsInitialized = true
        }
    }

    private fun changeCurrentTabTextColor(position: Int, positionOffset: Float, firstColor: Int, secondColor: Int) {
        if (mCurrentTextView == null || mIsInitialized)
            mCurrentTextView = getTextViewReference(position)

        mTabColor = mArgbEvaluator.evaluate(positionOffset, firstColor, secondColor) as Int
        mCurrentTextView!!.setTextColor(mTabColor)
    }

    private fun changeNextTabTextColor(position: Int, positionOffset: Float, firstColor: Int, secondColor: Int) {
        if (mNextTextView == null || mIsInitialized)
            mNextTextView = getTextViewReference(position)

        mTabColor = mArgbEvaluator.evaluate(positionOffset, firstColor, secondColor) as Int
        mNextTextView!!.setTextColor(mTabColor)
    }

    private fun getTextViewReference(position: Int): TextView {
        return if (mTabLayout.getTabAt(position) == null || mTabLayout.getTabAt(position)!!.customView == null)
            TextView(mContext)
        else
            mTabLayout.getTabAt(position)!!.customView!!.findViewById(R.id.tabTxt)
    }

    //endregion
}