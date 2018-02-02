package cn.ghostcloud.cmal.utils;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.SerializationUtils;


/**
 * 签名验证组件
 */
@Component
public class SignatureUtils {
    /**
     * SECRET_KEY ACCESS_ID CALLBACK_URI  用于鉴权的常量 从配置文件获取
     */
    private static String SECRET_KEY;
    private static String ACCESS_ID;
    private static String CALLBACK_URI;

    @Value("${push.http.secret-key}")
    public void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }

    @Value("${push.http.access-id}")
    public void setAccessId(String accessId) {
        ACCESS_ID = accessId;
    }

    @Value("${push.http.callback-url}")
    public void setCallbackUri(String callbackUri) {
        CALLBACK_URI = callbackUri;
    }


    /**
     * 返回本地计算产生的签名验证字符串与Headers 里面的 Signature 比较的结果，相等为真
     *
     * @return [isAuthorized]  检验结果
     */
    public static Boolean isSignatureValid(RequestEntity request) {

        // 解析request数据
        HttpHeaders headers = request.getHeaders();
        String contentType = headers.getContentType() == null ? "" : headers.getContentType().getType();
        String timestamp = headers.containsKey("Timestamp")  ? headers.get("Timestamp").toString():"";
        String signatureHost = headers.containsKey("Signature")  ? headers.get("Signature").toString():"";
        Object body = request.hasBody() ? request.getBody() : "";

        // 本地计算的 signature
        String signatureLocal = processSignature(request.getMethod(), contentType, timestamp, body);

        return signatureLocal.equals(signatureHost);
    }

    /**
     * @param method      HTTP 请求方法
     * @param contentType HTTP header 中的 ContentType
     * @param timeStamp   HTTP header 中的 timeStamp
     * @param body        request Body
     * @return 签名本地计算结果
     */
    private static String processSignature(HttpMethod method, String contentType, String timeStamp, Object body) {
        //StringToSign = HTTP-Verb + "\n" + content_MD5 + "\n" + Content-Type +"\n" + Timestamp + "\n" + {回调uri}
        String stringToSign = method
                + "\n"
                + DigestUtils.md5Hex(SerializationUtils.serialize(body))
                + "\n"
                + contentType
                + "\n"
                + timeStamp
                + "\n"
                + CALLBACK_URI;

        //Signature = Base64(HMAC_SHA1({SecretKey}, UTF_8_Encoding_Of(StringToSign)));
        String signature = Base64Utils.encodeToString(new HmacUtils(HmacAlgorithms.HMAC_SHA_1, SECRET_KEY).hmac(stringToSign.getBytes(Charsets.UTF_8)));

        return "g7ac" + " " + timeStamp + " " + ACCESS_ID + ":" + signature;
    }
}
