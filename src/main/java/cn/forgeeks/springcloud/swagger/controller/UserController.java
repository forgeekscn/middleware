package cn.forgeeks.springcloud.swagger.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/swagger", produces = "application/json")
@Api(value = "User", tags = {"User"}, description = "用户相关")
public class UserController {

    @GetMapping("/{id}")
    @ApiOperation(value = "使用ID查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "ID", name = "id", dataType = "int", paramType = "path", required = true, defaultValue = "1")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucess>"),
            @ApiResponse(code = 400, message = "请求参数有误"),
            @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"),
            @ApiResponse(code = 404, message = "请求路径不存在"),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    public String getById(@PathVariable("id") Integer id) {
        return "hello";
    }

}