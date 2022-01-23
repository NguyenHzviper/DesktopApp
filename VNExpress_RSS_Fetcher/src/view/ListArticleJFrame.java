package view;
import model.Book;
import services.IRssService;
import services.RssService;
import services.ThoiTrangRssService;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class ListArticleJFrame extends JFrame {
	private int width=350;
	private int height = 200;
	private JList<Book> listBook;
	private IRssService service;
	
	public ListArticleJFrame() {
		
		this.service = new ThoiTrangRssService();
		
		add(createMainPanel());
		setTitle("JListCustomRenderer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension());
		setSize(width,height);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(10,10,10,10));
		panel.add(new JScrollPane(listBook = createListBooks()),
                BorderLayout.CENTER);
        return panel;
	}
	
	private JList<Book> createListBooks() {
		DefaultListModel<Book> model = new DefaultListModel<>();
		ArrayList<Book> books = service.loadRss();
		
		for (Book b : books) {
			model.addElement(b);
		}
				
		JList<Book> list = new JList<Book>(model);
		list.setCellRenderer(new ArticleRenderer());
		return list;
	}
	
}
