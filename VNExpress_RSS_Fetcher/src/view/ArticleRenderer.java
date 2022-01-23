package view;

import model.Book;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;


public class ArticleRenderer extends JPanel implements ListCellRenderer<Book> {
	private JLabel lblTitle = new JLabel();
	private JLabel lblDecscription = createDescriptionLabel();
	private JLabel lblThumbnail = new JLabel();
	
	public ArticleRenderer() {
		setLayout(new BorderLayout(5,5));
		JPanel panelText = new JPanel(new GridLayout(0,1));
		panelText.add(lblTitle);
     	panelText.add(lblDecscription);
		lblThumbnail.setBounds(0, 0, 2, 1);
		add(lblThumbnail, BorderLayout.WEST);
		add(panelText, BorderLayout.CENTER);
	}
	
	private JLabel createDescriptionLabel() {
		JLabel label = new JLabel();
		
		label.setFont(new Font("Serif", Font.PLAIN, 11));
		
		return label;
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Book> list, Book value, int index, boolean isSelected,
			boolean cellHasFocus) {
			setData(value);
		return this;
	}
	private BufferedImage resize(final URL url, final Dimension size) throws IOException{
	    final BufferedImage image = ImageIO.read(url);
	    final BufferedImage resized = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
	    final Graphics2D g = resized.createGraphics();
	    g.drawImage(image, 0, 0, size.width, size.height, null);
	    g.dispose();
	    return resized;
	}
	
	private void setData(Book book) {
		lblTitle.setText(book.title);
		lblDecscription.setText(book.description);
		try {
			final BufferedImage imageIcon = resize(new URL(book.thumbnail), new Dimension(100, 100));
			ImageIcon Icon = new ImageIcon(imageIcon);
			
			lblThumbnail.setIcon(Icon);
		} catch (Exception e) {
			
		}
		
	}
	

}
