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
	// ������״ͼ�����Ӷ����Ƿ���ʾ����
	private static boolean baseItemLabelsVisible = true;
	
	private List<RK> rkDate;

	public RKPicture(List<RK> date) {
		this.rkDate = date;
	}

	public void create(String path,String time) {
		// ��״ͼ����
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		// �������
		for (RK item : rkDate)
			dataset.addValue(item.getTotalNumber(), "", item.getBandTypeName());

		// ���ɵ���״ͼ
		JFreeChart chart = ChartFactory.createBarChart3D(time+"�ֻ����ͳ��ͼ��", "Ʒ��", // X��ı�ǩ
				"�������", // Y��ı�ǩ
				dataset, // ͼ����ʾ�����ݼ���
				PlotOrientation.VERTICAL, // ͼ�����ʾ��ʽ��ˮƽ���ߴ�ֱ��
				true, // �Ƿ���ʾ�ӱ���
				true, // �Ƿ�������ʾ�ı�ǩ
				false); // �Ƿ�����URL����

		/*
		 * ����ͼ���ϵ�����
		 */

		// ���������������
		chart.getTitle().setFont(new Font("����", Font.BOLD, 18));

		// ��ȡͼ���������
		CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
		// �Զ�����״ͼ�����ӵ���ʽ
	    BarRenderer brender = new BarRenderer();
	    brender.setSeriesPaint(1, Color.decode("#C0504D")); // ��series1 Bar
	    brender.setSeriesPaint(0, Color.decode("#E46C0A")); // ��series2 Bar
	    brender.setSeriesPaint(2, Color.decode("#4F81BD")); // ��series3 Bar
	    brender.setSeriesPaint(3, Color.decode("#00B050")); // ��series4 Bar
	    brender.setSeriesPaint(4, Color.decode("#7030A0")); // ��series5 Bar
	    brender.setSeriesPaint(5, Color.decode("#00BF00")); // ��series6 Bar
	 // ������״ͼ�Ķ�����ʾ����
	    brender.setIncludeBaseInRange(true);
	    brender.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
	    brender.setBaseItemLabelsVisible(isBaseItemLabelsVisible());
	    // ����ͼ�ı���Ϊ��ɫ
	    categoryPlot.setBackgroundPaint(Color.WHITE);
	    // ���ñ������ߵ���ɫ
	    categoryPlot.setRangeGridlinePaint(Color.decode("#B6A2DE"));
	    // ȥ����״ͼ�ı����߿�ʹ�߿򲻿ɼ�
	    categoryPlot.setOutlineVisible(true);

	    categoryPlot.setRenderer(brender);

		// ��ȡX��Ķ���
		CategoryAxis3D categoryAxis3D = (CategoryAxis3D) categoryPlot.getDomainAxis();

		// ��ȡY��Ķ���
		NumberAxis3D numberAxis3D = (NumberAxis3D) categoryPlot.getRangeAxis();

		// ����X���ϵ�����
		categoryAxis3D.setTickLabelFont(new Font("����", Font.BOLD, 10));

		// ����X���������
		categoryAxis3D.setLabelFont(new Font("����", Font.BOLD, 10));

		// ����Y���ϵ�����
		numberAxis3D.setTickLabelFont(new Font("����", Font.BOLD, 10));

		// ����Y���������
		numberAxis3D.setLabelFont(new Font("����", Font.BOLD, 10));

		// �Զ���Y������ʾ�Ŀ̶ȣ���10��Ϊ1��
		numberAxis3D.setAutoTickUnitSelection(false);
		NumberTickUnit unit = new NumberTickUnit(1);
		numberAxis3D.setTickUnit(unit);

		// ��״ͼ���������
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
