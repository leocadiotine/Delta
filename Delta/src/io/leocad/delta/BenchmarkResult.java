package io.leocad.delta;

public class BenchmarkResult {

	public String className;
	
	public double warmupDurationSecs;
	public long warmupCycles;
	public double warmupAvgTaskTimeNs;
	
	public double benchmarkDurationSecs;
	public long benchmarkCycles;
	public double benchmarkAvgTaskTimeNs;
	
}
