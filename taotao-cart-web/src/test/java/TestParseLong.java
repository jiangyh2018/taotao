import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @description: 测试
 * @author:
 * @create: 2018-10-07 21:51
 **/
public class TestParseLong {
    @Test
    public void test1() {
//        String price = "";        //报错 For input string: ""
//        String price = null;     //报错 java.lang.NumberFormatException: null
        String price = "null";     //报错 For input string: "null"
//        String price="0";
        if (StringUtils.isBlank(price) || "null".equals(price)) {
            price = "0";
        }
        long l = Long.parseLong(price);
        System.out.println(l);
    }

}