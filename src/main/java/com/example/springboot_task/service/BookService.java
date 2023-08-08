package com.example.springboot_task.service;

import com.example.springboot_task.domain.Book;
import com.example.springboot_task.domain.School;
import com.example.springboot_task.domain.User;
import com.example.springboot_task.dto.request.BookUpdateDto;
import com.example.springboot_task.dto.response.BookDto;
import com.example.springboot_task.dto.response.ResponseDto;
import com.example.springboot_task.exceptions.ApiBadRequestException;
import com.example.springboot_task.mapping.BookMapper;
import com.example.springboot_task.repository.BookRepository;
import com.example.springboot_task.repository.SchoolRepository;
import com.example.springboot_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    public void createBook(BookUpdateDto bookUpdateDto) {
        try {
            School school = schoolRepository.findById(bookUpdateDto.getSchoolId()).get();
            User user = userRepository.findById(bookUpdateDto.getUserId()).orElse(null);
            Book book = BookMapper.mapToBook(bookUpdateDto, school, user);
            bookRepository.save(book);
        } catch (Exception e) {
            throw new ApiBadRequestException("No school with " + bookUpdateDto.getSchoolId() + " id found.\nOr bad args!!!!!!!!!!");
        }
    }

    public BookDto getBookById(Long id) {
        try {
            Book book = bookRepository.findById(id).get();
            BookDto bookDto = BookMapper.mapToBookDto(book);
            return bookDto;
        } catch (Exception e) {
            throw new ApiBadRequestException("No book with " + id + " id found.");
        }
    }


    public ResponseDto<BookDto> getAllBooks(Integer limit, Integer offset) {
        List fullList = bookRepository.findAll();
        ResponseDto responseDto = new ResponseDto(fullList, limit, offset);
        return responseDto;
    }


    public void updateBook(BookUpdateDto bookUpdateDto) {
        try {
            Long bookId = bookUpdateDto.getId();
            Book book = bookRepository.findById(bookId).get();

            if (bookUpdateDto.getName() != null) {
                book.setName(bookUpdateDto.getName());
            }
            if (bookUpdateDto.getAuthor() != null) {
                book.setAuthor(bookUpdateDto.getAuthor());
            }
            if (bookUpdateDto.getSchoolId() != null) {
                try {
                    School school = schoolRepository.findById(bookUpdateDto.getSchoolId()).get();
                    book.setSchool(school);
                } catch (Exception e) {
                    throw new ApiBadRequestException("No school with " + bookUpdateDto.getSchoolId() + " id found.");
                }
            }
            if (bookUpdateDto.getUserId() != null) {
                try {
                    User user = userRepository.findById(bookUpdateDto.getUserId()).get();
                    book.setUser(user);
                } catch (Exception e) {
                    throw new ApiBadRequestException("No user with " + bookUpdateDto.getUserId() + " id found.");
                }
            }

        } catch (Exception e) {
            throw new ApiBadRequestException("No book with " + bookUpdateDto.getId() + " id found.");
        }
    }

    public void freeBook(Long id) {
        try {
            Book book = bookRepository.findById(id).get();
            book.setUser(null);
            bookRepository.save(book);
        } catch (Exception e) {
            throw new ApiBadRequestException("No book with " + id + " id found.");
        }
    }

    public void deleteBook(Long id) {
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            throw new ApiBadRequestException("No book with " + id + " id found.");
        }
    }
}
