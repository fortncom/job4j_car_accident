package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    private final RowMapper<Accident> accidentRowMapper = (rs, row) -> {
        Accident accident = new Accident();
        accident.setId(rs.getInt("aId"));
        accident.setName(rs.getString("aName"));
        accident.setText(rs.getString("aText"));
        accident.setAddress(rs.getString("aAddress"));
        accident.setType(AccidentType.of(
                rs.getInt("tId"), rs.getString("tName")));
        return accident;
    };

    private final RowMapper<AccidentType> accidentTypeRowMapper = (rs, row) -> {
        AccidentType type = new AccidentType();
        type.setId(rs.getInt("tId"));
        type.setName(rs.getString("tName"));
        return type;
    };

    private final RowMapper<Rule> ruleRowMapper = (rs, row) -> {
        Rule rule = new Rule();
        rule.setId(rs.getInt("rId"));
        rule.setName(rs.getString("rName"));
        return rule;
    };

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Accident getAccident(int id) {
        Accident accident = jdbc.queryForObject(
                "select a.id aId, a.name aName, a.text aText, a.address aAddress,"
                        + " t.id tId, t.name tName "
                        + "from accident a "
                        + "left join accident_type t on a.accident_type_id = t.id "
                        + "where a.id = ?", accidentRowMapper, id);
        List<Rule> rules = jdbc.query(
                "select r.id rId, r.name rName from accident a "
                        + "left join accidents_rules ar on a.id = ar.accident_id "
                        + "left join rules r on r.id = ar.rule_id where a.id = ?",
                ruleRowMapper, id);
        accident.setRules(new HashSet<>(rules));
        return accident;
    }

    public Collection<Accident> getAllAccidents() {
        List<Accident> accidents = jdbc.query(
                "select a.id aId, a.name aName, a.text aText, a.address aAddress,"
                        + " t.id tId, t.name tName "
                        + "from accident a "
                        + "left join accident_type t on a.accident_type_id = t.id",
                accidentRowMapper);
        for (Accident accident : accidents) {
            List<Rule> rules = jdbc.query(
                    "select r.id rId, r.name rName from accident a "
                            + "left join accidents_rules ar on a.id = ar.accident_id "
                            + "left join rules r on r.id = ar.rule_id where a.id = ?",
                    ruleRowMapper, accident.getId());
            accident.setRules(new HashSet<>(rules));
        }
        return accidents;
    }

    public Accident save(Accident accident) {
        Accident rsl;
        if (accident.getId() == 0) {
            rsl = add(accident);
        } else {
            rsl = replace(accident);
        }
        return rsl;
    }

    private Accident add(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into accident (name, text, address, accident_type_id) "
                    + "values (?,?,?,?) ON CONFLICT DO NOTHING", new String[]{"id"});
            preparedStatement.setString(1, accident.getName());
            preparedStatement.setString(2, accident.getText());
            preparedStatement.setString(3, accident.getAddress());
            preparedStatement.setInt(4, accident.getType().getId());
            return preparedStatement;
        }, keyHolder);
        accident.setId((Integer) keyHolder.getKey());
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into accidents_rules (accident_id, rule_id) values (?,?)",
                    accident.getId(), rule.getId());
        }
        return accident;
    }

    private Accident replace(Accident accident) {
        jdbc.update("update accident a set name = ?, text = ?, "
                        + "address = ?, accident_type_id = ? where a.id = ?",
                accident.getName(), accident.getText(), accident.getAddress(),
                accident.getType().getId(), accident.getId());
        jdbc.update("delete from accidents_rules where accident_id = ?", accident.getId());
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into accidents_rules (accident_id, rule_id) values (?,?)",
                    accident.getId(), rule.getId());
        }
        return accident;
    }

        public boolean remove(Accident accident) {
            jdbc.update("delete from accident a where a.id = ?", accident.getId());
            jdbc.update("delete from accidents_rules ar where ar.accident_id = ?",
                    accident.getId());
        return true;
    }

    public AccidentType getAccidentType(int id) {
        return jdbc.queryForObject(
                "select t.id tId, t.name tName from accident_type t where t.id = ?",
                accidentTypeRowMapper, id);
    }

    public Collection<AccidentType> getAllAccidentTypes() {
        return jdbc.query("select t.id tId, t.name tName from accident_type t",
                accidentTypeRowMapper);
    }

    public AccidentType save(AccidentType type) {
        AccidentType rsl;
        if (type.getId() == 0) {
            rsl = add(type);
        } else {
            rsl = replace(type);
        }
        return rsl;
    }

    private AccidentType add(AccidentType type) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into accident_type (name) "
                            + "values (?) ON CONFLICT DO NOTHING", new String[]{"id"});
            preparedStatement.setString(1, type.getName());
            return preparedStatement;
        }, keyHolder);
        type.setId((Integer) keyHolder.getKey());
        return type;
    }

    private AccidentType replace(AccidentType type) {
        jdbc.update("update accident_type a set name = ? where a.id = ?",
                type.getName(), type.getId());
        return type;
    }

    public boolean remove(AccidentType type) {
        jdbc.update("delete from accident_type t where t.id = ?",
                type.getId());
        return true;
    }

    public Rule getRule(int id) {
        return jdbc.queryForObject(
                "select r.id rId, r.name rName from rules r where r.id = ?",
                ruleRowMapper, id);
    }

    public Collection<Rule> getAllRules() {
        return jdbc.query("select r.id rId, r.name rName from rules r",
                ruleRowMapper);
    }

    public Rule save(Rule rule) {
        Rule rsl;
        if (rule.getId() == 0) {
            rsl = add(rule);
        } else {
            rsl = replace(rule);
        }
        return rsl;
    }

    private Rule add(Rule rule) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into rules (name) "
                            + "values (?) ON CONFLICT DO NOTHING", new String[]{"id"});
            preparedStatement.setString(1, rule.getName());
            return preparedStatement;
        }, keyHolder);
        rule.setId((Integer) keyHolder.getKey());
        return rule;
    }

    private Rule replace(Rule rule) {
        jdbc.update("update rules r set name = ? where r.id = ?",
                rule.getName(), rule.getId());
        return rule;
    }

    public boolean remove(Rule rule) {
        jdbc.update("delete from rules r where r.id = ?",
                rule.getId());
        return true;
    }
}