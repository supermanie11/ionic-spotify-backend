package com.spotify.backend.dao;

import java.util.Collection;
import java.util.Optional;

public interface GenericDao<T> {

	Optional<T> get(int id);

	Collection<T> getAll();

	T save(T model);

	T update(T model);

	void delete(T model);
}
