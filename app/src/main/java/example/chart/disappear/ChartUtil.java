package example.chart.disappear;

import com.scichart.charting.model.dataSeries.OhlcDataSeries;

import java.util.Date;

public class ChartUtil {

  public static OhlcDataSeries<Date, Double> createDataSeries() {
    return new OhlcDataSeries<>(Date.class, Double.class);
  }
}
