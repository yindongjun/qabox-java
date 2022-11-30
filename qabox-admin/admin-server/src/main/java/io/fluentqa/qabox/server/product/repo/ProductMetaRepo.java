package io.fluentqa.qabox.server.product.repo;

import io.fluentqa.qabox.server.product.model.ProductMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductMetaRepo extends JpaRepository<ProductMeta, Long>, JpaSpecificationExecutor<Long> {

    Optional<ProductMeta> findProductMetaByNameAndValid(String name, boolean valid);

    Optional<ProductMeta> findProductMetaByParentIdAndNameAndValid(Long parentId, String name, boolean valid);
}
