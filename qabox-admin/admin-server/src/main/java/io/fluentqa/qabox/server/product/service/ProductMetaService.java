package io.fluentqa.qabox.server.product.service;


import io.fluentqa.qabox.server.product.model.ProductMeta;
import io.fluentqa.qabox.server.product.repo.ProductMetaRepo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class ProductMetaService {
    @Resource
    private ProductMetaRepo metaRepo;

    public ProductMeta createModuleIfNotExist(Long productId, String moduleName) {
        Optional<ProductMeta> meta = metaRepo.findProductMetaByParentIdAndNameAndValid(productId,
                moduleName, true);
        if (meta.isPresent()) return meta.get();
        ProductMeta parent = new ProductMeta();
        parent.setId(productId);
        ProductMeta module = new ProductMeta();
        module.setName(moduleName);
        module.setDetails(moduleName);
        module.setParent(parent);
        return metaRepo.save(module);
    }
}
