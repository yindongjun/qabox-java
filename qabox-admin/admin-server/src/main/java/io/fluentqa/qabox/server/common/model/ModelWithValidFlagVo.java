package io.fluentqa.qabox.server.common.model;


import lombok.Data;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.MetaModelCreateVo;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class ModelWithValidFlagVo extends MetaModelCreateVo {

    @EruptField(
            views = @View(
                    title = "是否有效"
            ),
            edit = @Edit(
                    title = "是否有效",
                    type = EditType.BOOLEAN, search = @Search, notNull = true,
                    boolType = @BoolType
            )
    )
    private Boolean valid = true;

}
