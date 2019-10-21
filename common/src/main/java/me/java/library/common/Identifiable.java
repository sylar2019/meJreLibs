package me.java.library.common;

import java.io.Serializable;

/**
 * File Name             :  Identifiable
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
public interface Identifiable<ID> extends Serializable, Comparable<Identifiable<ID>> {
    ID getId();
}
