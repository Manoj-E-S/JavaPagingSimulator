package io.github.manoj_e_s.java_page_simulator;

import io.github.manoj_e_s.java_page_simulator.backend_components.GlobalConfig;
import io.github.manoj_e_s.java_page_simulator.backend_components.Process;
import io.github.manoj_e_s.java_page_simulator.backend_components.caching_policy.LRUPolicy;

import java.io.IOException;

public class JavaPageSimulatorApplication {

	public static void main(String[] args) throws IOException {
		LRUPolicy lruPolicy = new LRUPolicy();
		GlobalConfig globalConfig = new GlobalConfig(
				false,
				10,
				2,
				lruPolicy,
				3,
				1024
		);
		System.out.println(globalConfig);

		Process p1 = new Process("/home/manoj/Manoj/Projects/java-page-simulator/src/main/resources/process_files/p1.txt", "p1");
		Process p2 = new Process("/home/manoj/Manoj/Projects/java-page-simulator/src/main/resources/process_files/p2.txt", "p2");
		System.out.println(p1);
		System.out.println(p2);

		p1.startSimulation(globalConfig);
		p2.startSimulation(globalConfig);
    }

}
