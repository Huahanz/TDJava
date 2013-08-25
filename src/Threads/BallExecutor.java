package Threads;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BallExecutor<T> {
	ExecutorService executorService = Executors.newCachedThreadPool();
	HashSet<Callable<T>> callables;

	public BallExecutor(HashSet<Callable<T>> callables) {
		if (callables == null || callables.size() == 0) {
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.callables = callables;
	}

	public void start() {
		List<Future<T>> futures;
		try {
			futures = executorService.invokeAll(callables);
			/*
			 * The return of futures maybe not in sequence. 
			 */
			for (Future<T> future : futures) {
				future.get();
			}
			executorService.shutdown();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}