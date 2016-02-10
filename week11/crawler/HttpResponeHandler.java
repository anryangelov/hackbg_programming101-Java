package week11.crawler;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpResponeHandler {

	public final static String getContent(String uri) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(uri);

			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					StatusLine statusLine = response.getStatusLine();
					int status = statusLine.getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						ContentType contentType = ContentType.getOrDefault(entity);
					    Charset charset = contentType.getCharset();
					    if (charset == null) {
					    	charset =  Charset.defaultCharset();
					    }
						return entity != null ? EntityUtils.toString(entity, charset)
								: null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + statusLine);
					}
				}

			};
			String responseBody = httpclient.execute(httpget, responseHandler);
			return responseBody;
		} finally {
			httpclient.close();
		}
	}

}
