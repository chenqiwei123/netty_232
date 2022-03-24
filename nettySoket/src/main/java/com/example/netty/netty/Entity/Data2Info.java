package com.example.netty.netty.Entity;

public class Data2Info {
    private String nodeId;
    private String TagId;

    public Data2Info(String nodeId, String tagId) {
        this.nodeId = nodeId;
        TagId = tagId;
    }

    public Data2Info() {
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getTagId() {
        return TagId;
    }

    public void setTagId(String tagId) {
        TagId = tagId;
    }
}
