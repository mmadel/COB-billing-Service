


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: claim_adjustment_reason_codes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE claim_adjustment_reason_codes (
                                               id bigint NOT NULL,
                                               code character varying(255),
                                               description character varying(1024)
);


ALTER TABLE claim_adjustment_reason_codes OWNER TO postgres;

--
-- Name: claim_adjustment_reason_codes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE claim_adjustment_reason_codes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE claim_adjustment_reason_codes_id_seq OWNER TO postgres;

--
-- Name: claim_adjustment_reason_codes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE claim_adjustment_reason_codes_id_seq OWNED BY claim_adjustment_reason_codes.id;


--
-- Name: claim_status_lookup; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE claim_status_lookup (
                                     id bigint NOT NULL,
                                     description character varying(255),
                                     status_id character varying(255)
);


ALTER TABLE claim_status_lookup OWNER TO postgres;

--
-- Name: claim_status_lookup_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE claim_status_lookup_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE claim_status_lookup_id_seq OWNER TO postgres;

--
-- Name: claim_status_lookup_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE claim_status_lookup_id_seq OWNED BY claim_status_lookup.id;


--
-- Name: clinic; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE clinic (
                        id bigint NOT NULL,
                        data jsonb,
                        npi character varying(255),
                        title character varying(255)
);


ALTER TABLE clinic OWNER TO postgres;

--
-- Name: clinic_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE clinic_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE clinic_id_seq OWNER TO postgres;

--
-- Name: clinic_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE clinic_id_seq OWNED BY clinic.id;


--
-- Name: era_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE era_history (
                             id bigint NOT NULL,
                             created_at bigint,
                             ear_id integer,
                             era_line jsonb,
                             is_archive boolean
);


ALTER TABLE era_history OWNER TO postgres;

--
-- Name: era_history_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE era_history_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE era_history_id_seq OWNER TO postgres;

--
-- Name: era_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE era_history_id_seq OWNED BY era_history.id;


--
-- Name: fee_schedule; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE fee_schedule (
                              id bigint NOT NULL,
                              active boolean,
                              created_at bigint,
                              default_fee boolean,
                              fee_schedule_line jsonb,
                              insurance jsonb,
                              fee_schedule_name character varying(255),
                              provider jsonb
);


ALTER TABLE fee_schedule OWNER TO postgres;

--
-- Name: fee_schedule_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE fee_schedule_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fee_schedule_id_seq OWNER TO postgres;

--
-- Name: fee_schedule_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE fee_schedule_id_seq OWNED BY fee_schedule.id;


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hibernate_sequence OWNER TO postgres;

--
-- Name: insurance_company; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE insurance_company (
                                   id bigint NOT NULL,
                                   addresses jsonb,
                                   name character varying(255),
                                   uuid character varying(255)
);


ALTER TABLE insurance_company OWNER TO postgres;

--
-- Name: insurance_company_configuration; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE insurance_company_configuration (
                                                 id bigint NOT NULL,
                                                 box_26 character varying(255),
                                                 box_32 boolean,
                                                 box_33 bigint,
                                                 external_insurance_company_id bigint,
                                                 internal_insurance_company_id bigint
);


ALTER TABLE insurance_company_configuration OWNER TO postgres;

--
-- Name: insurance_company_configuration_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE insurance_company_configuration_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE insurance_company_configuration_id_seq OWNER TO postgres;

--
-- Name: insurance_company_configuration_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE insurance_company_configuration_id_seq OWNED BY insurance_company_configuration.id;


--
-- Name: insurance_company_external; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE insurance_company_external (
                                            id bigint NOT NULL,
                                            addresses jsonb,
                                            display_name character varying(255),
                                            name character varying(255),
                                            payer_id character varying(255)
);


ALTER TABLE insurance_company_external OWNER TO postgres;

--
-- Name: insurance_company_external_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE insurance_company_external_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE insurance_company_external_id_seq OWNER TO postgres;

--
-- Name: insurance_company_external_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE insurance_company_external_id_seq OWNED BY insurance_company_external.id;


--
-- Name: insurance_company_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE insurance_company_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE insurance_company_id_seq OWNER TO postgres;

--
-- Name: insurance_company_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE insurance_company_id_seq OWNED BY insurance_company.id;


--
-- Name: insurance_company_payer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE insurance_company_payer (
                                         id bigint NOT NULL,
                                         payer_data jsonb,
                                         payer_id character varying(255),
                                         internal_insurance_company_id bigint
);


ALTER TABLE insurance_company_payer OWNER TO postgres;

