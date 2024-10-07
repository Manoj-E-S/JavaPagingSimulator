package io.github.manoj_e_s.java_page_simulator;

import io.github.manoj_e_s.java_page_simulator.backend_components.cache.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.cache.CacheConfig;
import io.github.manoj_e_s.java_page_simulator.backend_components.performance.Logger;
import io.github.manoj_e_s.java_page_simulator.backend_components.process.Process;
import io.github.manoj_e_s.java_page_simulator.backend_components.cache.caching_policy.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaPageSimulatorApplication {

	public static void main(String[] args) throws IOException {
		Logger.configure(false);
		Logger.getInstance().log(null, "\n");

		CacheConfig cacheConfig = new CacheConfig();
		cacheConfig.setPageSizeInKb(1);
		cacheConfig.setMeasurePerformance(false);
		cacheConfig.setFramesInCache(10);
		cacheConfig.setCacheHitTimeIntervalInSeconds(0);
		cacheConfig.setCacheMissTimeIntervalInSeconds(3);

		Cache.instantiate(cacheConfig);

		FIFOPolicy fifoPolicy = new FIFOPolicy();
		LRUPolicy lruPolicy = new LRUPolicy();
		MRUPolicy mruPolicy = new MRUPolicy();
		MostFrequentlyUsedPolicy mfuPolicy = new MostFrequentlyUsedPolicy();
		LeastFrequentlyUsedPolicy lfuPolicy = new LeastFrequentlyUsedPolicy();
		TwoQPolicy twoQPolicy = new TwoQPolicy();
		AdaptiveReplacementCachePolicy arcPolicy = new AdaptiveReplacementCachePolicy();

		Cache.setCachingPolicy(twoQPolicy);

		Logger.getInstance().logVerbose(cacheConfig, "Cache Config:\n");
		Logger.getInstance().log(Cache.getCachingPolicy(), "Caching Policy: ");

		List<Process> processes = new ArrayList<>();
		for (int i = 1; i <= 14; i++) {
			Process p = new Process("/home/manoj/Manoj/Projects/java-page-simulator/src/main/resources/process_files/p" + i + ".txt", "p" + (i-1));
			processes.add(p);
			processes.get(i-1).startSimulation();
		}
    }

}
