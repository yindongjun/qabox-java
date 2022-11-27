package io.fluentqa.qabox.xmind;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import io.fluentqa.qabox.freemind.model.Map;
import io.fluentqa.qabox.freemind.model.Node;
import io.fluentqa.qabox.mindmapping.MindMappingProcessor;
import io.fluentqa.qabox.mindmapping.xml.XmlHelper;
import io.fluentqa.qabox.xmind.model.Attached;
import io.fluentqa.qabox.xmind.model.JsonRootBean;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class XmindUtilTest {

    @Test
    void readXmindFile_xmind8() {

       XmindRawData data =  XmindUtil.readXmindFile("Xmind8.xmind");
        System.out.println(data);
       assertNotNull(data);
    }

    @Test
    void readXmindFile_xmind_zen() {

        XmindRawData data =  XmindUtil.readXmindFile("XmindZen.xmind");
        System.out.println(data);
        //Both Json and XML file
        assertNotNull(data);
    }

    @Test
    void readXmindFile_xmind_10() {
        //Both Json and XML file

        XmindRawData data =  XmindUtil.readXmindFile("Xmind10+.xmind");
        System.out.println(data);
        //Both Json and XML file
        assertNotNull(data);
    }

    @Test
    void readXmindFile_case_template() {
        //Both Json and XML file

        XmindRawData data =  XmindUtil.readXmindFile("case-template.xmind");
        System.out.println(data);
        //Both Json and XML file
        assertNotNull(data);
    }

    @Test
    public void testRawdataToJson(){
        XmindRawData data =  XmindUtil.readXmindFile("case-template.xmind");
        List<JsonRootBean> topic = JSONUtil.toList(data.getContentJson(), JsonRootBean.class);
        System.out.println(topic);
    }

    @Test
    public void testRawdataToJson_zen(){
//        XmindRawData data =  XmindUtil.readXmindFile("XmindZen.xmind");
//        List<JsonRootBean> topic = JSONUtil.toList(data.getContentJson(), JsonRootBean.class);
//        System.out.println(topic);
//
//        XmindAccessor accessor = new XmindAccessor(topic.get(0).getRootTopic());
//        List<MindMappingModel> all = accessor.getFullPathNodes();
//        System.out.println(all);

        MindMappingProcessor processor = new MindMappingProcessor();
       List<TestCaseModel> models =  processor.process("XmindZen.xmind", TestCaseModel.class, new Function<Attached, String>() {
            @Override
            public String apply(Attached attached) {
               return (String) ReflectUtil.getFieldValue(attached,"title");
            }
        });
        System.out.println(models);
        Object result = XmlHelper.readXmlToObject("t2.mm", Map.class);
        List<TestCaseModel> model2= processor.process("t2.mm", TestCaseModel.class, new Function<Node, String>() {
            @Override
            public String apply(Node node) {
                return node.getTEXT();
            }
        });
        System.out.println(model2);
    }



}