import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class Repository<T> {
    private final Map<String,T> items = new HashMap<>();
    public synchronized T save(T entity) {
        if(entity==null) throw new IllegalArgumentException("Cannot save null.");
        try {
            Field idField=null;
            for(Class<?> c=entity.getClass(); c!=null && idField==null; c=c.getSuperclass())
                for(Field f:c.getDeclaredFields()) if(f.isAnnotationPresent(Id.class)){idField=f;break;}
            if(idField==null) throw new IllegalArgumentException("Entity has no field annotated with @Id: "+entity.getClass().getName());
            idField.setAccessible(true);
            Object raw=idField.get(entity);
            if(raw==null || raw.toString().isBlank()) throw new IllegalArgumentException("@Id value cannot be blank.");
            items.put(raw.toString(),entity);
            return entity;
        } catch(ReflectiveOperationException e){throw new IllegalStateException("Unable to read @Id field.",e);}
    }
    @SafeVarargs public final synchronized void saveAll(T... entities){for(T entity:entities)save(entity);}
    public synchronized T findById(String id){return items.get(id);}
    public synchronized ArrayList<T> findAll(){return new ArrayList<>(items.values());}
    public synchronized boolean delete(String id){return items.remove(id)!=null;}
    public synchronized int size(){return items.size();}
    public synchronized void clear(){items.clear();}
}
