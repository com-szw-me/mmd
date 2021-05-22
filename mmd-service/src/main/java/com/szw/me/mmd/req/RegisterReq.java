package com.szw.me.mmd.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@ApiModel("用户注册请求")
public class RegisterReq {
    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty(value = "昵称", required = true)
    private String nick;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @NotEmpty(message = "请给出正确的手机号码")
    @ApiModelProperty(value = "手机号码")
    private String phone;

    @NotEmpty(message = "请给出正确的邮箱地址")
    @ApiModelProperty(value = "邮箱地址")
    private String email;
}
