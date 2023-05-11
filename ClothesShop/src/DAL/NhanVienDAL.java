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

public class NhanVienDAL  extends JFrame{
	 	private JTextField txtMaNV;
	    private JTextField txtHoTen;
	    private JTextField txtGioiTinh;
	    private JTextField txtEmail;
	    private JTextField txtNgaySinh;
	    private JTextField txtDiaChi;
	    private JTextField txtSDT;

	    private ConnectionDB connectionDB;

	    public NhanVienDAL () {
	        connectionDB = new ConnectionDB();
	    }

	    private void loadData(DefaultTableModel model) {
	        String query = "SELECT * FROM nhan_vien";
	        ResultSet rs = connectionDB.executeQuery(query);
	        try {
	            // Xóa hết dữ liệu trong model
	            model.setRowCount(0);
	            // Thêm dữ liệu vào model
	            while (rs.next()) {
	                String maNV = rs.getString("ma_nv");
	                String hoTEN = rs.getString("ten_nv");
	                String gioiTinh = rs.getString("gioi_tinh");
	                String email = rs.getString("email");
	                String ngaySinh = rs.getString("ngay_sinh");
	                String diaChi = rs.getString("dia_chi");
	                String SDT = rs.getString("so_dien_thoai");

		            System.out.print(maNV);
	                model.addRow(new String[]{maNV, hoTEN,SDT,email,  diaChi, gioiTinh,  ngaySinh, });
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    private void addData(DefaultTableModel model) {
	        // Lấy dữ liệu từ các trường nhập liệu
	        String maNV = txtMaNV.getText().trim();
	        String hoTen = txtHoTen.getText().trim();
	        String sdt = txtSDT.getText().trim();
	        String email = txtEmail.getText().trim();
	        String diaChi = txtDiaChi.getText().trim();
	        String gioiTinh = txtGioiTinh.getText().trim();
	        String ngaySinh = txtNgaySinh.getText().trim();

	        // Kiểm tra các trường nhập liệu có trống hay không
	        if (maNV.isEmpty() || hoTen.isEmpty() || sdt.isEmpty() || email.isEmpty() || diaChi.isEmpty() || gioiTinh.isEmpty() || ngaySinh.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ dữ liệu", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        // Thực hiện câu truy vấn để thêm dữ liệu vào cơ sở dữ liệu
	        String query = "INSERT INTO nhan_vien(ma_nv, ten_nv, gioi_tinh, email, ngay_sinh, dia_chi, so_dien_thoai) " +
	                "VALUES('" + maNV + "','" + hoTen + "','" + gioiTinh + "','" + email + "','" + ngaySinh + "','" + diaChi + "','" + sdt + "')";

	        boolean result = connectionDB.executeUpdate(query);

	        // Nếu thêm dữ liệu thành công, cập nhật lại bảng
	        if (result) {
	            loadData(model);
	            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công", "Information", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại", "Error", JOptionPane.ERROR_MESSAGE);
	        }

	        // Xóa các trường nhập liệu sau khi thêm dữ liệu thành công
	        txtMaNV.setText("");
	        txtHoTen.setText("");
	        txtSDT.setText("");
	        txtEmail.setText("");
	        txtDiaChi.setText("");
	        txtGioiTinh.setText("");
	        txtNgaySinh.setText("");
	    }
	    
	    private void editData(DefaultTableModel model) {
	        // Get the selected row index

	        // Get the data from the model
	    	  String maNV = txtMaNV.getText().trim();
		        String hoTen = txtHoTen.getText().trim();
		        String sdt = txtSDT.getText().trim();
		        String email = txtEmail.getText().trim();
		        String diaChi = txtDiaChi.getText().trim();
		        String gioiTinh = txtGioiTinh.getText().trim();
		        String ngaySinh = txtNgaySinh.getText().trim();

	        // Validate the data
	        if (maNV.trim().equals("") || hoTen.trim().equals("") || sdt.trim().equals("") || email.trim().equals("") ||
	                diaChi.trim().equals("") || gioiTinh.trim().equals("") || ngaySinh.trim().equals("")) {
	            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ dữ liệu");
	            return;
	        }

	        // Update the data in the database
	        String query = "UPDATE nhan_vien SET ten_nv = '" + hoTen + "', gioi_tinh = '" + gioiTinh + "', email = '" + email +
	                "', ngay_sinh = '" + ngaySinh + "', dia_chi = '" + diaChi + "', so_dien_thoai = '" + sdt +
	                "' WHERE ma_nv = '" + maNV + "'";
	        boolean result = connectionDB.executeUpdate(query);
	        if (!result) {
	            JOptionPane.showMessageDialog(this, "Chỉnh sửa nhân viên thất bại");
	        }else {
	        	 JOptionPane.showMessageDialog(this, "Chỉnh sửa nhân viên thành công");
	        }
	        txtMaNV.setText("");
	        txtHoTen.setText("");
	        txtSDT.setText("");
	        txtEmail.setText("");
	        txtDiaChi.setText("");
	        txtGioiTinh.setText("");
	        txtNgaySinh.setText("");
	    }

	    private void deleteData(DefaultTableModel model, JTable table) {
	        // Lấy chỉ số của dòng được chọn
	        int row = table.getSelectedRow();
	        if (row == -1) {
	            JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhân viên để xóa!");
	            return;
	        }

	        // Lấy thông tin của dòng được chọn
	        String maNV = model.getValueAt(row, 0).toString();

	        // Xác nhận xóa dữ liệu
	        int option = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nhân viên này?");
	        if (option == JOptionPane.YES_OPTION) {
	            // Thực hiện xóa dữ liệu trong cơ sở dữ liệu
	            String query = "DELETE FROM nhan_vien WHERE ma_nv='" + maNV + "'";
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
