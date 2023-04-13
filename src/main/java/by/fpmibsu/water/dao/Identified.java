package by.fpmibsu.water.dao;

public interface Identified<PK> {

    /** Возвращает идентификатор объекта */
    public PK getId();
}
