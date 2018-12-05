package com.ai.service.file;

import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;

@Component
public class OosFileQianNiu {
    private static final String accessKey = "aMJBVHzA8JoA00aInKjOB63aR3PNiPWrw67qz3pU";
    private static final String secretKey = "CPduqZsuV19azs0bSOvMqiomIshO37AzFZ7s-53V";
    private static final String bucket = "lovepicture";

    public String produceSign(String key){
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucket);
    }
}
