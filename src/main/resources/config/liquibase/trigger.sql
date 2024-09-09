CREATE OR REPLACE FUNCTION limit_program_aud_size_per_entity() RETURNS TRIGGER AS $$
DECLARE
  max_audits INTEGER := 30;  
  audit_count INTEGER;
BEGIN
  
  SELECT COUNT(*) INTO audit_count
  FROM program_aud
  WHERE id = NEW.id;
  
  IF audit_count > max_audits THEN
    
    DELETE FROM program_aud
    WHERE id = NEW.id
    AND ctid IN (
      SELECT ctid FROM program_aud
      WHERE id = NEW.id
      ORDER BY rev ASC
      LIMIT (audit_count - max_audits)  
    );
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER program_aud_limit_size_per_entity_trigger
    AFTER INSERT ON program_aud
    FOR EACH ROW
    EXECUTE FUNCTION limit_program_aud_size_per_entity();


CREATE OR REPLACE FUNCTION limit_organisation_unit_aud_size_per_entity() RETURNS TRIGGER AS $$
DECLARE
  max_audits INTEGER := 30;  
  audit_count INTEGER;
BEGIN
  
  SELECT COUNT(*) INTO audit_count
  FROM organisation_unit_aud
  WHERE id = NEW.id;
  
  IF audit_count > max_audits THEN
    
    DELETE FROM organisation_unit_aud
    WHERE id = NEW.id
    AND ctid IN (
      SELECT ctid FROM organisation_unit_aud
      WHERE id = NEW.id
      ORDER BY rev ASC
      LIMIT (audit_count - max_audits)  
    );
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER organisation_unit_aud_limit_size_per_entity_trigger
    AFTER INSERT ON organisation_unit_aud
    FOR EACH ROW
    EXECUTE FUNCTION limit_organisation_unit_aud_size_per_entity();


CREATE OR REPLACE FUNCTION limit_categorycombo_aud_size_per_entity() RETURNS TRIGGER AS $$
DECLARE
  max_audits INTEGER := 30;  
  audit_count INTEGER;
BEGIN
  
  SELECT COUNT(*) INTO audit_count
  FROM categorycombo_aud
  WHERE id = NEW.id;
  
  IF audit_count > max_audits THEN
    
    DELETE FROM categorycombo_aud
    WHERE id = NEW.id
    AND ctid IN (
      SELECT ctid FROM categorycombo_aud
      WHERE id = NEW.id
      ORDER BY rev ASC
      LIMIT (audit_count - max_audits)  
    );
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER categorycombo_aud_limit_size_per_entity_trigger
    AFTER INSERT ON categorycombo_aud
    FOR EACH ROW
    EXECUTE FUNCTION limit_categorycombo_aud_size_per_entity();


CREATE OR REPLACE FUNCTION limit_dataelement_aud_size_per_entity() RETURNS TRIGGER AS $$
DECLARE
  max_audits INTEGER := 30;  
  audit_count INTEGER;
BEGIN
  
  SELECT COUNT(*) INTO audit_count
  FROM dataelement_aud
  WHERE id = NEW.id;
  
  IF audit_count > max_audits THEN
    
    DELETE FROM dataelement_aud
    WHERE id = NEW.id
    AND ctid IN (
      SELECT ctid FROM dataelement_aud
      WHERE id = NEW.id
      ORDER BY rev ASC
      LIMIT (audit_count - max_audits)  
    );
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER dataelement_aud_limit_size_per_entity_trigger
    AFTER INSERT ON dataelement_aud
    FOR EACH ROW
    EXECUTE FUNCTION limit_dataelement_aud_size_per_entity();

CREATE OR REPLACE FUNCTION limit_dataset_aud_size_per_entity() RETURNS TRIGGER AS $$
DECLARE
  max_audits INTEGER := 30;  
  audit_count INTEGER;
BEGIN
  
  SELECT COUNT(*) INTO audit_count
  FROM dataset_aud
  WHERE id = NEW.id;
  
  IF audit_count > max_audits THEN
    
    DELETE FROM dataset_aud
    WHERE id = NEW.id
    AND ctid IN (
      SELECT ctid FROM dataset_aud
      WHERE id = NEW.id
      ORDER BY rev ASC
      LIMIT (audit_count - max_audits)  
    );
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER dataset_aud_limit_size_per_entity_trigger
    AFTER INSERT ON dataset_aud
    FOR EACH ROW
    EXECUTE FUNCTION limit_dataset_aud_size_per_entity();


CREATE OR REPLACE FUNCTION limit_dhis_user_aud_size_per_entity() RETURNS TRIGGER AS $$
DECLARE
  max_audits INTEGER := 30;  
  audit_count INTEGER;
