package paiso.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import paiso.model.RateInquiry;

@Repository
public interface IRateInquiryRepository extends CrudRepository<RateInquiry, Long> {
	
	public List<RateInquiry> findTop10ByOrderByIdDesc();
}
