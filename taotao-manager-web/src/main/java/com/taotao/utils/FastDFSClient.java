package com.taotao.utils;

import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.IOException;

/**
 * @program: taotao
 * @description: 文件上传工具类
 * @author:
 * @create: 2018-08-12 23:24
 **/
public class FastDFSClient {


    private TrackerClient trackerClient;
    private TrackerServer trackerServer;
    private StorageServer storageServer = null;
    private StorageClient storageClient = null;

    public FastDFSClient() {

    }

    public FastDFSClient(String conf) throws Exception {
        ClientGlobal.init(conf);

        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageClient = new StorageClient(trackerServer, storageServer);
    }

    public String uploadFile(byte[] content, String extName) throws IOException, MyException {

        String[] strings = storageClient.upload_file(content, extName, null);

        String path = "/"+strings[0] + "/" + strings[1];

        return path;
    }

}
