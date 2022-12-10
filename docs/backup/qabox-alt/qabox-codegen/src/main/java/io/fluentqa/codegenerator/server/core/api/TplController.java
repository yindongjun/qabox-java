package io.fluentqa.codegenerator.server.core.api;

import io.fluentqa.codegenerator.server.common.model.Res;
import io.fluentqa.codegenerator.server.core.service.TplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模版控制器
 *
 * @author hank
 * @create 2020-12-29 下午2:30
 **/
@RestController
@RequestMapping("api/tpl")
public class TplController {

    @Autowired TplService tplService;

    @RequestMapping("all")
    public Res all(){
        return Res.data(tplService.queryAll());
    }

}
