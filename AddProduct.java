package com.hari.billing;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;

public class AddProduct extends JFrame {

	public JPanel contentPane;
	public JTextField nameTextField;
	public JTextField priceTextField;
	public JLabel quantity;
	public JLabel name;
	public JLabel price;
	public JTextField quantityTextField;
	public JLabel idLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddProduct frame = new AddProduct();
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
	public AddProduct() {
		setTitle("Sajilo Billing");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 439, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		name = new JLabel("Product Name");
		name.setFont(new Font("Tahoma", Font.BOLD, 12));
		name.setBounds(10, 25, 100, 24);
		contentPane.add(name);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(135, 21, 219, 33);
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);
		
		price = new JLabel("Price");
		price.setFont(new Font("Tahoma", Font.BOLD, 12));
		price.setBounds(10, 74, 100, 24);
		contentPane.add(price);
		
		priceTextField = new JTextField();
		priceTextField.setColumns(10);
		priceTextField.setBounds(135, 76, 219, 33);
		contentPane.add(priceTextField);
		
		quantity = new JLabel("Quantity");
		quantity.setFont(new Font("Tahoma", Font.BOLD, 12));
		quantity.setBounds(10, 136, 100, 24);
		contentPane.add(quantity);
		
		quantityTextField = new JTextField();
		quantityTextField.setColumns(10);
		quantityTextField.setBounds(135, 138, 219, 33);
		contentPane.add(quantityTextField);
		
		JButton addBtn = new JButton("Add");
		addBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		addBtn.setBackground(Color.GREEN);
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText();
				String price = priceTextField.getText();
				String quantity = quantityTextField.getText();
				Bill bill = new BillImpl();
				AddProductDAO addProductDAO = new AddProductDAO();
				addProductDAO.setName(name);
				addProductDAO.setPrice(Integer.parseInt(price));
				addProductDAO.setQuantity(Integer.parseInt(quantity));
				String stdId = idLabel.getText();
				if(stdId == null || stdId.isEmpty()) {
					bill.saveBillInfo(addProductDAO);
				}else {
					addProductDAO.setId(Integer.parseInt(stdId));
					bill.updateBillInfo(addProductDAO);
				}
				new ProductDetails().setVisible(true);
			}
		});
		addBtn.setBounds(135, 193, 76, 38);
		contentPane.add(addBtn);
		
		JButton cancelBtn = new JButton("Product List");
		cancelBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		cancelBtn.setBackground(new Color(128, 255, 128));
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ProductDetails().setVisible(true);
			}
			
		});
		cancelBtn.setBounds(221, 193, 135, 38);
		contentPane.add(cancelBtn);
		
		idLabel = new JLabel("");
		idLabel.setBounds(364, 21, 44, 28);
		contentPane.add(idLabel);
	}
}
