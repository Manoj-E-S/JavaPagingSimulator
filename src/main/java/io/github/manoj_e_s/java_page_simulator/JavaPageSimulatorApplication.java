package io.github.manoj_e_s.java_page_simulator;

import io.github.manoj_e_s.java_page_simulator.backend_components.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.GlobalConfig;
import io.github.manoj_e_s.java_page_simulator.backend_components.Process;
import io.github.manoj_e_s.java_page_simulator.backend_components.caching_policy.*;

import java.io.IOException;

public class JavaPageSimulatorApplication {

	public static void main(String[] args) throws IOException {
		FIFOPolicy fifoPolicy = new FIFOPolicy();
		LRUPolicy lruPolicy = new LRUPolicy();
		MRUPolicy mruPolicy = new MRUPolicy();
		MostFrequentlyUsedPolicy mfuPolicy = new MostFrequentlyUsedPolicy();
		LeastFrequentlyUsedPolicy lfuPolicy = new LeastFrequentlyUsedPolicy();
		TwoQPolicy twoQPolicy = new TwoQPolicy();

		GlobalConfig globalConfig = new GlobalConfig(
				false,
				3,
				0,
				twoQPolicy,
				3,
				1024
		);
		Cache.instantiate(globalConfig);
		System.out.println(globalConfig);

		Process p1 = new Process("/home/manoj/Manoj/Projects/java-page-simulator/src/main/resources/process_files/p1.txt", "p1");
		Process p2 = new Process("/home/manoj/Manoj/Projects/java-page-simulator/src/main/resources/process_files/p2.txt", "p2");
		System.out.println(p1);
		System.out.println(p2);

		p1.startSimulation();
		p2.startSimulation();
    }

}
