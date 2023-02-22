package com.limodevelopment.limobrowser.ui.web;

import java.awt.Font;

import javax.swing.JLabel;

public class HeaderLabel extends JLabel {
	
	public Font f;
	
	public HeaderLabel() {
		f = new Font("Times New Roman", Font.PLAIN, 40);
		this.setFont(f);
	}
	
	public HeaderLabel(String text) {
		f = new Font("Times New Roman", Font.PLAIN, 40);
		this.setFont(f);
		this.setText(text);
	}
	
	public HeaderLabel(String text, Font font) {
		f = font;
		this.setFont(f);
		this.setText(text);
	}
	
	public HeaderLabel(String text, int fontsize) {
		f = new Font("Times new Roman", Font.PLAIN, fontsize);
		this.setFont(f);
		this.setText(text);
	}
	
	public HeaderLabel(String text, String fontname, int fontsize) {
		f = new Font(fontname, Font.PLAIN, 40);
		this.setFont(f);
		this.setText(text);
	}
	
	public HeaderLabel(String text, String fontname, int fonttype, int fontsize) {
		f = new Font(fontname, fonttype, fontsize);
		this.setFont(f);
		this.setText(text);
	}
	
	public void loadFont(String fontname, int fonttype, int fontsize) {
		f = new Font(fontname, fonttype, fontsize);
		this.setFont(f);
	}
}
