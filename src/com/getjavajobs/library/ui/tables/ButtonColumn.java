package com.getjavajobs.library.ui.tables;

import java.awt.Component;

import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 * Класс для создания колонки с кнопочкой вместо значения.
 */
public class ButtonColumn extends AbstractCellEditor 
							implements TableCellRenderer, TableCellEditor {

	private static final long serialVersionUID = 1L;
	
	private JButton renderButton;	// Рисуемая кнопочка.
	private JButton editButton;		// Нажимаемая кнопочка.
	private String text;			// Текст на кнопке.
	
	/**
	 * В конструктор передаем таблицу и номер столбца, в который вставляем кнопку.
	 */
	public ButtonColumn(JTable table, int column, ActionListener actionListener) {
		super();
		
		renderButton = new JButton();					// отрисовываемая кнопка.
		editButton = new JButton();						// рабочая кнопка.

		editButton.setFocusPainted(false);							// кнопку при нажатии - не отрисовываем.
		editButton.addActionListener(actionListener);				// настраиваем действия кнопки.
		
		TableColumnModel columnModel = table.getColumnModel();	// получаем колонки таблицы.
        columnModel.getColumn(column).setCellRenderer( this );	// отрисовщик клетки данной колонки - данный объект
        columnModel.getColumn(column).setCellEditor( this );	// и редактор клетки - тоже данный объект
	}

	
	
	// Возвращает объект, который рисует данную клетку таблицы.
	@Override
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	
		if (hasFocus) {
			renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        } else if (isSelected) {
        	renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        } else {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        renderButton.setText( (value == null) ? "" : value.toString() );
        return renderButton;
	}

	
	// Возвращает объект, который редактирует клетку.
	@Override
	public Component getTableCellEditorComponent(JTable table,
			Object value, boolean isSelected, int row, int column) {
		
		// Заметь, что всякие установки фона опускаются. Потому что это как бы "невидимый" объект.
		text = (value == null) ? "" : value.toString();
        editButton.setText(text);
        return editButton;
	}
	
	// Возвращает значение, ассоциированное с объектом-редактором клетки.
	@Override
	public Object getCellEditorValue() {
		return text;
	}

}