package com.company.blog.service.admin;

import com.company.blog.dao.admin.AdminTypeDao;
import com.company.blog.model.entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("TypeService")
public class AdminTypeService {

    @Autowired
    private AdminTypeDao adminTypeDao;

    @Transactional
    public void addType(Type type)  {
        adminTypeDao.addType(type);
    }

    @Transactional
    public Type findTypeById(Long id){
        return adminTypeDao.findTypeById(id);
    }

    @Transactional
    public Type findTypeByName(String name){
        return adminTypeDao.findTypeByName(name);
    }

    @Transactional
    public List<Type> findAllType(){
        List<Type> types = adminTypeDao.findAllType();
        return types;
    }

    @Transactional
    public void updateType(Long id, String name){
        adminTypeDao.updateType(id, name);
    }

    @Transactional
    public void deleteType(Long id){
        adminTypeDao.deleteType(id);
        adminTypeDao.file1();
        adminTypeDao.file2();
        adminTypeDao.file3();
    }

}
