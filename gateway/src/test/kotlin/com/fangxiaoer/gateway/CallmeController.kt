package com.fangxiaoer.gateway

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/callme")
class CallmeController {
    @PreAuthorize("hasAuthority('SCOPE_TEST')")
    @GetMapping("/ping")
    fun ping(): String {
        return "Hello!"
    }
}
