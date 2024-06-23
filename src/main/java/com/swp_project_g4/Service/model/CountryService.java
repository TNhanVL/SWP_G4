package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Chapter;
import com.swp_project_g4.Model.Country;
import com.swp_project_g4.Model.Lesson;
import com.swp_project_g4.Repository.ChapterRepository;
import com.swp_project_g4.Repository.CountryRepository;
import com.swp_project_g4.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public Optional<Country> getById(int countryId) {
        return countryRepository.findById(countryId);
    }

    public List<Country> getAll() {
        return countryRepository.findAll();
    }
}
