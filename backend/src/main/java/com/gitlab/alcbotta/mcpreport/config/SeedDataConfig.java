package com.gitlab.alcbotta.mcpreport.config;

import com.gitlab.alcbotta.mcpreport.entities.Report;
import com.gitlab.alcbotta.mcpreport.repositories.ReportRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedDataConfig {

  @Bean
  CommandLineRunner seedReports(ReportRepository repo) {
    return args -> {
      if (repo.count() > 0) return;

      List<Report> reports =
          List.of(
              createReport(
                  "High",
                  "Database outage in production",
                  "Critical production database is down, affecting all customer transactions."),
              createReport(
                  "Medium",
                  "Scheduled maintenance notice",
                  "Planned system downtime for maintenance to upgrade core services and security patches."),
              createReport(
                  "Low",
                  "Weekly analytics summary",
                  "Summary of weekly performance metrics including traffic, conversions, and user engagement."),
              createReport(
                  "High",
                  "Security vulnerability found",
                  "Urgent patch required to address a security vulnerability in authentication service."),
              createReport(
                  "Medium",
                  "Feature deployment update",
                  "Deployment of the new dashboard feature completed, minor bugs being monitored."),
              createReport(
                  "Low",
                  "Internal training session",
                  "Details of the upcoming staff training session on the updated reporting tool."),
              createReport(
                  "High",
                  "Payment processing delays",
                  "Intermittent payment gateway delays causing occasional transaction timeouts."),
              createReport(
                  "Medium",
                  "API rate limit changes",
                  "API rate limits have been updated to improve service stability and prevent abuse."),
              createReport(
                  "Low",
                  "Office relocation update",
                  "Facilities team confirms move to the new office space will occur next month."),
              createReport(
                  "High",
                  "System performance degradation",
                  "Increased load times reported on key customer-facing applications during peak hours."));

      repo.saveAll(reports);
    };
  }

  private Report createReport(String priority, String title, String description) {
    Report r = new Report();
    r.setPriority(priority);
    r.setTitle(title);
    r.setDescription(description);
    return r;
  }
}
