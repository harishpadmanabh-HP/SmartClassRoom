package com.harish.smartclassroom.ui.walkthrough

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager
import com.harish.smartclassroom.R
import com.harish.smartclassroom.WalkthroughTitleAdapter
import com.harish.smartclassroom.adapters.WalkthroughAdapter
import com.harish.smartclassroom.ui.Login
import com.harish.smartclassroom.util.Utils
import kotlinx.android.synthetic.main.activity_walkthrough.*
import kotlinx.coroutines.cancelChildren
import java.util.*
import kotlin.collections.ArrayList

class Walkthrough : AppCompatActivity() {

    val onboardItems:ArrayList<AppOnboardItem> by lazy {
        getOnbordingResponse()
    }
    private val adapter by lazy {
        WalkthroughAdapter(onboardItems)
    }
    private val titleAdapter by lazy {
        WalkthroughTitleAdapter(onboardItems)
    }
    private var currentIndex=0
    private var isOnPause=false
    val timer = Timer()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walkthrough)
        initViews()
    }

    fun getOnbordingResponse():ArrayList<AppOnboardItem>{
        val items=ArrayList<AppOnboardItem>()
        items.add(AppOnboardItem(R.drawable.art_1,"Your studies made simpler !","Get all your study tasks in one place."))
        items.add(AppOnboardItem(R.drawable.art_2,"Know Your Assignments","Get all details about your assignments and submit through the app"))
        items.add(AppOnboardItem(R.drawable.art_3,"Get your notes on touch","Get all your study materials uploaded by your faculties and study with in the app"))
        items.add(AppOnboardItem(R.drawable.art_4,"Complete your Assessments","Do all your tests through the app and get your analytics"))
        //items.add(AppOnboardItem(R.drawable.art_5,getString(R.string.onboarding_leaderboard_title),getString(R.string.onboarding_analyze_results)))
        return items
    }
    private fun initViews() {

        vp_feat.adapter=adapter
        vp_feat.clipToPadding = false
        tl_indicator.setupWithViewPager(vp_feat)
        btn_get_started.setOnClickListener { onGetStarted() }
        vp_feat.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                //renderTitleAndDescription(position)
                vp_titles.setCurrentItem(position)
            }

        })

        vp_feat.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_MOVE){
                releaseTimer()

            }
            false
        }
        vp_feat.setCurrentItem(0)
        setScrollerDelay(vp_feat)
        setTabItemSpacing()
        //renderTitleAndDescription(0)

        setupTitlesViewPager()

        initTimer()
        //EventBus.getDefault().post(SyncFirebaseRemoteConfigEvent(true, false))
        //GeneralRepository(application).clearAllTableDataExceptDownloadTable()
        // super.syncFirebaseConfigDefaults(this, true, true)
        btn_get_started.isClickable=true
    }

    private fun onGetStarted() {
        startActivity(Intent(this,Login::class.java))
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupTitlesViewPager() {

        vp_titles.adapter=titleAdapter
        vp_titles.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) { vp_feat.setCurrentItem(position) }

        })

        setScrollerDelay(vp_titles)
        vp_titles.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_MOVE){
                releaseTimer()
            }
            false
        }
    }

    private fun setTabItemSpacing() {
        try {
            val betweenSpace = Utils.convertDpToPx(this,6).toInt()

            val slidingTabStrip = tl_indicator.getChildAt(0) as ViewGroup

            for (i in 0 until slidingTabStrip.childCount - 1) {
                val v: View = slidingTabStrip.getChildAt(i)
                val params = v.getLayoutParams() as ViewGroup.MarginLayoutParams
                params.rightMargin = betweenSpace
            }
        }catch (e:Exception){

        }

    }

    private fun setScrollerDelay(viewpager: ViewPager) {
        try {
            val mScroller = ViewPager::class.java.getDeclaredField("mScroller")
            mScroller.isAccessible = true
            mScroller[viewpager] = CustomPagerScroller(this, 1500)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    private fun initTimer() {
        try {
            val handler = Handler()

            val update = Runnable {

                vp_feat.setCurrentItem(currentIndex, true)
                if (currentIndex >= onboardItems.size-1) {
                    currentIndex = 0
                } else {
                    ++currentIndex
                }
            }

            timer.schedule(object : TimerTask() {
                override fun run() {
                    if (!isOnPause) {
                        handler.post(update)
                    }
                }
            }, 0, 3500)
        }catch (e:java.lang.Exception){

        }
    }

    override fun onDestroy() {
        releaseTimer()
        //coroutineContext.cancelChildren()
        super.onDestroy()
    }
    private fun releaseTimer() {
        try {
            timer.cancel()
            timer.purge()
        }catch (e:java.lang.Exception){

        }
    }

    override fun onPause() {
        isOnPause = true
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        isOnPause = false
    }

}
data class AppOnboardItem(val imageId:Int,val title:String,val desc:String)

class CustomPagerScroller(context: Context, var mDuration: Int) : Scroller(context) {

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }
}
