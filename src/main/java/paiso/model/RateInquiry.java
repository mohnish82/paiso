package paiso.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RateInquiry extends BaseEntity {
	private static final long serialVersionUID = 8583510693169377393L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private Long userId;
    private LocalDate date;
    private LocalDateTime timestamp;

    public RateInquiry() {
    	timestamp = LocalDateTime.now();
    }
    
    public RateInquiry(Long userId) {
    	this();
    	this.userId = userId;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long inquiryId) {
		this.id = inquiryId;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

    public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Long getUserId() {
		return userId;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
}
