package com.wzb.web.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * ClassName:MockServer  <br/>
 * Funtion:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/3 17:58   <br/>
 */

public class MockServer {

    public static void main(String[] args) {

        // 配置WireMock服务器的地址 http://localhost:8080
        configureFor("192.168.0.102", 8065);
        // 清除WireMock服务器里之前的配置
        removeAllMappings();

        //伪造一个get请求，请求地址为 /user/1，然后返回体包含JSON数据，状态为200
        stubFor(get(urlEqualTo("/user/1"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"mrbird\",\"url\":\"https://mrbird.cc\",\"age\":18}")
                        .withStatus(200)));
    }
}
