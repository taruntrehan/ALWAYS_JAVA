/**
 * 
 */
package home.always.learning.java.lucene;

/**
 * @author ttrehan
 *
 */
public class TagDetailsBean {
	
	private String metricName;
	private String metricDescription;
	public String getMetricName() {
		return metricName;
	}
	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}
	public String getMetricDescription() {
		return metricDescription;
	}
	public void setMetricDescription(String metricDescription) {
		this.metricDescription = metricDescription;
	}
	@Override
	public String toString() {
		return "MetricDetailsBean [metricName=" + metricName + ", metricDescription=" + metricDescription + "]";
	}
}

