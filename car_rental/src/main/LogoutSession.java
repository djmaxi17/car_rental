package main;

import javax.swing.JFrame;

import database.DbConnect;
import gui.LoginGui;

public class LogoutSession {

	public static void logout(JFrame context, LoginGui loginScreen) {
		LoginSession.isLoggedIn = false;
		context.setVisible(false);
		loginScreen.setVisible(true);
	}
	public static void logoutH(LoginGui loginScreen) {
		LoginSession.isLoggedIn = false;
		loginScreen.setVisible(true);
		DbConnect.close();
	}
}