--
-- Name: insurance_company_payer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE insurance_company_payer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE insurance_company_payer_id_seq OWNER TO postgres;

--
-- Name: insurance_company_payer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE insurance_company_payer_id_seq OWNED BY insurance_company_payer.id;


--
-- Name: modifier_rule; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE modifier_rule (
                               id bigint NOT NULL,
                               active boolean,
                               default_rule boolean,
                               insurance_company jsonb,
                               name character varying(255),
                               rules jsonb
);


ALTER TABLE modifier_rule OWNER TO postgres;

--
-- Name: modifier_rule_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE modifier_rule_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE modifier_rule_id_seq OWNER TO postgres;

--
-- Name: modifier_rule_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE modifier_rule_id_seq OWNED BY modifier_rule.id;


--
-- Name: organization; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE organization (
                              id bigint NOT NULL,
                              business_name character varying(255),
                              first_name character varying(255),
                              last_name character varying(255),
                              npi character varying(255),
                              data jsonb,
                              type character varying(255)
);


ALTER TABLE organization OWNER TO postgres;

--
-- Name: organization_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE organization_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE organization_id_seq OWNER TO postgres;

--
-- Name: organization_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE organization_id_seq OWNED BY organization.id;


--
-- Name: patient; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient (
                         id bigint NOT NULL,
                         patient_addresses jsonb,
                         authorization_watching boolean,
                         birth_date bigint,
                         copay character varying(255),
                         email character varying(255),
                         external_id character varying(255),
                         first_name character varying(255),
                         gender character varying(255),
                         gender_identity integer,
                         last_name character varying(255),
                         marital_status character varying(255),
                         middle_name character varying(255),
                         patient_advanced_information jsonb,
                         phone character varying(255),
                         phone_type character varying(255),
                         ssn character varying(255),
                         status boolean,
                         referring_provider_id bigint
);


ALTER TABLE patient OWNER TO postgres;

--
-- Name: patient_authorization; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient_authorization (
                                       id bigint NOT NULL,
                                       auth_number character varying(255),
                                       expire_date bigint,
                                       ins_company_id bigint,
                                       ins_company_name character varying(255),
                                       remaining integer,
                                       service_code character varying(255),
                                       start_date bigint,
                                       visit integer,
                                       patient_id bigint
);


ALTER TABLE patient_authorization OWNER TO postgres;

--
-- Name: patient_authorization_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_authorization_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_authorization_id_seq OWNER TO postgres;

--
-- Name: patient_authorization_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_authorization_id_seq OWNED BY patient_authorization.id;


--
-- Name: patient_balance_settings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient_balance_settings (
                                          id bigint NOT NULL,
                                          balance_account jsonb,
                                          billing_provider jsonb
);


ALTER TABLE patient_balance_settings OWNER TO postgres;

--
-- Name: patient_balance_settings_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_balance_settings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_balance_settings_id_seq OWNER TO postgres;

--
-- Name: patient_balance_settings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_balance_settings_id_seq OWNED BY patient_balance_settings.id;


--
-- Name: patient_case; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient_case (
                              id bigint NOT NULL,
                              case_diagnosis jsonb,
                              title character varying(255),
                              patient_id bigint
);


ALTER TABLE patient_case OWNER TO postgres;

--
-- Name: patient_case_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_case_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_case_id_seq OWNER TO postgres;

--
-- Name: patient_case_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_case_id_seq OWNED BY patient_case.id;


--
-- Name: patient_claim; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient_claim (
                               id bigint NOT NULL,
                               claim_id character varying(255),
                               submission_messages jsonb,
                               submission_status character varying(255),
                               patient_invoice_id bigint
);


ALTER TABLE patient_claim OWNER TO postgres;

--
-- Name: patient_claim_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_claim_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_claim_id_seq OWNER TO postgres;

--
-- Name: patient_claim_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_claim_id_seq OWNED BY patient_claim.id;


--
-- Name: patient_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_id_seq OWNER TO postgres;

--
-- Name: patient_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_id_seq OWNED BY patient.id;


--
-- Name: patient_insurance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient_insurance (
                                   id bigint NOT NULL,
                                   created_at bigint,
                                   insurance_company_address jsonb,
                                   is_archived boolean,
                                   patient_insurance_advanced jsonb,
                                   patient_insurance_policy jsonb,
                                   patient_relation jsonb,
                                   relation character varying(255),
                                   patient_id bigint
);


ALTER TABLE patient_insurance OWNER TO postgres;

