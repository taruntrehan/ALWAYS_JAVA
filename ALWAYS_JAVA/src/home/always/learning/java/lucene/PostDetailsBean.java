/**
 * 
 */
package home.always.learning.java.lucene;

/**
 * @author ttrehan
 *
 */
public class PostDetailsBean {
	
	private String reportTitle;
	private String reportLink;
	private String reportMetrics;
	private String reportDescription;
	
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	public String getReportLink() {
		return reportLink;
	}
	public void setReportLink(String reportLink) {
		this.reportLink = reportLink;
	}
	public String getReportMetrics() {
		return reportMetrics;
	}
	public void setReportMetrics(String reportMetrics) {
		this.reportMetrics = reportMetrics;
	}
	public String getReportDescription() {
		return reportDescription;
	}
	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
	}
	@Override
	public String toString() {
		return "ReportMetricDetails [reportTitle=" + reportTitle + ", reportLink=" + reportLink + ", reportMetrics="
				+ reportMetrics + ", reportDescription=" + reportDescription + "]";
	}
}
