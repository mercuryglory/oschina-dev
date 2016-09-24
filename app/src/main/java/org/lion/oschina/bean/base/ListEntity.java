package org.lion.oschina.bean.base;

import org.lion.oschina.bean.Entity;

import java.io.Serializable;
import java.util.List;

public interface ListEntity<T extends Entity> extends Serializable {

    public List<T> getList();
}
