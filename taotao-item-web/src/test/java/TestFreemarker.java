import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 测试freemarker生成静态文件
 * @author:
 * @create: 2018-10-01 20:24
 **/
public class TestFreemarker {
    @Test
    public void testFreemarker() throws IOException, TemplateException {
//        第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
        Configuration configuration = new Configuration(Configuration.getVersion());
//        第二步：设置模板文件所在的路径。
        configuration.setDirectoryForTemplateLoading(new File("D:\\myTools\\myProject\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
//        第三步：设置模板文件使用的字符集。一般就是utf-8.
        configuration.setDefaultEncoding("UTF-8");
//        第四步：加载一个模板，创建一个模板对象。
        Template template = configuration.getTemplate("hello.ftl");
//        第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
        Map map = new HashMap<>();
        map.put("hello", "hello freemarker!");
        map.put("date", new Date());
//        第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
        Writer out = new FileWriter("D:\\myTools\\myProject\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\hello.html");
//        第七步：调用模板对象的process方法输出文件。
        template.process(map, out);
//        第八步：关闭流。
        out.close();
    }
}