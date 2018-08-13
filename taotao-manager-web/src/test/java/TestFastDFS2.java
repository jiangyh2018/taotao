import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * @program: taotao
 * @description: 测试文件上传
 * @author:
 * @create: 2018-08-12 22:35
 **/
public class TestFastDFS2 {

    @Test
    public void testUploadFile()throws Exception{
//        ClientGlobal.init("classpath:resource/FastDFSClient.conf");

        ClientGlobal.init("D:\\IT\\myProject\\taotao\\taotao-manager-web\\src\\main\\resources\\resource\\FastDFSClient.conf");

        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer=trackerClient.getConnection();
        StorageServer storageServer=null;

        StorageClient storageClient=new StorageClient(trackerServer,storageServer);
        String[] strs=storageClient.upload_file("C:\\Users\\x1c\\Pictures\\Saved Pictures\\灌篮高手.jpg","jpg",null);

        for (String str:strs) {
            System.out.println(str);
        }
    }

}
