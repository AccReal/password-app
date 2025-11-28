package com.password.dto;

public class BatchGenerationRequest extends GenerationRequest {
    private Integer count;

    public BatchGenerationRequest() {
        super();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
