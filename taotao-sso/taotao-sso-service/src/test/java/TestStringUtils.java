import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @description: 测试StringUtils中的一些方法
 * @author:
 * @create: 2018-10-05 20:56
 **/
public class TestStringUtils {

    @Test
    public void testIsBlank() {
        String str = " ";
        String str1 = null;

        boolean a = StringUtils.isBlank(str);
        boolean b = StringUtils.isBlank(str1);
        //空串和null都会被识别为空，返回true。

        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void testIsEmpty() {
        String str = " ";
        String str1 = null;

        boolean a = StringUtils.isEmpty(str);
        boolean b = StringUtils.isEmpty(str1);
        //空串和null都会被识别为空，返回true。

        System.out.println(a);
        System.out.println(b);
    }


    @Test
    public void testIsNotBlank() {
        String str = "";
        String str1 = null;

        boolean a = StringUtils.isNotBlank(str);
        boolean b = StringUtils.isNotBlank(str1);

        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void testIsNotEmpty() {
        String str = "";
        String str1 = null;

        boolean a = StringUtils.isNotEmpty(str);
        boolean b = StringUtils.isNotEmpty(str1);

        System.out.println(a);
        System.out.println(b);
    }

}