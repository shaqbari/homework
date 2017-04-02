package tablemodel;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableModel;

//��ܿ� ���������� �Է��Կ� ���� �����ϰ� �����͸� jtable�� ����Ҽ� �ְ� ������. 
public class TableSwitch extends JFrame implements ItemListener{	
	JPanel p_north, p_west, p_center;
	JLabel la_dbms, la_url, la_user, la_pw;
	Choice cho_dbms, cho_table;
	JTextField txt_url, txt_user;
	JPasswordField pw;
	
	String driver;
	String url;
	String user;
	String password;
	
	JTable table;	
	JScrollPane scroll;	
	MyModel model;
	String[] empCol={"ename",	 "job","mgr","hiredate", "sal", "comm", "deptno"};	
	String[] deptCol={"deptno", "dname", "loc"};	
	
	public TableSwitch() {
		p_north=new JPanel();
		p_west=new JPanel();
		p_center=new JPanel();
		la_dbms=new JLabel("DBMS");
		la_url=new JLabel("url");
		la_user=new JLabel("user");
		la_pw=new JLabel("password");	
		cho_dbms=new Choice();		
		cho_table=new Choice();
		txt_url=new JTextField("jdbc:mariadb://localhost:3306/ss_edu", 20);
		txt_user=new JTextField("root", 8);
		pw=new JPasswordField("", 8);		
		
		cho_dbms.add("mariadb");
		cho_dbms.add("oracle");		
		cho_table.add("���ϼ���");
		cho_table.add("������");
		cho_table.add("�μ����");	
		
		cho_dbms.addItemListener(this);
		cho_table.addItemListener(this);
		
		p_north.add(la_dbms);
		p_north.add(cho_dbms);
		p_north.add(la_url);
		p_north.add(txt_url);
		p_north.add(la_user);
		p_north.add(txt_user);
		p_north.add(la_pw);
		p_north.add(pw);		
		p_west.add(cho_table);
		
		add(p_north, BorderLayout.NORTH);
		add(p_west, BorderLayout.WEST);
		add(p_center);			
		
		p_west.setPreferredSize(new Dimension(100, 500));
		p_center.setPreferredSize(new Dimension(600, 500));
		
		setSize(700, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
	}
	
	//cho_table�� ����  select sql�� �� ���̺� �̸��� ���ϰ�, �������Է��Ҷ��� column�̸� ����
	public void setTable(String tableName, String[] columnName){
		url=txt_url.getText();
		user=txt_user.getText();
		password=new String(pw.getPassword());
				
		//����� �����ֱ�
		p_center.removeAll();
		model=new MyModel(driver, url, user, password, tableName, columnName);
		table=new JTable(model);		
		scroll=new JScrollPane(table);
		p_center.add(scroll);
		p_center.updateUI();		
	}
	
	public void itemStateChanged(ItemEvent e) {
		//choice���� dbms���ÿ����� �⺻ ������������
		String dbms=cho_dbms.getSelectedItem();
		if (dbms=="mariadb") {			
			driver="org.mariadb.jdbc.Driver";
			txt_url.setText("jdbc:mariadb://localhost:3306/ss_edu");
			txt_user.setText("root");			
			
		} else if (dbms=="oracle") {			
			driver="oracle.jdbc.driver.OracleDriver";
			txt_url.setText("jdbc:oracle:thin:@localhost:1521:XE");
			txt_user.setText("batman");	
		}
		
		//choice���� table���ÿ����� table ����
		String table=cho_table.getSelectedItem();		
		if(table=="���ϼ���"){			
			p_center.removeAll();
			p_center.updateUI();
		}else if	(table=="������") {			
			setTable("emp", empCol);
		} else if(table=="�μ����"){			
			setTable("dept", deptCol);
		}		
	}

	public static void main(String[] args) {
		new TableSwitch();
		
	}


}
