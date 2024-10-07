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
		setupLogger();

		CacheConfig cacheConfig = loadCacheConfig();
		Logger.getInstance().logVerbose(cacheConfig, "Cache Config:\n");

		runAllProcessesUnderEveryPolicy(cacheConfig);
	}

	private static void runAllProcessesUnderEveryPolicy(CacheConfig cacheConfig) throws IOException {
		List<CachingPolicy> policies = getAllPolicies();

		for (CachingPolicy c: policies) {
			Logger.getInstance().log(null, "\n################################################################################\n");
			Cache.instantiate(cacheConfig);
			Cache.setCachingPolicy(c);
			Logger.getInstance().log(Cache.getCachingPolicy(), "Caching Policy: ");

			List<Process> processes = new ArrayList<>();
			for (int i = 1; i <= 14; i++) {
				Process p = new Process(
						"/home/manoj/Manoj/Projects/java-page-simulator/src/main/resources/process_files/p" + i + ".txt",
						"p" + (i-1),
						false
				);
				processes.add(p);
				processes.get(i-1).startSimulation();
			}
			Cache.getPerformanceMetrics().netLog();
			Cache.unmount();
			Logger.getInstance().log(null, "\n################################################################################\n");
		}
	}

	private static CacheConfig loadCacheConfig() {
		CacheConfig cacheConfig = new CacheConfig();
		cacheConfig.setPageSizeInBytes(1024);
		cacheConfig.setMeasurePerformance(true);
		cacheConfig.setFramesInCache(4);
		cacheConfig.setCacheHitTimeIntervalInMillis(0);
		cacheConfig.setCacheMissTimeIntervalInMillis(3);
		return cacheConfig;
	}

	private static void setupLogger() {
		Logger.configure(false);
		Logger.getInstance().log(null, "\n");
	}

	private static List<CachingPolicy> getAllPolicies() {
		FIFOPolicy fifoPolicy = new FIFOPolicy();
		LRUPolicy lruPolicy = new LRUPolicy();
		MRUPolicy mruPolicy = new MRUPolicy();
		LeastFrequentlyUsedPolicy lfuPolicy = new LeastFrequentlyUsedPolicy();
		MostFrequentlyUsedPolicy mfuPolicy = new MostFrequentlyUsedPolicy();
		TwoQPolicy twoQPolicy = new TwoQPolicy();
		AdaptiveReplacementCachePolicy arcPolicy = new AdaptiveReplacementCachePolicy();

		List<CachingPolicy> policies = new ArrayList<>();
		policies.add(fifoPolicy);
		policies.add(lruPolicy);
		policies.add(mruPolicy);
		policies.add(lfuPolicy);
		policies.add(mfuPolicy);
		policies.add(twoQPolicy);
		policies.add(arcPolicy);

		return policies;
	}

}
