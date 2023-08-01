package com.example.springboot_task.controller;

import com.example.springboot_task.dto.request.CityUpdateDTO;
import com.example.springboot_task.dto.response.CityDTO;
import com.example.springboot_task.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CityDTO> createCity(@RequestBody CityUpdateDTO city) {
        return new ResponseEntity<>(cityService.createCity(city), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<CityDTO> getCityById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(cityService.getCityById(id), HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<CityDTO>> getAllCities() {
        return new ResponseEntity<>(cityService.getAllCities(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public CityDTO updateCity(@PathVariable("id") Long id, @RequestBody CityUpdateDTO cityUpdateDTO) {
        cityUpdateDTO.setId(id);
        return cityService.updateCity(cityUpdateDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CityDTO> deleteCity(@PathVariable("id") Long id) {
        return new ResponseEntity<>(cityService.deleteCityById(id), HttpStatus.OK);
    }


}
