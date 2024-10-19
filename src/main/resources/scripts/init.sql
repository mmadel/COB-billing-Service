INSERT INTO user_role_scope(id, email, name, role_scope, user_account, uuid)
VALUES (1, 'madel@mail.com', 'adel,mohamed', '[{"role":"provider-role","scope":"modify"},{"role":"patient-role","scope":"modify"},{"role":"filing-role","scope":"modify"},{"role":"group-info-admin-tool-role","scope":"modify"},{"role":"insurance-mapping-admin-tool-role","scope":"modify"},{"role":"account-management-admin-tool-role","scope":"modify"},{"role":"billing-role","scope":"modify"},{"role":"batch-insurance-payment-role","scope":"modify"},{"role":"batch-client-payment-role","scope":"modify"},{"role":"balance-statement-payment-role","scope":"modify"},{"role":"session-payment-role","scope":"modify"}]','madel', '2dbc0870-4d4e-45f7-a079-2243c792719e');

-- INSERT INTO user_role_scope(id, email, name, role_scope, user_account, uuid)
-- VALUES (1, 'adnan@cobsolution.com', 'megahed,adnan', '[{"role":"provider-role","scope":"modify"},{"role":"patient-role","scope":"modify"},{"role":"filing-role","scope":"modify"},{"role":"group-info-admin-tool-role","scope":"modify"},{"role":"insurance-mapping-admin-tool-role","scope":"modify"},{"role":"account-management-admin-tool-role","scope":"modify"},{"role":"billing-role","scope":"modify"},{"role":"batch-insurance-payment-role","scope":"modify"},{"role":"batch-client-payment-role","scope":"modify"},{"role":"balance-statement-payment-role","scope":"modify"},{"role":"session-payment-role","scope":"modify"}]','adnan', 'f7ffe6a9-cd4c-41b6-83da-905da7695186');



INSERT INTO PUBLIC.clinic (id,data,title,npi)
VALUES      (1,
             '{"city": "Brooklyn", "state": "NY", "address": "4949 Bay Parkwaty", "zipCode": "3747848"}'
                ,
             'Bay Parkwaty',
             '1265940399'),
            (3,
             '{"city": "Brooklyn", "state": "NY", "address": "7978 Bay Ridg", "zipCode": "19958585"}'
                ,
             'Bay Ridg',
             '1265940399'),
            (4,
             '{"city": "Brooklyn", "state": "NY", "address": "34944 Bedstuy", "zipCode": "394998"}'
                ,
             'Bedstuy',
             '1265940399');



INSERT INTO organization(
    id, business_name, first_name, last_name, npi, data, type)
VALUES
    (1, 'Physical Therapy of The City PC','Mahmoud','Shalaby','1265940399','{"fax": "(855)955-3899", "city": "Brooklyn", "email": "info@therapyofnewyork.com", "phone": "(347)543-2232", "state": "NY", "taxId": "823830674", "address": "815 Gravesend Neck Road,APT 1B", "zipcode": "12235566", "taxonomy": "22510000Y", "addressTwo": ""}','Default');



-- INSERT INTO public.payer(
--     id, address, display_name, name, payer_id)
-- VALUES (1,'{"city": "New York", "state": "NY", "address": "PO Box 717", "zipCode": "10108-0717"}', '1199 NATIONAL BENEFIT FUND', '1199 NATIONAL BENEFIT FUND', 13162);


INSERT INTO public.patient_balance_settings(
    id, balance_account, billing_provider)
VALUES (1, '{"npi": true, "poc": true, "taxID": true, "icdCodes": true, "location": true, "patientDOB": true, "renderingProvider": true}', '{"lineOne": "Physical Therapy of The City PC", "lineTwo": "815 Gravesend Neck Road,APT 1B", "lineThree": "Brooklyn,NY,000999"}');

