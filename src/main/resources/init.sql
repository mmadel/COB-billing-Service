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