--
-- Name: patient_insurance_external_company; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient_insurance_external_company (
                                                    id bigint NOT NULL,
                                                    patient_insurance_id bigint,
                                                    external_insurance_company_id bigint
);


ALTER TABLE patient_insurance_external_company OWNER TO postgres;

--
-- Name: patient_insurance_external_company_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_insurance_external_company_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_insurance_external_company_id_seq OWNER TO postgres;

--
-- Name: patient_insurance_external_company_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_insurance_external_company_id_seq OWNED BY patient_insurance_external_company.id;


--
-- Name: patient_insurance_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_insurance_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_insurance_id_seq OWNER TO postgres;

--
-- Name: patient_insurance_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_insurance_id_seq OWNED BY patient_insurance.id;


--
-- Name: patient_insurance_internal_company; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient_insurance_internal_company (
                                                    id bigint NOT NULL,
                                                    internal_insurance_company_id bigint,
                                                    patient_insurance_id bigint
);


ALTER TABLE patient_insurance_internal_company OWNER TO postgres;

--
-- Name: patient_insurance_internal_company_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_insurance_internal_company_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_insurance_internal_company_id_seq OWNER TO postgres;

--
-- Name: patient_insurance_internal_company_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_insurance_internal_company_id_seq OWNED BY patient_insurance_internal_company.id;


--
-- Name: patient_invoice; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient_invoice (
                                 id bigint NOT NULL,
                                 document bytea,
                                 created_at bigint,
                                 delayed_reason character varying(255),
                                 insurance_company jsonb,
                                 is_one_date_service_per_claim boolean,
                                 submission_id bigint,
                                 submission_type character varying(255),
                                 patient_id bigint
);


ALTER TABLE patient_invoice OWNER TO postgres;

--
-- Name: patient_invoice_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient_invoice_details (
                                         id bigint NOT NULL,
                                         patient_invoice_id bigint,
                                         patient_session_id bigint,
                                         service_line_id bigint
);


ALTER TABLE patient_invoice_details OWNER TO postgres;

--
-- Name: patient_invoice_details_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_invoice_details_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_invoice_details_id_seq OWNER TO postgres;

--
-- Name: patient_invoice_details_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_invoice_details_id_seq OWNED BY patient_invoice_details.id;


--
-- Name: patient_invoice_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_invoice_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_invoice_id_seq OWNER TO postgres;

--
-- Name: patient_invoice_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_invoice_id_seq OWNED BY patient_invoice.id;


--
-- Name: patient_invoice_record; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient_invoice_record (
                                        id bigint NOT NULL,
                                        document bytea,
                                        created_at bigint,
                                        insurance_company_id bigint,
                                        insurance_company_name character varying(255),
                                        patient jsonb,
                                        submission_id bigint,
                                        submission_type character varying(255)
);


ALTER TABLE patient_invoice_record OWNER TO postgres;

--
-- Name: patient_invoice_record_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_invoice_record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_invoice_record_id_seq OWNER TO postgres;

--
-- Name: patient_invoice_record_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_invoice_record_id_seq OWNED BY patient_invoice_record.id;


--
-- Name: patient_session; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient_session (
                                 id bigint NOT NULL,
                                 case_diagnosis jsonb,
                                 case_title character varying(255),
                                 doctor_info jsonb,
                                 place_of_code character varying(255),
                                 service_date bigint,
                                 service_end_time bigint,
                                 service_start_time bigint,
                                 status character varying(255),
                                 clinic_id bigint,
                                 patient_id bigint,
                                 authorization_id bigint
);


ALTER TABLE patient_session OWNER TO postgres;

--
-- Name: patient_session_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_session_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_session_id_seq OWNER TO postgres;

--
-- Name: patient_session_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_session_id_seq OWNED BY patient_session.id;


--
-- Name: patient_session_service_line; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient_session_service_line (
                                              id bigint NOT NULL,
                                              cpt_code jsonb,
                                              diagnoses jsonb,
                                              is_correct boolean,
                                              line_note character varying(255),
                                              type character varying(255),
                                              session_id bigint
);


ALTER TABLE patient_session_service_line OWNER TO postgres;

--
-- Name: patient_session_service_line_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_session_service_line_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_session_service_line_id_seq OWNER TO postgres;

--
-- Name: patient_session_service_line_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_session_service_line_id_seq OWNED BY patient_session_service_line.id;


--
-- Name: patient_session_service_line_payment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient_session_service_line_payment (
                                                      id bigint NOT NULL,
                                                      adjust double precision,
                                                      balance double precision,
                                                      created_at bigint,
                                                      payment double precision,
                                                      payment_action character varying(255),
                                                      payment_info_id bigint,
                                                      service_line bigint
);


