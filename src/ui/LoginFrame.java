package ui;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import bll.ArgmentsDao;
import bll.UsersDao;
import util.MyProperties;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Label;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;

import java.awt.Button;
import java.awt.Color;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField accountTextField;
	private JPasswordField passwordField;
	private JLabel lblNewLabel,accountLabel,passwordLabel,loginDateLabel,warnLabel,imageLabel;
	private JCheckBox chckbxNewCheckBox;
	private JComboBox comboBox;
	public static String loginDay = null;
	private JLabel label;
	private JLabel lblNewLabel_1;
	private JLabel label_1;

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
					LoginFrame frame = new LoginFrame();
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
	public LoginFrame() {
		setFont(new Font("方正舒体", Font.PLAIN, 26));
		setTitle("\u624B\u673A\u5E93\u5B58\u7BA1\u7406\u4FE1\u606F\u7CFB\u7EDF");
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 686, 478);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		lblNewLabel = new JLabel("\u624B\u673A\u5E93\u5B58\u7BA1\u7406\u4FE1\u606F\u7CFB\u7EDF");
		lblNewLabel.setFont(new Font("方正舒体", Font.PLAIN, 40));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(65, 78, 466, 52);
		contentPane.add(lblNewLabel);
		
		accountLabel = new JLabel("用户名");
		accountLabel.setFont(new Font("方正舒体", Font.PLAIN, 24));
		accountLabel.setBounds(138, 245, 113, 18);
		contentPane.add(accountLabel);
		
		passwordLabel = new JLabel("\u5BC6  \u7801");
		passwordLabel.setFont(new Font("方正舒体", Font.PLAIN, 24));
		passwordLabel.setBounds(138, 284, 98, 24);
		contentPane.add(passwordLabel);
		
		accountTextField = new JTextField();
		accountTextField.setFont(new Font("宋体", Font.PLAIN, 20));
		accountTextField.setBounds(240, 244, 255, 24);
		contentPane.add(accountTextField);
		try {
			if( !MyProperties.getInstance().getProperty("username").equals(null))
				accountTextField.setText(MyProperties.getInstance().getProperty("username"));	
			else{
				accountTextField.requestFocus();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		accountTextField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("宋体", Font.PLAIN, 20));
		passwordField.setBounds(240, 286, 255, 24);
		contentPane.add(passwordField);
		try {
			if( MyProperties.getInstance().getProperty("username")!=null && MyProperties.getInstance().getProperty("remember").equals("true"))
				passwordField.setText(MyProperties.getInstance().getProperty("password"));
			else if(!accountTextField.getText().equals("")){
				passwordField.setText("");
				passwordField.requestFocus();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JButton loginButton = new JButton("登录");
		loginButton.setFont(new Font("宋体", Font.PLAIN, 20));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {           //处理登录操作
				String account;
				String password;
				account=accountTextField.getText();
				password=String.valueOf(passwordField.getPassword());
				if( account.equals("") ){
					warnLabel.setText("用户名未填");
					accountTextField.requestFocus();
				}else if( password.equals("") ){
					warnLabel.setText("密码未填");
					passwordField.requestFocus();
				}else{
					try {
					if(UsersDao.getInstance().checkAccount(account, password)==1)  //登录成功
					{
						DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String nowTime = sdf.format(new Date());//当前时间
						String endTime= UsersDao.loginUser.getUnlock_time(); //数据库锁定时间
						try {
							if(endTime !=null && sdf.parse(nowTime).getTime()<sdf.parse(endTime).getTime())   //账户被锁定
							{
								warnLabel.setText("当前用户处于锁定状态，解锁时间为"+endTime);
								passwordField.setText("");
								accountTextField.requestFocus();
							}
							else{      //账户未锁定
								//将登录失败次数清零、解锁时间置null
								if(UsersDao.getInstance().setFault_Time(0, account) && UsersDao.getInstance().setUnlock_Time(null, account)){
									warnLabel.setText("登录成功");
									try {
										MyProperties.WriteProperties("F:/Eclipse/eclipse/code/Phone/bin/my.properties","username", account);
										MyProperties.WriteProperties("F:/Eclipse/eclipse/code/Phone/bin/my.properties","password", password);
										MyProperties.WriteProperties("F:/Eclipse/eclipse/code/Phone/bin/my.properties", "remember", chckbxNewCheckBox.isSelected()==true ? "true": "false");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									dispose();
									loginDay = comboBox.getSelectedItem().toString();
									new MenuFrame(UsersDao.getInstance().QueryID(account));
								}
								else{
									warnLabel.setText("清空登录失败次数或重置解锁时间失败");
								}
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else        //登录失败时
					{
						UsersDao.getInstance().QueryUser(account);  //查询用户信息
						DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if( UsersDao.getInstance().checkAccount(account, password) == -1 ){  //账户存在，密码错误
							try {
								String nowTime = sdf.format(new Date());//当前时间
								String endTime= UsersDao.queryUser.getUnlock_time(); //数据库锁定时间
								if(endTime !=null && sdf.parse(nowTime).getTime()<sdf.parse(endTime).getTime())   //账户被锁定
								{
									warnLabel.setText("当前用户处于锁定状态，解锁时间为"+endTime);
									passwordField.setText("");
									accountTextField.requestFocus();
								}else{
									UsersDao.getInstance().setFault_Time(UsersDao.queryUser.getFault_time()+1, account); //登录失败次数加1
									warnLabel.setText("密码错误,你剩余的登录尝试次数还有"+(ArgmentsDao.getInstance().getNum()-UsersDao.queryUser.getFault_time())+"次");
									passwordField.setText("");
									passwordField.requestFocus();
									if(UsersDao.queryUser.getFault_time() >= ArgmentsDao.getInstance().getNum()){  //登录失败已达封锁次数
										Calendar calendar = Calendar.getInstance();
										calendar.add(Calendar.DATE,ArgmentsDao.getInstance().getLock_days());
										UsersDao.getInstance().setUnlock_Time(sdf.format(calendar.getTime()), account);  //设置解锁时间
										warnLabel.setText("登录失败次数已达"+UsersDao.queryUser.getFault_time()+"次,账号解锁时间为"+UsersDao.queryUser.getUnlock_time());
										UsersDao.getInstance().setFault_Time(0, account);  //账号封禁,将登录失败清零
									}	
								}
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{  //账户不存在
								warnLabel.setText("用户名"+account+"不存在");
								accountTextField.setText("");
								passwordField.setText("");
								accountTextField.requestFocus();
							}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
			}
		});
		loginButton.setBounds(250, 353, 113, 45);
		contentPane.add(loginButton);
		
		loginDateLabel = new JLabel("\u5DE5\u4F5C\u65E5\u671F");
		loginDateLabel.setFont(new Font("方正舒体", Font.PLAIN, 24));
		loginDateLabel.setBounds(138, 322, 133, 18);
		contentPane.add(loginDateLabel);
		
		comboBox = new JComboBox();
		comboBox.setBackground(Color.WHITE);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		List<String> calendarList = new ArrayList<String>();//获取当前日期以及之前的日期
		try {
			int days = ArgmentsDao.getInstance().getDays();//从数据库中读取值
			for(int i = 0; i < days; i++)
			{
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE,-i);
				calendarList.add(sdf.format(calendar.getTime()));
			}
			comboBox.setModel(new DefaultComboBoxModel(calendarList.toArray()));   //初始化日期
			comboBox.setBounds(240, 323, 255, 24);
			contentPane.add(comboBox);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       

		ImageIcon im1 = new ImageIcon("image/login_001.png");
		imageLabel = new JLabel();
		imageLabel.setIcon(im1);
		imageLabel.setBounds(14, 13, 652, 223);
		contentPane.add(imageLabel);
		
		chckbxNewCheckBox = new JCheckBox("记住密码");
		chckbxNewCheckBox.setBackground(Color.WHITE);
		chckbxNewCheckBox.setFont(new Font("宋体", Font.PLAIN, 20));
		try {
			if( MyProperties.getInstance().getProperty("remember").equals("true"))
				chckbxNewCheckBox.setSelected(true);
			else
				chckbxNewCheckBox.setSelected(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chckbxNewCheckBox.setBounds(517, 285, 133, 27);
		contentPane.add(chckbxNewCheckBox);
		
		warnLabel = new JLabel("",JLabel.CENTER);
		warnLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		warnLabel.setForeground(Color.RED);
		warnLabel.setBounds(25, 403, 641, 27);
		contentPane.add(warnLabel);
		
		JButton btnNewButton = new JButton("取消");
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(377, 353, 113, 45);
		contentPane.add(btnNewButton);
		
		ImageIcon img1 = new ImageIcon("image/login_002.png");
		ImageIcon img2 = new ImageIcon("image/login_003.png");
		ImageIcon img3 = new ImageIcon("image/login_004.png");
		label = new JLabel();
		label.setBounds(95, 237, 34, 34);
		label.setIcon(img1);
		contentPane.add(label);
		
		lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setBounds(95, 284, 34, 34);
		lblNewLabel_1.setIcon(img2);
		contentPane.add(lblNewLabel_1);
		
		label_1 = new JLabel();
		label_1.setIcon(img3);
		label_1.setBounds(95, 313, 34, 34);
		contentPane.add(label_1);
		
	}
}
