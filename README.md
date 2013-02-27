#Delta

![Delta logo](https://dl.dropbox.com/u/5135185/blog/delta-icon.png)

Microbenchmarking framework for Android.

##Current version
1.2 (20130227). Download the binary here: [delta-1.2.jar](https://dl.dropbox.com/u/5135185/blog/delta-1.2.jar).

##Purpose
Microbenchmarking is "attempting to measure the performance of a small piece of code". If you want to know whether is faster to use a "getter" rather than access the field directly, or if it's better to run a for loop backwards instead of forwards, then you should do a microbenchmark. Interesting information can be found [here](https://code.google.com/p/caliper/wiki/JavaMicrobenchmarks).

##Origin of the name
Delta is named after the [Delta robot](https://en.wikipedia.org/wiki/Delta_robot), a kind of robot very popular in the industry. Its main purpose is to do small and repetitive tasks, like picking and packaging, and it can do them very fast.

The Delta framework for Android aims to do the same thing: repeat simple tasks over and over again, and tell you how long they took.

##Similar projects
Google has a very nice framework to do microbenchmarks called [caliper](https://code.google.com/p/caliper/). But calliper targets the Java VM, and isn't optimised for Android. Since [the Java VM and the Android VM are different](http://blog.leocad.io/post/so-is-android-java), the results from caliper may not be a good snapshot of what's going on your Android device.

Delta runs on the Android device itself, and measures Dalvik bytecode.

##How it works
The framework simply gets a piece of code written by you and runs it multiple times. But before that, it first [warms up the virtual machine](http://4groundtechsolutions.com/jvm-warmup/), so the JIT can optimise your code.

Reading the results, you'll find that your code runs much slower during the warmup stage. This is why it's important to do this step. By default, Delta runs your task a million times during warmup.

##Usage
You can find a whole working example on the `DeltaExample` folder. It's a working Android project that uses the Delta framework. But for the purposes of this manual, following are step by step instructions.

###Step 1: Add the framework to your Android project
You can do this in two different ways: importing the Delta project on Eclipse and [referencing it as a library project](https://developer.android.com/tools/projects/projects-eclipse.html#ReferencingLibraryProject) or simply copying the binary file (delta.jar) to the `libs` directory of your Android project. You can find the latest binary on the ["Current Version"](#current_version) paragraph of this document.

###Step 2: Write your tasks
You'll need to extend `BenchmarkTask` and override its methods:

1. `onPreExecute()`: *[optional]* This method will be called **before** the repetitive task. It will be called only once, not every time the task is executed. This is a good place to allocate memory, instantiate objects and do heavy operations that would affect the benchmark itself;
2. `task()`: This is the code that will run multiple times. Inside this method, you should write what you want to benchmark. Notice that this method returns `Object`. It's important to return something here or else the compiler can optimise your method and [remove unused variables and dead code](https://code.google.com/p/caliper/source/browse/tutorial/Tutorial.java?r=ca7cd501f7eb464402b5815d773143bee835114d&spec=svn0ce0d129d35397c5a4384553ff52c14f5ba3d864#86).
3. `onPostExecute()`: *[optional]* This method will be called **after** the repetitive task. This is a good place to free memory.

You can find a working task in the `BenchmarkGetSet.java` file in the `Delta Example` project.

###Step 3: Call Delta from within your Activity
To better explain that, here's a code excerpt from `MainActivity.java`, included in `Delta Example`:

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		new Delta() {
			
			@Override
			public void onPreExecute() {
				mDialog = ProgressDialog.show(MainActivity.this,
				    null, "Benchmarking…");
			}

			@Override
			public void onPostExecute(BenchmarkResult result) {
				showResult(result);
				mDialog.dismiss();
			}
			
		}.benchmark(this, BenchmarkGetSet.class, 1000000000L);
	}

This example calls `Delta` as an [Anonymous Inner Class](http://stackoverflow.com/a/355177/1054366). You need to create an instance of the `Delta` class and call the execute() method. Let me explain how all methods work:

1. `onPreExecute()`: **[runs on the UI thread]** This runs before the benchmark. This is a good place to warn the user that a benchmark is about to run. It runs on the UI thread, which means:
    - It's safe to	update the UI. Feel free to show progress dialogs, change views and so on;
    - Don't do heavy/expensive operations here, or [your app can crash](https://developer.android.com/training/articles/perf-anr.html).
2. `onPostExecute(BenchmarkResult result)`: **[runs on the UI thread]** Runs after the benchmark is finished. This method receives a plain old Java object containing the results of the test.
3. `benchmark(Activity activity, Class<? extends BenchmarkTask> classType, long numCycles)`: **[runs on a background thread]** You should call this method, or the benchmarking will not be done. The parameters:
    - `activity`: the current `Activity` instance;
    - `classType`: the Class that you wrote on the Step 2 of this manual;
    - `numCycles`: the number of times you want your task to be executed. The largest the value you put here, the most accurate your results will be. But be careful: if your task is expensive and you put an enormous number here, your benchmark can last days!

##License
Delta source code is released under BSD 2-clause license. Check LICENSE file for more information.

If you use this code, I'd appreciate you refer my name (Leocadio Tiné) and the link to this project's page in your project's website or credits screen. Though you don't have any legal/contractual obligation to do so, just good karma.

##Suggestions? Comments?
Pull requests are always welcome. So are donations :)

To find me, buzz at `me[at]leocad.io` or [follow me on Twitter](http://www.twitter.com/leocadiotine). To read interesting stuff, go to [my blog](http://blog.leocad.io).