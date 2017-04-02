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

//상단에 접속정보를 입력함에 따라 유연하게 데이터를 jtable에 출력할수 있게 만들어본다. 
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
		cho_table.add("▼목록선택");
		cho_table.add("사원목록");
		cho_table.add("부서목록");	
		
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
	
	//cho_table에 따라  select sql에 들어갈 테이블 이름을 정하고, 데이터입력할때쓸 column이름 설정
	public void setTable(String tableName, String[] columnName){
		url=txt_url.getText();
		user=txt_user.getText();
		password=new String(pw.getPassword());
				
		//지우고 보여주기
		p_center.removeAll();
		model=new MyModel(driver, url, user, password, tableName, columnName);
		table=new JTable(model);		
		scroll=new JScrollPane(table);
		p_center.add(scroll);
		p_center.updateUI();		
	}
	
	public void itemStateChanged(ItemEvent e) {
		//choice에서 dbms선택에따라 기본 접속정보변경
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
		
		//choice에서 table선택에따라 table 변경
		String table=cho_table.getSelectedItem();		
		if(table=="▼목록선택"){			
			p_center.removeAll();
			p_center.updateUI();
		}else if	(table=="사원목록") {			
			setTable("emp", empCol);
		} else if(table=="부서목록"){			
			setTable("dept", deptCol);
		}		
	}

	public static void main(String[] args) {
		new TableSwitch();
		
	}


}
