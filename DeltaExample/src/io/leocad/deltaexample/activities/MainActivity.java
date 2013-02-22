package io.leocad.deltaexample.activities;

import io.leocad.delta.Delta;
import io.leocad.deltaexample.core.BenchmarkGetSet;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		new Delta() {
			@Override
			public void onPostExecute() {
				finish();
			}
		}.benchmark(this, BenchmarkGetSet.class, 1000000000L);
	}

}
