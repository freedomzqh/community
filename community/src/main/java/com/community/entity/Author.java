package com.community.entity;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 作者的简要信息
 */
public class Author implements Serializable{
    /**
     *作者I
     */
    @NotBlank(message = "作者id不能为空")
    private String authorId;

    /**
     * 用户名
     */
    private String nickName;

    /**
     * 头像地址
     */
    private String avatarUrl;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