ALTER TABLE patient_session_service_line_payment OWNER TO postgres;

--
-- Name: patient_session_service_line_payment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_session_service_line_payment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_session_service_line_payment_id_seq OWNER TO postgres;

--
-- Name: patient_session_service_line_payment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_session_service_line_payment_id_seq OWNED BY patient_session_service_line_payment.id;


--
-- Name: patient_session_service_line_payment_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient_session_service_line_payment_info (
                                                           id bigint NOT NULL,
                                                           check_date bigint,
                                                           check_number character varying(255),
                                                           deposit_date bigint,
                                                           payment_entity_id bigint,
                                                           payment_method character varying(255),
                                                           received_date bigint,
                                                           payment_type character varying(255),
                                                           total_amount bigint
);


ALTER TABLE patient_session_service_line_payment_info OWNER TO postgres;

--
-- Name: patient_session_service_line_payment_info_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_session_service_line_payment_info_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_session_service_line_payment_info_id_seq OWNER TO postgres;

--
-- Name: patient_session_service_line_payment_info_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_session_service_line_payment_info_id_seq OWNED BY patient_session_service_line_payment_info.id;


--
-- Name: patient_submitted_claim; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient_submitted_claim (
                                         id bigint NOT NULL,
                                         case_diagnosis jsonb,
                                         clinic jsonb,
                                         dos bigint,
                                         local_claim_id bigint,
                                         submitted_claim_message jsonb,
                                         place_of_code character varying(255),
                                         provider_first_name character varying(255),
                                         provider_last_name character varying(255),
                                         provider_npi character varying(255),
                                         remote_claim_id bigint,
                                         service_end_time bigint,
                                         service_start_time bigint,
                                         submission_status character varying(255),
                                         patient_invoice_record_id bigint,
                                         patient_session_id bigint
);


ALTER TABLE patient_submitted_claim OWNER TO postgres;

--
-- Name: patient_submitted_claim_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_submitted_claim_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_submitted_claim_id_seq OWNER TO postgres;

--
-- Name: patient_submitted_claim_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_submitted_claim_id_seq OWNED BY patient_submitted_claim.id;


--
-- Name: patient_submitted_claim_service_line; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE patient_submitted_claim_service_line (
                                                      id bigint NOT NULL,
                                                      cpt_code jsonb,
                                                      diagnoses jsonb,
                                                      is_correct boolean,
                                                      line_note character varying(255),
                                                      payments double precision,
                                                      service_line_id bigint,
                                                      type character varying(255),
                                                      submitted_claim bigint
);


ALTER TABLE patient_submitted_claim_service_line OWNER TO postgres;

--
-- Name: patient_submitted_claim_service_line_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE patient_submitted_claim_service_line_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_submitted_claim_service_line_id_seq OWNER TO postgres;

--
-- Name: patient_submitted_claim_service_line_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE patient_submitted_claim_service_line_id_seq OWNED BY patient_submitted_claim_service_line.id;


--
-- Name: payer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE payer (
                       id bigint NOT NULL,
                       address jsonb,
                       display_name character varying(255),
                       name character varying(255),
                       payer_id character varying(255)
);


ALTER TABLE payer OWNER TO postgres;

--
-- Name: payer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE payer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE payer_id_seq OWNER TO postgres;

--
-- Name: payer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE payer_id_seq OWNED BY payer.id;


--
-- Name: provider; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE provider (
                          id bigint NOT NULL,
                          provider_info jsonb,
                          address jsonb,
                          email character varying(255),
                          first_name character varying(255),
                          last_name character varying(255),
                          legacy_id jsonb,
                          npi character varying(255),
                          phone character varying(255)
);


ALTER TABLE provider OWNER TO postgres;

--
-- Name: provider_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE provider_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE provider_id_seq OWNER TO postgres;

--
-- Name: provider_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE provider_id_seq OWNED BY provider.id;


--
-- Name: referring_provider; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE referring_provider (
                                    id bigint NOT NULL,
                                    first_name character varying(255),
                                    last_name character varying(255),
                                    npi character varying(255),
                                    profession_abbr character varying(255),
                                    referring_provider_id character varying(255),
                                    referring_provider_qualifier character varying(255)
);


ALTER TABLE referring_provider OWNER TO postgres;

--
-- Name: referring_provider_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE referring_provider_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE referring_provider_id_seq OWNER TO postgres;

