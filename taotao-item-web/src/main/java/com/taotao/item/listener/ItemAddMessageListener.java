package com.taotao.item.listener;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import freemarker.core.ParseException;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 监听添加商品时的消息队列
 * @author:
 * @create: 2018-10-01 21:07
 **/
public class ItemAddMessageListener implements MessageListener {
    @Autowired
    private FreeMarkerConfig freemarkerConfig;
    @Autowired
    private ItemService itemService;
    @Value("${HTML_OUT_PATH}")
    private String HTML_OUT_PATH;


    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String itemId = textMessage.getText();

            Configuration configuration = freemarkerConfig.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            Map data = new HashMap<>();
            //查询商品信息和描述,放入map
            Thread.sleep(1000);
            TbItem tbItem = itemService.getItemById(Long.parseLong(itemId));
            Item item = new Item(tbItem);

            TbItemDesc itemDesc = itemService.getItemDescById(Long.parseLong(itemId));

            data.put("item", item);
            data.put("itemDesc", itemDesc);

            Writer out = new FileWriter(new File(HTML_OUT_PATH + itemId + ".html"));

            template.process(data, out);

            out.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}