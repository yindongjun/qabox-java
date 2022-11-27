package io.fluentqa.qabox.mindmapping;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class MindMappingTransformer {


    /**
     * Transfer Node to String
     *
     * @param path
     * @param <T>
     * @return
     */
    public static <T> List<String> transfer(MindMappingModel<T> path, String extractFieldName) {
        List<String> result = new LinkedList<>();
        for (T childrenNode : path.getChildrenNodes()) {
            if (childrenNode instanceof String) {
                result.add((String) childrenNode);
            } else {
                try {
                    result.add(ReflectUtil.getFieldValue(childrenNode, extractFieldName).toString());
                } catch (Exception e) {
                    result.add(StrUtil.EMPTY);
                }
            }
        }
        return result;
    }


    /**
     * Transfer to Node Path
     *
     * @param path
     * @param obj
     * @param <R>
     * @return
     */
    public static <T, R> R transfer(MindMappingModel<T> path, R obj, Function<T, String> extractFunc) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            MindMappingLevel order = field.getAnnotation(MindMappingLevel.class);
            if (order == null) continue;
            int index = order.value();
            try {
                ReflectUtil.setFieldValue(obj, field,
                        extractFunc.apply(path.getChildrenNodes().get(index)));

            } catch (Exception e) {
                log.error("transfer failed,", e);
            }
        }
        return obj;
    }

    public static <T, R> List<R> transferAll(List<MindMappingModel<T>> paths,
                                             Class<R> clazz, Function<T, String> extractFunc) {
        List<R> result = new ArrayList<>();
        paths.forEach(path -> {
            R obj = ReflectUtil.newInstance(clazz);
            result.add(transfer(path, obj, extractFunc));
        });

        return result;
    }
}
