package com.team.app.constant;

import java.util.Random;

public class PasswordGenerator {
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%&";
	static Random rnd = new Random();

	public String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}
}