BEGIN
  
  SELECT COUNT(*) INTO audit_count
  FROM dhis_user_aud
  WHERE id = NEW.id;
  
  IF audit_count > max_audits THEN
    
    DELETE FROM dhis_user_aud
    WHERE id = NEW.id
    AND ctid IN (
      SELECT ctid FROM dhis_user_aud
      WHERE id = NEW.id
      ORDER BY rev ASC
      LIMIT (audit_count - max_audits)  
    );
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER dhis_user_aud_limit_size_per_entity_trigger
    AFTER INSERT ON dhis_user_aud
    FOR EACH ROW
    EXECUTE FUNCTION limit_dhis_user_aud_size_per_entity();

CREATE OR REPLACE FUNCTION limit_indicator_aud_size_per_entity() RETURNS TRIGGER AS $$
DECLARE
  max_audits INTEGER := 30;  
  audit_count INTEGER;
BEGIN
  
  SELECT COUNT(*) INTO audit_count
  FROM indicator_aud
  WHERE id = NEW.id;
  
  IF audit_count > max_audits THEN
    
    DELETE FROM indicator_aud
    WHERE id = NEW.id
    AND ctid IN (
      SELECT ctid FROM indicator_aud
      WHERE id = NEW.id
      ORDER BY rev ASC
      LIMIT (audit_count - max_audits)  
    );
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER indicator_aud_limit_size_per_entity_trigger
    AFTER INSERT ON indicator_aud
    FOR EACH ROW
    EXECUTE FUNCTION limit_indicator_aud_size_per_entity();

CREATE OR REPLACE FUNCTION limit_indicatortype_aud_size_per_entity() RETURNS TRIGGER AS $$
DECLARE
  max_audits INTEGER := 30;  
  audit_count INTEGER;
BEGIN
  
  SELECT COUNT(*) INTO audit_count
  FROM indicatortype_aud
  WHERE id = NEW.id;
  
  IF audit_count > max_audits THEN
    
    DELETE FROM indicatortype_aud
    WHERE id = NEW.id
    AND ctid IN (
      SELECT ctid FROM indicatortype_aud
      WHERE id = NEW.id
      ORDER BY rev ASC
      LIMIT (audit_count - max_audits)  
    );
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER indicatortype_aud_limit_size_per_entity_trigger
    AFTER INSERT ON indicatortype_aud
    FOR EACH ROW
    EXECUTE FUNCTION limit_indicatortype_aud_size_per_entity();

CREATE OR REPLACE FUNCTION limit_option_group_aud_size_per_entity() RETURNS TRIGGER AS $$
DECLARE
  max_audits INTEGER := 30;  
  audit_count INTEGER;
BEGIN
  
  SELECT COUNT(*) INTO audit_count
  FROM option_group_aud
  WHERE id = NEW.id;
  
  IF audit_count > max_audits THEN
    
    DELETE FROM option_group_aud
    WHERE id = NEW.id
    AND ctid IN (
      SELECT ctid FROM option_group_aud
      WHERE id = NEW.id
      ORDER BY rev ASC
      LIMIT (audit_count - max_audits)  
    );
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER option_group_aud_limit_size_per_entity_trigger
    AFTER INSERT ON option_group_aud
    FOR EACH ROW
    EXECUTE FUNCTION limit_option_group_aud_size_per_entity();

CREATE OR REPLACE FUNCTION limit_optionset_aud_size_per_entity() RETURNS TRIGGER AS $$
DECLARE
  max_audits INTEGER := 30;  
  audit_count INTEGER;
BEGIN
  
  SELECT COUNT(*) INTO audit_count
  FROM optionset_aud
  WHERE id = NEW.id;
  
  IF audit_count > max_audits THEN
    
    DELETE FROM optionset_aud
    WHERE id = NEW.id
    AND ctid IN (
      SELECT ctid FROM optionset_aud
      WHERE id = NEW.id
      ORDER BY rev ASC
      LIMIT (audit_count - max_audits)  
    );
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER optionset_aud_limit_size_per_entity_trigger
    AFTER INSERT ON optionset_aud
    FOR EACH ROW
    EXECUTE FUNCTION limit_optionset_aud_size_per_entity();


CREATE OR REPLACE FUNCTION limit_program_indicator_aud_size_per_entity() RETURNS TRIGGER AS $$
DECLARE
  max_audits INTEGER := 30;  
  audit_count INTEGER;
BEGIN
  
  SELECT COUNT(*) INTO audit_count
  FROM program_indicator_aud
  WHERE id = NEW.id;
  
  IF audit_count > max_audits THEN
    
    DELETE FROM program_indicator_aud
    WHERE id = NEW.id
    AND ctid IN (
      SELECT ctid FROM program_indicator_aud
      WHERE id = NEW.id
      ORDER BY rev ASC
      LIMIT (audit_count - max_audits)  
    );
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER program_indicator_aud_limit_size_per_entity_trigger
    AFTER INSERT ON program_indicator_aud
    FOR EACH ROW
    EXECUTE FUNCTION limit_program_indicator_aud_size_per_entity();

