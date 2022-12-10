
package io.fluentqa.qabox.excel.core.cache;


import io.fluentqa.qabox.excel.utils.RegexpUtil;
import io.fluentqa.qabox.excel.utils.TempFileOperator;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Only for xlsx strings cache

 */
public class StringsCache implements Cache<Integer, String> {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static final int MAX_SIZE_PATH = 1000;

    private static final int MAX_PATH = 5;

    private final List<Path> cacheFiles = new ArrayList<>();

    private final LinkedHashMap<Integer, String[]> activeCache = new LinkedHashMap<Integer, String[]>(MAX_PATH, 0.75F, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > MAX_PATH;
        }
    };

    private String[] cacheValues;

    private int totalCount;

    private int index;

    public void init(int stringCount) {
        if (stringCount == 0) {
            return;
        }
        cacheValues = new String[Math.min(stringCount, MAX_SIZE_PATH)];
    }

    @Override
    public void cache(Integer key, String value) {
        int cacheIndex = key - (key / MAX_SIZE_PATH * MAX_SIZE_PATH);
        // 存在部分情况下，count与实际不一致
        if (cacheIndex >= cacheValues.length) {
            String[] resizeCache = new String[MAX_SIZE_PATH];
            System.arraycopy(cacheValues, 0, resizeCache, 0, cacheValues.length);
            cacheValues = resizeCache;
        }
        cacheValues[cacheIndex] = value;
        totalCount++;
        if ((key + 1) % MAX_SIZE_PATH == 0) {
            if (index == 0) {
                String[] preCache = new String[MAX_SIZE_PATH];
                System.arraycopy(cacheValues, 0, preCache, 0, MAX_SIZE_PATH);
                activeCache.put(0, preCache);
            }
            index++;
            writeToFile();
        }
    }

    @Override
    public String get(Integer key) {
        int route = key / MAX_SIZE_PATH;
        String[] strings = activeCache.get(route);
        if (strings == null) {
            strings = this.getStrings(route);
            activeCache.put(route, strings);
        }
        int index = key - (route * MAX_SIZE_PATH);
        return index >= strings.length ? "" : strings[index];
    }

    private String[] getStrings(int route) {
        Path file = cacheFiles.get(route);
        MappedByteBuffer mbb = null;
        try (FileInputStream fis = new FileInputStream(file.toFile());
             FileChannel fc = fis.getChannel()) {
            mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            byte[] bb = new byte[(int) fc.size()];
            mbb.get(bb);
            String result = new String(bb, StandardCharsets.UTF_8);
            String[] values = result.split(LINE_SEPARATOR);
            for (int i = 0, length = values.length; i < length; i++) {
                values[i] = RegexpUtil.unescapeLineFeed(values[i]);
            }
            return values;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (mbb != null) {
                try {
                    Method getCleanerMethod = mbb.getClass().getMethod("cleaner");
                    getCleanerMethod.setAccessible(true);
                    Object cleaner = getCleanerMethod.invoke(mbb);
                    Method clean = cleaner.getClass().getMethod("clean");
                    clean.setAccessible(true);
                    clean.invoke(cleaner);
                } catch (Exception e) {
                    // do nothing
                }
            }
        }
    }

    public void finished() {
        if (index == 0) {
            activeCache.put(0, cacheValues);
            return;
        }
        int remainder = totalCount - index * MAX_SIZE_PATH;
        if (remainder == 0) {
            cacheValues = null;
            return;
        }
        String[] temp = new String[remainder];
        System.arraycopy(cacheValues, 0, temp, 0, remainder);
        cacheValues = temp;
        writeToFile();
        cacheValues = null;
    }

    private void writeToFile() {
        Path file = TempFileOperator.createTempFile("s_c", ".data");
        cacheFiles.add(file);
        try {
            String content = Arrays.stream(cacheValues).filter(Objects::nonNull)
                    .map(RegexpUtil::escapeLineFeed)
                    .collect(Collectors.joining(LINE_SEPARATOR));
            Files.write(file, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearAll() {
        TempFileOperator.deleteTempFiles(cacheFiles);
    }
}
