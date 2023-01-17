package io.springbatch.practice.interfaces;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {

    private final String id;
    private final String value;

    public Member(String id, String value) {
        this.id = id;
        this.value = value;
    }
}