--
-- Name: referring_provider_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE referring_provider_id_seq OWNED BY referring_provider.id;


--
-- Name: user_role_scope; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE user_role_scope (
                                 id bigint NOT NULL,
                                 email character varying(255),
                                 name character varying(255),
                                 role_scope jsonb,
                                 user_account character varying(255),
                                 uuid character varying(255)
);


ALTER TABLE user_role_scope OWNER TO postgres;

--
-- Name: user_role_scope_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_role_scope_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_role_scope_id_seq OWNER TO postgres;

--
-- Name: user_role_scope_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_role_scope_id_seq OWNED BY user_role_scope.id;


--
-- Name: claim_adjustment_reason_codes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY claim_adjustment_reason_codes ALTER COLUMN id SET DEFAULT nextval('claim_adjustment_reason_codes_id_seq'::regclass);


--
-- Name: claim_status_lookup id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY claim_status_lookup ALTER COLUMN id SET DEFAULT nextval('claim_status_lookup_id_seq'::regclass);


--
-- Name: clinic id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY clinic ALTER COLUMN id SET DEFAULT nextval('clinic_id_seq'::regclass);


--
-- Name: era_history id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY era_history ALTER COLUMN id SET DEFAULT nextval('era_history_id_seq'::regclass);


--
-- Name: fee_schedule id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fee_schedule ALTER COLUMN id SET DEFAULT nextval('fee_schedule_id_seq'::regclass);


--
-- Name: insurance_company id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY insurance_company ALTER COLUMN id SET DEFAULT nextval('insurance_company_id_seq'::regclass);


--
-- Name: insurance_company_configuration id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY insurance_company_configuration ALTER COLUMN id SET DEFAULT nextval('insurance_company_configuration_id_seq'::regclass);


--
-- Name: insurance_company_external id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY insurance_company_external ALTER COLUMN id SET DEFAULT nextval('insurance_company_external_id_seq'::regclass);


--
-- Name: insurance_company_payer id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY insurance_company_payer ALTER COLUMN id SET DEFAULT nextval('insurance_company_payer_id_seq'::regclass);


--
-- Name: modifier_rule id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY modifier_rule ALTER COLUMN id SET DEFAULT nextval('modifier_rule_id_seq'::regclass);


--
-- Name: organization id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY organization ALTER COLUMN id SET DEFAULT nextval('organization_id_seq'::regclass);


--
-- Name: patient id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient ALTER COLUMN id SET DEFAULT nextval('patient_id_seq'::regclass);


--
-- Name: patient_authorization id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_authorization ALTER COLUMN id SET DEFAULT nextval('patient_authorization_id_seq'::regclass);


--
-- Name: patient_balance_settings id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_balance_settings ALTER COLUMN id SET DEFAULT nextval('patient_balance_settings_id_seq'::regclass);


--
-- Name: patient_case id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_case ALTER COLUMN id SET DEFAULT nextval('patient_case_id_seq'::regclass);


--
-- Name: patient_claim id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_claim ALTER COLUMN id SET DEFAULT nextval('patient_claim_id_seq'::regclass);


--
-- Name: patient_insurance id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_insurance ALTER COLUMN id SET DEFAULT nextval('patient_insurance_id_seq'::regclass);


--
-- Name: patient_insurance_external_company id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_insurance_external_company ALTER COLUMN id SET DEFAULT nextval('patient_insurance_external_company_id_seq'::regclass);


--
-- Name: patient_insurance_internal_company id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_insurance_internal_company ALTER COLUMN id SET DEFAULT nextval('patient_insurance_internal_company_id_seq'::regclass);


--
-- Name: patient_invoice id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_invoice ALTER COLUMN id SET DEFAULT nextval('patient_invoice_id_seq'::regclass);


--
-- Name: patient_invoice_details id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_invoice_details ALTER COLUMN id SET DEFAULT nextval('patient_invoice_details_id_seq'::regclass);


--
-- Name: patient_invoice_record id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_invoice_record ALTER COLUMN id SET DEFAULT nextval('patient_invoice_record_id_seq'::regclass);


--
-- Name: patient_session id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_session ALTER COLUMN id SET DEFAULT nextval('patient_session_id_seq'::regclass);


--
-- Name: patient_session_service_line id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_session_service_line ALTER COLUMN id SET DEFAULT nextval('patient_session_service_line_id_seq'::regclass);


--
-- Name: patient_session_service_line_payment id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_session_service_line_payment ALTER COLUMN id SET DEFAULT nextval('patient_session_service_line_payment_id_seq'::regclass);


