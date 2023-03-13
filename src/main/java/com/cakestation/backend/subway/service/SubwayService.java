package com.cakestation.backend.subway.service;

import com.cakestation.backend.subway.domain.Subway;
import com.cakestation.backend.subway.dto.request.CreateSubwayDto;
import com.cakestation.backend.subway.repository.SubwayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubwayService {

    private final SubwayRepository subwayRePository;

    public List<Subway> findAll() {

        return subwayRePository.findAll();
    }

    @Transactional
    public void saveSubway(CreateSubwayDto createSubwayDto) {
        Subway subway = CreateSubwayDto.toEntity(createSubwayDto);

        subwayRePository.save(subway);
    }
}
