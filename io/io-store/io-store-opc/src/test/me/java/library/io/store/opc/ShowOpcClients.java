package me.java.library.io.store.opc;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.dcom.list.ClassDetails;
import org.openscada.opc.lib.list.Categories;
import org.openscada.opc.lib.list.Category;
import org.openscada.opc.lib.list.ServerList;

import java.net.UnknownHostException;
import java.util.Collection;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.opc.DemoTest
 * @createDate : 2020/7/24
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class ShowOpcClients {


    /**
     * 列举某Server下的所有OPC连接
     *
     * @throws JIException
     * @throws UnknownHostException
     */
    public static void main(String[] args) throws JIException, UnknownHostException {
        ServerList serverList = new ServerList("10.1.5.123", "freud",
                "password", "");

        Collection<ClassDetails> classDetails = serverList
                .listServersWithDetails(new Category[]{
                        Categories.OPCDAServer10, Categories.OPCDAServer20,
                        Categories.OPCDAServer30}, new Category[]{});

        for (ClassDetails cds : classDetails) {
            System.out.println(cds.getProgId() + "=" + cds.getDescription());
        }
    }


}