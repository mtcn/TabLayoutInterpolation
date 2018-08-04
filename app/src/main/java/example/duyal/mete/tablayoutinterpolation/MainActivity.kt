package example.duyal.mete.tablayoutinterpolation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout!!.setupWithViewPager(viewPager)
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.addOnPageChangeListener(TabLayoutInterpolationListener(tabLayout, this))
        for (i in 0..14) {
            val mainView = LayoutInflater.from(this).inflate(R.layout.custom_tab_view, null)
            val tabTextView = mainView.findViewById<TextView>(R.id.tabTxt)
            tabTextView.text = "TabExample $i"
            if (tabLayout!!.getTabAt(i) != null)
                tabLayout!!.getTabAt(i)!!.customView = tabTextView

        }


    }
}
