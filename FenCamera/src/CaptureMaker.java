


import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class CaptureMaker extends JFrame {

	JButton capture = new JButton("Capture");
	static JTextField zoneTexte = new JTextField(7);
	static String nomFichier;
	static JLabel largeurHauteur = new JLabel();
	JPanel zoneclient = new JPanel();
	JTextField tx = new JTextField(3);
	JTextField ty = new JTextField(3);
	int x, y;
	JButton path = new JButton("Folder");
	JPanel TFieldPanel = new JPanel();
	JPanel downPanel = new JPanel();
	JPanel ButtonPanel = new JPanel();
	JPanel replace = new JPanel();
	JPanel coordonnees = new JPanel();
	JPanel panXYTField = new JPanel();
	JPanel panZoneTexte = new JPanel();
	JButton imgur = new JButton("Imgur");

	public boolean openLink = true;
	public boolean openImgur = false;
	
	String folderName = "FenCamera";
	
	JPanel panel = new JPanel() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {

			Paint p = new Color(0,0,0,0);
			//Graphics2D g2d = (Graphics2D)g;
			//g2d.setPaint(p);
			//g2d.fillRect(0, 0, getWidth(), getHeight());		
			largeurHauteur.setText("x:" + panel.getWidth() + " y:" + panel.getHeight());
			visiblePanel();
		}
	};

	static Rectangle zone = new Rectangle();

	public CaptureMaker() {

		super("FenCamera V3.9");
		//panel.setBorder(BorderFactory.createLineBorder(Color.RED));
		this.setBackground(new Color(0,0,0,0));
		this.setSize(new Dimension(210,341));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setAlwaysOnTop(true);

		ImageIcon ico = new ImageIcon (this.getClass().getResource("misc/camera.png"));
		this.setIconImage(ico.getImage());


		this.setContentPane(zoneclient);
		this.setLayout(new GridBagLayout());
		capture.setMnemonic('c');
		imgur.setMnemonic('i');
		path.setMnemonic('f');

		downPanel.setPreferredSize(new Dimension(0, 108));
		zoneTexte.setMaximumSize(new Dimension(150,20));



		//panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		//zoneclient.setBorder(BorderFactory.createLineBorder(Color.RED));
		//buttonPanel.setBorder(BorderFactory.createLineBorder(Color.green));
		zoneclient.setLayout(new BorderLayout());
		zoneclient.setOpaque(false);
		zoneclient.add(panel, BorderLayout.CENTER);


		downPanel.setLayout(new GridLayout(1,2));
		zoneclient.add(downPanel, BorderLayout.SOUTH);


		ButtonPanel.setLayout(new GridLayout(3,1));
		ButtonPanel.setBorder(BorderFactory.createEtchedBorder());

		TFieldPanel.setLayout(new GridLayout(3,1));
		TFieldPanel.setBorder(BorderFactory.createEtchedBorder());

		replace.setLayout(new GridLayout(3,1));
		replace.setBorder(BorderFactory.createEtchedBorder());


		panZoneTexte.add(Box.createVerticalGlue());
		panZoneTexte.add(zoneTexte);
		panZoneTexte.setBorder(BorderFactory.createEtchedBorder());
		panZoneTexte.setLayout(new BoxLayout(panZoneTexte, BoxLayout.Y_AXIS));
		panZoneTexte.add(Box.createVerticalGlue());

		coordonnees.add(Box.createGlue());
		coordonnees.add(Box.createHorizontalGlue());		
		coordonnees.add(largeurHauteur);
		coordonnees.add(Box.createHorizontalGlue());
		coordonnees.add(Box.createGlue());
		coordonnees.setBorder(BorderFactory.createEtchedBorder());
		//coordonnees.setLayout(new BoxLayout(coordonnees, BoxLayout.Y_AXIS));

		panXYTField.add(tx);
		panXYTField.add(ty);
		panXYTField.setBorder(BorderFactory.createEtchedBorder());


		ButtonPanel.add(capture);
		ButtonPanel.add(path);
		ButtonPanel.add(imgur);

		TFieldPanel.add(panZoneTexte);
		TFieldPanel.add(coordonnees);
		TFieldPanel.add(panXYTField);

		//============================ppppppppppp=====================


		downPanel.add(ButtonPanel);
		downPanel.add(TFieldPanel);






		tx.setMinimumSize(new Dimension(1,1));

		ty.setMinimumSize(new Dimension(1,1));


		path.setMinimumSize(new Dimension(1,1));
		panel.setMinimumSize(new Dimension(50,82));
		largeurHauteur.setMinimumSize(new Dimension(1,1));

		capture.setMinimumSize(new Dimension(1,1));

		largeurHauteur.setMinimumSize(new Dimension(1,1));

		capture.addActionListener(new appActionListener());
		zoneTexte.addFocusListener(new appFocusListener());
		zoneTexte.addKeyListener(new appKeyListener());
		panel.addKeyListener(new appKeyListener());
		panel.setFocusable(true);
		tx.addKeyListener(new appKeyListener());
		ty.addKeyListener(new appKeyListener());
		tx.addFocusListener(new appFocusListener());
		ty.addFocusListener(new appFocusListener());
		path.addActionListener(new appActionListener());
		imgur.addActionListener(new appActionListener());
	}

	class appKeyListener implements KeyListener{


		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

			if(e.getKeyCode() == KeyEvent.VK_SPACE && panel.isFocusOwner()){

				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("kk-mm-ss");
				String folDate = dateFormat.format(date);
				zoneTexte.setText(folDate);

			}

			if(e.getKeyCode() == KeyEvent.VK_DELETE && panel.isFocusOwner()){
				tx.setText("");
				ty.setText("");
				zoneTexte.setText("");
			}

			if(e.getKeyCode() == KeyEvent.VK_X && panel.isFocusOwner()){
				tx.requestFocus();
			}

			if(e.getKeyCode() == KeyEvent.VK_Y && panel.isFocusOwner()){
				ty.requestFocus();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_I && panel.isFocusOwner()){
				try {
					sendImgur();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			if(e.getKeyCode() == KeyEvent.VK_H && panel.isFocusOwner()){
				if(downPanel.isVisible()){
					downPanel.setVisible(false);
				}
				else{
					downPanel.setVisible(true);
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_Q && panel.isFocusOwner()){
				switchOpenimgur();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_W && panel.isFocusOwner()){
				if(openLink){
					imgur.setForeground(Color.RED);
					imgur.repaint();
					imgur.revalidate();
					openLink = false;
				}
				else{
					imgur.setForeground(Color.BLACK);
					imgur.repaint();
					imgur.revalidate();
					openLink = true;
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_LESS){
				zoneTexte.requestFocus();
			}

			if(e.getSource() == zoneTexte && e.getKeyCode() == KeyEvent.VK_ENTER){
				panel.requestFocus();
			}

			if(e.getKeyCode() == KeyEvent.VK_NUMPAD1 && panel.isFocusOwner()){

				try {
					CaptureCurrentScreen("current");
				} catch (AWTException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}

			if(e.getKeyCode() == KeyEvent.VK_NUMPAD2 && panel.isFocusOwner()){
				try {
					CaptureCurrentScreen("all");
				} catch (AWTException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			if(e.getKeyCode() == KeyEvent.VK_NUMPAD0 && !ty.isFocusOwner() && !tx.isFocusOwner() && !zoneTexte.isFocusOwner()){
				resetSize();
				System.out.println("resetSize");
			}

			if((e.getSource() == tx || e.getSource() == ty) && tx.getText() != "" && ty.getText() != "" && e.getKeyCode() == KeyEvent.VK_ENTER){

				try{
					x = Integer.valueOf(tx.getText()) + 10;
					y = Integer.valueOf(ty.getText()) + 141;

					panel.requestFocus();

					customResize();

				}
				catch(NumberFormatException nfe){
					System.out.println("not a number");
					panel.requestFocus();
					tx.setText("");
					ty.setText("");
				}

			}

			if (e.getSource() != zoneTexte && e.getSource() != tx && e.getSource() != ty && e.getKeyCode() == KeyEvent.VK_ENTER){

				capture.doClick();

			}

			if(panel.isFocusOwner() && e.getKeyCode() == KeyEvent.VK_RIGHT){

				if(e.isShiftDown() && e.isControlDown()){
					down();
					right();
				}
				else if(e.isAltDown()){
					right10();
					System.out.println("listener goright");
				}
				else if(e.isControlDown()){
					goRight10();
					System.out.println("listener goright");
				}
				else if(e.isShiftDown()){
					goRight();
					System.out.println("listener goright");
				}
				else{
					right();
					System.out.println("right");
				}
			}

			if(panel.isFocusOwner() && e.getKeyCode() == KeyEvent.VK_DOWN){

				if(e.isShiftDown() && e.isControlDown()){
					up10();
					left10();
				}
				else if(e.isAltDown()){
					down10();
				}
				else if(e.isControlDown()){
					goDown10();
				}
				else if(e.isShiftDown()){
					goDown();
				}
				else{
					down();
					System.out.println("down");
				}
			}

			if(panel.isFocusOwner() && e.getKeyCode() == KeyEvent.VK_LEFT){

				if(e.isShiftDown() && e.isControlDown()){
					up();
					left();
				}
				else if(e.isAltDown()){
					left10();
				}
				else if(e.isControlDown()){
					goLeft10();
				}
				else if(e.isShiftDown()){
					goLeft();
				}
				else{
					left();
					System.out.println("left");
				}
			}

			if(panel.isFocusOwner() && e.getKeyCode() == KeyEvent.VK_UP){

				if(e.isShiftDown() && e.isControlDown()){
					down10();
					right10();
				}
				else if(e.isAltDown()){
					up10();
				}
				else if(e.isControlDown()){
					goUp10();
				}
				else if(e.isShiftDown()){
					goUp();
				}

				else{
					up();
					System.out.println("up");
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	class appFocusListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == zoneTexte){
				zoneTexte.setText("");
			}

			if(e.getSource() == tx){
				tx.setText("");
			}

			if(e.getSource() == ty){
				ty.setText("");
			}

		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub


		}

	}

	class appActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			if(e.getSource() == imgur){
				try {
					sendImgur();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("ça a merdé!");
				}
			}

			if(e.getSource() == path){
				try {
					openPath();
					panel.requestFocus();
					minimize();
				} catch (IOException e1) {
					// TODO Auto-generated catch b
					e1.printStackTrace();
				}
			}






			if(e.getSource() == capture){

				buttonCapture();

			}
		}

	}

	public void buttonCapture(){
		try {
			zone.setBounds((int)(panel.getLocationOnScreen().getX()), (int)(panel.getLocationOnScreen().getY()), panel.getWidth(), panel.getHeight());
			capture();
			panel.requestFocus();
			if(openImgur){
				java.awt.Desktop.getDesktop().browse(new URI("http://imgur.com/?"));
				panel.requestFocus();
				minimize();
			}
		} catch (AWTException | IOException | URISyntaxException e1) {
			// TODO Auto-generated catch b
			e1.printStackTrace();
		}
	}

	private void CaptureCurrentScreen(String option) throws AWTException, IOException{
		int numEcran = 0;
		int x1=0, y1=0, x2=0, y2=0, x3=0, y3=0;
		int w1=0, h1=0, w2=0, h2=0, w3=0, h3=0;

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		for(GraphicsDevice curGs : gs){

			GraphicsConfiguration[] gc = curGs.getConfigurations();
			for(GraphicsConfiguration curGc : gc){

				Rectangle bounds = curGc.getBounds();
				numEcran += 1;
				System.out.println("Ecran n°" + numEcran + "\nPlacement en x,y: " + (int)bounds.getX()
						+ "," + (int)bounds.getY() + "\nDimensions :" + (int)bounds.getWidth() + "x" + (int)bounds.getHeight() +
						" pxl\n");

				if (numEcran == 1){
					x1 = (int)bounds.getX();
					y1 = (int)bounds.getY();
					w1 = (int)bounds.getWidth();
					h1 = (int)bounds.getHeight();
				}

				if (numEcran == 2){
					x2 = (int)bounds.getX();
					y2 = (int)bounds.getY();
					w2 = (int)bounds.getWidth();
					h2 = (int)bounds.getHeight();
				}

				if (numEcran == 3){
					x3 = (int)bounds.getX();
					y3 = (int)bounds.getY();
					w3 = (int)bounds.getWidth();
					h3 = (int)bounds.getHeight();
				}
			}
		}

		//calcul de l'emplacement de la fenetre

		int frameX = this.getX();
		System.out.println("framex = " + frameX);
		String output;
		Rectangle zone = new Rectangle();

		if (frameX <= w1){
			zone.setSize(w1, h1);
			zone.setLocation(x1, y1);
			output = "premier écran!";
		}
		else if (frameX <= w1 + w2){
			zone.setSize(w2, h2);
			zone.setLocation(x2, y2);
			output = "second écran!";
		}
		else if (frameX <= w1 + w2 + w3){
			zone.setSize(w3, h3);
			zone.setLocation(x3, y3);
			output = "troisième écran!";
		}
		else {
			zone.setSize(0, 0);
			zone.setLocation(1, 1);
			output = ":erreur d'écran:";
			resetSize();
		}
		if (option.equals("all")){

			int maxHeight = h1;

			if (maxHeight < h2){
				maxHeight = h2;
			}

			if (maxHeight < h3){
				maxHeight = h3;
			}

			System.out.println("all captured");
			zone.setSize(w1 + w2 + w3, maxHeight);
			zone.setLocation(0, 0);
		}
		fullscreen(zone);
		System.out.println("\nCapturé: \nx: " + zone.getX() + "\ny: " + zone.getY() + "\nWidth: " 
				+ zone.getWidth() + "\nHeight: " + zone.getHeight() + "\ncapturé sur le " + output + "\n");

	}




	private void createDirectory(){

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String folDate = dateFormat.format(date);
		System.out.println("test: " + folDate);

		String directoryName = folderName + "/" + folDate;
		File theDir = new File(directoryName);

		// if the directory does not exist, create it
		if (!theDir.exists())
		{
			System.out.println("creating directory: " + directoryName);
			theDir.mkdirs();

			System.out.println("created: " + theDir);

		}
	}



	public void visiblePanel(){
		if(panel.getWidth() < 185){


			replace.add(coordonnees);
			replace.add(panZoneTexte);
			replace.add(capture);

			downPanel.remove(ButtonPanel);
			downPanel.remove(TFieldPanel);
			downPanel.add(replace);
		}
		else{

			ButtonPanel.add(capture);
			ButtonPanel.add(path);
			ButtonPanel.add(imgur);

			TFieldPanel.add(panZoneTexte);
			TFieldPanel.add(coordonnees);
			TFieldPanel.add(panXYTField);

			downPanel.remove(replace);
			downPanel.add(ButtonPanel);
			downPanel.add(TFieldPanel);
		} 
	}


	public void minimize(){
		this.setExtendedState(JFrame.ICONIFIED);
	}

	public void maximize(){
		this.setExtendedState(JFrame.NORMAL);
	}



	public void openPath() throws IOException{

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String folDate = dateFormat.format(date);

		createDirectory();

		Desktop desktop = Desktop.getDesktop();
		desktop.open(new File(folderName + "/" + folDate + "/"));
	}

	public void fullscreen(Rectangle zone) throws AWTException, IOException{
		try{
			minimize();

			Robot robot = new Robot();

			BufferedImage screenShot = robot.createScreenCapture(zone);


			if(zoneTexte.getText().equals("Clipboarded!") || zoneTexte.getText().equals("Saved!")){
				zoneTexte.setText("");
			}

			if (zoneTexte.getText().equals("")){

				System.out.println("\nClipboard loaded:\nCapture size: " + panel.getWidth() + " x " + panel.getHeight() + " pxl");
				java.awt.Toolkit.getDefaultToolkit().beep();
				zoneTexte.setText("Clipboarded!");

				//=============
				Toolkit.getDefaultToolkit()
				.getSystemClipboard()
				.setContents(new ImageTransferable(screenShot), null);
				//============
			}
			else{
				createDirectory();

				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
				String folDate = dateFormat.format(date);

				//String filePath = new File("").getAbsolutePath();
				nomFichier = zoneTexte.getText();
				File file = new File(folderName + "/" + folDate + "/" + nomFichier + ".png");
				ImageIO.write(screenShot, "png", file);
				zoneTexte.setText("Saved!");
			}

			maximize();
		}catch(Exception e){

		}


		/* vieux code
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int)dimension.getHeight();
		int width  = (int)dimension.getWidth();

		this.setBounds(-5, -28, width + 10, height + 141);*/
	}

	public void customResize(){
		this.setBounds(this.getX(), this.getY(), x, y);
	}

	public void resetSize(){

		this.setBounds(0, 0, 210, 341);
		this.setLocationRelativeTo(null);
	}

	public void up(){
		System.out.println("function");
		int x;
		x = this.getHeight();
		x--;
		System.out.println(x);
		if (x <=51)x = 151;
		this.setBounds(this.getX(), this.getY(), this.getWidth(), x);
	}

	public void up10(){
		System.out.println("function");
		int x;
		x = this.getHeight();
		x -= 10;
		System.out.println(x);
		if (x <=151)x = 151;
		this.setBounds(this.getX(), this.getY(), this.getWidth(), x);
	}

	public void goUp(){
		System.out.println("godown");
		int x;
		x = this.getY();
		x--;
		this.setLocation(this.getX(), x);
	}

	public void goUp10(){
		System.out.println("godown");
		int x;
		x = this.getY();
		x = x - 10;
		this.setLocation(this.getX(), x);
	}

	public void left(){
		System.out.println("function");
		int x;
		x = this.getWidth();
		x--;
		if (x <= 20)x = 20;
		this.setBounds(this.getX(), this.getY(), x, this.getHeight());

	}

	public void left10(){
		System.out.println("function");
		int x;
		x = this.getWidth();
		x -= 10;
		if (x <= 20)x = 20;
		this.setBounds(this.getX(), this.getY(), x, this.getHeight());

	}

	public void goLeft(){
		System.out.println("goLeft");
		int x;
		x = this.getX();
		x--;
		this.setLocation(x, this.getY());
	}

	public void goLeft10(){
		System.out.println("goLeft");
		int x;
		x = this.getX();
		x = x - 10;
		this.setLocation(x, this.getY());
	}


	public void right(){
		System.out.println("function");
		int x;
		x = this.getWidth();
		x++;
		if (x >= 1920)x = 1920;
		this.setBounds(this.getX(), this.getY(), x, this.getHeight());

	}

	public void right10(){
		System.out.println("function");
		int x;
		x = this.getWidth();
		x+=10;
		if (x >= 1920)x = 1920;
		this.setBounds(this.getX(), this.getY(), x, this.getHeight());

	}

	public void goRight(){
		System.out.println("goright");
		int x;
		x = this.getX();
		x++;
		this.setLocation(x, this.getY());
	}

	public void goRight10(){
		System.out.println("goright");
		int x;
		x = this.getX();
		x = x + 10;
		this.setLocation(x, this.getY());
	}

	public void down(){
		int x;
		x = this.getHeight();
		x++;
		System.out.println(x);
		if (x >= 1080)x = 1080;
		this.setBounds(this.getX(), this.getY(), this.getWidth(), x);

	}

	public void down10(){
		int x;
		x = this.getHeight();
		x+=10;
		System.out.println(x);
		if (x >= 1080)x = 1080;
		this.setBounds(this.getX(), this.getY(), this.getWidth(), x);

	}

	public void goDown(){
		System.out.println("godown");
		int x;
		x = this.getY();
		x++;
		this.setLocation(this.getX(), x);
	}

	public void goDown10(){
		System.out.println("godown");
		int x;
		x = this.getY();
		x = x + 10;
		this.setLocation(this.getX(), x);
	}

	public void switchOpenimgur(){
		if(openImgur){
			openImgur = false;
			capture.setForeground(Color.black);
		}
		else{
			openImgur = true;
			capture.setForeground(Color.red);
		}
	}
	
	public void capture() throws AWTException, IOException {

		if(zoneTexte.getText().equals("Clipboarded!") || zoneTexte.getText().equals("Saved!")){
			zoneTexte.setText("");
		}

		BufferedImage screencapture = new Robot().createScreenCapture(zone);
		if (zoneTexte.getText().equals("")){

			System.out.println("\nClipboard loaded:\nCapture size: " + panel.getWidth() + " x " + panel.getHeight() + " pxl");
			java.awt.Toolkit.getDefaultToolkit().beep();
			zoneTexte.setText("Clipboarded!");

			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new ImageTransferable(screencapture), null);


		}
		else{
			createDirectory();

			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String folDate = dateFormat.format(date);

			//String filePath = new File("").getAbsolutePath();
			nomFichier = zoneTexte.getText();
			File file = new File(folderName + "/" + folDate + "/" + nomFichier + ".png");
			ImageIO.write(screencapture, "png", file);
			zoneTexte.setText("Saved!");
			//System.out.println("\nfile " + nomFichier + ".png saved.\nSize: " + panel.getWidth() + " x " + panel.getHeight() + " pxl\nPath: " + filePath);

		}
	}

	public void sendImgur() throws Exception{
		zone.setBounds((int)(panel.getLocationOnScreen().getX()), (int)(panel.getLocationOnScreen().getY()), panel.getWidth(), panel.getHeight());
		System.out.println("sent Imgur!");
		//creation of the image
		BufferedImage screencapture = new Robot().createScreenCapture(zone);
		java.awt.Toolkit.getDefaultToolkit().beep();
		zoneTexte.setText("Clipboarded!");
		
		
		//create base64 image
	    BufferedImage image = null;
	    File file = new File("image.png");
	    ImageIO.write(screencapture, "png", file);
	    //read image
	    image = ImageIO.read(file);
	    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
	    ImageIO.write(image, "png", byteArray);
	    byte[] byteImage = byteArray.toByteArray();
	    String dataImage = new String(Base64.getEncoder().encode(byteImage));
	    String data = URLEncoder.encode("image", "UTF-8") + "="
	    + URLEncoder.encode(dataImage, "UTF-8");
		
	    String clientID = "e596438eff3854d";
	    String link = getImgurContent(clientID, data);
		
		//putting string in the clipboard
		System.out.println("----Link:" + link);
		link = link.substring(link.indexOf("http"), link.indexOf(".png") + 4);
		link = link.replace("\\", "");
		System.out.println("----Link:" + link);
		String myString = link;
		StringSelection stringSelection = new StringSelection(myString);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		
		clpbrd.setContents(stringSelection, null);
		
		if(openLink){
			Desktop.getDesktop().browse(new URL(link.replace("\\", "/")).toURI());
		}
		
	}

	public static String getImgurContent(String clientID, String data) throws Exception {
	    URL url;
	    url = new URL("https://api.imgur.com/3/image");
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	    conn.setDoOutput(true);
	    conn.setDoInput(true);
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Authorization", "Client-ID " + clientID);
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

	    conn.connect();
	    StringBuilder stb = new StringBuilder();
	    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	    wr.write(data);
	    wr.flush();

	    // Get the response
	    BufferedReader rd = new BufferedReader(
	            new InputStreamReader(conn.getInputStream()));
	    String line;
	    while ((line = rd.readLine()) != null) {
	        stb.append(line).append("\n");
	    }
	    wr.close();
	    rd.close();

	    return stb.toString();
	}
	
	static final class ImageTransferable implements Transferable {
		final BufferedImage image;

		public ImageTransferable(final BufferedImage image) {
			this.image = image;
		}

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[] {DataFlavor.imageFlavor};
		}

		@Override
		public boolean isDataFlavorSupported(final DataFlavor flavor) {
			return DataFlavor.imageFlavor.equals(flavor);
		}

		@Override
		public Object getTransferData(final DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			if (isDataFlavorSupported(flavor)) {
				return image;
			}

			throw new UnsupportedFlavorException(flavor);
		}
	};

}