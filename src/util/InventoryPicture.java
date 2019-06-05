package util;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.VerticalAlignment;

import model.Phone;

public class InventoryPicture {
	// 设置柱状图的柱子顶部是否显示数据
		private static boolean baseItemLabelsVisible = true;

		private List<Phone> inventoryDate;

		public InventoryPicture(List<Phone> date) {
			this.inventoryDate = date;
		}

		/**
		 * 创建数据集合
		 * 
		 * @return dataSet
		 */
		public CategoryDataset createDataSet() {
			// 实例化DefaultCategoryDataset对象
			DefaultCategoryDataset datasSet = new DefaultCategoryDataset();
			// 添加数据
			for (Phone item : inventoryDate)
				datasSet.addValue(item.getInventory(), item.getPhoneName(), item.getBandType());
			return datasSet;
		}

		public void create(String path) {
			StandardChartTheme standardChartTheme = new StandardChartTheme("CN"); // 创建主题样式
			standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20)); // 设置标题字体
			standardChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15)); // 设置图例的字体
			standardChartTheme.setLargeFont(new Font("宋体", Font.PLAIN, 15)); // 设置轴向的字体
			ChartFactory.setChartTheme(standardChartTheme);// 设置主题样式
			// 生成的柱状图
			JFreeChart chart = ChartFactory.createBarChart3D("手机库存统计图表", "品牌", // X轴的标签
					"库存数量", // Y轴的标签
					createDataSet(), // 图标显示的数据集合
					PlotOrientation.VERTICAL, // 图像的显示形式（水平或者垂直）
					true, // 是否显示子标题
					true, // 是否生成提示的标签
					false); // 是否生成URL链接

			/*
			 * chart.getTitle().setFont(new Font("隶书", Font.BOLD, 25)); // 设置标题字体
			 * chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12)); // 设置图例类别字体
			 */
			chart.setBorderVisible(true); // 设置显示边框

			// 小标题
			TextTitle subTitle = new TextTitle("品牌数据图表");// 实例化TextTitle对象
			subTitle.setVerticalAlignment(VerticalAlignment.BOTTOM); // 设置居中显示
			chart.addSubtitle(subTitle);// 添加子标题

			CategoryPlot plot = chart.getCategoryPlot(); // 获取绘图区对象
			plot.setForegroundAlpha(0.8F);// 设置绘图区前景色透明度
			plot.setBackgroundAlpha(0.5F);// 设置绘图区背景色透明度
			// plot.setBackgroundImage(image);//设置绘图区背景图片
			CategoryAxis categoryAxis = plot.getDomainAxis();// 获取坐标轴对象
			categoryAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));// 设置坐标轴标题字体
			categoryAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));// 设置坐标轴尺值字体
			categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);// 设置坐标轴标题旋转角度
			ValueAxis valueAxis = plot.getRangeAxis();// 设置数据轴对象
			valueAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
			BarRenderer brender = new BarRenderer();
			brender.setIncludeBaseInRange(true);
			brender.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			brender.setBaseItemLabelsVisible(isBaseItemLabelsVisible());
			brender.setItemMargin(0.32);// 设置柱间的间距
			plot.setRenderer(brender);// 设置图片渲染对象
			/*
			 * 处理图形上的乱码
			 */

			// 处理主标题的乱码
			chart.getTitle().setFont(new Font("黑体", Font.BOLD, 18));

			// 获取图表区域对象
			CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();

			// 设置图的背景为白色
			categoryPlot.setBackgroundPaint(Color.WHITE);
			// 设置背景虚线的颜色
			categoryPlot.setRangeGridlinePaint(Color.decode("#B6A2DE"));
			// 去掉柱状图的背景边框，使边框不可见
			categoryPlot.setOutlineVisible(true);

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
