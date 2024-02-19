package com.cloud.learningaws.repository;

import com.cloud.learningaws.entity.PdfDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PdfDocumenrRepository extends JpaRepository<PdfDocument, Long> {
}
