package com.example.springboot_task.dto.response;

import com.example.springboot_task.exceptions.ApiBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ResponseDto<T> {
    List<T> content;
    Integer total;

    public ResponseDto(List<T> content, Integer limit, Integer offset) {
        try {
            total = content.size();
            Integer lastIndex = limit + offset;
            if(lastIndex > total - 1){
                lastIndex = total;
            }
            this.content = content.subList(offset, lastIndex);
        } catch (Exception e){
            throw new ApiBadRequestException("Limit or Offset are incorrect");
        }
    }

    public ResponseDto get(){
        return this;
    }
}
