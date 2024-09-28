package io.github.manoj_e_s.java_page_simulator;

import io.github.manoj_e_s.java_page_simulator.backend_components.GlobalConfig;
import io.github.manoj_e_s.java_page_simulator.backend_components.caching_policy.LRUPolicy;
import io.github.manoj_e_s.java_page_simulator.backend_components.Process;

public class JavaPageSimulatorApplication {

	public static void main(String[] args) {
		LRUPolicy lruPolicy = new LRUPolicy();
		GlobalConfig globalConfig = new GlobalConfig(lruPolicy);
		System.out.println(globalConfig);

		Process p1 = new Process("/path/to/p1File", "p1");
		Process p2 = new Process("/path/to/p2File", "p2");
		System.out.println(p1);
		System.out.println(p2);
    }

}
