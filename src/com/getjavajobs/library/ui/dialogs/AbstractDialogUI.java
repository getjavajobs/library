package com.getjavajobs.library.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Абстрактный диалог с наиболее часто встречающимися операциями.
 */
public abstract class AbstractDialogUI {

	/* Размеры и положение */
	protected static final int dialogWidth = 500;
	protected static final int dialogHeight = 300;
	protected static final int startXPosition = 700;
	protected static final int startYPosition = 200;
	
	/* Форма диалога и главная панелька, куда добавляются все элементы */
	protected final JDialog dialogFrame;
	protected final JPanel mainPanel;
	
	/* Прочее */
	public static final Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
	
	/**
	 * Конструктор абстрактного диалога.
	 * @param parentFrame - родительский фрейм
	 * @param dialogTitle - заголовок диалога
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
	 * Добавление текстового поля для ввода и надписи рядом.
	 * @param textField - текстовое поле
	 * @param labelText - текст рядом
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
	 * Добавить надпись.
	 * @param labelText
	 */
	protected void addLabel(String labelText) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(300, 30));
		panel.add(new JLabel(labelText), BorderLayout.WEST);
		
		mainPanel.add(panel);
	}
	
	/**
	 * Добавить выпадающий список и надпись рядом.
	 * @param labelText - надпись
	 * @param dataArray - данные, заполняющие выпадающий список
	 * @return ссылка на получившийся JComboBox
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
	 * Добавление кнопки с каким-то действием при нажатии
	 * @param buttonText - текст на кнопке
	 * @param buttonAction - действие при нажатии
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
	 * Добавление кнопки выхода из диалога - нужна, так как дефолтное
	 * действие при закрытии (нажатии на крестик в углу) - это "ничего".
	 * @param parentFrame - родительский фрейм.
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
	 * Завершающие действия каждого диалога - 
	 * добавление построенной панели и скрытие диалога.
	 */
	protected void finalActions(final JFrame parentFrame) {
		addExitButton(parentFrame);
		dialogFrame.add(mainPanel);		
		dialogFrame.setVisible(false);
	}
			
	/**
	 * Сделать диалог видимым для пользователя.
	 */
	public void setVisible() {
		dialogFrame.setVisible(true);
	}
	
	/**
	 * Проверить, все ли текстовые поля заполнены.
	 * @param textFields - набор текстовых полей
	 * @return true, если все заполнены. Если хотя бы один не заполнен - false.
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
	 * Очистить все текстовые поля.
	 * @param textFields - набор текстовых полей.
	 */
	public void clearTextFields(JTextField[] textFields) {
		for (JTextField field : textFields) {
			field.setText("");
		}
	}
	
}