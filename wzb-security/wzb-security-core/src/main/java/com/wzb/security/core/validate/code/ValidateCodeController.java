package com.wzb.security.core.validate.code;

import com.wzb.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName:ValidateCodeController  <br/>
 * Funtion:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/7 9:25   <br/>
 */

@RestController
public class ValidateCodeController {

    /************  第一版 start  ************/
//
//    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
//    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
//
//    @Autowired
//    @Qualifier("imageCodeGenerator")
//    private ValidateCodeGenerator imageCodeGenerator;
//
//    @Autowired
//    @Qualifier("smsCodeGenerator")
//    private ValidateCodeGenerator smsCodeGenerator;
//
//    @Autowired
//    private SmsCodeSender smsCodeSender;
//
//    @GetMapping("/code/image")
//    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));
//        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
//        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
//    }
//
//    @GetMapping("/code/sms")
//    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
//       // 生成验证码
//        ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
//        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
//        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
//        smsCodeSender.send(mobile, smsCode.getCode());
//    }
    /************  第二版  end  ************/

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     *
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
            throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
    }

}
