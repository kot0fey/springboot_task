package com.example.springboot_task.mapping;

import com.example.springboot_task.domain.Book;
import com.example.springboot_task.domain.School;
import com.example.springboot_task.domain.User;
import com.example.springboot_task.dto.request.BookUpdateDto;
import com.example.springboot_task.dto.response.BookDto;

import java.util.Locale;

public class BookMapper {
    public static Book mapToBook(BookUpdateDto bookUpdateDto, School school, User user){
        return Book.builder()
                .name(bookUpdateDto.getName())
                .author(bookUpdateDto.getAuthor())
                .school(school)
                .user(user)
                .build();
    }

    public static BookDto mapToBookDto(Book book){
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .schoolId(book.getSchool().getId())
                .free(book.getUser()==null)
                .build();
    }
}
