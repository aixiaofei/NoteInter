package com.ai.service.file;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class OosFileTencent {

    private static final String AK = "AKIDe8PRElg3nHVS9auiPULyLUO3ShpuIkW6";
    private static final String SK = "VlmY5NDpDMHxOaWDfsrb6OQDj01lLgss";
    private static final String bucketName = "lovepicture-1257159905";
    private COSClient cosClient;
    private static final Map<String, HttpMethodName> TYPE = new HashMap<>(3);

    OosFileTencent() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(AK, SK);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig中包含了设置region, https(默认http), 超时, 代理等set方法, 使用可参见源码或者接口文档FAQ中说明
        ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
        // 3 生成cos客户端
        cosClient = new COSClient(cred, clientConfig);

        TYPE.put("0", HttpMethodName.PUT);
        TYPE.put("1", HttpMethodName.GET);
        TYPE.put("2", HttpMethodName.DELETE);
    }

    public String produceSign(String key, String type) {
        COSCredentials cred = new BasicCOSCredentials(AK, SK);
        COSSigner signer = new COSSigner();
        Date expiredTime = new Date(System.currentTimeMillis() + 180L * 1000L);
        return signer.buildAuthorizationStr(TYPE.get(type), key, cred, expiredTime);
    }
}
