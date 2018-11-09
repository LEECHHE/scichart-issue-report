package example.chart.disappear

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import com.scichart.charting.visuals.axes.AutoRange
import com.scichart.charting.visuals.axes.IAxis
import com.scichart.charting.visuals.renderableSeries.FastCandlestickRenderableSeries
import com.scichart.core.framework.UpdateSuspender
import com.scichart.extensions.builders.SciChartBuilder
import example.chart.disappear.data.DataManager
import kotlinx.android.synthetic.main.fragment_first_paged.*
import java.util.*

class FirstPagedFragment: Fragment() {

  protected val sciChartBuilder = SciChartBuilder.instance()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_first_paged, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initExample()
  }

  protected fun initExample() {
    val priceSeries = DataManager.getInstance().getPriceDataIndu(activity)
    val size = priceSeries.size.toDouble()

    val xAxis = sciChartBuilder.newCategoryDateAxis()
      .withVisibleRange(size - 30, size)
      .withGrowBy(0.0, 0.1)
      .build()
    val yAxis = sciChartBuilder.newNumericAxis()
      .withGrowBy(0.0, 0.1)
      .withAutoRangeMode(AutoRange.Always)
      .build()

    val dataSeries = ChartUtil.createDataSeries()
    dataSeries.append(
      priceSeries.getDateData(),
      priceSeries.getOpenData(),
      priceSeries.getHighData(),
      priceSeries.getLowData(),
      priceSeries.getCloseData()
    )

    val rSeries = sciChartBuilder.newCandlestickSeries()
      .withStrokeUp(-0xff5600)
      .withFillUpColor(-0x77ff5600)
      .withStrokeDown(-0x10000)
      .withFillDownColor(-0x77010000)
      .withDataSeries(dataSeries)
      .build()

    UpdateSuspender.using(surface) {
      Collections.addAll<IAxis>(surface.getXAxes(), xAxis)
      Collections.addAll<IAxis>(surface.getYAxes(), yAxis)
      Collections.addAll<FastCandlestickRenderableSeries>(surface.getRenderableSeries(), rSeries)
      Collections.addAll(
        surface.getChartModifiers(), sciChartBuilder.newModifierGroupWithDefaultModifiers().build())

      sciChartBuilder.newAnimator(rSeries).withWaveTransformation().withInterpolator(DecelerateInterpolator())
        .withDuration(3000).withStartDelay(350).start()
    }
  }
}