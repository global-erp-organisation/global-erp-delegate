package com.camlait.global.erp.delegate.auth;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.camlait.global.erp.dao.auth.GroupeDao;
import com.camlait.global.erp.dao.auth.RessourceDao;
import com.camlait.global.erp.dao.auth.UtilisateurDao;
import com.camlait.global.erp.domain.auth.Group;
import com.camlait.global.erp.domain.auth.Resource;
import com.camlait.global.erp.domain.auth.User;
import com.camlait.global.erp.domain.exception.DataStorageException;

@Component
@Transactional
public class DefaultUserManager implements UserManager {

    private final UtilisateurDao userDao;
    private final GroupeDao groupDao;
    private final RessourceDao ressourceDao;

    @Autowired
    public DefaultUserManager(UtilisateurDao userDao, GroupeDao groupDao, RessourceDao ressourceDao) {
        this.userDao = userDao;
        this.groupDao = groupDao;
        this.ressourceDao = ressourceDao;
    }

    @Override
    public User addUser(final User user) throws DataStorageException {
        return userDao.save(user);
    }

    @Override
    public User updateUser(final User user) throws DataStorageException {
        final User u = retrieveUser(user.getUserId());
        return userDao.saveAndFlush(user.merge(u));
    }

    @Override
    public User retrieveUser(final String userCode) throws DataStorageException {
        final User u = userDao.findOne(userCode);
        if (u == null) {
            throw new DataStorageException("The user you are trying to retrieve does not exist.");
        }
        return u.lazyInit();
    }

    @Override
    public Boolean removeUser(final String userCode) throws DataStorageException {
        final User u = retrieveUser(userCode);
        userDao.delete(u);
        return true;
    }

    @Override
    public Page<User> retrieveUsers(final String keyWord, Pageable page) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<User> retrieveUsers(Pageable page) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Group addGroup(final Group group) throws DataStorageException {
        return groupDao.save(group);
    }

    @Override
    public Group updateGroup(final Group group) throws DataStorageException {
        final Group g = retrieveGroup(group.getGroupId());
        return groupDao.saveAndFlush(group.merge(g));
    }

    @Override
    public Group retrieveGroup(final String groupId) throws DataStorageException {
        final Group g = groupDao.findOne(groupId);
        if (g == null) {
            throw new DataStorageException("The group you are trying to retrieve does not exist.");
        }
        return g.lazyInit();
    }

    @Override
    public Boolean removeGroup(final String groupId) throws DataStorageException {
        final Group g = retrieveGroup(groupId);
        groupDao.delete(g);
        return true;
    }

    @Override
    public Page<User> retrieveGroups(final String keyWord, Pageable page) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<User> retrieveGroups(Pageable page) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Resource addResource(final Resource resource) throws DataStorageException {
        return ressourceDao.save(resource);
    }

    @Override
    public Resource updateResource(final Resource resource) throws DataStorageException {
        final Resource r = retrieveResource(resource.getResourceId());
        return ressourceDao.saveAndFlush(resource.merge(r));
    }

    @Override
    public Resource retrieveResource(final String ressourceId) throws DataStorageException {
        final Resource r = ressourceDao.findOne(ressourceId);
        if (r == null) {
            throw new DataStorageException("The resource that you are trying to retrieve does not exist.");
        }
        return r.lazyInit();
    }

    @Override
    public Boolean removeResource(final String resourceId) throws DataStorageException {
        final Resource r = retrieveResource(resourceId);
        ressourceDao.delete(r);
        return true;
    }

    @Override
    public Page<Resource> retrieveResources(final String keyWord, Pageable page) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Resource> retrieveResources(Pageable page) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

}