--
-- Name: patient_session_service_line_payment_info id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_session_service_line_payment_info ALTER COLUMN id SET DEFAULT nextval('patient_session_service_line_payment_info_id_seq'::regclass);


--
-- Name: patient_submitted_claim id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_submitted_claim ALTER COLUMN id SET DEFAULT nextval('patient_submitted_claim_id_seq'::regclass);


--
-- Name: patient_submitted_claim_service_line id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_submitted_claim_service_line ALTER COLUMN id SET DEFAULT nextval('patient_submitted_claim_service_line_id_seq'::regclass);


--
-- Name: payer id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payer ALTER COLUMN id SET DEFAULT nextval('payer_id_seq'::regclass);


--
-- Name: provider id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY provider ALTER COLUMN id SET DEFAULT nextval('provider_id_seq'::regclass);


--
-- Name: referring_provider id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY referring_provider ALTER COLUMN id SET DEFAULT nextval('referring_provider_id_seq'::regclass);


--
-- Name: user_role_scope id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_role_scope ALTER COLUMN id SET DEFAULT nextval('user_role_scope_id_seq'::regclass);


--
-- Name: claim_adjustment_reason_codes claim_adjustment_reason_codes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY claim_adjustment_reason_codes
    ADD CONSTRAINT claim_adjustment_reason_codes_pkey PRIMARY KEY (id);


--
-- Name: claim_status_lookup claim_status_lookup_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY claim_status_lookup
    ADD CONSTRAINT claim_status_lookup_pkey PRIMARY KEY (id);


--
-- Name: clinic clinic_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY clinic
    ADD CONSTRAINT clinic_pkey PRIMARY KEY (id);


--
-- Name: era_history era_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY era_history
    ADD CONSTRAINT era_history_pkey PRIMARY KEY (id);


--
-- Name: fee_schedule fee_schedule_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fee_schedule
    ADD CONSTRAINT fee_schedule_pkey PRIMARY KEY (id);


--
-- Name: insurance_company_configuration insurance_company_configuration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY insurance_company_configuration
    ADD CONSTRAINT insurance_company_configuration_pkey PRIMARY KEY (id);


--
-- Name: insurance_company_external insurance_company_external_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY insurance_company_external
    ADD CONSTRAINT insurance_company_external_pkey PRIMARY KEY (id);


--
-- Name: insurance_company_payer insurance_company_payer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY insurance_company_payer
    ADD CONSTRAINT insurance_company_payer_pkey PRIMARY KEY (id);


--
-- Name: insurance_company insurance_company_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY insurance_company
    ADD CONSTRAINT insurance_company_pkey PRIMARY KEY (id);


--
-- Name: modifier_rule modifier_rule_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY modifier_rule
    ADD CONSTRAINT modifier_rule_pkey PRIMARY KEY (id);


--
-- Name: organization organization_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY organization
    ADD CONSTRAINT organization_pkey PRIMARY KEY (id);


--
-- Name: patient_authorization patient_authorization_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_authorization
    ADD CONSTRAINT patient_authorization_pkey PRIMARY KEY (id);


--
-- Name: patient_balance_settings patient_balance_settings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_balance_settings
    ADD CONSTRAINT patient_balance_settings_pkey PRIMARY KEY (id);


--
-- Name: patient_case patient_case_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_case
    ADD CONSTRAINT patient_case_pkey PRIMARY KEY (id);


--
-- Name: patient_claim patient_claim_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_claim
    ADD CONSTRAINT patient_claim_pkey PRIMARY KEY (id);


--
-- Name: patient_insurance_external_company patient_insurance_external_company_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_insurance_external_company
    ADD CONSTRAINT patient_insurance_external_company_pkey PRIMARY KEY (id);


--
-- Name: patient_insurance_internal_company patient_insurance_internal_company_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_insurance_internal_company
    ADD CONSTRAINT patient_insurance_internal_company_pkey PRIMARY KEY (id);


--
-- Name: patient_insurance patient_insurance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_insurance
    ADD CONSTRAINT patient_insurance_pkey PRIMARY KEY (id);


--
-- Name: patient_invoice_details patient_invoice_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_invoice_details
    ADD CONSTRAINT patient_invoice_details_pkey PRIMARY KEY (id);


--
-- Name: patient_invoice patient_invoice_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_invoice
    ADD CONSTRAINT patient_invoice_pkey PRIMARY KEY (id);


--
-- Name: patient_invoice_record patient_invoice_record_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_invoice_record
    ADD CONSTRAINT patient_invoice_record_pkey PRIMARY KEY (id);


