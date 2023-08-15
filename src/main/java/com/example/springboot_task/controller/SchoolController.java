package com.example.springboot_task.controller;

import com.example.springboot_task.dto.request.SchoolUpdateDTO;
import com.example.springboot_task.dto.response.base.ResponseDto;
import com.example.springboot_task.dto.response.SchoolDTO;
import com.example.springboot_task.service.SchoolService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
@Tag(name = "Schools")
public class SchoolController {
    private final SchoolService schoolService;

    @PostMapping
    public SchoolDTO createSchool(@RequestBody SchoolUpdateDTO schoolDTO) {
        return schoolService.createSchool(schoolDTO);
    }

    @GetMapping("{id}")
    public SchoolDTO getSchoolById(@PathVariable("id") Long id) {
        return schoolService.getSchoolById(id);
    }

    @GetMapping("all")
    public ResponseDto<SchoolDTO> getAllSchools(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                @RequestParam(value = "limit", defaultValue = "3") Integer limit
    ) {
        return schoolService.getAllSchools(offset, limit);
    }

    @PutMapping("{id}")
    public SchoolDTO updateSchool(@PathVariable("id") Long id, @RequestBody SchoolUpdateDTO schoolUpdateDTO) {
        schoolUpdateDTO.setId(id);
        return schoolService.updateSchool(schoolUpdateDTO);
    }

    @DeleteMapping("{id}")
    public SchoolDTO deleteSchool(@PathVariable("id") Long id) {
        return schoolService.deleteSchoolById(id);
    }

}