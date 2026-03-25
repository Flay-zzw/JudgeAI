package com.zzw.judgeai.controller;

import com.zzw.judgeai.common.BaseResponse;
import com.zzw.judgeai.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JudgeAIController {

    @GetMapping("/health")
    public BaseResponse<String> healthCheck() {
        return ResultUtils.success("ok");
    }



}
