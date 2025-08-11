package com.gitlab.alcbotta.mcpreport.controllers;

import com.gitlab.alcbotta.mcpreport.entities.Report;
import com.gitlab.alcbotta.mcpreport.repositories.ReportRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

  private final ReportRepository reportRepository;

  public ReportController(ReportRepository reportRepository) {
    this.reportRepository = reportRepository;
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  public Page<Report> findAll(
      @QuerydslPredicate(root = Report.class) Predicate predicate, Pageable pageable) {

    return reportRepository.findAll(predicate, pageable);
  }
}
