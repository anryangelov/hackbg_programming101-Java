package week09.thumbnail_generator;

public class Test {

	public static void main(String[] args) {

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					System.out.println(e);
				}
				System.out.println("in t");
			}

		});
		t.setDaemon(true);
		t.start();
		System.out.println("Done");
	}
}