CREATE OR REPLACE FUNCTION limit_program_rule_action_aud_size_per_entity() RETURNS TRIGGER AS $$
DECLARE
  max_audits INTEGER := 30;  
  audit_count INTEGER;
BEGIN
  
  SELECT COUNT(*) INTO audit_count
  FROM program_rule_action_aud
  WHERE id = NEW.id;
  
  IF audit_count > max_audits THEN
    
    DELETE FROM program_rule_action_aud
    WHERE id = NEW.id
    AND ctid IN (
      SELECT ctid FROM program_rule_action_aud
      WHERE id = NEW.id
      ORDER BY rev ASC
      LIMIT (audit_count - max_audits)  
    );
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER program_rule_action_aud_limit_size_per_entity_trigger
    AFTER INSERT ON program_rule_action_aud
    FOR EACH ROW
    EXECUTE FUNCTION limit_program_rule_action_aud_size_per_entity();


CREATE OR REPLACE FUNCTION limit_program_rule_aud_size_per_entity() RETURNS TRIGGER AS $$
DECLARE
  max_audits INTEGER := 30;  
  audit_count INTEGER;
BEGIN
  
  SELECT COUNT(*) INTO audit_count
  FROM program_rule_aud
  WHERE id = NEW.id;
  
  IF audit_count > max_audits THEN
    
    DELETE FROM program_rule_aud
    WHERE id = NEW.id
    AND ctid IN (
      SELECT ctid FROM program_rule_aud
      WHERE id = NEW.id
      ORDER BY rev ASC
      LIMIT (audit_count - max_audits)  
    );
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER program_rule_aud_limit_size_per_entity_trigger
    AFTER INSERT ON program_rule_aud
    FOR EACH ROW
    EXECUTE FUNCTION limit_program_rule_aud_size_per_entity();

CREATE OR REPLACE FUNCTION limit_program_rule_variable_aud_size_per_entity() RETURNS TRIGGER AS $$
DECLARE
  max_audits INTEGER := 30;  
  audit_count INTEGER;
BEGIN
  
  SELECT COUNT(*) INTO audit_count
  FROM program_rule_variable_aud
  WHERE id = NEW.id;
  
  IF audit_count > max_audits THEN
    
    DELETE FROM program_rule_variable_aud
    WHERE id = NEW.id
    AND ctid IN (
      SELECT ctid FROM program_rule_variable_aud
      WHERE id = NEW.id
      ORDER BY rev ASC
      LIMIT (audit_count - max_audits)  
    );
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER program_rule_variable_aud_limit_size_per_entity_trigger
    AFTER INSERT ON program_rule_variable_aud
    FOR EACH ROW
    EXECUTE FUNCTION limit_program_rule_variable_aud_size_per_entity();

CREATE OR REPLACE FUNCTION limit_program_stage_aud_size_per_entity() RETURNS TRIGGER AS $$
DECLARE
  max_audits INTEGER := 30;  
  audit_count INTEGER;
BEGIN
  
  SELECT COUNT(*) INTO audit_count
  FROM program_stage_aud
  WHERE id = NEW.id;
  
  IF audit_count > max_audits THEN
    
    DELETE FROM program_stage_aud
    WHERE id = NEW.id
    AND ctid IN (
      SELECT ctid FROM program_stage_aud
      WHERE id = NEW.id
      ORDER BY rev ASC
      LIMIT (audit_count - max_audits)  
    );
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER program_stage_aud_limit_size_per_entity_trigger
    AFTER INSERT ON program_stage_aud
    FOR EACH ROW
    EXECUTE FUNCTION limit_program_stage_aud_size_per_entity();

CREATE OR REPLACE FUNCTION limit_tracked_entity_attribute_aud_size_per_entity() RETURNS TRIGGER AS $$
DECLARE
  max_audits INTEGER := 30;  
  audit_count INTEGER;
BEGIN
  
  SELECT COUNT(*) INTO audit_count
  FROM tracked_entity_attribute_aud
  WHERE id = NEW.id;
  
  IF audit_count > max_audits THEN
    
    DELETE FROM tracked_entity_attribute_aud
    WHERE id = NEW.id
    AND ctid IN (
      SELECT ctid FROM tracked_entity_attribute_aud
      WHERE id = NEW.id
      ORDER BY rev ASC
      LIMIT (audit_count - max_audits)  
    );
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER tracked_entity_attribute_aud_limit_size_per_entity_trigger
    AFTER INSERT ON tracked_entity_attribute_aud
    FOR EACH ROW
    EXECUTE FUNCTION limit_tracked_entity_attribute_aud_size_per_entity();