package com.example.springboot_task.controller;

import com.example.springboot_task.dto.request.BookUpdateDto;
import com.example.springboot_task.dto.response.BookDto;
import com.example.springboot_task.dto.response.base.ResponseDto;
import com.example.springboot_task.service.BookService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public void createBook(@RequestBody BookUpdateDto bookUpdateDto) {
        bookService.createBook(bookUpdateDto);
    }

    @GetMapping("{id}")
    public BookDto getBookById(@PathVariable("id") Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("all")
    public ResponseDto<BookDto> getAllBooks(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                            @RequestParam(value = "limit", defaultValue = "3") Integer limit
    ) {
        return bookService.getAllBooks(offset, limit);
    }

    @PutMapping
    public void updateBook(@RequestBody BookUpdateDto bookUpdateDto) {
        bookService.updateBook(bookUpdateDto);
    }

    @PutMapping("free/{id}")
    public void freeBook(@PathVariable("id") Long id) {
        bookService.freeBook(id);
    }

    @PutMapping("take/book{bookId}/user{userId}")
    public void takeBook(@PathVariable("bookId") Long bookId,
                         @PathVariable("userId") Long userId
    ){
        bookService.takeBook(bookId, userId);
    }

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }

}
