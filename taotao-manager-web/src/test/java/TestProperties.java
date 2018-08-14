import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

public class TestProperties {
	
	@Test
	public void test() throws IOException {
		InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("resource/resource.properties");
		
		Properties properties=new Properties();
		properties.load(inputStream);
		
		String url=properties.getProperty("IMAGE_SERVER_URL");
		
		System.out.println("url: "+url);
	}

}
