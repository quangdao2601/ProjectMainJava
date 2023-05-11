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
import java.util.EventObject;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class KhachHangDAL  extends JFrame{
		private JTextField txtMaKH;
		private JTextField txtTenKH;
		private JTextField txtGioiTinh;
		private JTextField txtEmail;
		private JTextField txtNgaySinh;
		private JTextField txtDiaChi;
		private JTextField txtSoDienThoai;

	    private ConnectionDB connectionDB;

	    public KhachHangDAL () {
	        connectionDB = new ConnectionDB();
            }

	    private void loadData(DefaultTableModel model) {
	        String query = "SELECT * FROM khach_hang";
	        ResultSet rs = connectionDB.executeQuery(query);
	        try {
	            // Xóa hết dữ liệu trong model
	            model.setRowCount(0);
	            // Thêm dữ liệu vào model
	            while (rs.next()) {
	            	 String maKH = rs.getString("ma_kh");
	                 String tenKH = rs.getString("ten_kh");
	                 String gioiTinh = rs.getString("gioi_tinh");
	                 String ngaySinh = rs.getString("ngay_sinh");
	                 String email = rs.getString("email");
	                 String diaChi = rs.getString("dia_chi");
	                 String soDienThoai = rs.getString("so_dien_thoai");
	                 model.addRow(new Object[]{maKH, tenKH, gioiTinh, ngaySinh, email, diaChi, soDienThoai});
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    private void addData(DefaultTableModel model) {
	        // Lấy dữ liệu từ các trường nhập liệu
	    	 String maKH = txtMaKH.getText();
	    	 String tenKH = txtTenKH.getText();
	    	 String gioiTinh = txtGioiTinh.getText();
	    	 String ngaySinh = txtNgaySinh.getText();
	    	 String email = txtEmail.getText();
	    	 String diaChi = txtDiaChi.getText();
	    	 String soDienThoai = txtSoDienThoai.getText();

	        // Kiểm tra các trường nhập liệu có trống hay không
	    	 if (maKH.isEmpty() || tenKH.isEmpty() || gioiTinh.isEmpty() || ngaySinh.isEmpty() || email.isEmpty() || diaChi.isEmpty() || soDienThoai.isEmpty()) {
	             JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các thông tin", "Error", JOptionPane.ERROR_MESSAGE);
	             return;
	         }

	        // Thực hiện câu truy vấn để thêm dữ liệu vào cơ sở dữ liệu
	        String query = "INSERT INTO khach_hang(ma_kh, ten_kh, gioi_tinh, ngay_sinh, email, dia_chi, so_dien_thoai) " +
	                "VALUES('" + maKH + "','" + tenKH + "','" + gioiTinh + "','" + ngaySinh + "','" + email + "','" + diaChi + "','" + soDienThoai + "')";

	        boolean result = connectionDB.executeUpdate(query);

	        // Nếu thêm dữ liệu thành công, cập nhật lại bảng
	        if (result) {
	            loadData(model);
	            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công", "Information", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    	 
	        // Xóa các trường nhập liệu sau khi thêm dữ liệu thành công
	        txtMaKH.setText("");
	        txtTenKH.setText("");
	        txtGioiTinh.setText("");
	        txtNgaySinh.setText("");
	        txtEmail.setText("");
	        txtDiaChi.setText("");
	        txtSoDienThoai.setText("");
	    }
	    
	    private void editData(DefaultTableModel model) {
	        // Get the selected row index

	    	 	String maKH = txtMaKH.getText();
	    	    String tenKH = txtTenKH.getText();
	    	    String gioiTinh = txtGioiTinh.getText();
	    	    String ngaySinh = txtNgaySinh.getText();
	    	    String email = txtEmail.getText();
	    	    String diaChi = txtDiaChi.getText();
	    	    String soDienThoai = txtSoDienThoai.getText();

	        // Validate the data
	    	    if (maKH.isEmpty() || tenKH.isEmpty() || gioiTinh.isEmpty() || ngaySinh.isEmpty() || email.isEmpty() || diaChi.isEmpty() || soDienThoai.isEmpty()) {
		             JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các thông tin", "Error", JOptionPane.ERROR_MESSAGE);
		             return;
		         }

	        // Update the data in the database
	        String query = "UPDATE khach_hang SET " +
	                "ten_kh='" + tenKH + "'," +
	                "gioi_tinh='" + gioiTinh + "'," +
	                "ngay_sinh='" + ngaySinh + "'," +
	                "email='" + email + "'," +
	                "dia_chi='" + diaChi + "'," +
	                "so_dien_thoai='" + soDienThoai + "' " +
	                "WHERE ma_kh='" + maKH + "'";
	        boolean result = connectionDB.executeUpdate(query);
	        if (!result) {
	            JOptionPane.showMessageDialog(this, "Chỉnh sửa khách hàng thất bại");
	        }else {
	        	 JOptionPane.showMessageDialog(this, "Chỉnh sửa khách hàng thành công");
	        }
	        txtMaKH.setText("");
	        txtTenKH.setText("");
	        txtGioiTinh.setText("");
	        txtNgaySinh.setText("");
	        txtEmail.setText("");
	        txtDiaChi.setText("");
	        txtSoDienThoai.setText("");
	    }

	    private void deleteData(DefaultTableModel model, JTable table) {
	        // Lấy chỉ số của dòng được chọn
	        int row = table.getSelectedRow();
	        if (row == -1) {
	            JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhân viên để xóa!");
	            return;
	        }

	        // Lấy thông tin của dòng được chọn
	        String maKH = model.getValueAt(row, 0).toString();

	        // Xác nhận xóa dữ liệu
	        int option = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nhân viên này?");
	        if (option == JOptionPane.YES_OPTION) {
	            // Thực hiện xóa dữ liệu trong cơ sở dữ liệu
	            String query = "DELETE FROM khach_hang WHERE ma_kh='" + maKH + "'";
	            boolean result = connectionDB.executeUpdate(query);
	            if (result) {
	                // Xóa dòng được chọn trong JTable
	                model.removeRow(row);
	                JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công!");
	            } else {
	                JOptionPane.showMessageDialog(null, "Xóa nhân viên không thành công!");
	            }
	        }
	    }
}
