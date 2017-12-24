package com.community.common.state;

public enum TopicState {
    DELETE(0),NORMAL(1);
    private Integer state;

    public Integer getState() {
        return state;
    }

    TopicState(Integer state){
        this.state=state;
    }
    public void setState(Integer state) {
        this.state = state;
    }
}
