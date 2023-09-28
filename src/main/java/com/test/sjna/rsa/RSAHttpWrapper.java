package com.test.sjna.rsa;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.PrivateKey;

/**
 * 화면단에서 보내는 데이터를 복호화 함
 * http 프로토콜 중 parameter만 복호화 시도
 * multipart(파일)은 지원안함
 */
@Slf4j
public class RSAHttpWrapper extends HttpServletRequestWrapper {

    private PrivateKey privateKey;

    public RSAHttpWrapper(HttpServletRequest request) {
        super(request);

        privateKey = (PrivateKey) request.getSession().getAttribute(RSAUtil.RSA_WEB_KEY);

        log.debug("privateKey {}", privateKey);
        if(privateKey == null) {
            //복호화하기 위해서 privatekey가 필요한데 session에 privatekey가 없는 상태
            //화면단에서 서버로 보낼 때 공개키로 복호화 하지 않았거나 session의 privatekey가 없는 것이니 확인 필요
            throw new NullPointerException("privatekey is null. Please send RSA encrypted data. Or check if a privatekey has been created");
        }
    }

    //controller메소드의 파라미터로 데이터를 가져오려고 할 때 getParameterValues를 사용함으로 getParameterValues에 복호화함수 넣음
    @Override
    public String[] getParameterValues(String name) {
        log.debug("getParameterValues name={}", name);

        String[] result = super.getParameterValues(name);

        try {
            if(result != null && result.length > 0) {
                for(int i = 0; i < result.length;i++) {
                    if(result[i] ==  null || result[i].isEmpty()) continue;

                    String a = RSAUtil.decryptRsa(privateKey, result[i]);
                    result[i] = a;
                }
            }

        } catch (Exception e) {
            log.error("RSA decrypt error: {}", e.getMessage(), e);  //tomcat catalina.out에 로그 출력(throw만 할 경우 로그가 출력되지 않음)
            throw new RuntimeException();   //filter chain을 중지시키고 사용자에게 500에러 출력
        }

        log.debug("getParameter result={}", result);

        return result;
    }


    @Override
    public String getParameter(String name) {
        log.debug("getParameter url {}", ((HttpServletRequest)getRequest()).getRequestURL().toString());
        log.debug("getParameter {}", name);

        String result = super.getParameter(name);

        if(result != null && !result.isEmpty()) {
            try {
                return RSAUtil.decryptRsa(privateKey, result);
            } catch (Exception e) {
                log.error("RSA decrypt error: {}", e.getMessage(), e);  //tomcat catalina.out에 로그 출력(throw만 할 경우 로그가 출력되지 않음)
                throw new RuntimeException();   //filter chain을 중지시키고 사용자에게 500에러 출력
            }
        }

        return result;

    }

}
