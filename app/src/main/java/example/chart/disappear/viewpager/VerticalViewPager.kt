package example.chart.disappear.viewpager

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller

class VerticalViewPager : ViewPager {

  constructor(context: Context) : super(context) {
    init()
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    init()
  }

  private fun init() {
    setPageTransformer(true, VerticalPageTransformer())
    overScrollMode = View.OVER_SCROLL_NEVER
    try {
      val mScroller = ViewPager::class.java.getDeclaredField("mScroller")
      mScroller.isAccessible = true
      mScroller.set(this, ViewPagerScroller(this.context))
    } catch (t: Throwable) {
      t.printStackTrace()
    }
  }

  private inner class VerticalPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {
      if (position < -1) { // [-Infinity,-1)
        // This page is way off-screen to the left.
        view.alpha = 0F
      } else if (position <= 1) { // [-1,1]
        // This is for view pager fade animation.
        view.alpha = 1F - Math.abs(position)

        // Counteract the default slide transition
        view.translationX = view.width * -position

        //set Y position to swipe in from top
        val yPosition = position * view.height
        view.translationY = yPosition
      } else { // (1,+Infinity]
        // This page is way off-screen to the right.
        view.alpha = 0F
      }
    }
  }

  /**
   * Swaps the X and Y coordinates of your touch event.
   */
  private fun swapXY(ev: MotionEvent): MotionEvent {
    val width = width.toFloat()
    val height = height.toFloat()

    val newX = ev.y / height * width
    val newY = ev.x / width * height

    ev.setLocation(newX, newY)

    return ev
  }

  override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
    val intercepted = super.onInterceptTouchEvent(swapXY(ev))
    swapXY(ev)
    return intercepted
  }

  override fun onTouchEvent(ev: MotionEvent): Boolean {
    return super.onTouchEvent(swapXY(ev))
  }
}

class ViewPagerScroller constructor(context: Context) : Scroller(context) {

  private val scrollDuration = 1000

  override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
    super.startScroll(startX, startY, dx, dy, scrollDuration)
  }

  override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
    super.startScroll(startX, startY, dx, dy, scrollDuration)
  }
}