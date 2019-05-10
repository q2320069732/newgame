package com.socket.dispatcher.config;


import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/4/30 17:48
 */

public class RegistSerializerMessage {
    //private static final ApplicationContext applicationContext =  new ClassPathXmlApplicationContext("message.xml");
    private static final String location = "src\\main\\resources\\message.xml";

    public static final Map<Integer, Class<?>> idClassMap = new HashMap<Integer, Class<?>>();

    /**
     * 注册协议
     */

    public void init(){

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document parse = db.parse(location);
            NodeList catalogs = parse.getElementsByTagName("catalog");
            for (int i = 0; i < catalogs.getLength(); i++) {
                Node name = catalogs.item(i);
                // System.out.println(name.getAttributes().getNamedItem("name"));
                String sid = name.getAttributes().getNamedItem("id").getNodeValue().toString();
                //System.out.println(sid);
                String svalue = name.getAttributes().getNamedItem("value").getNodeValue().toString();
                int id = Integer.parseInt(sid);
                Class<?> clz = Class.forName(svalue);
                if(idClassMap.containsKey(id)){
                    throw new IllegalArgumentException("协议id：["+id+"] 重复了！");
                }
                //System.out.println("id:"+id+",clz:"+clz);
                idClassMap.put(id, clz);
            }

            /*Resource resource = applicationContext.getResource(location);
            SAXBuilder sb = new SAXBuilder();
            Document document = sb.build(resource.getInputStream());
            Element rootElement = document.getRootElement();
            List<Element> child = (List<Element>) rootElement.getChild("catalog");
            for (Element element : child){
                Integer id = Integer.valueOf(element.getAttributeValue("id"));
                String value = element.getAttributeValue("value");
                Class<?> clz = Class.forName(value);
                if(idClassMap.containsKey(id)){
                    throw new IllegalArgumentException("协议id：["+id+"] 重复了！");
                }
                System.out.println("id:"+id+",clz:"+clz);
                idClassMap.put(id, clz);

            }*/

        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}
