package exprotTest;

import java.awt.FileDialog;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class test extends JFrame implements ActionListener{
	private JMenuBar menuBar;
	private String[] menuStr = {"Screen", "File"};
	private JMenu[] menu = new JMenu[menuStr.length];
	private String[] screenItemStr = { "Load", "Hide", "ReShow", "Exit" }; 
	private JMenuItem[] screenItem = new JMenuItem[screenItemStr.length];
	private String[] fileItemStr = { "�̹��� �ҷ�����", "�̹��� �����ϱ�"}; 
	private JMenuItem[] fileItem = new JMenuItem[fileItemStr.length];
	private ImageIcon image;
	private JLabel labImg;
	private String path;
	private FileDialog dialog;
	// ���� Ȯ���� ���� ǥ����
	private final String REFEXP = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
	
	public test() {
		this.setTitle("Menu���� Action ������ �����ϱ�");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		for(int i = 0; i < menu.length; i++) {
			menu[i] = new JMenu(menuStr[i]);
			menuBar.add(menu[i]);	
		}
		
		for(int i = 0; i < screenItem.length; i++) {
			screenItem[i] = new JMenuItem(screenItemStr[i]);
			screenItem[i].addActionListener(this);
			menu[0].add(screenItem[i]);
		}
		
		for(int i = 0; i < fileItem.length; i++) {
			fileItem[i] = new JMenuItem(fileItemStr[i]);
			fileItem[i].addActionListener(this);
			menu[1].add(fileItem[i]);
		}
		
		labImg = new JLabel();
		image = new ImageIcon("D:/mms/exprotTest/src/exprotTest/img1.jpg");
		labImg.setVisible(false);
		labImg.setIcon(image);
		this.add(labImg);
		
		this.setSize(image.getIconWidth(), image.getIconHeight());
		
		
	}

	private FileDialog DialogOption(String option) {
		FileDialog dialog = null;
		if(option.equals("save")) {
			dialog = new FileDialog(this, "�����ϱ�", FileDialog.SAVE);
		} else if (option.equals("load")) {
			dialog = new FileDialog(this, "�ҷ�����", FileDialog.LOAD);
		}
		dialog.setFile("*.jpg");
		dialog.setVisible(true);
		return dialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == screenItem[0] || obj == screenItem[2]) {
			// ���̰� �ϱ�
			labImg.setVisible(true);
		}else if (obj == screenItem[1]) {
			// �Ⱥ��̰� �ϱ�
			labImg.setVisible(false);
		}else if (obj == screenItem[3]) {
			// ����
			int check = JOptionPane.showConfirmDialog(null, "���� �����Ͻðڽ��ϱ�?", "���� �ȳ�", JOptionPane.OK_CANCEL_OPTION);
			if (check == 0){
				System.exit(0);
			}
			JOptionPane.showMessageDialog(null, "��� �ϼ̽��ϴ�!");
			return;
		} else if(obj == fileItem[0]) {
			// �̹��� �ҷ�����
			dialog = DialogOption("load");
			
			path = dialog.getDirectory() + dialog.getFile();
			
			if(path.matches(REFEXP)) {
				image = new ImageIcon(path);
				labImg.setIcon(image);
				this.setSize(image.getIconWidth(), image.getIconHeight());
				labImg.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "�̹��� ���ϸ� �����մϴ�!");
			}
		} else if(obj == fileItem[1]) {
			// �̹��� ����
			dialog = DialogOption("save");
			
			String dirPath = dialog.getDirectory() + dialog.getFile();
			
			Image img = image.getImage();	
			int width = img.getWidth(null);
			int height = img.getHeight(null);
			
			// String fileNm = dirPath.substring(path.lastIndexOf("\\")+1);
			if(!dirPath.matches(REFEXP)) {
				dirPath+=".jpg";
			}
			try {
				BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
				bufferedImage.createGraphics().drawImage(img, 0, 0, this);
				ImageIO.write(bufferedImage, "jpg", new File(dirPath));
			} catch (Exception e1){
				e1.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args) {
		new test();
	}
}