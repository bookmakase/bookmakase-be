package com.bookmakase.dto.admin;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import com.bookmakase.domain.Book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookAdminUpdateRequest {
	private String title;
	private String contents;
	private String isbn;
	private LocalDate publishedAt;

	private List<String> authors;
	private List<String> translators;
	private String publisher;

	private Integer price;
	private Integer salePrice;
	private Integer count;

	private String thumbnail;
	private String status;

	public Book toDomain() {
		return Book.builder()
			.title(this.title)
			.contents(this.contents)
			.isbn(this.isbn)
			.publishedAt(
				this.publishedAt != null
					? this.publishedAt.atStartOfDay().atOffset(ZoneOffset.ofHours(9))
					: null
			)

			.authors(this.authors)
			.publisher(this.publisher)
			.translators(this.translators)
			.price(this.price)
			.salePrice(this.salePrice)
			.count(this.count)
			.thumbnail(this.thumbnail)
			.status(this.status)
			.build();
	}
}