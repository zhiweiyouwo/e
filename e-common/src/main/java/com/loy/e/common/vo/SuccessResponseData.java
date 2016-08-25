package com.loy.e.common.vo;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class SuccessResponseData extends SuccessResponse {

    private Object data;

    public SuccessResponseData() {
        super();
    }

    public static SuccessResponseData newInstance(Object data) {
        return new SuccessResponseData(data);
    }

    public SuccessResponseData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}