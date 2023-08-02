package com.example.springboot_task.controller;

import com.example.springboot_task.dto.request.SchoolUpdateDTO;
import com.example.springboot_task.dto.response.SchoolDTO;
import com.example.springboot_task.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;

    @PostMapping
    public ResponseEntity<SchoolDTO> createSchool(@RequestBody SchoolUpdateDTO schoolDTO) {
        return new ResponseEntity<>(schoolService.createSchool(schoolDTO), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<SchoolDTO> getSchoolById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(schoolService.getSchoolById(id), HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<SchoolDTO>> getAllSchools() {
        return new ResponseEntity<>(schoolService.getAllSchools(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public SchoolDTO updateSchool(@PathVariable("id") Long id, @RequestBody SchoolUpdateDTO schoolUpdateDTO) {
        schoolUpdateDTO.setId(id);
        return schoolService.updateSchool(schoolUpdateDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SchoolDTO> deleteSchool(@PathVariable("id") Long id) {
        return new ResponseEntity<>(schoolService.deleteSchoolById(id), HttpStatus.OK);
    }

}