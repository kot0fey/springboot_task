package com.example.springboot_task.service;

import com.example.springboot_task.domain.Book;
import com.example.springboot_task.domain.School;
import com.example.springboot_task.domain.User;
import com.example.springboot_task.dto.request.BookUpdateDto;
import com.example.springboot_task.dto.response.BookDto;
import com.example.springboot_task.dto.response.base.OffsetBasedPageRequest;
import com.example.springboot_task.dto.response.base.ResponseDto;
import com.example.springboot_task.exceptions.ApiBadRequestException;
import com.example.springboot_task.mapping.BookMapper;
import com.example.springboot_task.repository.BookRepository;
import com.example.springboot_task.repository.SchoolRepository;
import com.example.springboot_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    public void createBook(BookUpdateDto bookUpdateDto) {
        School school = schoolRepository.findById(bookUpdateDto.getSchoolId())
                .orElseThrow(() -> new ApiBadRequestException("No school with " + bookUpdateDto.getSchoolId() + " id found.\nOr bad args!!!!!!!!!!"));
        User user = userRepository.findById(bookUpdateDto.getUserId()).orElse(null);
        Book book = BookMapper.mapToBook(bookUpdateDto, school, user);
        bookRepository.save(book);
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ApiBadRequestException("No book with " + id + " id found."));
        BookDto bookDto = BookMapper.mapToBookDto(book);
        return bookDto;
    }


    public ResponseDto<BookDto> getAllBooks(int offset, int limit) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        List<BookDto> bookDtoList = bookRepository
                .findAll(pageable)
                .stream()
                .map(BookMapper::mapToBookDto)
                .toList();
        long total = bookRepository
                .count();
        ResponseDto<BookDto> responseDto = new ResponseDto<>(bookDtoList, total);
        return responseDto;
    }


    public void updateBook(BookUpdateDto bookUpdateDto, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ApiBadRequestException("No book with " + bookId + " id found."));

        if (bookUpdateDto.getName() != null) {
            book.setName(bookUpdateDto.getName());
        }
        if (bookUpdateDto.getAuthor() != null) {
            book.setAuthor(bookUpdateDto.getAuthor());
        }
        if (bookUpdateDto.getSchoolId() != null) {
            School school = schoolRepository.findById(bookUpdateDto.getSchoolId())
                    .orElseThrow(() -> new ApiBadRequestException("No school with " + bookUpdateDto.getSchoolId() + " id found."));
            book.setSchool(school);
        }
        if (bookUpdateDto.getUserId() != null) {
            User user = userRepository.findById(bookUpdateDto.getUserId())
                    .orElseThrow(() -> new ApiBadRequestException("No user with " + bookUpdateDto.getUserId() + " id found."));
            book.setUser(user);
        }
    }

    public void freeBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ApiBadRequestException("No book with " + id + " id found."));
        book.setUser(null);
        bookRepository.save(book);
    }
    //give book to user

    public void takeBook(Long bookId, Long userId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ApiBadRequestException("No book with " + bookId + " id found."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiBadRequestException("No user with " + userId + " id found."));
        book.takeBook(user);
    }

    public void deleteBook(Long id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new ApiBadRequestException("No book with " + id + " id found."));
        bookRepository.deleteById(id);
    }
}
