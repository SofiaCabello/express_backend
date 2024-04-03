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

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用来发送邮件
 */
public class EmailUtil {
    private Map<String, String> codeMap = new ConcurrentHashMap<>();
    private Timer timer = new Timer();

    /**
     * 发送验证码，应当调用此方法来发送验证码，而不是直接调用sendVerifyEmail
     * @param email 邮箱
     * @return 是否发送成功
     */
    public boolean sendCode(String email) {
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000)); // 生成6位随机验证码
        codeMap.put(email, code);
        timer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                codeMap.remove(email);
            }
        }, 1000 * 60 * 5); // 5分钟后删除验证码
        try {
            return sendVerifyEmail(email, code);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 验证验证码是否正确，调用此方法来验证验证码
     * @param email 邮箱
     * @param code 验证码
     * @return 是否正确
     */
    public boolean isCorrect(String email, String code) {
        return codeMap.containsKey(email) && codeMap.get(email).equals(code);
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