--
-- Name: patient patient_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient
    ADD CONSTRAINT patient_pkey PRIMARY KEY (id);


--
-- Name: patient_session patient_session_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_session
    ADD CONSTRAINT patient_session_pkey PRIMARY KEY (id);


--
-- Name: patient_session_service_line_payment_info patient_session_service_line_payment_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_session_service_line_payment_info
    ADD CONSTRAINT patient_session_service_line_payment_info_pkey PRIMARY KEY (id);


--
-- Name: patient_session_service_line_payment patient_session_service_line_payment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_session_service_line_payment
    ADD CONSTRAINT patient_session_service_line_payment_pkey PRIMARY KEY (id);


--
-- Name: patient_session_service_line patient_session_service_line_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_session_service_line
    ADD CONSTRAINT patient_session_service_line_pkey PRIMARY KEY (id);


--
-- Name: patient_submitted_claim patient_submitted_claim_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_submitted_claim
    ADD CONSTRAINT patient_submitted_claim_pkey PRIMARY KEY (id);


--
-- Name: patient_submitted_claim_service_line patient_submitted_claim_service_line_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_submitted_claim_service_line
    ADD CONSTRAINT patient_submitted_claim_service_line_pkey PRIMARY KEY (id);


--
-- Name: payer payer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payer
    ADD CONSTRAINT payer_pkey PRIMARY KEY (id);


--
-- Name: provider provider_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY provider
    ADD CONSTRAINT provider_pkey PRIMARY KEY (id);


--
-- Name: referring_provider referring_provider_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY referring_provider
    ADD CONSTRAINT referring_provider_pkey PRIMARY KEY (id);


--
-- Name: user_role_scope user_role_scope_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_role_scope
    ADD CONSTRAINT user_role_scope_pkey PRIMARY KEY (id);


--
-- Name: patient_insurance_external_company fk11ysb9v7xgy039xyafg7ywjb8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_insurance_external_company
    ADD CONSTRAINT fk11ysb9v7xgy039xyafg7ywjb8 FOREIGN KEY (patient_insurance_id) REFERENCES patient_insurance(id);


--
-- Name: insurance_company_payer fk2g5trivnq0m5d6ydhd0owyu21; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY insurance_company_payer
    ADD CONSTRAINT fk2g5trivnq0m5d6ydhd0owyu21 FOREIGN KEY (internal_insurance_company_id) REFERENCES insurance_company(id);


--
-- Name: patient_session fk44iikbxgay51oba9asxwp79vh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_session
    ADD CONSTRAINT fk44iikbxgay51oba9asxwp79vh FOREIGN KEY (authorization_id) REFERENCES patient_authorization(id);


--
-- Name: patient_session fk49dtidv9ipuo4py7hje5m9561; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_session
    ADD CONSTRAINT fk49dtidv9ipuo4py7hje5m9561 FOREIGN KEY (patient_id) REFERENCES patient(id);


--
-- Name: patient_submitted_claim fk4ccupord9p6029eg6bv8c9xf5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_submitted_claim
    ADD CONSTRAINT fk4ccupord9p6029eg6bv8c9xf5 FOREIGN KEY (patient_session_id) REFERENCES patient_session(id);


--
-- Name: patient_session_service_line_payment fk5wug3t9ajxb41j3318uc5m4si; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_session_service_line_payment
    ADD CONSTRAINT fk5wug3t9ajxb41j3318uc5m4si FOREIGN KEY (payment_info_id) REFERENCES patient_session_service_line_payment_info(id);


--
-- Name: patient_insurance fk7afwp2wua8xdh8q3v724wtk8l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_insurance
    ADD CONSTRAINT fk7afwp2wua8xdh8q3v724wtk8l FOREIGN KEY (patient_id) REFERENCES patient(id);


--
-- Name: patient_claim fk7aq3irq9w1klg57vj2ybdf4v9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_claim
    ADD CONSTRAINT fk7aq3irq9w1klg57vj2ybdf4v9 FOREIGN KEY (patient_invoice_id) REFERENCES patient_invoice(id);


--
-- Name: patient_case fk7j0pp4te762x9biweau5ku58y; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_case
    ADD CONSTRAINT fk7j0pp4te762x9biweau5ku58y FOREIGN KEY (patient_id) REFERENCES patient(id);


--
-- Name: patient_session fkbh4k4o0jcr9ehpfc3q26g06ov; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_session
    ADD CONSTRAINT fkbh4k4o0jcr9ehpfc3q26g06ov FOREIGN KEY (clinic_id) REFERENCES clinic(id);


