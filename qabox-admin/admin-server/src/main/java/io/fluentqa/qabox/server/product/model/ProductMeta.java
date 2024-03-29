package io.fluentqa.qabox.server.product.model;

import cn.hutool.core.lang.UUID;
import io.fluentqa.qabox.server.common.model.ModelWithValidFlag;
import lombok.Data;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_erupt.Tree;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.InputType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTreeType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.toolkit.handler.SqlChoiceFetchHandler;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Erupt(name = "产品元数据",
        power = @Power(importable = true, export = true),
        tree = @Tree(pid = "parent.id"))
@Entity
@Table(name = "product_meta")
@Data
public class ProductMeta extends ModelWithValidFlag {

    @EruptField(
            views = @View(
                    title = "名称"
            ),
            edit = @Edit(
                    title = "名称",
                    type = EditType.INPUT, search = @Search,
                    notNull = true,
                    inputType = @InputType
            )
    )
    private String name;

    @EruptField(
            views = @View(
                    title = "代号"
            ),
            edit = @Edit(
                    title = "代号",
                    type = EditType.INPUT, search = @Search,
                    notNull = true,
                    inputType = @InputType
            )
    )
    private String code;

    @EruptField(
            views = @View(
                    title = "详细描述"
            ),
            edit = @Edit(
                    title = "详细描述",
                    type = EditType.INPUT, search = @Search, notNull = true,
                    inputType = @InputType
            )
    )
    private String details;

    @EruptField(
            views = @View(title = "类型"),
            edit = @Edit(
                    search = @Search,
                    title = "获取可选类型",
                    type = EditType.CHOICE,
                    desc = "动态获取可选类型",
                    choiceType = @ChoiceType(
                            fetchHandler = SqlChoiceFetchHandler.class,
                            fetchHandlerParams = "select id,name from master_data where category_code='PRODUCT'"
                    ))
    )
    private String metaType;

    @ManyToOne
    @EruptField(
            edit = @Edit(
                    title = "上级树节点",
                    type = EditType.REFERENCE_TREE,
                    referenceTreeType = @ReferenceTreeType(pid = "parent.id")
            )
    )
    private ProductMeta parent;


    @EruptField(
            views = @View(show = false, title = "uid")
    )
    private String uId = UUID.fastUUID().toString();
}