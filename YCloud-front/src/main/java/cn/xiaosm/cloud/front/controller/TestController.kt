package cn.xiaosm.cloud.front.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping
@RestController
class TestController {

    @RequestMapping("/test1")
    fun test(): String {
        return "10";
    }

}