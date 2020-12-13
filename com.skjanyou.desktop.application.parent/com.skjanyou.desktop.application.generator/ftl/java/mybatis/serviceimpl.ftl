package ${package}.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${package}.entity.${fixName};
import ${package}.mapper.${fixName}Mapper;

import ${package}.service.${fixName}Service;

/**
 * @author ${author}
 */
@Service("${fixName?uncap_first}Service")
@Transactional
public class ${fixName}ServiceImpl implements ${fixName}Service{
	private static final long serialVersionUID = "${fixName}Service".hashCode();
	@Resource
	private ${fixName}Mapper ${fixName?uncap_first}Mapper;
	
	public void insert(${fixName} ${fixName?uncap_first}){
		${fixName} t = ${fixName?uncap_first}Mapper.detail(${fixName?uncap_first});
		if(t != null){
			throw new RuntimeException("t != null");
		}
		${fixName?uncap_first}Mapper.insert(${fixName?uncap_first});
	};
	
	public void update(${fixName} ${fixName?uncap_first}){
		${fixName} t = ${fixName?uncap_first}Mapper.detail(${fixName?uncap_first});
		if(t == null){
			throw new RuntimeException("t == null");
		}
		${fixName?uncap_first}Mapper.update(${fixName?uncap_first});
	};
	
	public void delete(${fixName} ${fixName?uncap_first}){
		${fixName} t = ${fixName?uncap_first}Mapper.detail(${fixName?uncap_first});
		if(t == null){
			throw new RuntimeException("t == null");
		}
		${fixName?uncap_first}Mapper.delete(${fixName?uncap_first});
	};
	
	public ${fixName} detail(${fixName} ${fixName?uncap_first}){
		return ${fixName?uncap_first}Mapper.detail(${fixName?uncap_first});
	};
}
