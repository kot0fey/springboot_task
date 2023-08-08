package com.example.springboot_task.controller;

import com.example.springboot_task.dto.request.TeacherUpdateDto;
import com.example.springboot_task.dto.response.BookDto;
import com.example.springboot_task.dto.response.ResponseDto;
import com.example.springboot_task.dto.response.TeacherDto;
import com.example.springboot_task.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping
    public void createTeacher(@RequestBody TeacherUpdateDto teacherUpdateDto) {
        teacherService.createTeacher(teacherUpdateDto);
    }

    @GetMapping("{id}")
    public TeacherDto getTeacherById(@PathVariable("id") Long id) {
        return teacherService.getTeacherById(id);
    }

    @GetMapping("all")
    public ResponseDto<TeacherDto> getAllBooks(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                               @RequestParam(value = "limit", defaultValue = "3") Integer limit) {
        return teacherService.getAllTeachers(limit, offset);
    }

    @PutMapping
    public void updateTeacher(@RequestBody TeacherUpdateDto teacherUpdateDto){
        teacherService.updateTeacher(teacherUpdateDto);
    }
    @DeleteMapping("{id}")
    public void deleteTeacher(@PathVariable("id")Long id){
        teacherService.deleteTeacher(id);
    }
}
