package test;

import game.utils.SUtils;

import java.util.Random;

public class TestClass {
	public static void main(String[] args) {
		Random rd = new Random() ;
		for (int i = 0; i < 10; i++) {
			System.out.println(SUtils.conObjtoInt(i+""));
		}
		
	}
}
