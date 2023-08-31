package com.example.springboot_task.controller;

import com.example.springboot_task.domain.City;
import com.example.springboot_task.dto.request.CityUpdateDTO;
import com.example.springboot_task.dto.response.CityDTO;
import com.example.springboot_task.dto.response.base.ResponseDto;
import com.example.springboot_task.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    public CityDTO createCity(@RequestBody CityUpdateDTO city) {
        return cityService.createCity(city);
    }

    @GetMapping("{id}")
    public CityDTO getCityById(@PathVariable("id") Long id) {
        return cityService.getCityById(id);
    }

    @GetMapping("all")
    public ResponseDto<CityDTO> getAllCities(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                             @RequestParam(value = "limit", defaultValue = "3") Integer limit
    ) {
        return cityService.getAllCities(offset, limit);
    }

    @PutMapping("{id}")
    public CityDTO updateCity(@PathVariable("id") Long id,
                              @RequestBody CityUpdateDTO cityUpdateDTO) {
        return cityService.updateCity(cityUpdateDTO, id);
    }

    @DeleteMapping("{id}")
    public CityDTO deleteCity(@PathVariable("id") Long id) {
        return cityService.deleteCityById(id);
    }


}
