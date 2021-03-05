package service;

import java.util.List;

public interface IGeneralService<E> {
    List<E> getAll();

    boolean remove(int id);

    boolean add(E e);

    boolean update(int id, E e);

    E getById(int id);
}
