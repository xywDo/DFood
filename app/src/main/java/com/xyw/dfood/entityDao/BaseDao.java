package com.xyw.dfood.entityDao;

/**
 * Created by Do on 2015/4/3.
 */
import java.util.List;
public interface BaseDao<T> {
    public List<T> getEntitys();
    public boolean deleteEntity(int id);
    public T getEntity(int id);
    public T UpdateEntity(T t);
}
