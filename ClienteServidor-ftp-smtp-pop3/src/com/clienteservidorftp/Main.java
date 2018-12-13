/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteservidorftp;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Guillermo Ramos Alberti <i>"Ervo"</i>
 */
public class Main {

	private static NimRODLookAndFeel nimRODLookAndFeel;
	private static Properties properties;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.initMain();
	}

	public void initMain() {
		initVar();
		nimRODLookAndFeel.setCurrentTheme(new NimRODTheme("com/javaproperties/theme.properties"));
		establecerTema(nimRODLookAndFeel);
	}

	public static void initVar() {
		properties = new Properties();
		nimRODLookAndFeel = new NimRODLookAndFeel();
	}

	public static void establecerTema(LookAndFeel laf) {
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			laf.initialize();
			UIManager.setLookAndFeel(laf);
		} catch(UnsupportedLookAndFeelException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
