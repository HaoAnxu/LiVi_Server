package com.anxu.livi.VBScript;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PlaceholderReplacer {
    // 正则
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{([^}]+)}");

    /**
     * 核心方法：替换文本中的 {占位符}
     * @param template 带占位符的模板文本（比如VBS脚本模板）
     * @param valueMap 占位符-值映射（key=占位符名，value=替换值）
     * @return 替换后的文本
     */
    public static String replace(String template, Map<String, String> valueMap) {
        if (template == null || template.isEmpty()) {
            return "";
        }
        if (valueMap == null) {
            valueMap = Map.of();
        }

        Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
        StringBuilder result = new StringBuilder();

        // 循环替换所有占位符
        while (matcher.find()) {
            String key = matcher.group(1); // 获取{}内的占位符名（比如{p}→p）
            String value = valueMap.getOrDefault(key, ""); // 无对应值则置空
            matcher.appendReplacement(result, value);
        }
        matcher.appendTail(result); // 拼接最后一个占位符后的剩余文本

        return result.toString();
    }
}