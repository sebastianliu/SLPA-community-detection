package com.computinglife.slpa.util;

import java.util.Random;

/**
 * 随机数工具类
 * 
 * @author youngliu
 *
 */
public class RandomNumGenerator {
	public static Random random = new Random();

	public static int getRandomInt(int max) {
		return random.nextInt(max);
	}

}
