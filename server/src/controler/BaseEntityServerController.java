package controler;

import data.entity.BaseData;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingListener;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Zdenec    .
 * User: Zdenec
 * WWW BASKET TREBIC
 */
public abstract class BaseEntityServerController<T extends BaseData> {
    ObservableCollections.ObservableListHelper<T> listHelper;
    ObservableList<T> list;
    BindingListener bindingListener;

    public BaseEntityServerController() {
        listHelper = ObservableCollections.observableListHelper(new ArrayList<T>());
        list = listHelper.getObservableList();
        bindingListener = new AbstractBindingListener() {
            @Override
            public void synced(Binding binding) {
                onSynced(binding);
            }
        };
    }

    protected abstract T create();

    protected T createNew() {
        T data = create();
        data.setId(UUID.randomUUID());
        return data;
    }

    public ObservableList<T> getList() {
        return list;
    }

    public void setList(ObservableList<T> list) {
        this.list = list;
    }

    public BindingListener getBindingListener() {
        return bindingListener;
    }

    private void onSynced(Binding binding) {
        validate(binding, (T) binding.getSourceObject());
        entityChanged((T) binding.getSourceObject());
    }

    public void entityChanged(T t) {
        listHelper.fireElementChanged(getList().indexOf(t));
        // TODO send data to clients
    }

    protected abstract void validate(Binding binding, T entity);
}
