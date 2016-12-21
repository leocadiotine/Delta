package io.leocad.delta;

public class BenchmarkResult<T> {

	public String className;
	
	public double warmupDurationSecs;
	public long warmupCycles;
	public double warmupAvgTaskTimeNs;
	
	public double benchmarkDurationSecs;
	public long benchmarkCycles;
	public double benchmarkAvgTaskTimeNs;
	
	public T lastResult;
}
