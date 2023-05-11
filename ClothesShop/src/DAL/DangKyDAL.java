package DAL;
import javax.swing.*;

import BUS.ConnectionDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class DangKyDAL extends JFrame{
            private JTextField txtMatk;
	    private JTextField txtTendn;
	    private JTextField txtMatkhau;
	    private JTextField txtNgaytao;
	    private JTextField txtTinhtrang;

	    private ConnectionDB connectionDB;

        ArrayList<String[]> quyenList = new ArrayList<>();

	    public DangKyDAL() {
	        connectionDB = new ConnectionDB();
	        
            }
	    
	    private void addData() {
	    	 String tendn = txtTendn.getText();
	    	 String matkhau = txtMatkhau.getText();
	    	if (checkTenDNExists(tendn)) { 
	    	    JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại");
	    	    return;
	    	}
	    	
	    	if (tendn.isEmpty() || matkhau.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Tên đăng nhập và mật khẩu không được để trống!");
	            return;
	        }
	        
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
	        String ngaytao = LocalDateTime.now().format(formatter);
	        String tinhtrang = txtTinhtrang.getText();

	        String query = String.format(
	                "INSERT INTO TAIKHOAN (TENDN, MATKHAU, NGAYTAO, TINHTRANG) VALUES ('%s', '%s', '%s', '%s')",
	                tendn, matkhau, ngaytao, tinhtrang);
	        boolean result = connectionDB.executeUpdate(query);
	        if (result) {
	            JOptionPane.showMessageDialog(this, "Thêm mới tài khoản thành công");
	        } else {
	            JOptionPane.showMessageDialog(this, "Thêm mới tài khoản thất bại");
	        }
	    }
	    
	    private boolean checkTenDNExists(String hoTen) {
	    	String query = "SELECT COUNT(*) FROM TAIKHOAN WHERE TENDN = '" + hoTen + "'";
	        ResultSet rs = connectionDB.executeQuery(query);
	        try {
	            rs.next();
	            int count = rs.getInt(1);
	            return count > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return true;
	        }
	    }
	    
	    private boolean checkMaTKExists(String maTK) {
	    	String query = "SELECT COUNT(*) FROM TAIKHOAN WHERE MATK = '" + maTK + "'";
	        ResultSet rs = connectionDB.executeQuery(query);
	        try {
	            rs.next();
	            int count = rs.getInt(1);
	            return count > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return true;
	        }
	    }
	    
	    private void editData(DefaultTableModel model) {
	        
	        String matk = txtMatk.getText();
	        String tendn = txtTendn.getText();
	        String matkhau = txtMatkhau.getText();
	        String ngaytao = txtNgaytao.getText();
	        String tinhtrang = txtTinhtrang.getText();

	        String query = String.format(
	                "UPDATE TAIKHOAN SET TENDN = '%s', MATKHAU = '%s', NGAYTAO = '%s', TINHTRANG = '%s', MAQUYEN = '%s' WHERE MATK = '%s'",
	                tendn, matkhau, ngaytao, tinhtrang, matk);
	        boolean result = connectionDB.executeUpdate(query);
	        if (result) {
	            JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thành công");
	        } else {
	            JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thất bại");
	        }
	    }

	    private void deleteData(DefaultTableModel model, JTable table) {
	        int selectedRow = table.getSelectedRow();
	        if (selectedRow == -1) {
	            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
	            return;
	        }
	        String matk = (String) model.getValueAt(selectedRow, 0);
	        String query = "DELETE FROM TAIKHOAN WHERE MATK = '" + matk + "'";
	        boolean result = connectionDB.executeUpdate(query);
	        if (result) {
	            model.removeRow(selectedRow);
	            JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công.");
	        } else {
	            JOptionPane.showMessageDialog(this, "Xóa tài khoản thất bại.");
	        }
	    }
}
