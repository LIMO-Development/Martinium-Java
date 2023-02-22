package com.limodevelopment.limobrowser.app;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.plaf.TabbedPaneUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import javax.crypto.BadPaddingException;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.GridLayout;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BrowserApp extends JFrame {

    // browser version
    public String bVersion = "v0.0.1";

    // default ui colors
    public Color defBg = new Color(255, 161, 0), defBc = new Color(255, 150, 0), defFg = new Color(140, 140, 140);

    // customizeable ui colors
    public Color navbarBg, navbarBc, sidebarBg, sidebarBc, fg;
    
    // default font
    public Font font = new Font("Times New Roman", Font.PLAIN, 40);

    // ui elems
    public JPanel browserApp, browserTitlebar;
    public JTabbedPane browserContent;

    public BrowserApp() {
        // sizes
        int size1 = Toolkit.getDefaultToolkit().getScreenSize().width, size2 = Toolkit.getDefaultToolkit().getScreenSize().height - 105;

        // window setup
        this.setTitle("Martinium " + bVersion);
        this.setSize(size1, Toolkit.getDefaultToolkit().getScreenSize().height - 75);
        this.setLocation(0, 0);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // browser app setup
        browserApp = new JPanel();
        browserApp.setLayout(null);
        browserApp.setBackground(defBg);
        
        // titlebar
        browserTitlebar = new JPanel();
        browserTitlebar.setLayout(null);
        browserTitlebar.setBackground(defBg);
        browserTitlebar.setBounds(0, 0, size1, 30);
        
        // logo
        Icon appicon = new ImageIcon("resources/app_logo.png");
        JLabel appiconLabel = new JLabel(appicon);
        appiconLabel.setBounds(0, 0, 30, 30);
        appiconLabel.setBackground(defBg);
        
        // title
        JLabel apptitle = new JLabel("Martinium " + bVersion);
        apptitle.setBounds(size1 / 2, 0, 300, 30);
        
        // titlebar buttons container
        JPanel tbBtnCont = new JPanel();
        tbBtnCont.setBackground(defBg);
        tbBtnCont.setLayout(null);
        tbBtnCont.setBounds(size1 - 30, 0, 60, 60);
        
        // titlebar buttons
        JLabel closeBtn = new JLabel("X");
        closeBtn.setBounds(10, 5, 15, 25);
        closeBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        

        // browser content setup
        browserContent = new JTabbedPane();
        browserContent.setBounds(0, 30, size1, size2);
        browserContent.setBackground(defBg);
        browserContent.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {

            }

            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
            {
                g.setColor(defBg);
                g.fillRect(x, y, w, h);
            }

            protected FocusListener createFocusListener() {
                return null;
            }
        });

        // adders
        this.add(browserApp);
        browserApp.add(browserTitlebar);
        browserApp.add(browserContent);
        browserTitlebar.add(appiconLabel);
        browserTitlebar.add(apptitle);
        browserTitlebar.add(tbBtnCont);
        tbBtnCont.add(closeBtn);
        browserContent.addTab("New Tab", newtab());
        browserContent.addTab("New Tab", newtab());

        // visibility setter
        this.setVisible(true);
    }
    
    public boolean isSidebarOn = true;

    public JPanel newtab() {
    	// page counter
    	
        // tab size
        int tabWidth = Toolkit.getDefaultToolkit().getScreenSize().width, tabHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 105;
        int sbWidth = 25;

        // tab
        JPanel tab = new JPanel();
        tab.setLayout(null);
        tab.setBounds(0, 0, tabWidth, tabHeight);

        // navbar
        JPanel navbar = new JPanel();
        navbar.setBackground(defBg);
        navbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, defBc));
        navbar.setBounds(0, 0, tabWidth, sbWidth);
        navbar.setLayout(null);

        // address bar
        JTextField addressbar = new JTextField();
        addressbar.setBounds(5, 5, tabWidth - 100, sbWidth - 10);
        addressbar.setBorder(BorderFactory.createLineBorder(defBc, 1));
        addressbar.setBackground(defBc);
        
        // sidebar on/off switch
        JLabel sbSwitch = new JLabel("ON");
        sbSwitch.setBounds(tabWidth - 55, 5, 25, 25);
        sbSwitch.setBackground(defBc);
        
        // go-to-url button
        JLabel goBtn = new JLabel("Go");
        goBtn.setBounds(tabWidth - 80, 5, 25, 25);
        goBtn.setBackground(defBc);
        
        // go-back button
        JLabel goBackBtn = new JLabel("<");
        goBackBtn.setBounds(tabWidth - 30, 5, 25, 25);
        
        // sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(null);
        sidebar.setBackground(defBg);
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, defBc));
        sidebar.setBounds(0, sbWidth, sbWidth, tabHeight);
        
        // sidebar stuff
        Font sbFont = new Font("Times New Roman", Font.PLAIN, sbWidth - 5);
        
        JLabel tabAdder = new JLabel("T+");
        tabAdder.setBounds(0, 0, sbWidth, sbWidth);
        tabAdder.setForeground(defFg);
        tabAdder.setFont(sbFont);
        
        JLabel tabRemover = new JLabel("T-");
        tabRemover.setBounds(0, sbWidth, sbWidth, sbWidth);
        tabRemover.setForeground(defFg);
        tabRemover.setFont(sbFont);
        
        // land page
        int lPageOffset = sbWidth;
        int lPageWidth = tabWidth - lPageOffset, lPageHeight = tabHeight - lPageOffset;
        BrowserView landpage = new BrowserView();
        
        // land page container
        JScrollPane landpageCont = new JScrollPane(landpage);
        landpageCont.setBackground(defBg);
        landpageCont.setBorder(BorderFactory.createLineBorder(defBc, 1));
        landpageCont.setBounds(lPageOffset, lPageOffset, lPageWidth - 4, lPageHeight - 27);
        landpageCont.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        landpageCont.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        landpageCont.getHorizontalScrollBar().setBackground(new Color(15, 15, 15));
        landpageCont.getVerticalScrollBar().setBackground(new Color(15, 15, 15));
        landpageCont.getHorizontalScrollBar().setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, defBg));
        landpageCont.getVerticalScrollBar().setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, defBg));
        //landpage.setSize(lPageWidth - 4, lPageHeight - 27);
        
        // listeners
        sbSwitch.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (isSidebarOn) {
					sidebar.setSize(0, 0);
					landpageCont.setBounds(0, lPageOffset, (lPageWidth + lPageOffset) - 4, lPageHeight - 27);
					//Dimension d = landpageCont.getSize();
					//landpage.setSize(d.width, d.height);
					sbSwitch.setText("OFF");
					isSidebarOn = false;
				} else {
					sidebar.setSize(sbWidth, tabHeight);
					landpageCont.setBounds(lPageOffset, lPageOffset, lPageWidth - 4, lPageHeight - 27);
					//Dimension d = landpageCont.getSize();
					//landpage.setSize(d.width, d.height);
					sbSwitch.setText("ON");
					isSidebarOn = true;
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        goBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				String text = addressbar.getText();
				landpage.navigate(text);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        tabAdder.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				browserContent.addTab("New Tab", newtab());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        tabRemover.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int currIndex = browserContent.getSelectedIndex();
				switch (currIndex) {
					case 0:
						dispose();
						break;
						
					default:
						browserContent.removeTabAt(currIndex);
						break;
				}
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        goBackBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				landpage.goBack();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        });

        // adders
        tab.add(navbar);
        tab.add(sidebar);
        tab.add(landpageCont);
        navbar.add(addressbar);
        navbar.add(sbSwitch);
        navbar.add(goBtn);
        navbar.add(goBackBtn);
        sidebar.add(tabAdder);
        sidebar.add(tabRemover);

        return tab;
    }

    public void fileCheck() {
        String os = System.getProperty("os.name").toUpperCase(), home = System.getProperty("user.home");
        File homeFolder = new File(home);
        if (!homeFolder.exists()) {
            print("[ERROR]: HOW DOES THIS USER NOT HAVE A HOME FOLDER???");
        } else {
            if (os.startsWith("WIN")) {
                
            } else if (os.startsWith("LINUX")) {

            }
        }
        
    }

    public void applyOuterTheme(String os, JPanel navbar) {
        if (os.startsWith("WIN")) {

        } else if (os.startsWith("LINUX")) {

        }
    }

    public void applyInnerTheme(String os, JPanel... elems) {
        if (os.startsWith("WIN")) {

        } else if (os.startsWith("LINUX")) {

        }
    }

    public void writeThemeFile() {

    }

    public void print(Object x) {
        System.out.println(x);
    }

    public class BrowserView extends JPanel {
    	
    	public int currPageIndex = 0;
    	public List<String> history = new ArrayList<>();
    	
    	public BrowserView() {
    		this.setBackground(new Color(15, 15, 15));
    		this.setLayout(null);
    		navigate("mart://home");
    	}
    	
    	public BrowserView(String url) {
    		this.setBackground(new Color(15, 15, 15));
    		this.setLayout(null);
    		navigate(url);
    	}
    	
    	public void navigate(String url) {
    		if (url == "mart://home") {
    			loadHomePage();
    			history.add(url);
    			currPageIndex++;
    		} else if (url.contains(".") && !url.contains(" ")){
    			if (url.startsWith("https://") || url.startsWith("http://")) {
    				try {
						this.setPage(url);
						history.add(url);
						currPageIndex++;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			} else {
    				try {
    					this.setPage("http://" + url);
    					history.add("http://" + url);
    					currPageIndex++;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    		} else {
    			try {
    				this.setPage("https://www.google.com/search?q=" + url);
    				history.add("https://www.google.com/search?q=" + url);
    				currPageIndex++;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	
    	public void goBack() {
    		switch (currPageIndex) {
    			case 0:
    				break;
    				
    			default: 
    				navigate(history.get(prevPageIndex()));
    				currPageIndex -= 1;
    				break;
    		}
    	}
    	
    	public int prevPageIndex() {
    		return currPageIndex - 1;
    	}
    	
    	public void setPage(String url) throws IOException {
    		int topIndex = 0;
    		this.removeAll();
    		Document doc = Jsoup.parse(new URL(url), 0);
    		for (Element elem : doc.select("div")) {
    			JPanel panel = new JPanel();
    			panel.setBackground(Color.white);
    			panel.setForeground(defFg);
    			panel.setLayout(null);
    			int panelheight = 0;
    			for (Element child : elem.children()) {
    				String nn = child.nodeName();
    				switch (nn) {
    					case "style":
    						break;
    						
    					case "script":
    						break;
    				
    					default: 
    						String text = child.text();
		    				JLabel label = new JLabel(text);
			    			label.setFont(font);
			    			label.setBounds(0, topIndex, this.getSize().width, 45);
			    			panel.add(label);
			    			topIndex += 45;
			    			panelheight += 45;
    						break;
    				}
    			}
    			panel.setBounds(0, topIndex, this.getSize().width, panelheight);
    			this.add(panel);
    			topIndex += panelheight;
    		}
    	}
    	
    	public void loadHomePage() {
    		this.removeAll();
    		JPanel panel = new JPanel();
    		Dimension d = this.getSize();
    		panel.setBackground(new Color(15, 15, 15));
    		panel.setBounds(0, 0, d.width, d.height);
    		panel.setLayout(new BorderLayout());
    		JLabel title = new JLabel("Martinium " + bVersion);
    		title.setFont(font);
    		title.setForeground(defBg);
    		panel.add(title, BorderLayout.CENTER);
    		this.add(panel);
    	}
    }
    
    public class WebPanel extends JPanel {
    	
    	public Color bg = new Color(15, 15, 15), bc = defBc;
    	
    	public WebPanel(String url) {
    	}
    }
}