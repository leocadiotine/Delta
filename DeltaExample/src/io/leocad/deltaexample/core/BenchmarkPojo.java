package io.leocad.deltaexample.core;

import io.leocad.delta.BenchmarkTask;


public class BenchmarkPojo extends BenchmarkTask {
	
	private ModelPojo mPojo;
	
	@Override
	protected void onPreExecute() {
		mPojo = new ModelPojo();
	}
	
	protected Object task() {
		
		mPojo.myString = "Am I slow?";
		return mPojo.myString;
	}
}
