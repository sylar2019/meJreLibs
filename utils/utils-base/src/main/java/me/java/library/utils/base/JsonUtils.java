package me.java.library.utils.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * File Name             :  JsonUtils
 * Author                :  sylar
 * Create Date           :  2018/4/10
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) xxx.com   All Rights Reserved
 * *******************************************************************************************
 */
public class JsonUtils {
    protected static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    private JsonUtils() {
    }

    public static String toJSONString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        try {
            return objectMapper.readValue(text, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
            return objectMapper.readValue(text, javaType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getString(String text, String key) {
        try {
            JsonNode jsonNode = objectMapper.readValue(text, JsonNode.class);
            return jsonNode.get(key).textValue();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用于校验一个字符串是否是合法的JSON格式
     *
     * @param input
     * @return
     */
    public static boolean isValid(String input) {
        return new JsonValidator().validate(input);
    }

    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        String json = toJSONString(source);
        return parseObject(json, targetClass);
    }

    public static <T> List<T> copyList(List<?> source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        if (source.size() == 0) {
            return Lists.newArrayList();
        }

        return parseArray(toJSONString(source), targetClass);
    }

    public static String format(String strJson) {
        // 计数tab的个数
        int tabNum = 0;
        StringBuilder jsonFormat = new StringBuilder();
        int length = strJson.length();

        char last = 0;
        for (int i = 0; i < length; i++) {
            char c = strJson.charAt(i);
            if (c == '{') {
                tabNum++;
                jsonFormat.append(c + "\n");
                jsonFormat.append(getSpaceOrTab(tabNum));
            } else if (c == '}') {
                tabNum--;
                jsonFormat.append("\n");
                jsonFormat.append(getSpaceOrTab(tabNum));
                jsonFormat.append(c);
            } else if (c == ',') {
                jsonFormat.append(c + "\n");
                jsonFormat.append(getSpaceOrTab(tabNum));
            } else if (c == ':') {
                jsonFormat.append(c + " ");
            } else if (c == '[') {
                tabNum++;
                char next = strJson.charAt(i + 1);
                if (next == ']') {
                    jsonFormat.append(c);
                } else {
                    jsonFormat.append(c + "\n");
                    jsonFormat.append(getSpaceOrTab(tabNum));
                }
            } else if (c == ']') {
                tabNum--;
                if (last == '[') {
                    jsonFormat.append(c);
                } else {
                    jsonFormat.append("\n" + getSpaceOrTab(tabNum) + c);
                }
            } else {
                jsonFormat.append(c);
            }
            last = c;
        }
        return jsonFormat.toString();
    }

    private static String getSpaceOrTab(int tabNum) {
        StringBuffer sbTab = new StringBuffer();
        for (int i = 0; i < tabNum; i++) {
            sbTab.append('\t');
        }
        return sbTab.toString();
    }

    static class JsonValidator {

        private CharacterIterator it;
        private char c;
        private int col;

        public JsonValidator() {
        }

        /**
         * 验证一个字符串是否是合法的JSON串
         *
         * @param input 要验证的字符串
         * @return true-合法 ，false-非法
         */
        public boolean validate(String input) {
            input = input.trim();
            boolean ret = valid(input);
            return ret;
        }

        private boolean valid(String input) {
            if ("".equals(input)) {
                return true;
            }

            boolean ret = true;
            it = new StringCharacterIterator(input);
            c = it.first();
            col = 1;
            if (!value()) {
                ret = error("value", 1);
            } else {
                skipWhiteSpace();
                if (c != CharacterIterator.DONE) {
                    ret = error("end", col);
                }
            }

            return ret;
        }

        private boolean value() {
            return literal("true") || literal("false") || literal("null") || string() || number() || object() || array();
        }

        private boolean literal(String text) {
            CharacterIterator ci = new StringCharacterIterator(text);
            char t = ci.first();
            if (c != t) {
                return false;
            }

            int start = col;
            boolean ret = true;
            for (t = ci.next(); t != CharacterIterator.DONE; t = ci.next()) {
                if (t != nextCharacter()) {
                    ret = false;
                    break;
                }
            }
            nextCharacter();
            if (!ret) {
                error("literal " + text, start);
            }
            return ret;
        }

        private boolean array() {
            return aggregate('[', ']', false);
        }

        private boolean object() {
            return aggregate('{', '}', true);
        }

        private boolean aggregate(char entryCharacter, char exitCharacter, boolean prefix) {
            if (c != entryCharacter) {
                return false;
            }
            nextCharacter();
            skipWhiteSpace();
            if (c == exitCharacter) {
                nextCharacter();
                return true;
            }

            for (; ; ) {
                if (prefix) {
                    int start = col;
                    if (!string()) {
                        return error("string", start);
                    }
                    skipWhiteSpace();
                    if (c != ':') {
                        return error("colon", col);
                    }
                    nextCharacter();
                    skipWhiteSpace();
                }
                if (value()) {
                    skipWhiteSpace();
                    if (c == ',') {
                        nextCharacter();
                    } else if (c == exitCharacter) {
                        break;
                    } else {
                        return error("comma or " + exitCharacter, col);
                    }
                } else {
                    return error("value", col);
                }
                skipWhiteSpace();
            }

            nextCharacter();
            return true;
        }

        private boolean number() {
            char line = '-';
            if (!Character.isDigit(c) && c != line) {
                return false;
            }
            int start = col;
            if (c == line) {
                nextCharacter();
            }

            char zero = '0';
            if (c == zero) {
                nextCharacter();
            } else if (Character.isDigit(c)) {
                while (Character.isDigit(c)) {
                    nextCharacter();
                }
            } else {
                return error("number", start);
            }
            char dot = '.';
            if (c == dot) {
                nextCharacter();
                if (Character.isDigit(c)) {
                    while (Character.isDigit(c)) {
                        nextCharacter();
                    }
                } else {
                    return error("number", start);
                }
            }

            char le = 'e';
            char ge = 'E';
            char plus = '+';
            if (c == le || c == ge) {
                nextCharacter();
                if (c == plus || c == line) {
                    nextCharacter();
                }
                if (Character.isDigit(c)) {
                    while (Character.isDigit(c)) {
                        nextCharacter();
                    }
                } else {
                    return error("number", start);
                }
            }
            return true;
        }

        private boolean string() {
            char s = '"';
            if (c != s) {
                return false;
            }

            int start = col;
            boolean escaped = false;
            for (nextCharacter(); c != CharacterIterator.DONE; nextCharacter()) {
                if (!escaped && c == '\\') {
                    escaped = true;
                } else if (escaped) {
                    if (!escape()) {
                        return false;
                    }
                    escaped = false;
                } else if (c == '"') {
                    nextCharacter();
                    return true;
                }
            }
            return error("quoted string", start);
        }

        private boolean escape() {
            int start = col - 1;
            String sstr = " \\\"/bfnrtu";
            if (sstr.indexOf(c) < 0) {
                return error("escape sequence  \\\",\\\\,\\/,\\b,\\f,\\n,\\r,\\t  or  \\uxxxx ", start);
            }
            char u = 'u';
            if (c == u) {
                if (!ishex(nextCharacter()) || !ishex(nextCharacter()) || !ishex(nextCharacter())
                        || !ishex(nextCharacter())) {
                    return error("unicode escape sequence  \\uxxxx ", start);
                }
            }
            return true;
        }

        private boolean ishex(char d) {
            return "0123456789abcdefABCDEF".indexOf(c) >= 0;
        }

        private char nextCharacter() {
            c = it.next();
            ++col;
            return c;
        }

        private void skipWhiteSpace() {
            while (Character.isWhitespace(c)) {
                nextCharacter();
            }
        }

        private boolean error(String type, int col) {
            System.out.printf("type: %s, col: %s%s", type, col, System.getProperty("line.separator"));
            return false;
        }
    }
}
