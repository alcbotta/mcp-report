package com.gitlab.alcbotta.mcpreport.repositories;

import com.gitlab.alcbotta.mcpreport.entities.QReport;
import com.gitlab.alcbotta.mcpreport.entities.Report;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository
    extends JpaRepository<Report, Long>,
        QuerydslPredicateExecutor<Report>,
        QuerydslBinderCustomizer<QReport> {

  @Override
  default void customize(QuerydslBindings bindings, QReport report) {

    bindings.excluding(QReport.report.id);
    bindings
        .bind(String.class)
        .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
  }
}
