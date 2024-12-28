package com.hichat.mychat.model.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String publicName;
    private Integer ageBefore;
    private Integer ageAfter;

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public Integer getAgeBefore() {
        return ageBefore;
    }

    public void setAgeBefore(Integer ageBefore) {
        this.ageBefore = ageBefore;
    }

    public Integer getAgeAfter() {
        return ageAfter;
    }

    public void setAgeAfter(Integer ageAfter) {
        this.ageAfter = ageAfter;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "username='" + publicName + '\'' +
                ", ageBefore=" + ageBefore +
                ", ageAfter=" + ageAfter +
                '}';
    }
}
