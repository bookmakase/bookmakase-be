package com.bookmakase.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BookAdminListResponse {
    private List<BookAdminDto> content;
    private PageInfo pageInfo;

    @Getter
    @Setter
    @Builder
    public static class PageInfo {
        private int currentPage;
        private int totalPages;
        private long totalElements;
    }
}
