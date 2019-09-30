package me.java.library.common;

import java.io.Serializable;

/**
 * File Name             :  IDPojo
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
public interface IDPojo<ID> extends Serializable {
    ID getId();
}
