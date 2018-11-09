package example.chart.disappear

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.scichart.charting.visuals.SciChartSurface
import com.scichart.extensions.builders.SciChartBuilder
import example.chart.disappear.viewpager.PagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    SciChartSurface.setRuntimeLicenseKeyFromResource(this, R.raw.license)
    SciChartBuilder.init(this)

    vertical_view_pager.adapter = PagerAdapter(supportFragmentManager)
  }
}
