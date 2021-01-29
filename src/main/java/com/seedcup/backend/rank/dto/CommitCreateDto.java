package com.seedcup.backend.rank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
public class CommitCreateDto {

    private MultipartFile commitFile;

}
