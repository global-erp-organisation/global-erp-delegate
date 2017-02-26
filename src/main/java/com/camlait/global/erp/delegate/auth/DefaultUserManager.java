package com.camlait.global.erp.delegate.auth;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.camlait.global.erp.dao.auth.GroupeDao;
import com.camlait.global.erp.dao.auth.RessourceDao;
import com.camlait.global.erp.dao.auth.UtilisateurDao;
import com.camlait.global.erp.domain.auth.Groupe;
import com.camlait.global.erp.domain.auth.Ressource;
import com.camlait.global.erp.domain.auth.Utilisateur;
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
    public Utilisateur addUser(final Utilisateur user) throws DataStorageException {
        return userDao.save(user);
    }

    @Override
    public Utilisateur updateUser(final Utilisateur user) throws DataStorageException {
        final Utilisateur u = retrieveUser(user.getUtilisateurId());
        return userDao.saveAndFlush(user.merge(u));
    }

    @Override
    public Utilisateur retrieveUser(final String userCode) throws DataStorageException {
        final Utilisateur u = userDao.findOne(userCode);
        if (u == null) {
            throw new DataStorageException("The user you are trying to retrieve does not exist.");
        }
        return u.lazyInit();
    }

    @Override
    public Boolean removeUser(final String userCode) throws DataStorageException {
        final Utilisateur u = retrieveUser(userCode);
        userDao.delete(u);
        return true;
    }

    @Override
    public Page<Utilisateur> retrieveUsers(final String keyWord, Pageable page) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Utilisateur> retrieveUsers(Pageable page) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Groupe addGroup(final Groupe group) throws DataStorageException {
        return groupDao.save(group);
    }

    @Override
    public Groupe updateGroup(final Groupe group) throws DataStorageException {
        final Groupe g = retrieveGroup(group.getGroupeId());
        return groupDao.saveAndFlush(group.merge(g));
    }

    @Override
    public Groupe retrieveGroup(final String groupId) throws DataStorageException {
        final Groupe g = groupDao.findOne(groupId);
        if (g == null) {
            throw new DataStorageException("The group you are trying to retrieve does not exist.");
        }
        return g.lazyInit();
    }

    @Override
    public Boolean removeGroup(final String groupId) throws DataStorageException {
        final Groupe g = retrieveGroup(groupId);
        groupDao.delete(g);
        return true;
    }

    @Override
    public Page<Utilisateur> retrieveGroups(final String keyWord, Pageable page) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Utilisateur> retrieveGroups(Pageable page) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Ressource addResource(final Ressource resource) throws DataStorageException {
        return ressourceDao.save(resource);
    }

    @Override
    public Ressource updateResource(final Ressource resource) throws DataStorageException {
        final Ressource r = retrieveResource(resource.getRessourceId());
        return ressourceDao.saveAndFlush(resource.merge(r));
    }

    @Override
    public Ressource retrieveResource(final String ressourceId) throws DataStorageException {
        final Ressource r = ressourceDao.findOne(ressourceId);
        if (r == null) {
            throw new DataStorageException("The resource that you are trying to retrieve does not exist.");
        }
        return r.lazyInit();
    }

    @Override
    public Boolean removeResource(final String resourceId) throws DataStorageException {
        final Ressource r = retrieveResource(resourceId);
        ressourceDao.delete(r);
        return true;
    }

    @Override
    public Page<Ressource> retrieveResources(final String keyWord, Pageable page) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Ressource> retrieveResources(Pageable page) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

}
