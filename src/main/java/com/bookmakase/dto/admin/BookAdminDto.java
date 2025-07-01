package com.bookmakase.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class BookAdminDto {
    private Long bookId;
    private String title;
    private List<String> authors;
    private String isbn;
    private OffsetDateTime createdAt;
    private String status;
    private Integer count;
}
