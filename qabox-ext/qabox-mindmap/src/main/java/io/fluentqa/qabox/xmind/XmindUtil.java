package io.fluentqa.qabox.xmind;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipFile;

public class XmindUtil {

    public static XmindRawData readXmindFile(String filePath) {
        XmindRawData.XmindRawDataBuilder builder = XmindRawData.builder();
        try (ZipFile zipFile = ZipUtil.toZipFile(FileUtil.file(filePath), StandardCharsets.UTF_8)) {
            ZipUtil.read(zipFile, zipEntry -> {
                // 读取到content.xml,存储起来
                if ("content.xml".equals(zipEntry.getName())) {
                    try (InputStream zipStream = ZipUtil.getStream(zipFile, zipEntry)) {
                        builder.contentXml(new String(IoUtil.readBytes(zipStream)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                // 读取到content.json,存储起来,说明当前读取的xmind是zen格式
                if ("content.json".equals(zipEntry.getName())) {
                    try (InputStream zipStream = ZipUtil.getStream(zipFile, zipEntry)) {
                        builder.contentJson(new String(IoUtil.readBytes(zipStream)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("解析失败!" + e.getLocalizedMessage());
        }

        return builder.build();
    }
}
