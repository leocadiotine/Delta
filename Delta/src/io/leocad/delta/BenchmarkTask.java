package io.leocad.delta;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import android.util.Log;

public abstract class BenchmarkTask<T> {
	
	private static final String TAG = "Delta";
	private static final DecimalFormat NUM_CYCLES_FORMATTER = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);

	protected void onPreExecute() {
		// Override me
	}
	protected abstract T task();
	protected void onPostExecute() {
		// Override me
	}
	
	private T doExecution(long numTimes) {
		
		onPreExecute();
		T result = null;
		for (int i = 0; i < numTimes; i++) {
			result = this.task();
		}
		
		onPostExecute();
		return result;
	}
	
	BenchmarkResult<T> execute(long numCycles, long numWarmupCycles) {
		
		BenchmarkResult<T> result = new BenchmarkResult<T>();
		result.className = getClass().getSimpleName();
		
		Log.v(TAG, "----------------------------------------------------------");
		Log.v(TAG, String.format("Starting benchmark. Class name: %s", result.className));
		
		long startTime = System.nanoTime();
		doExecution(numWarmupCycles); // Warmup
		long endTime = System.nanoTime();
		
		double elapsedTime = endTime - startTime;
		double avgTimePerTask = (double) elapsedTime / numWarmupCycles;
		
		result.warmupDurationSecs = elapsedTime / 1e9;
		result.warmupCycles = numWarmupCycles;
		result.warmupAvgTaskTimeNs = avgTimePerTask;
		Log.v(TAG, "-");
		Log.v(TAG, String.format("Warmup duration: %f seconds. Cycles: %s", result.warmupDurationSecs, NUM_CYCLES_FORMATTER.format(numWarmupCycles)) );
		Log.v(TAG, String.format("Average time per task during warmup: %f nanoseconds", avgTimePerTask) );
		
		startTime = System.nanoTime();
		T lastResult = doExecution(numCycles);
		endTime = System.nanoTime();
		
		elapsedTime = endTime - startTime;
		avgTimePerTask = (double) elapsedTime / numCycles;
		
		result.benchmarkDurationSecs = elapsedTime / 1e9;
		result.benchmarkCycles = numCycles;
		result.benchmarkAvgTaskTimeNs = avgTimePerTask;
		result.lastResult = lastResult;
		Log.v(TAG, "-");
		Log.v(TAG, String.format("Benchmarking duration: %f seconds. Cycles: %s", result.benchmarkDurationSecs, NUM_CYCLES_FORMATTER.format(numCycles)) );
		Log.v(TAG, String.format("Average time per task during benchmarking: %f nanoseconds", avgTimePerTask) );
		Log.v(TAG, "----------------------------------------------------------");
		
		return result;
	}
}
