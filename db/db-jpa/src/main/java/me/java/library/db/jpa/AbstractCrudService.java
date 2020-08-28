package me.java.library.db.jpa;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import me.java.library.common.Identifiable;
import me.java.library.common.Namable;
import me.java.library.common.model.pojo.IdName;
import me.java.library.common.service.CrudService;
import me.java.library.db.jpa.annotation.ReadTransactional;
import me.java.library.db.jpa.annotation.WriteTransactional;
import me.java.library.utils.base.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * File Name             :  AbstractCrudService
 *
 * @author :  sylar
 * @create :  2018/10/7
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
@DependsOn("foreignKeyService")
public abstract class AbstractCrudService<
        PO extends Identifiable<ID>,
        DTO extends Identifiable<ID>,
        ID extends Serializable>

        implements CrudService<DTO, ID> {

    protected Class<PO> classPO;
    protected Class<DTO> classDTO;
    protected Class<ID> classID;

    @Autowired
    protected ForeignKeyService foreignKeyService;

    @SuppressWarnings("unchecked")
    public AbstractCrudService() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();

        this.classPO = (Class<PO>) type.getActualTypeArguments()[0];
        this.classDTO = (Class<DTO>) type.getActualTypeArguments()[1];
        this.classID = (Class<ID>) type.getActualTypeArguments()[2];
    }

    @PostConstruct
    private void onInit() {
        registeForeignKeys();
    }

    /**
     * CustomJpaRepository
     *
     * @return
     */
    protected abstract CustomJpaRepository<PO, ID> getRepository();

    /**
     * 注册所有外键关系，在主表记录删除时检查能否被删除
     * eg: registeForeignKey(Student.class, "xxx_id");
     * eg: registeForeignKey(Teacher.class, "xxx_id");
     */
    protected abstract void registeForeignKeys();


    @ReadTransactional
    @Override
    public String getNameById(ID id) {
        Preconditions.checkNotNull(id, "id is null");
        checkExisted(id);
        if (IdName.class.isAssignableFrom(classPO)) {
            return ((IdName) getRepository().getOne(id)).getName();
        }
        return null;
    }

    @ReadTransactional
    @Override
    public DTO getById(ID id) {
        checkExisted(id);
        return poToDto(getRepository().getOne(id));
    }

    @WriteTransactional
    @Override
    public ID add(DTO dto) {
        checkOnAdd(dto);
        PO po = onAdd(dto);
        afterAdd(po.getId(), dto);
        return po.getId();
    }

    protected void checkOnAdd(DTO dto) {
        checkNullDto(dto);
        checkRequiredProperty(dto);
    }

    protected PO onAdd(DTO dto) {
        PO po = dtoToPo(dto);
        return getRepository().saveAndFlush(po);
    }

    protected void afterAdd(ID id, DTO dto) {

    }

    @WriteTransactional
    @Override
    public void modify(DTO dto) {
        checkOnModify(dto);
        onModify(dto);
        afterModify(dto);
    }

    protected void checkOnModify(DTO dto) {
        checkNullDto(dto);
        checkExisted(dto.getId());
        checkRequiredProperty(dto);
    }

    protected PO onModify(DTO dto) {
        PO po = dtoToPo(dto);
        return getRepository().saveAndFlush(po);
    }

    protected void afterModify(DTO dto) {

    }

    @WriteTransactional
    @Override
    public void delete(ID id) {
        checkOnDelete(id);
        onDelete(id);
        afterDelete(id);
        checkForeignKeysWhenDelete(id);
    }

    protected void checkOnDelete(ID id) {
        checkExisted(id);
    }

    protected void onDelete(ID id) {
        getRepository().deleteById(id);
    }

    protected void afterDelete(ID id) {
    }

    @WriteTransactional
    @Override
    public void batchDelete(List<ID> ids) {
        if (ids == null || ids.size() == 0) {
            return;
        }

        for (ID id : ids) {
            delete(id);
        }
    }

    protected DTO poToDto(PO po) {
        return JsonUtils.copyProperties(po, classDTO);
    }

    protected PO dtoToPo(DTO dto) {
        return JsonUtils.copyProperties(dto, classPO);
    }

    /**
     * 新增或修改时，检查必填的属性
     *
     * @param dto
     */
    protected void checkRequiredProperty(DTO dto) {
        if (dto instanceof Namable) {
            Namable namePojo = (Namable) dto;
            Preconditions.checkState(!Strings.isNullOrEmpty(namePojo.getName()), "名称不可为空");
        }
    }

    /**
     * 检查记录是否存在
     *
     * @param id
     */
    protected void checkExisted(ID id) {
        checkNullId(id);
        Preconditions.checkState(getRepository().existsById(id), "not found id:" + id);
    }

    protected void checkNullId(ID id) {
        Preconditions.checkNotNull(id, "id is null");
    }

    protected void checkNullDto(DTO dto) {
        Preconditions.checkNotNull(dto, "dto is null");
    }

    /**
     * 删除记录时，检查id是否是其它表的外键
     *
     * @param id
     */
    protected void checkForeignKeysWhenDelete(ID id) {
        foreignKeyService.checkOnDeletePrimaryEntity(classPO, id);
    }

    /**
     * 注册外键关系
     *
     * @param slaveEntityClass 从表实体类型
     * @param foreignKeyName   从表的外键字段名（非属性名）
     */
    protected void registeForeignKey(Class<?> slaveEntityClass, String foreignKeyName) {
        foreignKeyService.registeForeignKey(classPO, slaveEntityClass, foreignKeyName);
    }

}
