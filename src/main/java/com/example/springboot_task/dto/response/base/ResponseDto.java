package com.example.springboot_task.dto.response.base;

import com.example.springboot_task.exceptions.ApiBadRequestException;
import com.example.springboot_task.mapping.CityMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    List<T> data;
    long total;

}
