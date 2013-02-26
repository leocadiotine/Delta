package io.leocad.delta;

import android.app.Activity;

public abstract class Delta {

	public abstract void onPreExecute();
	public abstract void onPostExecute(BenchmarkResult result);
	
	private BenchmarkResult mResult;

	public void benchmark(final Activity activity, final Class<? extends BenchmarkTask> classType, final long numCycles) {

		onPreExecute();
		
		new Thread() {
			@Override
			public void run() {

				try {
					BenchmarkTask taskInstance = classType.newInstance();
					mResult = taskInstance.execute(numCycles);
					
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				
				} finally {
					
					activity.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							onPostExecute(mResult);
							mResult = null; //Release
						}
					});
				}
			};
		}.start();
	}
}
