package me.java.library.mq.ons.http;

import com.aliyun.openservices.ons.api.impl.authority.AuthUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import me.java.library.utils.base.JsonUtils;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentProvider;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by sylar on 2017/1/4.
 */
public class HttpUtil {


    final static String NEWLINE = "\n";
    public static String ACCESSKEY = "AccessKey";
    public static String SIGNATURE = "Signature";
    public static String PRODUCERID = "ProducerID";
    public static String CONSUMERID = "ConsumerID";

    public static HttpResult sendMsg(String webDomain,
                                     String accessKey,
                                     String secretKey,
                                     String producerId,
                                     String topic,
                                     String tags,
                                     String keys,
                                     String msgContent
    ) throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.setMaxConnectionsPerDestination(1);
        httpClient.start();

        String time = String.valueOf(System.currentTimeMillis());
        String fullUrl = String.format("%s/message/?topic=%s&time=%s&tag=%s&key=%s",
                webDomain, topic, time, tags, keys);
        Request req = httpClient.POST(fullUrl);
        ContentProvider content = new StringContentProvider(msgContent);
        req.content(content);
        String signString = topic + NEWLINE + producerId + NEWLINE + MD5.getInstance().getMD5String(msgContent) +
                NEWLINE + time;
        System.out.println("signString<---------------" + signString);
        System.out.println("signString--------------->");
        String sign = AuthUtil.calSignature(signString.getBytes(Charset.forName("UTF-8")), secretKey);
        req.header(SIGNATURE, sign);
        req.header(ACCESSKEY, accessKey);
        req.header(PRODUCERID, producerId);
        ContentResponse response;
        response = req.send();
        HttpResult result = new HttpResult(response.getStatus());
        result.setContent(response.getContentAsString());
        System.out.println(result);
        httpClient.stop();
        return result;
    }


    public static List<HttpMsgExt> receiveMsg(String webDomain,
                                              String accessKey,
                                              String secretKey,
                                              String consumerId,
                                              String topic,
                                              int maxMessageCount

    ) throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.setMaxConnectionsPerDestination(1);
        httpClient.start();

        String time = String.valueOf(System.currentTimeMillis());
        String fullUrl = String.format("%s/message/?topic=%s&time=%s&num=%s",
                webDomain, topic, time, maxMessageCount);

        Request req = httpClient.POST(fullUrl);
        req.method(HttpMethod.GET);
        ContentResponse response;
        String signString = topic + NEWLINE + consumerId + NEWLINE + time;
        String sign = AuthUtil.calSignature(signString.getBytes(Charset.forName("UTF-8")), secretKey);
        req.header(SIGNATURE, sign);
        req.header(ACCESSKEY, accessKey);
        req.header(CONSUMERID, consumerId);

        System.out.println("开始查询消息...");
        long start = System.currentTimeMillis();
        response = req.send();

        System.out.println("耗时:" + (System.currentTimeMillis() - start) / 1000
                + "秒  状态码:" + response.getStatus());

        List<HttpMsgExt> list = null;
        if (!Strings.isNullOrEmpty(response.getContentAsString())) {
            list = JsonUtils.parseArray(response.getContentAsString(), HttpMsgExt.class);
        }

        if (list == null || list.size() == 0) {
            System.out.println("消息数目: 0");
            return Lists.newArrayList();
        }

        System.out.println("消息数目:" + list.size());

        /**
         *  GET消息后，如果不DELETE, 那么至少5分钟后会被再次GET到。
         GET消息后，如果想DELETE，需要在30秒内删除（msgHandle在GET请求的response里返回，有效期限为30秒）。
         */
        for (HttpMsgExt msg : list) {
            time = String.valueOf(System.currentTimeMillis());

            fullUrl = String.format("%s/message/?msgHandle=%s&topic=%s&time=%s",
                    webDomain, msg.getMsgHandle(), topic, time);
            req = httpClient.POST(fullUrl);
            req.method(HttpMethod.DELETE);
            signString = topic + NEWLINE + consumerId + NEWLINE + msg.getMsgHandle() + NEWLINE + time;
            sign = AuthUtil.calSignature(signString.getBytes(Charset.forName("UTF-8")), secretKey);
            req.header(SIGNATURE, sign);
            req.header(ACCESSKEY, accessKey);
            req.header(CONSUMERID, consumerId);
            response = req.send();
            System.out.println("delete msg:" + response.toString());
        }

        httpClient.stop();
        return list;
    }

}
