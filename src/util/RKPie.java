package util;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import bll.RKDao;
import model.RK;

public class RKPie {
	
	private List<RK> rkDate;

	public RKPie(List<RK> date) {
		this.rkDate = date;
	}
	
	public void createPie(String path) {
		
		// 建立图表
		JFreeChart mChart = ChartFactory.createPieChart("手机入库品牌占比", GetDataset(), true, true, false);
		// 设置图表标题
		mChart.setTitle(new TextTitle("手机入库品牌占比", new Font("黑体", Font.CENTER_BASELINE, 20)));
		// 设置Legend字体
		mChart.getLegend().setItemFont(new Font("宋体", Font.ROMAN_BASELINE, 15));

		PiePlot mPiePlot = (PiePlot)mChart.getPlot();
		// 以默认方式显示百分比
		// mPiePlot.setLabelGenerator(new
		// StandardPieSectionLabelGenerator(StandardPieToolTipGenerator.DEFAULT_TOOLTIP_FORMAT));
		// 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
		mPiePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})",
				NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
		// 底部图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例
		mPiePlot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));
		// 设置饼图标签字体
		mPiePlot.setLabelFont(new Font("宋体", Font.PLAIN, 15));
		File file = new File(path);
		try {
			ChartUtilities.saveChartAsJPEG(file, mChart, 800, 600);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PieDataset GetDataset() {
		// 设置数据源
		DefaultPieDataset mDataset = new DefaultPieDataset();
		List<RK> result = queryData(rkDate);
		for (int i = 0; i < result.size(); i++)
			mDataset.setValue(result.get(i).getBandTypeName(), new Double(result.get(i).getTotalNumber()));
		return mDataset;
	}
	
	/**
	 * 总和品牌数据
	 * @return
	 */
	public List<RK> queryData(List<RK> rkDate) {

		List<RK> result = new ArrayList<RK>();
		for (int i = 0; i < rkDate.size(); i++) {
			if(result == null)//库存显示列表
				result.add(rkDate.get(i));
			else {
				boolean find = false;
				for(RK item:result)
					if(item.getBandTypeName().equals(rkDate.get(i).getBandTypeName()))//品牌已存在，更新数量
					{
						item.setTotalNumber(item.getTotalNumber()+rkDate.get(i).getTotalNumber());
						find = true;
						break;
					}
				if(!find)
					result.add(rkDate.get(i));		
			}
		}
		return result;
	}
}
