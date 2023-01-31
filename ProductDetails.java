package com.hari.billing;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;

import javax.swing.JTextArea;
import java.awt.Font;


public class ProductDetails extends JFrame {

	private JPanel contentPane;
	private JTable table;
	Bill bill = new BillImpl();
	private JTextField searchBox;
	private JTextField squantity;
	public JLabel Grdtotal;
	public JButton btnSell;
	public JTextField ftextField;
	private JTextField bname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductDetails frame = new ProductDetails();
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
	public ProductDetails() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1071, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setBounds(53, 51, 316, 151);
		String[] columnName = {"ID", "Product Name", "Price", "Quantity"};
		DefaultTableModel tableModel = new DefaultTableModel(columnName, 0);
		table.setModel(tableModel);
		loadTableData();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 51, 410, 304);

		contentPane.add(scrollPane);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new AddProduct().setVisible(true);
			}
		});
		btnAdd.setBackground(Color.GREEN);
		btnAdd.setBounds(10, 366, 69, 32);
		contentPane.add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				int row = table.getSelectedRow();
				Object id = tableModel.getValueAt(row, 0);
				int status = JOptionPane.showConfirmDialog(ProductDetails.this, "Click Yes to Confirm! ","Do You want to delete??",JOptionPane.YES_NO_OPTION);
				if(status==0) {
					bill.deleteProductData(Integer.parseInt(id.toString()));
					loadTableData();
				}
			}
		});
		btnDelete.setBackground(Color.RED);
		btnDelete.setBounds(176, 368, 86, 28);
		contentPane.add(btnDelete);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEdit.setForeground(Color.BLACK);
		btnEdit.setBackground(Color.GREEN);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				int row = table.getSelectedRow();
				Object id = tableModel.getValueAt(row, 0);
				Object name = tableModel.getValueAt(row, 1);
				Object price = tableModel.getValueAt(row, 2);
				Object quantity = tableModel.getValueAt(row, 3);
				AddProduct stdForm = new AddProduct();
				stdForm.idLabel.setText(id.toString());
				stdForm.nameTextField.setText(name.toString());
				stdForm.priceTextField.setText(price.toString());
				stdForm.quantityTextField.setText(quantity.toString());
				dispose();
				stdForm.setVisible(true);
			}
		});
		btnEdit.setBounds(94, 367, 69, 30);
		contentPane.add(btnEdit);
		
		JLabel lblNewLabel = new JLabel("Search");
		lblNewLabel.setBounds(193, 26, 46, 14);
		contentPane.add(lblNewLabel);
		
		searchBox = new JTextField();
		searchBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String searchName = searchBox.getText(); 
				if(searchName==null || searchName.isEmpty()) {
					loadTableData();
				}else {
					searchProductData(searchName);
				}
			}
		});
		searchBox.setBounds(239, 23, 181, 20);
		contentPane.add(searchBox);
		searchBox.setColumns(10);
		
		JTextArea BilTxt = new JTextArea();
		BilTxt.setBounds(430, 53, 615, 249);
		contentPane.add(BilTxt);
		
		btnSell = new JButton("Add To Bill");
		btnSell.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSell.addActionListener(new ActionListener() {
			int i=0;
			double Gtotal = 0;
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				int row = table.getSelectedRow();
				Object id = tableModel.getValueAt(row, 0);
				Object name = tableModel.getValueAt(row, 1);
				Object price = tableModel.getValueAt(row, 2);
				Object quantity = tableModel.getValueAt(row, 3);
				AddProductDAO addProductDAO = new AddProductDAO();
				addProductDAO.setId(Integer.parseInt(id.toString()));
				addProductDAO.setName(name.toString());
				final double Uprice = Integer.valueOf(tableModel.getValueAt(row, 2).toString());
				final String Uproduct = String.valueOf(tableModel.getValueAt(row, 1).toString());
				final double Utotal = Uprice*Integer.parseInt(squantity.getText());
				addProductDAO.setPrice(Integer.parseInt(price.toString()));
				int quantityAfter = Integer.parseInt(quantity.toString())-Integer.parseInt(squantity.getText());
				addProductDAO.setQuantity(quantityAfter);
				
				
				bill.updateBillInfo(addProductDAO);
				loadTableData();
	
				//Billing
				i++;
				if(squantity.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(btnSell, "missing information");
				}else {
					
					Gtotal = Gtotal+Utotal;
					if(i == 1) 
					{
						BilTxt.setText(BilTxt.getText() + "\t\t\tHamro Kirana Pasal\n"
								+"\t\t\tBanepa, Tindobato\n"
								+"Buyer Name:"
								+bname.getText()+"\t\t\t\t\tPAN:424454\n"
										+"\t\t==============SALES INVOICE==============\n"
								+"_______________________________________________________________________________________\n"
								+"SN.\t"+"QUANTITY\t"+"PRICE\t"+"TOTAL\t"+"PRODUCT NAME\n"
								+"_______________________________________________________________________________________\n"
								+i+".\t"+squantity.getText()+"\t"+Uprice+"\t"+Utotal+"\t"+Uproduct+"\n"
								);
					}
					else 
					{
						BilTxt.setText(BilTxt.getText()
								+i+".\t"+squantity.getText()+"\t"+Uprice+"\t"+Utotal+"\t"+Uproduct+"\n"
								);
					}
					ftextField.setText("Rs"+Gtotal);
				}
				
				
			}
			
		});
		btnSell.setBackground(new Color(0, 255, 0));
		btnSell.setBounds(595, 362, 115, 41);
		contentPane.add(btnSell);
		
		JLabel squantitybtn = new JLabel("Quantity");
		squantitybtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		squantitybtn.setBounds(430, 366, 57, 32);
		contentPane.add(squantitybtn);
		
		squantity = new JTextField();
		squantity.setBounds(528, 367, 57, 32);
		contentPane.add(squantity);
		squantity.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Product Details");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(48, 26, 115, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Bill");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(728, 24, 57, 14);
		contentPane.add(lblNewLabel_1_1);
		
		//Printing bill.
		JButton printbtn = new JButton("Print");
		printbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BilTxt.setText(BilTxt.getText()
						+"_______________________________________________________________________________________\n"
						+"Grand Total:"+ftextField.getText()+"");
				try {
					BilTxt.print();
				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		printbtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		printbtn.setBounds(892, 363, 115, 36);
		contentPane.add(printbtn);
		
		ftextField = new JTextField("Rs.");
		ftextField.setFont(new Font("Tahoma", Font.BOLD, 17));
		ftextField.setBounds(909, 315, 86, 34);
		contentPane.add(ftextField);
		ftextField.setColumns(10);
		
		JLabel lblBuyerName = new JLabel("Buyer Name");
		lblBuyerName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblBuyerName.setBounds(430, 313, 88, 32);
		contentPane.add(lblBuyerName);
		
		bname = new JTextField();
		bname.setColumns(10);
		bname.setBounds(528, 313, 182, 32);
		contentPane.add(bname);
	
	}
	public void loadTableData() {
		List<AddProductDAO> stdList = bill.getProducts();
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		for (AddProductDAO std : stdList) {
			tableModel.addRow(new Object[] {
			std.getId(),std.getName(),std.getPrice(),std.getQuantity()
			});
		}
	}
	public void searchProductData(String name) {
		List<AddProductDAO> stdList = bill.searchProduct(name);
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		for (AddProductDAO std : stdList) {
			tableModel.addRow(new Object[] {
			std.getId(),std.getName(),std.getPrice(),std.getQuantity()
			});
		}
	}
}
