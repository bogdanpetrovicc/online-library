package com.bogdan.onlinelibrary.repository.generic;

import com.bogdan.onlinelibrary.bean.Exclude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Exclude
public interface GenericRepository<T> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {
}

