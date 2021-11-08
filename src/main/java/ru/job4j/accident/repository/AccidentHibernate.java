package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.function.Function;

public class AccidentHibernate {

    private static final Logger LOG = LoggerFactory.getLogger(AccidentHibernate.class.getName());
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    public Accident getAccident(int id) {
        return (Accident) execute(session -> {
            final Query query = session.createQuery(
                    "select distinct a from accident a left join fetch a.rules where a.id = :id");
            query.setParameter("id", id);
            return query.uniqueResult();
        });
    }

    public AccidentType getAccidentType(int id) {
        return execute(session -> session.get(AccidentType.class, id));
    }

    public Rule getRule(int id) {
        return execute(session -> session.get(Rule.class, id));
    }

    public Collection<Accident> getAllAccidents() {
        return execute(session -> {
            final Query query = session.createQuery(
                    "select distinct a from accident a left join fetch a.rules", Accident.class);
            return query.list();
        });
    }

    public Collection<AccidentType> getAllAccidentTypes() {
        return execute(session -> {
            final Query query = session.createQuery(
                    "select distinct t from type t", AccidentType.class);
            return query.list();
        });
    }

    public Collection<Rule> getAllRules() {
        return execute(session -> {
            final Query query = session.createQuery(
                    "select distinct r from rule r", Rule.class);
            return query.list();
        });
    }

    public Accident save(Accident accident) {
        Accident rsl = accident;
        if (accident.getId() == 0) {
           rsl = create(accident);
        } else {
            update(accident);
        }
        return rsl;
    }

    public boolean remove(Accident accident) {
        execute(session -> {
            session.delete(accident);
            return null;
        });
        return true;
    }

    public AccidentType save(AccidentType type) {
        AccidentType rsl = type;
        if (type.getId() == 0) {
            rsl = create(type);
        } else {
            update(type);
        }
        return rsl;
    }

    public boolean remove(AccidentType type) {
        execute(session -> {
            session.delete(type);
            return null;
        });
        return true;
    }

    public Rule save(Rule rule) {
        Rule rsl = rule;
        if (rule.getId() == 0) {
            rsl = create(rule);
        } else {
            update(rule);
        }
        return rsl;
    }

    public boolean remove(Rule rule) {
        execute(session -> {
            session.delete(rule);
            return null;
        });
        return true;
    }

    private <T> T create(T t) {
        return execute(session -> {
            session.save(t);
            return t;
        });
    }

    private <T> void update(T t) {
        execute(session -> {
            session.update(t);
            return t;
        });
    }

    private <T> T execute(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            transaction.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            LOG.error(e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }
}