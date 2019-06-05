package util;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import model.RK;

public class RKPicture {
	// 设置柱状图的柱子顶部是否显示数据
	private static boolean baseItemLabelsVisible = true;
	
	private List<RK> rkDate;

	public RKPicture(List<RK> date) {
		this.rkDate = date;
	}

	public void create(String path,String time) {
		// 柱状图数据
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		// 添加数据
		for (RK item : rkDate)
			dataset.addValue(item.getTotalNumber(), "", item.getBandTypeName());

		// 生成的柱状图
		JFreeChart chart = ChartFactory.createBarChart3D(time+"手机入库统计图表", "品牌", // X轴的标签
				"入库数量", // Y轴的标签
				dataset, // 图标显示的数据集合
				PlotOrientation.VERTICAL, // 图像的显示形式（水平或者垂直）
				true, // 是否显示子标题
				true, // 是否生成提示的标签
				false); // 是否生成URL链接

		/*
		 * 处理图形上的乱码
		 */

		// 处理主标题的乱码
		chart.getTitle().setFont(new Font("黑体", Font.BOLD, 18));

		// 获取图表区域对象
		CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
		// 自定义柱状图中柱子的样式
	    BarRenderer brender = new BarRenderer();
	    brender.setSeriesPaint(1, Color.decode("#C0504D")); // 给series1 Bar
	    brender.setSeriesPaint(0, Color.decode("#E46C0A")); // 给series2 Bar
	    brender.setSeriesPaint(2, Color.decode("#4F81BD")); // 给series3 Bar
	    brender.setSeriesPaint(3, Color.decode("#00B050")); // 给series4 Bar
	    brender.setSeriesPaint(4, Color.decode("#7030A0")); // 给series5 Bar
	    brender.setSeriesPaint(5, Color.decode("#00BF00")); // 给series6 Bar
	 // 设置柱状图的顶端显示数字
	    brender.setIncludeBaseInRange(true);
	    brender.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
	    brender.setBaseItemLabelsVisible(isBaseItemLabelsVisible());
	    // 设置图的背景为白色
	    categoryPlot.setBackgroundPaint(Color.WHITE);
	    // 设置背景虚线的颜色
	    categoryPlot.setRangeGridlinePaint(Color.decode("#B6A2DE"));
	    // 去掉柱状图的背景边框，使边框不可见
	    categoryPlot.setOutlineVisible(true);

	    categoryPlot.setRenderer(brender);

		// 获取X轴的对象
		CategoryAxis3D categoryAxis3D = (CategoryAxis3D) categoryPlot.getDomainAxis();

		// 获取Y轴的对象
		NumberAxis3D numberAxis3D = (NumberAxis3D) categoryPlot.getRangeAxis();

		// 处理X轴上的乱码
		categoryAxis3D.setTickLabelFont(new Font("黑体", Font.BOLD, 10));

		// 处理X轴外的乱码
		categoryAxis3D.setLabelFont(new Font("黑体", Font.BOLD, 10));

		// 处理Y轴上的乱码
		numberAxis3D.setTickLabelFont(new Font("黑体", Font.BOLD, 10));

		// 处理Y轴外的乱码
		numberAxis3D.setLabelFont(new Font("黑体", Font.BOLD, 10));

		// 自定义Y轴上显示的刻度，以10作为1格
		numberAxis3D.setAutoTickUnitSelection(false);
		NumberTickUnit unit = new NumberTickUnit(1);
		numberAxis3D.setTickUnit(unit);

		// 柱状图和纵轴紧靠
		categoryAxis3D.setLowerMargin(0.0);

		categoryAxis3D.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);

		File file = new File(path);
		try {
			ChartUtilities.saveChartAsJPEG(file, chart, 800, 600);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static boolean isBaseItemLabelsVisible() {
	    return baseItemLabelsVisible;
	}

}
