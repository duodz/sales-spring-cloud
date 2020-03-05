package com.duodz.sales.common;

import lombok.Getter;

/**
 * @author duodz
 * @date 2020/3/3 14:23
 */
@Getter
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败");

    private int code;
    private String message;

    private ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
