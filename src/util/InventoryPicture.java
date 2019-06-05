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
	// ������״ͼ�����Ӷ����Ƿ���ʾ����
		private static boolean baseItemLabelsVisible = true;

		private List<Phone> inventoryDate;

		public InventoryPicture(List<Phone> date) {
			this.inventoryDate = date;
		}

		/**
		 * �������ݼ���
		 * 
		 * @return dataSet
		 */
		public CategoryDataset createDataSet() {
			// ʵ����DefaultCategoryDataset����
			DefaultCategoryDataset datasSet = new DefaultCategoryDataset();
			// �������
			for (Phone item : inventoryDate)
				datasSet.addValue(item.getInventory(), item.getPhoneName(), item.getBandType());
			return datasSet;
		}

		public void create(String path) {
			StandardChartTheme standardChartTheme = new StandardChartTheme("CN"); // ����������ʽ
			standardChartTheme.setExtraLargeFont(new Font("����", Font.BOLD, 20)); // ���ñ�������
			standardChartTheme.setRegularFont(new Font("����", Font.PLAIN, 15)); // ����ͼ��������
			standardChartTheme.setLargeFont(new Font("����", Font.PLAIN, 15)); // �������������
			ChartFactory.setChartTheme(standardChartTheme);// ����������ʽ
			// ���ɵ���״ͼ
			JFreeChart chart = ChartFactory.createBarChart3D("�ֻ����ͳ��ͼ��", "Ʒ��", // X��ı�ǩ
					"�������", // Y��ı�ǩ
					createDataSet(), // ͼ����ʾ�����ݼ���
					PlotOrientation.VERTICAL, // ͼ�����ʾ��ʽ��ˮƽ���ߴ�ֱ��
					true, // �Ƿ���ʾ�ӱ���
					true, // �Ƿ�������ʾ�ı�ǩ
					false); // �Ƿ�����URL����

			/*
			 * chart.getTitle().setFont(new Font("����", Font.BOLD, 25)); // ���ñ�������
			 * chart.getLegend().setItemFont(new Font("����", Font.PLAIN, 12)); // ����ͼ���������
			 */
			chart.setBorderVisible(true); // ������ʾ�߿�

			// С����
			TextTitle subTitle = new TextTitle("Ʒ������ͼ��");// ʵ����TextTitle����
			subTitle.setVerticalAlignment(VerticalAlignment.BOTTOM); // ���þ�����ʾ
			chart.addSubtitle(subTitle);// ����ӱ���

			CategoryPlot plot = chart.getCategoryPlot(); // ��ȡ��ͼ������
			plot.setForegroundAlpha(0.8F);// ���û�ͼ��ǰ��ɫ͸����
			plot.setBackgroundAlpha(0.5F);// ���û�ͼ������ɫ͸����
			// plot.setBackgroundImage(image);//���û�ͼ������ͼƬ
			CategoryAxis categoryAxis = plot.getDomainAxis();// ��ȡ���������
			categoryAxis.setLabelFont(new Font("����", Font.PLAIN, 12));// �����������������
			categoryAxis.setTickLabelFont(new Font("����", Font.PLAIN, 12));// �����������ֵ����
			categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);// ���������������ת�Ƕ�
			ValueAxis valueAxis = plot.getRangeAxis();// �������������
			valueAxis.setLabelFont(new Font("����", Font.PLAIN, 12));
			BarRenderer brender = new BarRenderer();
			brender.setIncludeBaseInRange(true);
			brender.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			brender.setBaseItemLabelsVisible(isBaseItemLabelsVisible());
			brender.setItemMargin(0.32);// ��������ļ��
			plot.setRenderer(brender);// ����ͼƬ��Ⱦ����
			/*
			 * ����ͼ���ϵ�����
			 */

			// ���������������
			chart.getTitle().setFont(new Font("����", Font.BOLD, 18));

			// ��ȡͼ���������
			CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();

			// ����ͼ�ı���Ϊ��ɫ
			categoryPlot.setBackgroundPaint(Color.WHITE);
			// ���ñ������ߵ���ɫ
			categoryPlot.setRangeGridlinePaint(Color.decode("#B6A2DE"));
			// ȥ����״ͼ�ı����߿�ʹ�߿򲻿ɼ�
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
