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
		
		// ����ͼ��
		JFreeChart mChart = ChartFactory.createPieChart("�ֻ����Ʒ��ռ��", GetDataset(), true, true, false);
		// ����ͼ�����
		mChart.setTitle(new TextTitle("�ֻ����Ʒ��ռ��", new Font("����", Font.CENTER_BASELINE, 20)));
		// ����Legend����
		mChart.getLegend().setItemFont(new Font("����", Font.ROMAN_BASELINE, 15));

		PiePlot mPiePlot = (PiePlot)mChart.getPlot();
		// ��Ĭ�Ϸ�ʽ��ʾ�ٷֱ�
		// mPiePlot.setLabelGenerator(new
		// StandardPieSectionLabelGenerator(StandardPieToolTipGenerator.DEFAULT_TOOLTIP_FORMAT));
		// ͼƬ����ʾ�ٷֱ�:�Զ��巽ʽ��{0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ���� ,С�������λ
		mPiePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})",
				NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
		// �ײ�ͼ����ʾ�ٷֱ�:�Զ��巽ʽ�� {0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ����
		mPiePlot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));
		// ���ñ�ͼ��ǩ����
		mPiePlot.setLabelFont(new Font("����", Font.PLAIN, 15));
		File file = new File(path);
		try {
			ChartUtilities.saveChartAsJPEG(file, mChart, 800, 600);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PieDataset GetDataset() {
		// ��������Դ
		DefaultPieDataset mDataset = new DefaultPieDataset();
		List<RK> result = queryData(rkDate);
		for (int i = 0; i < result.size(); i++)
			mDataset.setValue(result.get(i).getBandTypeName(), new Double(result.get(i).getTotalNumber()));
		return mDataset;
	}
	
	/**
	 * �ܺ�Ʒ������
	 * @return
	 */
	public List<RK> queryData(List<RK> rkDate) {

		List<RK> result = new ArrayList<RK>();
		for (int i = 0; i < rkDate.size(); i++) {
			if(result == null)//�����ʾ�б�
				result.add(rkDate.get(i));
			else {
				boolean find = false;
				for(RK item:result)
					if(item.getBandTypeName().equals(rkDate.get(i).getBandTypeName()))//Ʒ���Ѵ��ڣ���������
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
