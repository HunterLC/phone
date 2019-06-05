package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.itextpdf.text.DocumentException;

import bll.InventoryDao;
import bll.PhoneDao;
import bll.RKDao;
import jxl.write.WriteException;
import model.Phone;
import model.RK;
import util.InventoryPDF;
import util.RKEXCEL;
import util.RKPDF;
import util.RKPicture;
import util.RKPie;
import util.TestPic;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;

public class Manage_Statistic extends JFrame {

	private JPanel contentPane,RKPanel;
	private JScrollPane scrollPane;
	private TitledBorder titledBorder1;
	private JButton closeButton,searchButton;
	private JTable rkTable;
	private DefaultTableModel tableModel1;
	private String head1[]=null;  //表格列名
	private Object [][]data1=null;   //表格行表
	private JSpinner RKTimeSearchSpinner;
	private List<RK> rkList = new ArrayList<RK>();
	private JPanel panel;
	private JButton btn_PDF;
	private JButton btn_Excel;
	private JButton btn_Picture;
	private ButtonGroup selectedGroup,selectedGroup1;
	private JRadioButton Year_RadioButton,YM_RadioButton,YMD_RadioButton,btn_histogram,btn_pie;
	private int SEARCHTYPE = 0;
	private JFrame FRAME;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try
				    {		
						BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;			
						org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();			
						UIManager.put("RootPane.setupButtonVisible", false);
				      //设置此开关量为false即表示关闭之，BeautyEye LNF中默认是true
				        BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
				    }
				    catch(Exception e)
				    {
				        //TODO exception
				    }
					Manage_Statistic frame = new Manage_Statistic();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Manage_Statistic() {
		FRAME = this;
		setTitle("\u624B\u673A\u5165\u5E93\u6570\u636E\u7EDF\u8BA1");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1020, 692);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		setLocationRelativeTo(null);
		RKPanel = new JPanel();
		titledBorder1 = new TitledBorder(null, "\u624B\u673A\u5165\u5E93\u660E\u7EC6\u67E5\u8BE2\uFF08\u5171XX\u6761\uFF09", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 102, 153));
		titledBorder1.setTitleFont(new Font("宋体", Font.PLAIN, 20));
		RKPanel.setBorder(titledBorder1);
		RKPanel.setBounds(38, 13, 974, 632);
		contentPane.add(RKPanel);
		RKPanel.setLayout(null);
		

		Year_RadioButton = new JRadioButton("\u67E5\u5E74");
		Year_RadioButton.setBounds(14, 462, 75, 27);
		RKPanel.add(Year_RadioButton);
		
		YM_RadioButton = new JRadioButton("\u67E5\u5E74\u3001\u6708");
		YM_RadioButton.setBounds(95, 462, 99, 27);
		RKPanel.add(YM_RadioButton);
		
		YMD_RadioButton = new JRadioButton("\u67E5\u5E74\u3001\u6708\u3001\u65E5");
		YMD_RadioButton.setSelected(true);
		YMD_RadioButton.setBounds(215, 462, 157, 27);
		RKPanel.add(YMD_RadioButton);
		selectedGroup = new ButtonGroup();//单选组
		selectedGroup.add(Year_RadioButton);
		selectedGroup.add(YM_RadioButton);
		selectedGroup.add(YMD_RadioButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 23, 946, 420);
		
		RKPanel.add(scrollPane);
		
		closeButton = new JButton("\u5173\u95ED");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		closeButton.setBounds(127, 567, 99, 33);
		RKPanel.add(closeButton);
		closeButton.setFont(new Font("宋体", Font.PLAIN, 20));
		
		searchButton = new JButton("\u67E5\u8BE2");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RefreshRKList();
			}
		});
		searchButton.setBounds(14, 567, 99, 33);
		RKPanel.add(searchButton);
		searchButton.setFont(new Font("宋体", Font.PLAIN, 20));
		
		 // 获得时间日期模型
		SpinnerDateModel model1 = new SpinnerDateModel();
		// 获得JSPinner对象
		RKTimeSearchSpinner = new JSpinner(model1);
		RKTimeSearchSpinner.setValue(new Date());
		// 设置时间格式
		JSpinner.DateEditor editor1 = new JSpinner.DateEditor(RKTimeSearchSpinner, "yyyy-MM-dd");
		RKTimeSearchSpinner.setEditor(editor1);
		RKTimeSearchSpinner.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					//osTypeComboBox.requestFocus();
					;
			}
		});
		RKTimeSearchSpinner.setFont(new Font("宋体", Font.PLAIN, 16));
		RKTimeSearchSpinner.setBounds(14, 508, 216, 33);
		RKPanel.add(RKTimeSearchSpinner);
		// 初始化表格
				rkTable = new JTable();
				rkTable.setRowHeight(35);
				rkTable.setBorder(new LineBorder(new Color(0, 0, 0)));
				head1 = new String[] { "名称","型号","品牌","入库时间","入库数量","入库次数","定价", "总码样"};
				tableModel1 = new DefaultTableModel(queryData1(), head1) {
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				rkTable.setFont(new Font("宋体", Font.BOLD, 14));
				rkTable.addMouseListener(new MouseAdapter() { // 用户名表格点击事件
					@Override
					public void mouseClicked(MouseEvent arg0) {

					}
				});
				rkTable.setModel(tableModel1);
				scrollPane.setViewportView(rkTable);
				
				
				panel = new JPanel();
				panel.setBorder(new TitledBorder(null, "\u5BFC\u51FA\u4E3A", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel.setBounds(499, 456, 461, 163);
				RKPanel.add(panel);
				panel.setLayout(null);
				
				btn_PDF = new JButton("PDF");
				btn_PDF.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JFileChooser chooser = new JFileChooser();
						Runtime runtime = Runtime.getRuntime(); 
						chooser.setDialogType(JFileChooser.SAVE_DIALOG); // 保存
						//chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 只显示目录
						//在弹出对话框之前，创建文件类型的过滤器
						FileNameExtensionFilter filter=new FileNameExtensionFilter("*.pdf", "pdf");
						chooser.setFileFilter(filter);
						int re = chooser.showSaveDialog(FRAME);
						if (re == JFileChooser.APPROVE_OPTION) {
							System.out.println(chooser.getSelectedFile().getPath());
							RKPDF pdf = new RKPDF(rkList);
							String searchTime = new SimpleDateFormat("yyyy-MM-dd").format(RKTimeSearchSpinner.getValue());
							String[] sArray=searchTime.split("-") ;
							int type = GetType();
							String time = null;
							switch(type) {
							case 1:time = sArray[0]+"年"; break;
							case 2:time = sArray[0]+"年"+sArray[1]+"月"; break;
							case 3:time = sArray[0]+"年"+sArray[1]+"月"+sArray[2]+"日"; break;
							}
							
							try {
								pdf.createPDF(chooser.getSelectedFile().getPath(),time);
								runtime.exec("rundll32 url.dll FileProtocolHandler "+chooser.getSelectedFile().getPath());//自动打开文件
							} catch (DocumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
				btn_PDF.setBounds(42, 35, 113, 27);
				panel.add(btn_PDF);
				
				btn_Excel = new JButton("EXCEL");
				btn_Excel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser chooser = new JFileChooser();
						Runtime runtime = Runtime.getRuntime(); 
						chooser.setDialogType(JFileChooser.SAVE_DIALOG); // 保存
						//chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 只显示目录
						//在弹出对话框之前，创建文件类型的过滤器
						FileNameExtensionFilter filter=new FileNameExtensionFilter("*.xls", "xls");
						chooser.setFileFilter(filter);
						int re = chooser.showSaveDialog(FRAME);
						if (re == JFileChooser.APPROVE_OPTION) {
							System.out.println(chooser.getSelectedFile().getPath());
							RKEXCEL excel = new RKEXCEL(rkList);
							String searchTime = new SimpleDateFormat("yyyy-MM-dd").format(RKTimeSearchSpinner.getValue());
							String[] sArray=searchTime.split("-") ;
							int type = GetType();
							String time = null;
							switch(type) {
							case 1:time = sArray[0]+"年"; break;
							case 2:time = sArray[0]+"年"+sArray[1]+"月"; break;
							case 3:time = sArray[0]+"年"+sArray[1]+"月"+sArray[2]+"日"; break;
							}
							
							try {
								excel.createExcel(chooser.getSelectedFile().getPath());
							} catch (WriteException | IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							try {
								runtime.exec("rundll32 url.dll FileProtocolHandler "+chooser.getSelectedFile().getPath());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}//自动打开文件
							
						}
					}
				});
				btn_Excel.setBounds(42, 75, 113, 27);
				panel.add(btn_Excel);
				
				btn_Picture = new JButton("\u7EDF\u8BA1\u56FE");
				btn_Picture.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser chooser = new JFileChooser();
						Runtime runtime = Runtime.getRuntime(); 
						chooser.setDialogType(JFileChooser.SAVE_DIALOG); // 保存
						//chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 只显示目录
						//在弹出对话框之前，创建文件类型的过滤器
						FileNameExtensionFilter filter=new FileNameExtensionFilter("*.png", "png");
						chooser.setFileFilter(filter);
						int re = chooser.showSaveDialog(FRAME);
						if (re == JFileChooser.APPROVE_OPTION) {
							System.out.println(chooser.getSelectedFile().getPath());
							
							String searchTime = new SimpleDateFormat("yyyy-MM-dd").format(RKTimeSearchSpinner.getValue());
							String[] sArray=searchTime.split("-") ;
							int type = GetType();
							String time = null;
							switch(type) {
							case 1:time = sArray[0]+"年"; break;
							case 2:time = sArray[0]+"年"+sArray[1]+"月"; break;
							case 3:time = sArray[0]+"年"+sArray[1]+"月"+sArray[2]+"日"; break;
							}
							if(btn_histogram.isSelected()) {
								TestPic picture = new TestPic(rkList);
								picture.create(chooser.getSelectedFile().getPath(), time);
							}else {
								RKPie picture = new RKPie(rkList);
								picture.createPie(chooser.getSelectedFile().getPath());
							}
							
							try {
								runtime.exec("rundll32 url.dll FileProtocolHandler "+chooser.getSelectedFile().getPath());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}//自动打开文件
							
						}
					}
				});
				btn_Picture.setBounds(42, 115, 113, 27);
				panel.add(btn_Picture);
				btn_pie = new JRadioButton("\u54C1\u724C\u5360\u6BD4\u997C\u56FE");
				btn_pie.setBounds(218, 115, 157, 27);
				panel.add(btn_pie);
				
				btn_histogram = new JRadioButton("\u624B\u673A\u76F4\u65B9\u56FE");
				btn_histogram.setSelected(true);
				btn_histogram.setBounds(218, 75, 157, 27);
				panel.add(btn_histogram);
				
				selectedGroup1 = new ButtonGroup();//单选组
				selectedGroup1.add(btn_pie);
				selectedGroup1.add(btn_histogram);
				
	}

    
	// 生成用户名表格数据
	/**
	 * @return
	 */
	public Object[][] queryData1() {
		System.out.println("前大小："+rkList.size());
		if(rkList!=null)
			rkList = new ArrayList<RK>();
		System.out.println("后大小："+rkList.size());
		int searchType = GetType();
		String searchText = new SimpleDateFormat("yyyy-MM-dd").format(RKTimeSearchSpinner.getValue());
		List<RK> result = RKDao.getInstance().QueryStatistic(searchText,searchType);
		for (int i = 0; i < result.size(); i++) {
			if(rkList == null)//库存显示列表
				rkList.add(result.get(i));
			else {
				boolean find = false;
				for(RK item:rkList)
					if(item.getPhoneNumber().equals(result.get(i).getPhoneNumber()))//给手机已存在，更新数量
					{
						item.setTotalNumber(item.getTotalNumber()+result.get(i).getTotalNumber());
						find = true;
						break;
					}
				if(!find)
					rkList.add(result.get(i));		
			}
		}
		titledBorder1.setTitle("手机入库明细（共"+rkList.size()+"条）");
		data1 = new Object[rkList.size()][head1.length];
		for (int i = 0; i < rkList.size(); i++) {
			data1[i][0] = rkList.get(i).getPhoneName();
			data1[i][1] = rkList.get(i).getPhoneNumber();
			data1[i][2] = rkList.get(i).getBandTypeName();
			data1[i][3] = rkList.get(i).getInDate();
			data1[i][4] = rkList.get(i).getTotalNumber();
			data1[i][5] = rkList.get(i).getCount();
			data1[i][6] = rkList.get(i).getPrice();
			data1[i][7] = rkList.get(i).getPrice()*rkList.get(i).getTotalNumber();
		}
		return data1;
	}
   
	public int GetType() {
		if(YMD_RadioButton.isSelected())
			SEARCHTYPE = 3;
		else if(YM_RadioButton.isSelected())
			SEARCHTYPE = 2;
		else
			SEARCHTYPE = 1;;
		return SEARCHTYPE;
	}

	public void RefreshRKList() {
		RKPanel.remove(scrollPane);
		RKPanel.add(scrollPane);
    	tableModel1=new DefaultTableModel(queryData1(),head1){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        rkTable.setModel(tableModel1);
        scrollPane.setViewportView(rkTable);
        RKPanel.validate();
        RKPanel.repaint();
	}
}
