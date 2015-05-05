package com.ukfire.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {
	private static final int MAX_THREAD_COUNT = 10;

	private static ExecutorService threadCache = new ThreadPoolExecutor(3,
			MAX_THREAD_COUNT, 60, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>());

	public static void execute(Runnable runnable) {
		threadCache.submit(runnable);
	}
}
