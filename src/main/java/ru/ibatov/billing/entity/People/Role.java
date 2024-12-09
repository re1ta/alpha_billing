package ru.ibatov.billing.entity.People;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public enum Role {

    ADMIN("admin", "admin"),
    USER("user","user");

    private String id;
    private String roleName;
}
