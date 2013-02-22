package io.leocad.delta;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import android.util.Log;

public abstract class BenchmarkTask {
	
	private static final String TAG = "Delta";
	private static final DecimalFormat NUM_CYCLES_FORMATTER = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);

	protected void onPreExecute() {
		// Override me
	}
	protected abstract void task();
	protected void onPostExecute() {
		// Override me
	}
	
	private void doExecution(long numTimes) {
		
		onPreExecute();
		
		for (int i = 0; i < numTimes; i++) {
			task();
		}
		
		onPostExecute();
	}
	
	public void execute(long numCycles) {
		
		Log.i(TAG, "----------------------------------------------------------");
		Log.i(TAG, String.format("Starting benchmark. Class name: %s", getClass().getSimpleName()));
		
		long numWarmupCycles = 1000000L;
		
		long startTime = System.nanoTime();
		doExecution(numWarmupCycles); // Warmup
		long endTime = System.nanoTime();
		
		double elapsedTime = endTime - startTime;
		double avgTimePerTask = (double) elapsedTime / numWarmupCycles;
		
		Log.i(TAG, "-");
		Log.i(TAG, String.format("Warmup duration: %f seconds. Cycles: %s", elapsedTime / 1e9, NUM_CYCLES_FORMATTER.format(numWarmupCycles)) );
		Log.i(TAG, String.format("Average time per task during warmup: %f nanoseconds", avgTimePerTask) );
		
		startTime = System.nanoTime();
		doExecution(numCycles);
		endTime = System.nanoTime();
		
		elapsedTime = endTime - startTime;
		avgTimePerTask = (double) elapsedTime / numCycles;
		
		Log.i(TAG, "-");
		Log.i(TAG, String.format("Benchmarking duration: %f seconds. Cycles: %s", elapsedTime / 1e9, NUM_CYCLES_FORMATTER.format(numCycles)) );
		Log.i(TAG, String.format("Average time per task during benchmarking: %f nanoseconds", avgTimePerTask) );
		Log.i(TAG, "----------------------------------------------------------");
	}
}
