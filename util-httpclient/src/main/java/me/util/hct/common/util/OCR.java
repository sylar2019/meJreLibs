package me.util.hct.common.util;

import me.util.hct.HttpClientUtil;
import me.util.hct.builder.HCB;
import me.util.hct.common.HttpConfig;
import me.util.hct.common.HttpHeader;
import me.util.hct.common.Utils;
import me.util.hct.exception.HttpProcessException;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 识别验证码
 *
 * @author arron
 * @version 1.0
 */
public class OCR {

    /**
     * 接口说明：
     * https://github.com/AvensLab/OcrKing/blob/master/线上识别http接口说明.txt
     */
    private static final String apiUrl = "http://lab.ocrking.com/ok.html";
    private static final String apiKey = PropertiesUtil.getProperty("OCR.key");
    private static HttpClient client = null;

    public static void enableCatch() {
        client = HCB.custom().proxy("127.0.0.1", 8888).build();
    }

    public static void unEnableCatch() {
        client = null;
    }

    //获取固定参数
    private static Map<String, Object> getParaMap() {
        //加载所有参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("service", "OcrKingForCaptcha");
        map.put("language", "eng");
        //7-数字大写小写，5-数字大写字母
        map.put("charset", "7");
        map.put("type", "http://www.unknown.com");
        map.put("apiKey", apiKey);
        return map;
    }

    /**
     * 识别本地校验码（英文：字母+大小写）
     *
     * @param filePath 验证码地址
     * @return 返回识别的验证码结果
     */
    public static String ocrCode(String filePath) {
        return ocrCode(filePath, 0);
    }

    /**
     * 识别本地校验码（英文：字母+大小写）
     *
     * @param imgFilePath  验证码地址
     * @param limitCodeLen 验证码长度（如果结果与设定长度不一致，则返回获取失败的提示）
     * @return 返回识别的验证码结果
     */
    public static String ocrCode(String imgFilePath, int limitCodeLen) {
        //读取文件
        File f = new File(imgFilePath);
        if (!f.exists()) {
            return "Error:文件不存在!";
        }

        String html;
        try {
            html = HttpClientUtil.upload(HttpConfig.custom().client(client).url(apiUrl).files(new String[]{imgFilePath}, "ocrfile", true).map(getParaMap()));
            String[] results = StringUtil.regex("<Result>([^<]*)</Result>\\s*<Status>([^<]*)</Status>", html);
            if (results.length > 0) {
                if (Boolean.parseBoolean(results[1])) {
                    //不判断长度或者长度一致时，直接返回
                    if (limitCodeLen <= 0 || limitCodeLen == results[0].length()) {
                        return results[0];
                    } else {
                        return "Error:获取失败! 原因：识别结果长度为:" + results[0].length() + "（期望长度:" + limitCodeLen + "）";
                    }
                } else {
                    return "Error:获取失败! 原因：" + results[0];
                }
            }
        } catch (HttpProcessException e) {
            Utils.exception(e);
        }

        return "Error:获取失败!";
    }


    /**
     * 直接获取网络验证码（验证码不刷新）
     *
     * @param imgUrl 验证码地址
     * @return 返回识别的验证码结果
     */
    public static String ocrCode4Net(String imgUrl) {
        return ocrCode4Net(imgUrl, 0);
    }

    /**
     * 直接获取网络验证码（验证码不刷新）
     *
     * @param imgUrl       验证码地址
     * @param limitCodeLen 验证码长度
     * @return 返回识别的验证码结果
     */
    public static String ocrCode4Net(String imgUrl, int limitCodeLen) {
        Map<String, Object> map = getParaMap();
        map.put("url", imgUrl);

        Header[] headers = HttpHeader.custom().userAgent("Mozilla/5.0 (Windows NT 5.1; zh-CN; rv:1.9.1.3) Gecko/20100101 Firefox/8.0").build();

        try {
            String html = HttpClientUtil.post(HttpConfig.custom().client(client).url(apiUrl).headers(headers).map(map));
            String[] results = StringUtil.regex("<Result>([^<]*)</Result>\\s*<Status>([^<]*)</Status>", html);
            if (results.length > 0) {
                if (Boolean.parseBoolean(results[1])) {
                    //不判断长度或者长度一致时，直接返回
                    if (limitCodeLen <= 0 || limitCodeLen == results[0].length()) {
                        return results[0];
                    } else {
                        return "Error:获取失败! 原因：识别结果长度为:" + results[0].length() + "（期望长度:" + limitCodeLen + "）";
                    }
                } else {
                    return "Error:获取失败! 原因：" + results[0];
                }
            }
        } catch (HttpProcessException e) {
            Utils.exception(e);
        }

        return "Error:获取失败!";
    }


    /**
     * 直接获取网络验证码（通过获取图片流，然后识别验证码）
     *
     * @param config   HttpConfig对象（设置cookie）
     * @param savePath 图片保存的完整路径（值为null时，不保存），如：c:/1.png
     * @return 返回识别的验证码结果
     */
    public static String ocrCode4Net(HttpConfig config, String savePath) {
        return ocrCode4Net(config, savePath, 0);
    }

    /**
     * 直接获取网络验证码（通过获取图片流，然后识别验证码）
     *
     * @param config       HttpConfig对象（设置cookie）
     * @param savePath     图片保存的完整路径（值为null时，不保存），如：c:/1.png
     * @param limitCodeLen 验证码长度
     * @return 返回识别的验证码结果
     */
    public static String ocrCode4Net(HttpConfig config, String savePath, int limitCodeLen) {
        //如果不保存图片，则直接使用图片地址的方式获取验证码
        if (savePath == null || savePath.equals("")) {
            return ocrCode4Net(config.url(), limitCodeLen);
        }

        //下载图片
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out = (ByteArrayOutputStream) HttpClientUtil.down(config.client(client).out(out));
            //本地测试，可以保存一下图片，方便核验
            FileOutputStream fos = new FileOutputStream(savePath);
            fos.write(out.toByteArray());

            return ocrCode(savePath, limitCodeLen);
        } catch (HttpProcessException e) {
            Utils.exception(e);
        } catch (IOException e) {
            Utils.exception(e);
        }

        return "Error:获取失败!";
    }
}
