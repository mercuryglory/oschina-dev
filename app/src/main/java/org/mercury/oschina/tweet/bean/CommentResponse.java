package org.mercury.oschina.tweet.bean;

import org.mercury.oschina.bean.base.BaseBean;

import java.util.List;

/**
 * 评论列表实体类
 * 
 * @author Mercury
 * @created 2014年10月14日 下午3:32:39
 * 
 */

public class CommentResponse extends BaseBean{

    private List<Comment> commentList;

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

}
