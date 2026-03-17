package com.hrdigital.Domain.Entities;

import com.hrdigital.Domain.Enums.BenefitTypeEnum;
import com.hrdigital.Domain.ValueObjects.ReferencePeriod;

import java.time.LocalDateTime;

public class Reimbursement extends Domain {
    private LocalDateTime createdAt;
    private EmployeeEntity employee;
    private EmployeeEntity applicant;
    private BenefitTypeEnum benefitType;
    private ReferencePeriod referencePeriod;
    private Double amount;
    private Double approvedAmount;
    private String annotations;

    public Reimbursement() {
        super();
    }
}
