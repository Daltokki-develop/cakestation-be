package com.cakestation.backend.service;

import com.cakestation.backend.domain.Subway;
import com.cakestation.backend.repository.SubwayRepository;
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

}
