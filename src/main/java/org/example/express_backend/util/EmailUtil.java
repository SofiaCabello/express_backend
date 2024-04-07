package org.example.express_backend.util;


import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dm20151123.AsyncClient;
import com.aliyun.sdk.service.dm20151123.models.SingleSendMailRequest;
import com.aliyun.sdk.service.dm20151123.models.SingleSendMailResponse;
import com.aliyun.tea.*;
import com.aliyun.teaopenapi.Client;
import com.aliyun.teaopenapi.models.Config;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 用来发送邮件
 */
@Component
public class EmailUtil {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean sendCode(String email) {
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000)); // 生成6位随机验证码
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(email, code, 5, TimeUnit.MINUTES); // 将验证码存储到Redis中，5分钟后过期

        try {
            return sendVerifyEmail(email, code);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isCorrect(String email, String code) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String storedCode = ops.get(email);
        return code.equals(storedCode);
    }


    /**
     * 发送验证邮件
     * @param email 邮箱
     * @param code 验证码
     * @return 是否发送成功
     * @throws Exception 异常
     */
    private boolean sendVerifyEmail(String email, String code) throws Exception {
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"))
                .accessKeySecret(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"))
                .build());
        System.out.println(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"));
        System.out.println(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));
        AsyncClient client = AsyncClient.builder()
                .region("cn-hangzhou") // Region ID
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dm.aliyuncs.com")
                )
                .build();
        SingleSendMailRequest singleSendMailRequest = SingleSendMailRequest.builder()
                .accountName("noreply@chisa.love")
                .addressType(1)
                .replyToAddress(false)
                .subject("验证码")
                .htmlBody("您的验证码是：" + code)
                .textBody("您的验证码是：" + code)
                .toAddress(email)
                .build();
        CompletableFuture<SingleSendMailResponse> response = client.singleSendMail(singleSendMailRequest);
        SingleSendMailResponse singleSendMailResponse = response.get();
        return singleSendMailResponse.getBody().getEnvId() != null && singleSendMailResponse.getBody().getRequestId() != null;
    }
}
