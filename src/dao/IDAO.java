package dao;

import java.util.Collection;


public interface IDAO <T>{
 	public abstract T findByID (int id);
 	public abstract  Collection <T> findAll();
    public abstract boolean insert (T o);
}
