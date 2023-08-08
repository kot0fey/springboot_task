package com.example.springboot_task.controller;

import com.example.springboot_task.dto.request.BookUpdateDto;
import com.example.springboot_task.dto.response.BookDto;
import com.example.springboot_task.dto.response.ResponseDto;
import com.example.springboot_task.service.BookService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                                            @RequestParam(value = "limit", defaultValue = "3") Integer limit) {
        return bookService.getAllBooks(limit, offset);
    }

    @PutMapping
    public void updateBook(@RequestBody BookUpdateDto bookUpdateDto) {
        bookService.updateBook(bookUpdateDto);
    }

    @PutMapping("free/{id}")
    public void freeBook(@PathVariable("id") Long id) {
        bookService.freeBook(id);
    }

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }
}
