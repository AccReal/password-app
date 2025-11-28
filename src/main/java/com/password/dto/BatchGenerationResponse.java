package com.password.dto;

import java.util.List;

public class BatchGenerationResponse {
    private List<String> passwords;
    private Integer count;

    public BatchGenerationResponse() {
    }

    public BatchGenerationResponse(List<String> passwords, Integer count) {
        this.passwords = passwords;
        this.count = count;
    }

    public List<String> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<String> passwords) {
        this.passwords = passwords;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
