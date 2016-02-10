package week11.wget;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Wget {

	URL url;

	public Wget(String url) throws MalformedURLException {
		this.url = new URL(url);
	}

	public void download() throws IOException {
		InputStream in = new BufferedInputStream(url.openStream());
		OutputStream out = new BufferedOutputStream(new FileOutputStream("/home/angel/image.jpg"));
		for ( int i; (i = in.read()) != -1; ) {
		    out.write(i);
		}
		in.close();
		out.close();
	}

	public static void main(String[] args) throws Exception {
		Wget w = new Wget("http://www.keenthemes.com/preview/conquer/assets/plugins/jcrop/demos/demo_files/image1.jpg");
		w.download();
	}
}