package io.fluentqa.qabox.mindmapping.xml;

import cn.hutool.core.util.XmlUtil;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathConstants;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

@Slf4j
public class XmlHelper {

    public static Document getDocument(String xmlFilePath) {
        return XmlUtil.readXML(xmlFilePath);
    }

    public static Object getValueByXpath(String xmlFilePath,
                                         String xpathExpr) {
        Document doc = getDocument(xmlFilePath);
        return XmlUtil.getByXPath(xpathExpr, doc, XPathConstants.STRING);
    }

    public static Object getValueByXpath(Document doc,
                                         String xpathExpr) {
        return XmlUtil.getByXPath(xpathExpr, doc, XPathConstants.STRING);
    }

    /**
     * JAXB Read Xml file
     *
     * @param xmlFilePath
     * @param clazz
     * @return
     */
    public static <T> T readXmlToObject(String xmlFilePath, Class<T> clazz) {

        try {
            Unmarshaller unmarshaller = getUnmarshaller(clazz);
            return (T) unmarshaller.unmarshal(new BufferedInputStream(new FileInputStream(xmlFilePath)));
        } catch (Exception e) {
            log.error("parse xml failed,", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T readToObject(Document document, Class<T> clazz) {

        try {
            Unmarshaller unmarshaller = getUnmarshaller(clazz);
            return (T) unmarshaller.unmarshal(document);
        } catch (Exception e) {
            log.error("parse xml failed,", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> Unmarshaller getUnmarshaller(Class<T> clazz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        return context.createUnmarshaller();
    }
}
