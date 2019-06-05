package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import bll.UsersDao;
import bll.UsersRightsDao;
import model.Users;
import util.MyProperties;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class UsersRightsFrame extends JFrame {

	private JPanel contentPane,updatePanel,userlistPanel,userrightsPanel;
	private JTextField usernameTextField;
	private JTextField passwordTextField;
	private JButton deleteButton,saveButton,resetButton,closeButton,increaseButton,decreaseButton;
	private JLabel usernameLabel,passwordLabel;
	private JScrollPane userlistScrollPane;
	private JLabel totaluserLabel;
	private JScrollPane notScrollPane;
	private JScrollPane yesScrollPane;
	private JLabel notLabel;
	private JLabel yesLabel;
	private JTable userTable;
	private DefaultTableModel tableModel,tableModel1,tableModel2;
	private TitledBorder titledBorder;
	private String head[]=null,unallocatedHead[]=null,allocatedHead[]=null;
    private Object [][]data=null;   //�û�����
    private Object [][]unallocatedData=null;  //δ����Ȩ�ޱ�
    private Object [][]allocatedData=null;;   //�ѷ���Ȩ�ޱ�
    private static int CURRENTUSERID = 0;
    private JTable unallocatedTable;
    private JTable allocatedTable;
    private int[] unallocatedSelectedCounts;  //δ����Ȩ�ޱ��ѡ����������¼
    private int[] allocatedSelectedCounts;    //�ѷ���Ȩ�ޱ��ѡ����������¼

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsersRightsFrame frame = new UsersRightsFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UsersRightsFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("���û�ע�ἰȨ�޹���");
		setBounds(100, 100, 1021, 659);
		setVisible(true);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		updatePanel = new JPanel();
		titledBorder = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "����", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED);
		titledBorder.setTitleFont(new Font("����", Font.PLAIN, 20));
		updatePanel.setBorder(titledBorder);
		updatePanel.setBounds(227, 13, 762, 92);
		contentPane.add(updatePanel);
		updatePanel.setLayout(null);
		
		deleteButton = new JButton("ɾ��");
		deleteButton.setFont(new Font("����", Font.PLAIN, 20));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selection = JOptionPane.showConfirmDialog(null,"ȷ��ɾ�����û���","ɾ��",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
				if( selection == JOptionPane.OK_OPTION ){
					String username = usernameTextField.getText();
					UsersDao.getInstance().DeleteUser(username);
					ResetUserRightsPanel();
					RefreshUserListPanel();//ˢ��table�Լ�������
					userTable.clearSelection();
					CURRENTUSERID = 0; //��ǰ�û�id����
					usernameTextField.setText("");
					passwordTextField.setText("");
					usernameTextField.requestFocus();
					deleteButton.setEnabled(false);
					increaseButton.setEnabled(false);
					decreaseButton.setEnabled(false);
					titledBorder.setTitle("����");
					updatePanel.repaint();
				}
			}
		});
		deleteButton.setEnabled(false);
		deleteButton.setBounds(230, 30, 98, 44);
		updatePanel.add(deleteButton);
		
		saveButton = new JButton("����");
		saveButton.setFont(new Font("����", Font.PLAIN, 20));
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = usernameTextField.getText();
				String password = passwordTextField.getText();
				if(username.equals(""))
					JOptionPane.showMessageDialog(null,"�û���Ϊ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				else if(password.equals(""))
					JOptionPane.showMessageDialog(null,"����Ϊ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				else{
					if(CURRENTUSERID !=0 ){  //��ǰѡ����User
						//�޸�֮����û����Ѿ�����
						if(!UsersDao.getInstance().QueryUserByID(CURRENTUSERID).getUsername().equals(username) && UsersDao.getInstance().UserIsExist(username))
							JOptionPane.showMessageDialog(null,"���û����Ѿ�����","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						else{//δ�޸��û������û���������
							if(UsersDao.getInstance().QueryID(UsersDao.MyUser) == CURRENTUSERID ) { //�޸ĵ�ǰ��¼�û�
								try {
									MyProperties.WriteProperties("F:/Eclipse/eclipse/code/Phone/bin/my.properties","username", username);
									MyProperties.WriteProperties("F:/Eclipse/eclipse/code/Phone/bin/my.properties","password", password);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							UsersDao.getInstance().UpdateUser(username, password,CURRENTUSERID);
						}
					}
					else if(CURRENTUSERID ==0 ){  //����û�
						if(UsersDao.getInstance().UserIsExist(username))
							JOptionPane.showMessageDialog(null,"���û����Ѿ�����","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						else {
							UsersDao.getInstance().AddUser(username, password);
							CURRENTUSERID = UsersDao.getInstance().QueryID(username);//���µ�ǰidָ��Ϊ�½��û���id
						}
					}
				}
				RefreshUserListPanel();//ˢ��table�Լ�������
				String queryName = UsersDao.getInstance().QueryUserByID(CURRENTUSERID).getUsername();
				for(int i =0;i<userTable.getRowCount();i++)
					if(queryName.equals(userTable.getValueAt(i,0).toString())){
						userTable.setRowSelectionInterval(i,i);//����������û���Ϊѡ����ʽ��
						break;
					}
				RefreshUserRightsPanel();//ˢ��Ȩ�ޱ�
			}
		});
		saveButton.setBounds(361, 30, 95, 44);
		updatePanel.add(saveButton);
		
		resetButton = new JButton("��λ");
		resetButton.setFont(new Font("����", Font.PLAIN, 20));
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RefreshUserListPanel();//ˢ��table�Լ�������
				ResetUserRightsPanel();
				CURRENTUSERID = 0; //��ǰ�û�id����
				usernameTextField.setText("");
				passwordTextField.setText("");
				usernameTextField.requestFocus();
				deleteButton.setEnabled(false);
				increaseButton.setEnabled(false);
				decreaseButton.setEnabled(false);
				titledBorder.setTitle("����");
				updatePanel.repaint();
			}
		});
		resetButton.setBounds(491, 30, 92, 44);
		updatePanel.add(resetButton);
		
		closeButton = new JButton("�ر�");
		closeButton.setFont(new Font("����", Font.PLAIN, 20));
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		closeButton.setBounds(612, 30, 95, 44);
		updatePanel.add(closeButton);
		
		usernameLabel = new JLabel("�û���");
		usernameLabel.setFont(new Font("����", Font.PLAIN, 20));
		usernameLabel.setBounds(14, 30, 72, 18);
		updatePanel.add(usernameLabel);
		
		passwordLabel = new JLabel("����");
		passwordLabel.setFont(new Font("����", Font.PLAIN, 20));
		passwordLabel.setBounds(14, 64, 72, 18);
		updatePanel.add(passwordLabel);
		
		usernameTextField = new JTextField();
		usernameTextField.setFont(new Font("����", Font.PLAIN, 14));
		usernameTextField.setBounds(81, 29, 135, 24);
		updatePanel.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		passwordTextField = new JTextField();
		passwordTextField.setFont(new Font("����", Font.PLAIN, 14));
		passwordTextField.setBounds(81, 63, 135, 24);
		updatePanel.add(passwordTextField);
		
		userlistPanel = new JPanel();
		TitledBorder titledBorder2 = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "�û�һ����", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED);
		titledBorder2.setTitleFont(new Font("����", Font.PLAIN, 20));
		userlistPanel.setBorder(titledBorder2);
		userlistPanel.setBounds(14, 13, 199, 599);
		contentPane.add(userlistPanel);
		userlistPanel.setLayout(null);
		
		userlistScrollPane = new JScrollPane();
		userlistScrollPane.setBounds(14, 23, 171, 524);
		userlistPanel.add(userlistScrollPane);
		
        //��ʼ�����
		userTable = new JTable();
		userTable.setRowHeight(25);
		userTable.setBorder(new LineBorder(new Color(0, 0, 0)));
        head=new String[] {"�û���"};
        tableModel=new DefaultTableModel(queryData(),head){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        userTable.setFont(new Font("����", Font.BOLD, 14));
        userTable.addMouseListener(new MouseAdapter() {   //�û���������¼�
			@Override
			public void mouseClicked(MouseEvent arg0) {
				titledBorder.setTitle("�޸�");
				updatePanel.repaint();
				String username = userTable.getValueAt(userTable.getSelectedRow(), 0).toString();
				String password = UsersDao.getInstance().QueryPassword(username);
				usernameTextField.setText(username);
				passwordTextField.setText(password);
				CURRENTUSERID = UsersDao.getInstance().QueryID(username);
				deleteButton.setEnabled(true);
				increaseButton.setEnabled(true);
				decreaseButton.setEnabled(true);
				RefreshUserRightsPanel();  //ˢ�¸��û���Ȩ�޷����б�
			}
		});
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        userTable.setDefaultRenderer(Object.class, r);
        userTable.setModel(tableModel);
		userlistScrollPane.setViewportView(userTable);
		
		totaluserLabel = new JLabel();
		totaluserLabel.setFont(new Font("����", Font.PLAIN, 20));
		totaluserLabel.setText("��"+UsersDao.getInstance().QueryUsersCount()+"��");
		totaluserLabel.setForeground(Color.RED);
		totaluserLabel.setBounds(59, 560, 96, 26);
		userlistPanel.add(totaluserLabel);
		
		userrightsPanel = new JPanel();
		TitledBorder titledBorder3 = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "����Ȩ�޷���", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED);
		titledBorder3.setTitleFont(new Font("����", Font.PLAIN, 20));
		userrightsPanel.setBorder(titledBorder3);
		userrightsPanel.setBounds(227, 118, 762, 494);
		contentPane.add(userrightsPanel);
		userrightsPanel.setLayout(null);
		
		increaseButton = new JButton("=>");
		increaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {  //����Ȩ��
				if(UsersRightsDao.getInstance().AddUserRights(CURRENTUSERID, unallocatedSelectedCounts, unallocatedTable)){  //����Ȩ�޳ɹ�
					RefreshUserRightsPanel();
				}
			}
		});
		increaseButton.setEnabled(false);
		increaseButton.setBounds(328, 115, 95, 43);
		userrightsPanel.add(increaseButton);
		
		decreaseButton = new JButton("<=");
		decreaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {  //����Ȩ��
				if(UsersRightsDao.getInstance().DeleteUserRights(CURRENTUSERID, allocatedSelectedCounts, allocatedTable)){  //����Ȩ�޳ɹ�
					RefreshUserRightsPanel();
				}
			}
		});
		decreaseButton.setEnabled(false);
		decreaseButton.setBounds(328, 184, 95, 43);
		userrightsPanel.add(decreaseButton);
		
		notScrollPane = new JScrollPane();
		notScrollPane.setBounds(14, 24, 305, 417);
		userrightsPanel.add(notScrollPane);
		
		unallocatedTable = new JTable();
		unallocatedTable.setRowHeight(25);
		unallocatedTable.setBorder(new LineBorder(new Color(0, 0, 0)));
        unallocatedHead=new String[] {"����ģ��","δ�ֻ���Ȩ��"};
        tableModel1=new DefaultTableModel(null,unallocatedHead){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        unallocatedTable.setFont(new Font("����", Font.BOLD, 14));
        unallocatedTable.addMouseListener(new MouseAdapter() {   //δ����Ȩ�ޱ�����¼�
			@Override
			public void mouseClicked(MouseEvent arg0) {
				unallocatedSelectedCounts = unallocatedTable.getSelectedRows(); //��������ѡ���е�����
				decreaseButton.setEnabled(false);
				increaseButton.setEnabled(true);
				for(int i = 0; i < unallocatedSelectedCounts.length; i++){
					System.out.println(unallocatedTable.getValueAt(unallocatedSelectedCounts[i],0));
					System.out.println(unallocatedTable.getValueAt(unallocatedSelectedCounts[i],1));
				}
					
				
				
			}
		});
        unallocatedTable.setDefaultRenderer(Object.class, r);
        unallocatedTable.setModel(tableModel1);
		notScrollPane.setViewportView(unallocatedTable);
		
		yesScrollPane = new JScrollPane();
		yesScrollPane.setBounds(437, 24, 311, 417);
		userrightsPanel.add(yesScrollPane);
		
		allocatedTable = new JTable();
		allocatedTable.setRowHeight(25);
		allocatedTable.setBorder(new LineBorder(new Color(0, 0, 0)));
        allocatedHead=new String[] {"����ģ��","�ѷֻ���Ȩ��"};
        tableModel2=new DefaultTableModel(null,allocatedHead){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        allocatedTable.setFont(new Font("����", Font.BOLD, 14));
        allocatedTable.addMouseListener(new MouseAdapter() {   //�ѷ���Ȩ�ޱ�����¼�
			@Override
			public void mouseClicked(MouseEvent arg0) {
				allocatedSelectedCounts = allocatedTable.getSelectedRows(); //��������ѡ���е�����
				decreaseButton.setEnabled(true);
				increaseButton.setEnabled(false);
			}
		});
        allocatedTable.setDefaultRenderer(Object.class, r);
        allocatedTable.setModel(tableModel2);
		yesScrollPane.setViewportView(allocatedTable);
		
		notLabel = new JLabel("δ���0");
		notLabel.setFont(new Font("����", Font.PLAIN, 20));
		notLabel.setForeground(Color.RED);
		notLabel.setBounds(77, 454, 115, 27);
		userrightsPanel.add(notLabel);
		
		yesLabel = new JLabel("�ѷ��0");
		yesLabel.setFont(new Font("����", Font.PLAIN, 20));
		yesLabel.setForeground(Color.RED);
		yesLabel.setBounds(552, 454, 106, 27);
		userrightsPanel.add(yesLabel);
		
	}
	
	//�����û����������
    /**
     * @return
     */
    public Object[][] queryData(){
        List<Users> list=UsersDao.getInstance().QueryUser();
        data=new Object[list.size()][head.length];
        for(int i=0;i<list.size();i++){
            for(int j=0;j<head.length;j++){
                data[i][0]=list.get(i).getUsername();
            }
        }
        return data;
    }
    
  //����δ����Ȩ�ޱ������
    /**
     * @return
     */
    public Object[][] queryUnallocatedData(int id){
    	List<HashMap<String,String>> result=UsersRightsDao.getInstance().UnallocatedByUserID(id);
        unallocatedData=new Object[result.size()][unallocatedHead.length];
        for(int i=0;i<result.size();i++){
            for(int j=0;j<unallocatedHead.length;j++){
            	for(Map.Entry<String, String> arg:result.get(i).entrySet()){
            		unallocatedData[i][0]=arg.getValue();
            	    unallocatedData[i][1]=arg.getKey();
            	}
            }
        }
        notLabel.setText("δ���"+result.size());
        return unallocatedData;
    }
    
  //�����ѷ���Ȩ�ޱ������
    /**
     * @return
     */
    public Object[][] queryAllocatedData(int id){
    	List<HashMap<String,String>> result=UsersRightsDao.getInstance().AllocatedByUserID(id);
        allocatedData=new Object[result.size()][allocatedHead.length];
        for(int i=0;i<result.size();i++){
            for(int j=0;j<allocatedHead.length;j++){
            	for(Map.Entry<String, String> arg:result.get(i).entrySet()){
            		allocatedData[i][0]=arg.getValue();
            	    allocatedData[i][1]=arg.getKey();
            	}    	
            }
        }
        yesLabel.setText("�ѷ��"+result.size());
        return allocatedData;
    }
    
    /**
     * ˢ���û��б����
     */
    public void RefreshUserListPanel(){
    	List<Users> list=UsersDao.getInstance().QueryUser();//����������
    	totaluserLabel.setText("��"+list.size()+"��");
    	userlistPanel.removeAll();
    	userlistPanel.add(userlistScrollPane);
    	tableModel=new DefaultTableModel(queryData(),head){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
    	userTable.setModel(tableModel);
    	userlistScrollPane.setViewportView(userTable);
    	userlistPanel.add(totaluserLabel);
		userlistPanel.validate();
		userlistPanel.repaint();
    }
    
    /**
     * ˢ���û�Ȩ�޽���
     */
    public void RefreshUserRightsPanel(){
    	userrightsPanel.removeAll();
    	userrightsPanel.add(increaseButton);
    	userrightsPanel.add(decreaseButton);
    	userrightsPanel.add(notLabel);
    	userrightsPanel.add(yesLabel);
    	userrightsPanel.add(notScrollPane);
    	userrightsPanel.add(yesScrollPane);
        tableModel1=new DefaultTableModel(queryUnallocatedData(CURRENTUSERID),unallocatedHead){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
       unallocatedTable.setModel(tableModel1);
       unallocatedTable.clearSelection();
       notScrollPane.setViewportView(unallocatedTable);
       tableModel2=new DefaultTableModel(queryAllocatedData(CURRENTUSERID),allocatedHead){
           public boolean isCellEditable(int row, int column)
           {
               return false;
           }
       };
       allocatedTable.setModel(tableModel2);
       allocatedTable.clearSelection();
       yesScrollPane.setViewportView(allocatedTable);
       userrightsPanel.validate();
       userrightsPanel.repaint();
    }
    
    /**
     * �����û�����Ȩ���б�
     */
    public void ResetUserRightsPanel(){
    	userrightsPanel.removeAll();
    	userrightsPanel.add(increaseButton);
    	userrightsPanel.add(decreaseButton);
    	userrightsPanel.add(notLabel);
    	userrightsPanel.add(yesLabel);
    	userrightsPanel.add(notScrollPane);
    	userrightsPanel.add(yesScrollPane);
        tableModel1=new DefaultTableModel(null,unallocatedHead){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
       unallocatedTable.setModel(tableModel1);
       unallocatedTable.clearSelection();
       notScrollPane.setViewportView(unallocatedTable);
       tableModel2=new DefaultTableModel(null,allocatedHead){
           public boolean isCellEditable(int row, int column)
           {
               return false;
           }
       };
       allocatedTable.setModel(tableModel2);
       allocatedTable.clearSelection();
       yesScrollPane.setViewportView(allocatedTable);
       notLabel.setText("δ���0");
       yesLabel.setText("�ѷ��0");
       userrightsPanel.validate();
       userrightsPanel.repaint();
    }
}
