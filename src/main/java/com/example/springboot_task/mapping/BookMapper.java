package com.example.springboot_task.mapping;

import com.example.springboot_task.domain.Book;
import com.example.springboot_task.domain.School;
import com.example.springboot_task.domain.User;
import com.example.springboot_task.dto.request.BookUpdateDto;
import com.example.springboot_task.dto.response.BookDto;

public class BookMapper {
    public static Book mapToBook(BookUpdateDto bookUpdateDto, School school, User user){
        return new Book(
                bookUpdateDto.getId(),
                bookUpdateDto.getName(),
                bookUpdateDto.getAuthor(),
                school,
                user
        );
    }

    public static BookDto mapToBookDto(Book book){
        return new BookDto(
                book.getId(),
                book.getName(),
                book.getSchool().getId(),
                book.getUser() == null
        );
    }
}
