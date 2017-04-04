package com.camlait.global.erp.delegate.auth;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.camlait.global.erp.dao.auth.GroupRepository;
import com.camlait.global.erp.dao.auth.ResourceRepository;
import com.camlait.global.erp.dao.auth.UserRepository;
import com.camlait.global.erp.delegate.util.encryption.PasswordManager;
import com.camlait.global.erp.domain.auth.Group;
import com.camlait.global.erp.domain.auth.Resource;
import com.camlait.global.erp.domain.auth.User;
import com.camlait.global.erp.domain.exception.DataStorageException;

@Component
@Transactional
public class DefaultUserManager implements UserManager {

    private final UserRepository userRepo;
    private final GroupRepository groupRepo;
    private final ResourceRepository resourceRepository;
    private final PasswordManager encryptor;

    @Autowired
    public DefaultUserManager(UserRepository userRepo, GroupRepository groupRepo, ResourceRepository resourceRepository, PasswordManager encryptor) {
        this.userRepo = userRepo;
        this.groupRepo = groupRepo;
        this.resourceRepository = resourceRepository;
        this.encryptor = encryptor;
    }

    @Override
    public User addUser(final User user) throws DataStorageException {
        user.setEncryptPassword(encryptor.encrypt(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public User updateUser(final User user) throws DataStorageException {
        final User u = retrieveUser(user.getUserId());       
        return userRepo.saveAndFlush(user.merge(u));
    }

    @Override
    public User retrieveUser(final String userCode) throws DataStorageException {
        final User u = userRepo.findOne(userCode);
        return u == null ? null : u.lazyInit();
    }

    @Override
    public Boolean removeUser(final String userCode) throws DataStorageException {
        final User u = retrieveUser(userCode);
        if (u == null) {
            return false;
        }
        userRepo.delete(u);
        return true;
    }

    @Override
    public Page<User> retrieveUsers(final String keyWord, Pageable page) throws DataStorageException {
        return null;
    }

    @Override
    public Page<User> retrieveUsers(Pageable page) throws DataStorageException {
        return userRepo.findAll(page);
    }

    @Override
    public Group addGroup(final Group group) throws DataStorageException {
        return groupRepo.save(group);
    }

    @Override
    public Group updateGroup(final Group group) throws DataStorageException {
        final Group g = retrieveGroup(group.getGroupId());
        return groupRepo.saveAndFlush(group.merge(g));
    }

    @Override
    public Group retrieveGroup(final String groupId) throws DataStorageException {
        final Group g = groupRepo.findOne(groupId);
        return g == null ? null : g.lazyInit();
    }

    @Override
    public Boolean removeGroup(final String groupId) throws DataStorageException {
        final Group g = retrieveGroup(groupId);
        if (g == null) {
            return false;
        }
        groupRepo.delete(g);
        return true;
    }

    @Override
    public Page<Group> retrieveGroups(final String keyWord, Pageable page) throws DataStorageException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Group> retrieveGroups(Pageable page) throws DataStorageException {
        return groupRepo.findAll(page);
    }

    @Override
    public Resource addResource(final Resource resource) throws DataStorageException {
        return resourceRepository.save(resource);
    }

    @Override
    public Resource updateResource(final Resource resource) throws DataStorageException {
        final Resource r = retrieveResource(resource.getResourceId());
        return resourceRepository.saveAndFlush(resource.merge(r));
    }

    @Override
    public Resource retrieveResource(final String ressourceId) throws DataStorageException {
        final Resource r = resourceRepository.findOne(ressourceId);
        return r == null ? null : r.lazyInit();
    }

    @Override
    public Boolean removeResource(final String resourceId) throws DataStorageException {
        final Resource r = retrieveResource(resourceId);
        if (r == null) {
            return false;
        }
        resourceRepository.delete(r);
        return true;
    }

    @Override
    public Page<Resource> retrieveResources(final String keyWord, Pageable page) throws DataStorageException {
        return null;
    }

    @Override
    public Page<Resource> retrieveResources(Pageable page) throws DataStorageException {
        return resourceRepository.findAll(page);
    }
}
