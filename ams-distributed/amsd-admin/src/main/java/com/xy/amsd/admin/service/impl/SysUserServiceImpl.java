package com.xy.amsd.admin.service.impl;

import com.xy.amsd.admin.constant.SysConstants;
import com.xy.amsd.admin.dao.SysUserMapper;
import com.xy.amsd.admin.dao.SysUserRoleMapper;
import com.xy.amsd.admin.model.SysMenu;
import com.xy.amsd.admin.model.SysUser;
import com.xy.amsd.admin.model.SysUserRole;
import com.xy.amsd.admin.service.SysMenuService;
import com.xy.amsd.admin.service.SysUserService;
import com.xy.amsd.common.utils.PoiUtils;
import com.xy.amsd.core.page.MyBatisPageHelper;
import com.xy.amsd.core.page.PageRequest;
import com.xy.amsd.core.page.PageResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    SysMenuService sysMenuService;

    @Override
    public List<SysUser> findAll() {
        return sysUserMapper.findAll();
    }

    @Override
    public SysUser findByName(String name) {
        SysUser user = sysUserMapper.findByName(name);
        System.out.println(user);
        return user;
    }

    @Transactional
    @Override
    public int save(SysUser record) {
        // ↓-------------------在sys_user表中更新user信息-------------------↓
        Long userId = null;
        if (record.getId() == null || record.getId() == 0) {
            sysUserMapper.insertSelective(record);
            userId = record.getId();    // 获取自增ID
        } else {
            sysUserMapper.updateByPrimaryKeySelective(record);
        }
        // ↓--------------在sys_user_role表中更新user_role信息--------------↓
        // 由前端返回的，用户的角色列表
        List<SysUserRole> userRoles = record.getUserRoles();
        // 如果userRoles列表不为空，就将该角色原有的UserRole对应关系删除，再插入新的对应关系
        if (!userRoles.isEmpty()) {
            // TODO 在前端逻辑中修改：如果对用户进行编辑后后未改动用户的角色，就应传回空的userRoles
            // TODO 一个user可能对应多个角色？没错！
            for (SysUserRole sysUserRole : userRoles) {
                sysUserRole.setUserId(userId);
            }
            sysUserRoleMapper.deleteByUserId(userId);
            for (SysUserRole sysUserRole : userRoles) {
                sysUserRoleMapper.insertSelective(sysUserRole);
            }
        }
        return 1;
    }

    @Override
    public int delete(SysUser record) {
        return sysUserMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysUser> records) {
        for (SysUser record : records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public SysUser findById(Long id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public File createUserExcelFile(PageRequest pageRequest) {
        PageResult pageResult = findPage(pageRequest);
        return createUserExcelFile(pageResult.getContent());
    }

    @Override
    public List<SysUserRole> findUserRolesByUserId(Long userId) {
        return sysUserRoleMapper.findByUserId(userId);
    }

    @Override
    public Set<String> findUserPermissions(String userName) {
        Set<String>  permissions = new HashSet<>();
        List<SysMenu> sysMenus = sysMenuService.findByUserName(userName);
        for (SysMenu sysMenu : sysMenus) {
            if (sysMenu.getPerms() != null && !"".equals(sysMenu.getPerms())) {
                permissions.add(sysMenu.getPerms());
            }
        }
        return permissions;
    }

    private static File createUserExcelFile(List<?> records) {
        if (records == null) records = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row titleRow = sheet.createRow(0);
        String [] titles = SysConstants.UserCellValues;
        titleRow.createCell(0).setCellValue("No.");
        for (int i = 0; i < titles.length; i++) {
            titleRow.createCell(i+1).setCellValue(titles[i]);
        }
        for (int i = 0; i < records.size(); i++) {
            SysUser user = (SysUser) records.get(i);
            Row row = sheet.createRow(i+1);
            setUserExcelRow(row, user);
        }

        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            System.out.println();
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                Cell cell = row.getCell(j);
                System.out.print(cell + "\t\t");
            }
        }


        File file = null;
        try{
            file = PoiUtils.createExcelFile(workbook, "user_table");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private static void setUserExcelRow(Row row, SysUser user) {
        int colIndex = 0;
        //System.out.println("row num: "+row.getRowNum());
        //System.out.println(user);
        row.createCell(colIndex++).setCellValue(row.getRowNum()); // TODO
        row.createCell(colIndex++).setCellValue(user.getId());
        row.createCell(colIndex++).setCellValue(user.getName());
        row.createCell(colIndex++).setCellValue(user.getNickName());
        row.createCell(colIndex++).setCellValue(user.getDeptName());
        row.createCell(colIndex++).setCellValue(user.getRoleName());
        row.createCell(colIndex++).setCellValue(user.getEmail());
        row.createCell(colIndex++).setCellValue(user.getStatus());
        row.createCell(colIndex++).setCellValue(user.getAvatar());
        row.createCell(colIndex++).setCellValue(user.getCreateBy());
        row.createCell(colIndex++).setCellValue(user.getCreateTime());
        row.createCell(colIndex++).setCellValue(user.getLastUpdateBy());
        row.createCell(colIndex++).setCellValue(user.getLastUpdateTime());
    }


    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MyBatisPageHelper.findPage(pageRequest, sysUserMapper);
    }
}
