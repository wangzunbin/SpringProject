package com.wzb.core;

import com.wzb.security.core.validate.code.ValidateCodeGenerator;
import com.wzb.security.core.validate.code.image.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * ClassName:DemoImageCodeGenerator  <br/>
 * Funtion: 此代码会覆盖core的默认imageCodeGenerator <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/7 12:59   <br/>
 */

//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator  implements ValidateCodeGenerator {

    @Override
    public ImageCode generate(ServletWebRequest request) {
        System.out.println("demo ---> 更高级的图形验证码生成代码");
        return null;
    }
}
