package com.getjavajobs.library.ui.dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ����������� ������ � �������� ����� �������������� ����������.
 */
public abstract class AbstractDialogUI {

	/* ������� � ��������� */
	protected static final int dialogWidth = 500;
	protected static final int dialogHeight = 300;
	protected static final int startXPosition = 700;
	protected static final int startYPosition = 200;
	
	/* ����� ������� � ������� ��������, ���� ����������� ��� �������� */
	protected final JDialog dialogFrame;
	protected final JPanel mainPanel;
	
	/* ������ */
	public static final Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
	
	/**
	 * ����������� ������������ �������.
	 * @param parentFrame - ������������ �����
	 * @param dialogTitle - ��������� �������
	 */
	public AbstractDialogUI(JFrame parentFrame, String dialogTitle) {
		this.dialogFrame = new JDialog(parentFrame, dialogTitle);
		
		this.mainPanel = new JPanel();
		this.dialogFrame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.dialogFrame.setSize(dialogWidth, dialogHeight);
		this.dialogFrame.setResizable(false);
		this.dialogFrame.setLocation(startXPosition,startYPosition);
	}
	
	/**
	 * ���������� ���������� ���� ��� ����� � ������� �����.
	 * @param textField - ��������� ����
	 * @param labelText - ����� �����
	 */
	protected void addLabeledTextField(JTextField textField, String labelText) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(300, 30));
		textField.setPreferredSize(new Dimension(200, 30));
		panel.add(new JLabel(labelText), BorderLayout.WEST);
		panel.add(textField, BorderLayout.CENTER);
		
		mainPanel.add(panel);
	}
	
	/**
	 * �������� �������.
	 * @param labelText
	 */
	protected void addLabel(String labelText) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(300, 30));
		panel.add(new JLabel(labelText), BorderLayout.WEST);
		
		mainPanel.add(panel);
	}
	
	/**
	 * �������� ���������� ������ � ������� �����.
	 * @param labelText - �������
	 * @param dataArray - ������, ����������� ���������� ������
	 * @return ������ �� ������������ JComboBox
	 */
	protected JComboBox<Object> addLabeledCombobox(String labelText, Object[] dataArray) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(300, 30));
		panel.add(new JLabel(labelText), BorderLayout.WEST);
		
		JComboBox<Object> comboBox = new JComboBox<>(dataArray);
		comboBox.setSize(150, 30);
		panel.add(comboBox, BorderLayout.CENTER);
		
		mainPanel.add(panel);
		return comboBox;
	}
	
	/**
	 * ���������� ������ � �����-�� ��������� ��� �������
	 * @param buttonText - ����� �� ������
	 * @param buttonAction - �������� ��� �������
	 */
	protected void addButton(String buttonText, ActionListener buttonAction) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(300, 30));
		
		JButton button = new JButton(buttonText);
		button.setCursor(handCursor);
		button.addActionListener(buttonAction);				
		
		panel.add(button, BorderLayout.PAGE_END);
		mainPanel.add(panel);
	}

	/**
	 * ���������� ������ ������ �� ������� - �����, ��� ��� ���������
	 * �������� ��� �������� (������� �� ������� � ����) - ��� "������".
	 * @param parentFrame - ������������ �����.
	 */
	private void addExitButton(final JFrame parentFrame) {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 50));
		JButton exitButton = new JButton("Exit button");
		exitButton.setCursor(handCursor);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialogFrame.dispose();
				parentFrame.setEnabled(true);
				parentFrame.setVisible(true);
			}
		});

		panel.add(exitButton);
		mainPanel.add(panel, BorderLayout.PAGE_END);
	}
		
	/**
	 * ����������� �������� ������� ������� - 
	 * ���������� ����������� ������ � ������� �������.
	 */
	protected void finalActions(final JFrame parentFrame) {
		addExitButton(parentFrame);
		dialogFrame.add(mainPanel);		
		dialogFrame.setVisible(false);
	}
			
	/**
	 * ������� ������ ������� ��� ������������.
	 */
	public void setVisible() {
		dialogFrame.setVisible(true);
	}
	
	/**
	 * ���������, ��� �� ��������� ���� ���������.
	 * @param textFields - ����� ��������� �����
	 * @return true, ���� ��� ���������. ���� ���� �� ���� �� �������� - false.
	 */
	public boolean isTextFieldsFilled(JTextField[] textFields) {
		boolean result = true;
		for (JTextField field : textFields) {
			if (field.getText().isEmpty()) {
				JOptionPane.showMessageDialog(dialogFrame, "You must fill all text fields!", "Text fields warning", JOptionPane.WARNING_MESSAGE);
				result = false;
				break;
			}
		}
		return result;
	}
	
	/**
	 * �������� ��� ��������� ����.
	 * @param textFields - ����� ��������� �����.
	 */
	public void clearTextFields(JTextField[] textFields) {
		for (JTextField field : textFields) {
			field.setText("");
		}
	}
	
}