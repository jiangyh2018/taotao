import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;

/**
 * @description: 测试md5加密算法
 * @author:
 * @create: 2018-10-04 22:09
 **/
public class TestMD5 {

    @Test
    public void demo2() {
        String pwd = "111111";
        String pwd1 = "111qqq";
        String str = DigestUtils.md5DigestAsHex(pwd.getBytes());
        String str1 = DigestUtils.md5DigestAsHex(pwd1.getBytes());
        System.out.println(str);
        System.out.println(str1);
    }

    @Test
    public void demo() {
        String pwd = "111111";
        //用于加密的字符
        char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(pwd.getBytes());
            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];  //95
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5
                str[k++] = md5String[byte0 & 0xf];   //   F
            }
            // 返回经过加密后的字符串
            System.out.println(str);
            System.out.println(new String(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}