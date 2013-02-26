package io.leocad.deltaexample.core;

import io.leocad.delta.BenchmarkTask;


public class BenchmarkGetSet extends BenchmarkTask {
	
	private ModelGetSet mGetSet;
	
	@Override
	protected void onPreExecute() {
		mGetSet = new ModelGetSet();
	}
	
	protected Object task() {
		
		mGetSet.setMyString("Am I slow?");
		return mGetSet.getMyString();
	}
}
