package ${package}.service;

import ${package}.entity.${fixName};
import java.io.Serializable;

/**
 * @author ${author}
 */
public interface ${fixName}Service extends Serializable{
	public void insert(${fixName} ${fixName?uncap_first});
	public void update(${fixName} ${fixName?uncap_first});
	public void delete(${fixName} ${fixName?uncap_first});
	public ${fixName} detail(${fixName} ${fixName?uncap_first});
}
