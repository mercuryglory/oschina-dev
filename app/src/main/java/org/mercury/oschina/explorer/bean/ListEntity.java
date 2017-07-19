package org.mercury.oschina.explorer.bean;

import java.io.Serializable;
import java.util.List;

public interface ListEntity<T extends Entity> extends Serializable {
    List<T> getList();
}
