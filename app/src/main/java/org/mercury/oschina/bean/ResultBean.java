package org.mercury.oschina.bean;

/**
 * Created by wang.zhonghao on 2017/9/12.
 */

public class ResultBean {


    /**
     * error_description : 取消关注成功
     * error : 200
     * relation : 3
     */

    private String error_description;
    private String error;
    private int    relation;

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }
}