--
-- Name: patient_authorization fkcgu06t3tf80xgaq8y182fdyfw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_authorization
    ADD CONSTRAINT fkcgu06t3tf80xgaq8y182fdyfw FOREIGN KEY (patient_id) REFERENCES patient(id);


--
-- Name: patient_invoice_details fkd9lmvwk8tb7loc3xhiuc7b6hl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_invoice_details
    ADD CONSTRAINT fkd9lmvwk8tb7loc3xhiuc7b6hl FOREIGN KEY (patient_invoice_id) REFERENCES patient_invoice(id);


--
-- Name: patient_insurance_external_company fkh0y69vv1sx7mmjb4j2f8elveo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_insurance_external_company
    ADD CONSTRAINT fkh0y69vv1sx7mmjb4j2f8elveo FOREIGN KEY (external_insurance_company_id) REFERENCES insurance_company_external(id);


--
-- Name: patient_submitted_claim fkhrmenyd71ig1rdm0unl6edovo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_submitted_claim
    ADD CONSTRAINT fkhrmenyd71ig1rdm0unl6edovo FOREIGN KEY (patient_invoice_record_id) REFERENCES patient_invoice_record(id);


--
-- Name: patient_insurance_internal_company fki0fr1cbstuv4rhtpjloyurmh7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_insurance_internal_company
    ADD CONSTRAINT fki0fr1cbstuv4rhtpjloyurmh7 FOREIGN KEY (patient_insurance_id) REFERENCES patient_insurance(id);


--
-- Name: patient_insurance_internal_company fki8ackjcievlojnh2ru5ygoc2x; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_insurance_internal_company
    ADD CONSTRAINT fki8ackjcievlojnh2ru5ygoc2x FOREIGN KEY (internal_insurance_company_id) REFERENCES insurance_company(id);


--
-- Name: insurance_company_configuration fkiq44xnm02kgf8b077dn8aviye; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY insurance_company_configuration
    ADD CONSTRAINT fkiq44xnm02kgf8b077dn8aviye FOREIGN KEY (internal_insurance_company_id) REFERENCES insurance_company(id);


--
-- Name: patient_session_service_line_payment fkk8ugfjwo0cry6nxasagi7j11k; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_session_service_line_payment
    ADD CONSTRAINT fkk8ugfjwo0cry6nxasagi7j11k FOREIGN KEY (service_line) REFERENCES patient_session_service_line(id);


--
-- Name: patient_invoice_details fkl04onj35b66i06jyk0tjdq9v5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_invoice_details
    ADD CONSTRAINT fkl04onj35b66i06jyk0tjdq9v5 FOREIGN KEY (patient_session_id) REFERENCES patient_session(id);


--
-- Name: patient_submitted_claim_service_line fklxugf5lwb6vkr7rcbusn0jjl2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_submitted_claim_service_line
    ADD CONSTRAINT fklxugf5lwb6vkr7rcbusn0jjl2 FOREIGN KEY (submitted_claim) REFERENCES patient_submitted_claim(id);


--
-- Name: patient fkmjgh7r6gcpgcopjq7iecpx8gs; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient
    ADD CONSTRAINT fkmjgh7r6gcpgcopjq7iecpx8gs FOREIGN KEY (referring_provider_id) REFERENCES referring_provider(id);


--
-- Name: patient_invoice_details fknbrnef0stusfaihr9gnl3wvyk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_invoice_details
    ADD CONSTRAINT fknbrnef0stusfaihr9gnl3wvyk FOREIGN KEY (service_line_id) REFERENCES patient_session_service_line(id);


--
-- Name: patient_session_service_line fknyjsj64j6j9a3h3ugsvkkxn78; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_session_service_line
    ADD CONSTRAINT fknyjsj64j6j9a3h3ugsvkkxn78 FOREIGN KEY (session_id) REFERENCES patient_session(id);


--
-- Name: patient_invoice fkpde3xppiasu6pqglaoucrqiqu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY patient_invoice
    ADD CONSTRAINT fkpde3xppiasu6pqglaoucrqiqu FOREIGN KEY (patient_id) REFERENCES patient(id);


--
-- Name: insurance_company_configuration fkqa8cdgwnqdn0vj0xyfoj2lcd8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY insurance_company_configuration
    ADD CONSTRAINT fkqa8cdgwnqdn0vj0xyfoj2lcd8 FOREIGN KEY (external_insurance_company_id) REFERENCES insurance_company_external(id);
