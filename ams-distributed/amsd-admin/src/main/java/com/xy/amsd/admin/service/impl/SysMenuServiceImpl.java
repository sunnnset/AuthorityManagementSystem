package com.xy.amsd.admin.service.impl;

import com.xy.amsd.admin.constant.SysConstants;
import com.xy.amsd.admin.dao.SysMenuMapper;
import com.xy.amsd.admin.model.SysMenu;
import com.xy.amsd.admin.service.SysMenuService;
import com.xy.amsd.core.page.MyBatisPageHelper;
import com.xy.amsd.core.page.PageRequest;
import com.xy.amsd.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> findTree(String userName, int menuType) {
        List<SysMenu> parentMenus = new ArrayList<>();
        List<SysMenu> menus = findByUserName(userName);
        for (SysMenu menu : menus) {
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                menu.setMenuLevel(0);
                parentMenus.add(menu);
            }
        }
        parentMenus.sort(Comparator.comparing(SysMenu::getOrderNum));
        findChildMenu(parentMenus, menus, menuType);
        return parentMenus;
    }

    /**
     * 用于辅助递归查询子菜单
     * @param parentMenus 父菜单的列表
     * @param menus 所有菜单的列表
     * @param menuType 查询菜单的种类
     */
    private void findChildMenu(List<SysMenu> parentMenus, List<SysMenu> menus, int menuType) {
        for (SysMenu parentMenu : parentMenus) {
            List<SysMenu> childMenus = new ArrayList<>();
            for (SysMenu menu : menus) {
                if (menuType == 1 && menu.getType() == 2) continue;
                if (parentMenu.getId() != null && parentMenu.getId().equals(menu.getParentId())) {
                    menu.setParentName(parentMenu.getName());
                    menu.setMenuLevel(parentMenu.getMenuLevel() + 1);
                    childMenus.add(menu);
                }
            }
            childMenus.sort(Comparator.comparing(SysMenu::getOrderNum));
            parentMenu.setChildMenus(childMenus);
            // 递归查找并设置子菜单的子菜单
            findChildMenu(childMenus, menus, menuType);

        }
    }

    @Override
    public List<SysMenu> findByUserName(String userName) {
        // 有必要吗？
        if (SysConstants.ADMIN.equals(userName)) return findAll();
        return sysMenuMapper.findByUserName(userName);
    }

    @Override
    public List<SysMenu> findAll() {
        return sysMenuMapper.findAll();
    }

    @Override
    public int save(SysMenu record) {
        if (record.getId() == null || record.getId() == 0) {
            return sysMenuMapper.insertSelective(record);
        } else {
            // 如果sysMenu的parentId为空，设置其为0
            if (record.getParentId() == null) record.setParentId(0L);
            return sysMenuMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public int delete(SysMenu record) {
        return sysMenuMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysMenu> records) {
        for (SysMenu record : records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public SysMenu findById(Long id) {
        return sysMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MyBatisPageHelper.findPage(pageRequest, sysMenuMapper);
    }
}
