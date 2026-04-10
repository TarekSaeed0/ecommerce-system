package com.github.tareksaeed0;

import java.util.List;
import com.github.tareksaeed0.examples.Example;
import com.github.tareksaeed0.examples.Example1;
import com.github.tareksaeed0.examples.Example2;
import com.github.tareksaeed0.examples.Example3;
import com.github.tareksaeed0.examples.Example4;
import com.github.tareksaeed0.examples.Example5;
import com.github.tareksaeed0.examples.Example6;
import com.github.tareksaeed0.examples.Example7;

public class App {
	private static List<Example> examples =
			List.of(new Example1(), new Example2(), new Example3(), new Example4(),
					new Example5(), new Example6(), new Example7());

	public static void main(String[] args) {

		for (Example example : examples) {
			System.out.println("=".repeat(50));
			System.out.println(example.getName());
			System.out.println("=".repeat(50));
			example.run();
			System.out.println();
		}
	}
}