INSERT INTO claim_status_lookup (status_id, description) VALUES (1, 'Processed as Primary');
INSERT INTO claim_status_lookup (status_id, description) VALUES (2, 'Processed as Secondary');
INSERT INTO claim_status_lookup (status_id, description) VALUES (3, 'Processed as Tertiary');
INSERT INTO claim_status_lookup (status_id, description) VALUES (4, 'Denied');
INSERT INTO claim_status_lookup (status_id, description) VALUES (5, 'Pended');
INSERT INTO claim_status_lookup (status_id, description) VALUES (6, 'Approved as amended');
INSERT INTO claim_status_lookup (status_id, description) VALUES (7, 'Approved as submitted');
INSERT INTO claim_status_lookup (status_id, description) VALUES (8, 'Cancelled due to inactivity');
INSERT INTO claim_status_lookup (status_id, description) VALUES (9, 'Pending - under investigation');
INSERT INTO claim_status_lookup (status_id, description) VALUES (10, 'Received, but not in process');
INSERT INTO claim_status_lookup (status_id, description) VALUES (11, 'Rejected, duplicate claim');
INSERT INTO claim_status_lookup (status_id, description) VALUES (12, 'Rejected, please resubmit with corrections');
INSERT INTO claim_status_lookup (status_id, description) VALUES (13, 'Suspended');
INSERT INTO claim_status_lookup (status_id, description) VALUES (14, 'Suspended - incomplete claim');
INSERT INTO claim_status_lookup (status_id, description) VALUES (15, 'Suspended - investigation with field');
INSERT INTO claim_status_lookup (status_id, description) VALUES (16, 'Suspended - return with material');
INSERT INTO claim_status_lookup (status_id, description) VALUES (17, 'Suspended - review pending');
INSERT INTO claim_status_lookup (status_id, description) VALUES (18, 'Suspended Product Registration');
INSERT INTO claim_status_lookup (status_id, description) VALUES (19, 'Processed as Primary, Forwarded to Additional Payer(s)');
INSERT INTO claim_status_lookup (status_id, description) VALUES (20, 'Processed as Secondary, Forwarded to Additional Payer(s)');
INSERT INTO claim_status_lookup (status_id, description) VALUES (21, 'Processed as Tertiary, Forwarded to Additional Payer(s)');
INSERT INTO claim_status_lookup (status_id, description) VALUES (22, 'Reversal of Previous Payment');
INSERT INTO claim_status_lookup (status_id, description) VALUES (23, 'Not Our Claim, Forwarded to Additional Payer(s)');
INSERT INTO claim_status_lookup (status_id, description) VALUES (24, 'Transferred to Proper Carrier');
INSERT INTO claim_status_lookup (status_id, description) VALUES (25, 'Predetermination Pricing Only - No Payment');
INSERT INTO claim_status_lookup (status_id, description) VALUES (26, 'Documentation Claim - No Payment Associated');
INSERT INTO claim_status_lookup (status_id, description) VALUES (27, 'Reviewed');
INSERT INTO claim_status_lookup (status_id, description) VALUES (28, 'Repriced');
INSERT INTO claim_status_lookup (status_id, description) VALUES (29, 'Audited');
INSERT INTO claim_status_lookup (status_id, description) VALUES (30, 'Processed as Conditional');
INSERT INTO claim_status_lookup (status_id, description) VALUES (31, 'Not Our Claim, Unable to Forward');
INSERT INTO claim_status_lookup (status_id, description) VALUES ('AD', 'Additional');
INSERT INTO claim_status_lookup (status_id, description) VALUES ('AP', 'Appealed');
INSERT INTO claim_status_lookup (status_id, description) VALUES ('CC', 'Weekly Certification');
INSERT INTO claim_status_lookup (status_id, description) VALUES ('CL', 'Closed');
INSERT INTO claim_status_lookup (status_id, description) VALUES ('CP', 'Open');
INSERT INTO claim_status_lookup (status_id, description) VALUES ('I', 'Initial');
INSERT INTO claim_status_lookup (status_id, description) VALUES ('RA', 'Reaudited');
INSERT INTO claim_status_lookup (status_id, description) VALUES ('RB', 'Reissue');
INSERT INTO claim_status_lookup (status_id, description) VALUES ('RC', 'Reopened and Closed');
INSERT INTO claim_status_lookup (status_id, description) VALUES ('RD', 'Redetermination');
INSERT INTO claim_status_lookup (status_id, description) VALUES ('RO', 'Reopened');

INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('2', 'Coinsurance');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('3', 'Co-payment');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('4', 'The procedure code is inconsistent with the modifier used. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('5', 'The procedure code/type of bill is inconsistent with the place of service. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('6', 'The procedure/revenue code is inconsistent with the patient\''s age. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('7', 'The procedure/revenue code is inconsistent with the patient\''s gender. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('8', 'The procedure code is inconsistent with the provider type/specialty (taxonomy). Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('9', 'The diagnosis is inconsistent with the patient\''s age. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('10', 'The diagnosis is inconsistent with the patient\''s gender. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('11', 'The diagnosis is inconsistent with the procedure. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('12', 'The diagnosis is inconsistent with the provider type. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('13', 'The date of death precedes the date of service.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('14', 'The date of birth follows the date of service.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('16', 'Claim/service lacks information or has submission/billing error(s). Usage: Do not use this code for claims attachment(s)/other documentation. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.) Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('18', 'Exact duplicate claim/service (Use only with Group Code OA except where state workers\'' compensation regulations requires CO)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('19', 'This is a work-related injury/illness and thus the liability of the Worker\''s Compensation Carrier.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('20', 'This injury/illness is covered by the liability carrier.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('21', 'This injury/illness is the liability of the no-fault carrier.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('22', 'This care may be covered by another payer per coordination of benefits.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('23', 'The impact of prior payer(s) adjudication including payments and/or adjustments. (Use only with Group Code OA)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('24', 'Charges are covered under a capitation agreement/managed care plan.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('26', 'Expenses incurred prior to coverage.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('27', 'Expenses incurred after coverage terminated.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('29', 'The time limit for filing has expired.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('31', 'Patient cannot be identified as our insured.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('32', 'Our records indicate the patient is not an eligible dependent.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('33', 'Insured has no dependent coverage.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('34', 'Insured has no coverage for newborns.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('35', 'Lifetime benefit maximum has been reached.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('39', 'Services denied at the time authorization/pre-certification was requested.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('40', 'Charges do not meet qualifications for emergent/urgent care. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('44', 'Prompt-pay discount.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('45', 'Charge exceeds fee schedule/maximum allowable or contracted/legislated fee arrangement. Usage: This adjustment amount cannot equal the total service or claim charge amount; and must not duplicate provider adjustment amounts (payments and contractual reductions) that have resulted from prior payer(s) adjudication. (Use only with Group Codes PR or CO depending upon liability)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('49', 'This is a non-covered service because it is a routine/preventive exam or a diagnostic/screening procedure done in conjunction with a routine/preventive exam. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('50', 'These are non-covered services because this is not deemed a \''medical necessity\'' by the payer. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('51', 'These are non-covered services because this is a pre-existing condition. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('53', 'Services by an immediate relative or a member of the same household are not covered');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('54', 'Multiple physicians/assistants are not covered in this case. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('55', 'Procedure/treatment/drug is deemed experimental/investigational by the payer. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('56', 'Procedure/treatment has not been deemed \''proven to be effective\'' by the payer. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('58', 'Treatment was deemed by the payer to have been rendered in an inappropriate or invalid place of service. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('59', 'Processed based on multiple or concurrent procedure rules. (For example multiple surgery or diagnostic imaging, concurrent anesthesia.) Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('60', 'Charges for outpatient services are not covered when performed within a period of time prior to or after inpatient services.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('61', 'Adjusted for failure to obtain second surgical');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('66', 'Blood Deductible.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('69', 'Day outlier amount.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('70', 'Cost outlier - Adjustment to compensate for additional costs.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('74', 'Indirect Medical Education Adjustment.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('75', 'Direct Medical Education Adjustment.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('76', 'Disproportionate Share Adjustment.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('78', 'Non-Covered days/Room charge adjustment.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('85', 'Patient Interest Adjustment (Use Only Group code PR)Notes: Only use when the payment of interest is the responsibility of the patient.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('89', 'Professional fees removed from charges.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('90', 'Ingredient cost adjustment. Usage: To be used for pharmaceuticals only.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('91', 'Dispensing fee adjustment.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('94', 'Processed in Excess of charges.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('95', 'Plan procedures not followed.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('96', 'Non-covered charge(s). At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.) Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('97', 'The benefit for this service is included in the payment/allowance for another service/procedure that has already been adjudicated. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('100', 'Payment made to patient/insured/responsible party.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('101', 'Predetermination: anticipated payment upon completion of services or claim adjudication.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('102', 'Major Medical Adjustment.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('103', 'Provider promotional discount (e.g., Senior citizen discount).');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('104', 'Managed care withholding.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('105', 'Tax withholding.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('106', 'Patient payment option/election not in effect.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('107', 'The related or qualifying claim/service was not identified on this claim. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('108', 'Rent/purchase guidelines were not met. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('109', 'Claim/service not covered by this payer/contractor. You must send the claim/service to the correct payer/contractor.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('110', 'Billing date predates service date.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('111', 'Not covered unless the provider accepts assignment.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('112', 'Service not furnished directly to the patient and/or not documented.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('114', 'Procedure/product not approved by the Food and Drug Administration.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('115', 'Procedure postponed, canceled, or delayed.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('116', 'The advance indemnification notice signed by the patient did not comply with requirements.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('117', 'Transportation is only covered to the closest facility that can provide the necessary care.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('118', 'ESRD network support adjustment.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('119', 'Benefit maximum for this time period or occurrence has been reached.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('121', 'Indemnification adjustment - compensation for outstanding member responsibility.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('122', 'Psychiatric reduction.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('128', 'Newborn\''s services are covered in the mother\''s Allowance.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('129', 'Prior processing information appears incorrect. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('130', 'Claim submission fee.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('131', 'Claim specific negotiated discount.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('132', 'Prearranged demonstration project adjustment.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('133', 'The disposition of this service line is pending further review. (Use only with Group Code OA). Usage: Use of this code requires a reversal and correction when the service line is finalized (use only in Loop 2110 CAS segment of the 835 or Loop 2430 of the 837).');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('134', 'Technical fees removed from charges.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('135', 'Interim bills cannot be processed.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('136', 'Failure to follow prior payer\''s coverage rules. (Use only with Group Code OA)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('137', 'Regulatory Surcharges, Assessments, Allowances or Health Related Taxes.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('139', 'Contracted funding agreement - Subscriber is employed by the provider of services. Use only with Group Code CO.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('140', 'Patient/Insured health identification number and name do not match.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('142', 'Monthly Medicaid patient liability amount.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('143', 'Portion of payment deferred.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('144', 'Incentive adjustment, e.g. preferred product/service.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('146', 'Diagnosis was invalid for the date(s) of service reported.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('147', 'Provider contracted/negotiated rate expired or not on file.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('148', 'Information from another provider was not provided or was insufficient/incomplete. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('149', 'Lifetime benefit maximum has been reached for this service/benefit category.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('150', 'Payer deems the information submitted does not support this level of service.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('151', 'Payment adjusted because the payer deems the information submitted does not support this many/frequency of services.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('152', 'Payer deems the information submitted does not support this length of service. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('153', 'Payer deems the information submitted does not support this dosage.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('154', 'Payer deems the information submitted does not support this day\''s supply.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('155', 'Patient refused the service/procedure.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('157', 'Service/procedure was provided as a result of an act of war.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('158', 'Service/procedure was provided outside of the United States.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('159', 'Service/procedure was provided as a result of terrorism.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('160', 'Injury/illness was the result of an activity that is a benefit exclusion.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('161', 'Provider performance bonusStart: 02/29/2004');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('163', 'Attachment/other documentation referenced on the claim was not received.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('164', 'Attachment/other documentation referenced on the claim was not received in a timely fashion.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('166', 'These services were submitted after this payers responsibility for processing claims under this plan ended.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('167', 'This (these) diagnosis(es) is (are) not covered. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('169', 'Alternate benefit has been provided.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('170', 'Payment is denied when performed/billed by this type of provider. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('171', 'Payment is denied when performed/billed by this type of provider in this type of facility. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('172', 'Payment is adjusted when performed/billed by a provider of this specialty. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('173', 'Service/equipment was not prescribed by a physician.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('174', 'Service was not prescribed prior to delivery.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('175', 'Prescription is incomplete.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('176', 'Prescription is not current.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('177', 'Patient has not met the required eligibility requirements.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('178', 'Patient has not met the required spend down requirements.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('179', 'Patient has not met the required waiting requirements. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('180', 'Patient has not met the required residency requirements.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('181', 'Procedure code was invalid on the date of service.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('182', 'Procedure modifier was invalid on the date of service.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('183', 'The referring provider is not eligible to refer the service billed. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('184', 'The prescribing/ordering provider is not eligible to prescribe/order the service billed. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('185', 'The rendering provider is not eligible to perform the service billed. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('186', 'Level of care change adjustment.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('187', 'Consumer Spending Account payments (includes but is not limited to Flexible Spending Account, Health Savings Account, Health Reimbursement Account, etc.)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('188', 'This product/procedure is only covered when used according to FDA recommendations.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('189', '\''Not otherwise classified\'' or \''unlisted\'' procedure code (CPT/HCPCS) was billed when there is a specific procedure code for this procedure/service.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('190', 'Payment is included in the allowance for a Skilled Nursing Facility (SNF) qualified stay.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('192', 'Non standard adjustment code from paper remittance. Usage: This code is to be used by providers/payers providing Coordination of Benefits information to another payer in the 837 transaction only. This code is only used when the non-standard code cannot be reasonably mapped to an existing Claims Adjustment Reason Code, specifically Deductible, Coinsurance and Co-payment.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('193', 'Original payment decision is being maintained. Upon review, it was determined that this claim was processed properly.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('194', 'Anesthesia performed by the operating physician, the assistant surgeon or the attending physician.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('195', 'Refund issued to an erroneous priority payer for this claim/service.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('197', 'Precertification/authorization/notification/pre-treatment absent.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('198', 'Precertification/notification/authorization/pre-treatment exceeded.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('199', 'Revenue code and Procedure code do not match.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('200', 'Expenses incurred during lapse in coverage');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('201', 'Patient is responsible for amount of this claim service through set aside arrangement or other agreement. (Use only with Group Code PR) At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.) Notes: Not for use by Workers Compensation payers; use code P3 instead.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('202', 'Non-covered personal comfort or convenience services.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('203', 'Discontinued or reduced service.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('204', 'This service/equipment/drug is not covered under the patient\''s current benefit plan');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('205', 'Pharmacy discount card processing fee');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('206', 'National Provider Identifier - missing.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('207', 'National Provider identifier - Invalid format');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('208', 'National Provider Identifier - Not matched.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('209', 'Per regulatory or other agreement. The provider cannot collect this amount from the patient. However, this amount may be billed to subsequent payer. Refund to patient if collected. (Use only with Group code OA)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('210', 'Payment adjusted because pre-certification/authorization not received in a timely fashion.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('211', 'National Drug Codes (NDC) not eligible for rebate, are not covered.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('212', 'Administrative surcharges are not covered');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('213', 'Non-compliance with the physician self referral prohibition legislation or payer policy.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('215', 'Based on subrogation of a third party settlement');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('216', 'Based on the findings of a review organization');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('219', 'Based on extent of injury. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier \''IG\'') for the jurisdictional regulation. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF).');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('222', 'Exceeds the contracted maximum number of hours/days/units by this provider for this period. This is not patient specific. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('223', 'Adjustment code for mandated federal, state or local law/regulation that is not already covered by another code and is mandated before a new code can be created.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('224', 'Patient identification compromised by identity theft. Identity verification required for processing this and future claims.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('225', 'Penalty or Interest Payment by Payer (Only used for plan to plan encounter reporting within the 837)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('226', 'Information requested from the Billing/Rendering Provider was not provided or not provided timely or was insufficient/incomplete. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('227', 'Information requested from the patient/insured/responsible party was not provided or was insufficient/incomplete. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('228', 'Denied for failure of this provider, another provider or the subscriber to supply requested information to a previous payer for their adjudication');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('229', 'Partial charge amount not considered by Medicare due to the initial claim Type of Bill being 12X. Usage: This code can only be used in the 837 transaction to convey Coordination of Benefits information when the secondary payer\''s cost avoidance policy allows providers to bypass claim submission to a prior payer. (Use only with Group Code PR)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('231', 'Mutually exclusive procedures cannot be done in the same day/setting. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.Start: 07/01/2009 | Last Modified: 07/01/2017');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('232', 'Institutional Transfer Amount. Usage: Applies to institutional claims only and explains the DRG amount difference when the patient care crosses multiple institutions.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('233', 'Services/charges related to the treatment of a hospital-acquired condition or preventable medical error.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('234', 'This procedure is not paid separately. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('235', 'Sales Tax');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('236', 'This procedure or procedure/modifier combination is not compatible with another procedure or procedure/modifier combination provided on the same day according to the National Correct Coding Initiative or workers compensation state regulations/ fee schedule requirements.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('237', 'Legislated/Regulatory Penalty. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('238', 'Claim spans eligible and ineligible periods of coverage, this is the reduction for the ineligible period. (Use only with Group Code PR)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('239', 'Claim spans eligible and ineligible periods of coverage. Rebill separate claims.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('240', 'The diagnosis is inconsistent with the patient\''s birth weight. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('241', 'Low Income Subsidy (LIS) Co-payment Amount');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('242', 'Services not provided by network/primary care providers.Notes: This code replaces deactivated code 38');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('243', 'Services not authorized by network/primary care providers.Notes: This code replaces deactivated code 38');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('245', 'Provider performance program withhold.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('246', 'This non-payable code is for required reporting only.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('247', 'Deductible for Professional service rendered in an Institutional setting and billed on an Institutional claim.Notes: For Medicare Bundled Payment use only, under the Patient Protection and Affordable Care Act (PPACA).');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('248', 'Coinsurance for Professional service rendered in an Institutional setting and billed on an Institutional claim.Notes: For Medicare Bundled Payment use only, under the Patient Protection and Affordable Care Act (PPACA).');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('249', 'This claim has been identified as a readmission. (Use only with Group Code CO)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('250', 'The attachment/other documentation that was received was the incorrect attachment/document. The expected attachment/document is still missing. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT).');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('251', 'The attachment/other documentation that was received was incomplete or deficient. The necessary information is still needed to process the claim. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT).');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('252', 'An attachment/other documentation is required to adjudicate this claim/service. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT).');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('253', 'Sequestration - reduction in federal payment');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('254', 'Claim received by the dental plan, but benefits not available under this plan. Submit these services to the patient\''s medical plan for further consideration.Notes: Use CARC 290 if the claim was forwarded.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('256', 'Service not payable per managed care contract.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('257', 'The disposition of the claim/service is undetermined during the premium payment grace period, per Health Insurance Exchange requirements. This claim/service will be reversed and corrected when the grace period ends (due to premium payment or lack of premium payment). (Use only with Group Code OA)Notes: To be used after the first month of the grace period.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('258', 'Claim/service not covered when patient is in custody/incarcerated. Applicable federal, state or local authority may cover the claim/service.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('259', 'Additional payment for Dental/Vision service utilization.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('260', 'Processed under Medicaid ACA Enhanced Fee Schedule');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('261', 'The procedure or service is inconsistent with the patient\''s history.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('262', 'Adjustment for delivery cost. Usage: To be used for pharmaceuticals only.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('263', 'Adjustment for shipping cost. Usage: To be used for pharmaceuticals only.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('264', 'Adjustment for postage cost. Usage: To be used for pharmaceuticals only.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('265', 'Adjustment for administrative cost. Usage: To be used for pharmaceuticals only.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('266', 'Adjustment for compound preparation cost. Usage: To be used for pharmaceuticals only.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('267', 'Claim/service spans multiple months. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('268', 'The Claim spans two calendar years. Please resubmit one claim per calendar year.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('269', 'Anesthesia not covered for this service/procedure. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('270', 'Claim received by the medical plan, but benefits not available under this plan. Submit these services to the patient\''s dental plan for further consideration.Notes: Use CARC 291 if the claim was forwarded.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('271', 'Prior contractual reductions related to a current periodic payment as part of a contractual payment schedule when deferred amounts have been previously reported. (Use only with Group Code OA)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('272', 'Coverage/program guidelines were not met.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('273', 'Coverage/program guidelines were exceeded.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('274', 'Fee/Service not payable per patient Care Coordination arrangement.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('275', 'Prior payer\''s (or payers\'') patient responsibility (deductible, coinsurance, co-payment) not covered. (Use only with Group Code PR)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('276', 'Services denied by the prior payer(s) are not covered by this payer.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('277', 'The disposition of the claim/service is undetermined during the premium payment grace period, per Health Insurance SHOP Exchange requirements. This claim/service will be reversed and corrected when the grace period ends (due to premium payment or lack of premium payment). (Use only with Group Code OA)Notes: To be used during 31 day SHOP grace period.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('278', 'Performance program proficiency requirements not met. (Use only with Group Codes CO or PI) Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('279', 'Services not provided by Preferred network providers. Usage: Use this code when there are member network limitations. For example, using contracted providers not in the member\''s \''narrow\'' network.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('280', 'Claim received by the medical plan, but benefits not available under this plan. Submit these services to the patient\''s Pharmacy plan for further consideration.Notes: Use CARC 292 if the claim was forwarded.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('281', 'Deductible waived per contractual agreement. Use only with Group Code CO.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('282', 'The procedure/revenue code is inconsistent with the type of bill. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('283', 'Attending provider is not eligible to provide direction of care.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('284', 'Precertification/authorization/notification/pre-treatment number may be valid but does not apply to the billed services.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('285', 'Appeal procedures not followed');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('286', 'Appeal time limits not met');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('287', 'Referral exceeded');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('288', 'Referral absent');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('289', 'Services considered under the dental and medical plans, benefits not available.Notes: Also see CARCs 254, 270 and 280.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('290', 'Claim received by the dental plan, but benefits not available under this plan. Claim has been forwarded to the patient\''s medical plan for further consideration.Notes: Use CARC 254 if the claim was not forwarded.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('291', 'Claim received by the medical plan, but benefits not available under this plan. Claim has been forwarded to the patient\''s dental plan for further consideration.Notes: Use CARC 270 if the claim was not forwarded.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('292', 'Claim received by the medical plan, but benefits not available under this plan. Claim has been forwarded to the patient\''s pharmacy plan for further consideration.Notes: Use CARC 280 if the claim was not forwarded.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('293', 'Payment made to employer.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('294', 'Payment made to attorney.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('295', 'Pharmacy Direct/Indirect Remuneration (DIR)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('296', 'Precertification/authorization/notification/pre-treatment number may be valid but does not apply to the provider.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('297', 'Claim received by the medical plan, but benefits not available under this plan. Submit these services to the patient\''s vision plan for further consideration.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('298', 'Claim received by the medical plan, but benefits not available under this plan. Claim has been forwarded to the patient\''s vision plan for further consideration.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('299', 'The billing provider is not eligible to receive payment for the service billed.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('300', 'Claim received by the Medical Plan, but benefits not available under this plan. Claim has been forwarded to the patient\''s Behavioral Health Plan for further consideration.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('301', 'Claim received by the Medical Plan, but benefits not available under this plan. Submit these services to the patient\''s Behavioral Health Plan for further consideration.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('302', 'Precertification/notification/authorization/pre-treatment time limit has expired.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('303', 'Prior payer\''s (or payers\'') patient responsibility (deductible, coinsurance, co-payment) not covered for Qualified Medicare and Medicaid Beneficiaries. (Use only with Group Code CO)');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('304', 'Claim received by the medical plan, but benefits not available under this plan. Submit these services to the patient\''s hearing plan for further consideration.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('305', 'Claim received by the medical plan, but benefits not available under this plan. Claim has been forwarded to the patient\''s hearing plan for further consideration.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('306', 'Type of bill is inconsistent with the patient status. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('A0', 'Patient refund amount.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('A1', 'Claim/Service denied. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.) Usage: Use this code only when a more specific Claim Adjustment Reason Code is not available.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('A5', 'Medicare Claim PPS Capital Cost Outlier Amount.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('A6', 'Prior hospitalization or 30 day transfer requirement not met.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('A8', 'Ungroupable DRG.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('B1', 'Non-covered visits.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('B4', 'Late filing penalty.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('B7', 'This provider was not certified/eligible to be paid for this procedure/service on this date of service. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('B8', 'Alternative services were available, and should have been utilized. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('B9', 'Patient is enrolled in a Hospice.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('B10', 'Allowed amount has been reduced because a component of the basic procedure/test was paid. The beneficiary is not liable for more than the charge limit for the basic procedure/test.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('B11', 'The claim/service has been transferred to the proper payer/processor for processing. Claim/service not covered by this payer/processor.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('B12', 'Services not documented in patient\''s medical records.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('B13', 'Previously paid. Payment for this claim/service may have been provided in a previous payment.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('B14', 'Only one visit or consultation per physician per day is covered.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('B15', 'This service/procedure requires that a qualifying service/procedure be received and covered. The qualifying other service/procedure has not been received/adjudicated. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('B16', '\''New Patient\'' qualifications were not met.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('B20', 'Procedure/service was partially or fully furnished by another provider.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('B22', 'This payment is adjusted based on the diagnosis.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('B23', 'Procedure billed is not authorized per your Clinical Laboratory Improvement Amendment (CLIA) proficiency test.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P1', 'State-mandated Requirement for Property and Casualty, see Claim Payment Remarks Code for specific explanation. To be used for Property and Casualty only.Notes: This code replaces deactivated code 162');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P2', 'Not a work related injury/illness and thus not the liability of the workers\'' compensation carrier Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier \''IG\'') for the jurisdictional regulation. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF). To be used for Workers\'' Compensation only.Notes: This code replaces deactivated code 191');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P3', 'Workers\'' Compensation case settled. Patient is responsible for amount of this claim/service through WC \''Medicare set aside arrangement\'' or other agreement. To be used for Workers\'' Compensation only. (Use only with Group Code PR)Notes: This code replaces deactivated code 201');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P4', 'Workers\'' Compensation claim adjudicated as non-compensable. This Payer not liable for claim or service/treatment. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier \''IG\'') for the jurisdictional regulation. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF). To be used for Workers\'' Compensation onlyNotes: This code replaces deactivated code 214');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P5', 'Based on payer reasonable and customary fees. No maximum allowable defined by legislated fee arrangement. To be used for Property and Casualty only.Notes: This code replaces deactivated code 217');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P6', 'Based on entitlement to benefits. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier \''IG\'') for the jurisdictional regulation. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF). To be used for Property and Casualty only.Notes: This code replaces deactivated code 218');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P7', 'The applicable fee schedule/fee database does not contain the billed code. Please resubmit a bill with the appropriate fee schedule/fee database code(s) that best describe the service(s) provided and supporting documentation if required. To be used for Property and Casualty only.Notes: This code replaces deactivated code 220');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P8', 'Claim is under investigation. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier \''IG\'') for the jurisdictional regulation. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF). To be used for Property and Casualty only.Notes: This code replaces deactivated code 221');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P9', 'No available or correlating CPT/HCPCS code to describe this service. To be used for Property and Casualty only.Notes: This code replaces deactivated code 230');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P10', 'Payment reduced to zero due to litigation. Additional information will be sent following the conclusion of litigation. To be used for Property and Casualty only.Notes: This code replaces deactivated code 244');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P11', 'The disposition of the related Property &amp; Casualty claim (injury or illness) is pending due to litigation. To be used for Property and Casualty only. (Use only with Group Code OA)Notes: This code replaces deactivated code 255');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P12', 'Workers\'' compensation jurisdictional fee schedule adjustment. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Class of Contract Code Identification Segment (Loop 2100 Other Claim Related Information REF). If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Workers\'' Compensation only.Notes: This code replaces deactivated code W1');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P13', 'Payment reduced or denied based on workers\'' compensation jurisdictional regulations or payment policies, use only if no other code is applicable. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier \''IG\'') if the jurisdictional regulation applies. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Workers\'' Compensation only.Notes: This code replaces deactivated code W2');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P14', 'The Benefit for this Service is included in the payment/allowance for another service/procedure that has been performed on the same day. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present. To be used for Property and Casualty only.Notes: This code replaces deactivated code W3');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P15', 'Workers\'' Compensation Medical Treatment Guideline Adjustment. To be used for Workers\'' Compensation only.Notes: This code replaces deactivated code W4');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P16', 'Medical provider not authorized/certified to provide treatment to injured workers in this jurisdiction. To be used for Workers\'' Compensation only. (Use with Group Code CO or OA)Notes: This code replaces deactivated code W5');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P17', 'Referral not authorized by attending physician per regulatory requirement. To be used for Property and Casualty only.Notes: This code replaces deactivated code W6');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P18', 'Procedure is not listed in the jurisdiction fee schedule. An allowance has been made for a comparable service. To be used for Property and Casualty only.Notes: This code replaces deactivated code W7');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P19', 'Procedure has a relative value of zero in the jurisdiction fee schedule, therefore no payment is due. To be used for Property and Casualty only.Notes: This code replaces deactivated code W8');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P20', 'Service not paid under jurisdiction allowed outpatient facility fee schedule. To be used for Property and Casualty only.Notes: This code replaces deactivated code W9');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P21', 'Payment denied based on the Medical Payments Coverage (MPC) and/or Personal Injury Protection (PIP) Benefits jurisdictional regulations, or payment policies. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier \''IG\'') if the jurisdictional regulation applies. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty Auto only.Notes: This code replaces deactivated code Y1');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P22', 'Payment adjusted based on the Medical Payments Coverage (MPC) and/or Personal Injury Protection (PIP) Benefits jurisdictional regulations, or payment policies. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier \''IG\'') if the jurisdictional regulation applies. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty Auto only.Notes: This code replaces deactivated code Y2');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P23', 'Medical Payments Coverage (MPC) or Personal Injury Protection (PIP) Benefits jurisdictional fee schedule adjustment. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Class of Contract Code Identification Segment (Loop 2100 Other Claim Related Information REF). If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty Auto only.Notes: This code replaces deactivated code Y3');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P24', 'Payment adjusted based on Preferred Provider Organization (PPO). Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Class of Contract Code Identification Segment (Loop 2100 Other Claim Related Information REF). If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty only. Use only with Group Code CO.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P25', 'Payment adjusted based on Medical Provider Network (MPN). Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Class of Contract Code Identification Segment (Loop 2100 Other Claim Related Information REF). If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty only. (Use only with Group Code CO).');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P26', 'Payment adjusted based on Voluntary Provider network (VPN). Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Class of Contract Code Identification Segment (Loop 2100 Other Claim Related Information REF). If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty only. (Use only with Group Code CO).');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P27', 'Payment denied based on the Liability Coverage Benefits jurisdictional regulations and/or payment policies. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier \''IG\'') if the jurisdictional regulation applies. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty Auto only.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P28', 'Payment adjusted based on the Liability Coverage Benefits jurisdictional regulations and/or payment policies. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier \''IG\'') if the jurisdictional regulation applies. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty Auto only.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P29', 'Liability Benefits jurisdictional fee schedule adjustment. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Class of Contract Code Identification Segment (Loop 2100 Other Claim Related Information REF). If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty Auto only.Start: 11/01/2017');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P30', 'Payment denied for exacerbation when supporting documentation was not complete. To be used for Property and Casualty only.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P31', 'Payment denied for exacerbation when treatment exceeds time allowed. To be used for Property and Casualty only.');
INSERT INTO claim_adjustment_reason_codes  (code, description) VALUES ('P32', 'Payment adjusted due to Apportionment.');