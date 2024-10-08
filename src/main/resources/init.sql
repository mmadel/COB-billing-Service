INSERT INTO user_role_scope(id, email, name, role_scope, user_account, uuid)
VALUES (1, 'madel@mail.com', 'adel,mohamed', '[{"role":"provider-role","scope":"modify"},{"role":"patient-role","scope":"modify"},{"role":"filing-role","scope":"modify"},{"role":"group-info-admin-tool-role","scope":"modify"},{"role":"insurance-mapping-admin-tool-role","scope":"modify"},{"role":"account-management-admin-tool-role","scope":"modify"},{"role":"billing-role","scope":"modify"},{"role":"batch-insurance-payment-role","scope":"modify"},{"role":"batch-client-payment-role","scope":"modify"},{"role":"balance-statement-payment-role","scope":"modify"},{"role":"session-payment-role","scope":"modify"}]','madel', '2dbc0870-4d4e-45f7-a079-2243c792719e');



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