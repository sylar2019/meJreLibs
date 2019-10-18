package me.java.library.common.service;

import me.java.library.common.IDPojo;

import java.io.Serializable;
import java.util.List;

/**
 * File Name             :
 *
 * @author :  sylar
 * @create :  2018/10/6
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) xxx.com   All Rights Reserved
 * *******************************************************************************************
 */
public interface CrudService<DTO extends IDPojo<ID>, ID extends Serializable> extends Serviceable {

    String getNameById(ID id);

    DTO getById(ID id);

    ID add(DTO dto);

    void modify(DTO dto);

    void delete(ID id);

    void batchDelete(List<ID> ids);
}
