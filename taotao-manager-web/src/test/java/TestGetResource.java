import org.junit.Test;

public class TestGetResource {
	
	@Test
	public void test(){
		System.out.println(this.getClass().getResource(""));
		System.out.println(this.getClass().getResource("").getPath());
		System.out.println(this.getClass().getResource("/").getFile());
		System.out.println("-----------------");
		System.out.println(this.getClass().getResource("/"));
		System.out.println(this.getClass().getResource("/").getPath());
		System.out.println(this.getClass().getResource("/").getFile());
		System.out.println("-----------------");
//		System.out.println(this.getClass().getResource("/demo-classes"));
//		System.out.println(this.getClass().getResource("/target/demo-classes/").getPath());
//		System.out.println(this.getClass().getResource("/target/").getFile());
//		System.out.println("-----------------");
//		System.out.println(this.getClass().getResource("aa"));
//		System.out.println(this.getClass().getResource("aa").getPath());
//		System.out.println(this.getClass().getResource("aa").getFile());
	}
}
