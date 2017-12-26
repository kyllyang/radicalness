package com.kyll.garbage.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * User: Kyll
 * Date: 2017-11-03 10:44
 */
@Slf4j
@Component
public class GarbageFacade {
	public void execute() {
		String str = "@training-{x}.com";
		/*for (int i = 0; i < 1000; i++) {
		//	System.out.println(str.replace("{x}", String.valueOf((char) ('a' + i))));
			System.out.println(str.replace("{x}", String.valueOf(i)));
		}*/

		for (int i = 0; i < 26; i++) {
			for (int j = 0; j < 26; j++) {
				System.out.println(str.replace("{x}", String.valueOf((char) ('a' + i)) + String.valueOf((char) ('a' + j))));
			}
		}
	}
}
