package com.palettereveries.backend.repositories;

import com.palettereveries.backend.entities.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry,Long> {

}
