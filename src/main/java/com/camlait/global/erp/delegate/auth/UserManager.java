package com.camlait.global.erp.delegate.auth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.auth.Group;
import com.camlait.global.erp.domain.auth.Resource;
import com.camlait.global.erp.domain.auth.User;
import com.camlait.global.erp.domain.exception.DataStorageException;

import lombok.NonNull;

/**
 * This interface provides all operations that belong to user management.
 * 
 * @author Martin Blaise Signe
 */
public interface UserManager {
    /**
     * Add the provided user to the data storage.
     * 
     * @param user User to store.
     * @return The user that have been added.
     * @throws DataStorageException
     */
    User addUser(@NonNull User user) throws DataStorageException;

    /**
     * Update the provided user informations in the data storage.
     * 
     * @param user User to be updated.
     * @return The user that have been updated.
     * @throws DataStorageException
     */
    User updateUser(@NonNull User user) throws DataStorageException;

    /**
     * Retrieve a user informations from the data storage based on the given
     * user code.
     * 
     * @param userCode Provided user code.
     * @return The user that belongs to the given user code.
     * @throws DataStorageException
     */
    User retrieveUser(@NonNull String userCode) throws DataStorageException;

    /**
     * Permanently delete from the data storage a user based on the provided
     * user code.
     * 
     * @param userCode provided user code.
     * @return a boolean that indicates if the delete operation where
     *         successfully perform or not
     * @throws DataStorageException
     */
    Boolean removeUser(@NonNull String userCode) throws DataStorageException;

    /**
     * Retrieve user from the data storage based on the provided key word.
     * 
     * @param keyWord Provided key word.
     * @param page Pageable object that indicated how many record need to be extracted per page.
     * @return A page of user that belongs to the given key word.
     * @throws DataStorageException
     */
    Page<User> retrieveUsers(@NonNull String keyWord, Pageable page) throws DataStorageException;

    /**
     * Retrieves all user from the data storage.
     * 
     * @param page Pageable object that indicated how many record need to be extracted per page.
     * @return
     * @throws DataStorageException
     */
    Page<User> retrieveUsers(Pageable page) throws DataStorageException;

    /**
     * Add the provided user group to the data storage.
     * 
     * @param group User group to store.
     * @return
     * @throws DataStorageException
     */
    Group addGroup(@NonNull Group group) throws DataStorageException;

    /**
     * Update the provided user group information in the data storage.
     * 
     * @param group User group to be updated.
     * @return The updated user group.
     * @throws DataStorageException
     */
    Group updateGroup(@NonNull Group group) throws DataStorageException;

    /**
     * Retrieves a user group from the data storage based on the given user
     * groupId.
     * 
     * @param groupId provided user groupId.
     * @return The user group that belongs to the provided groupId.
     * @throws DataStorageException
     */
    Group retrieveGroup(@NonNull String groupId) throws DataStorageException;

    /**
     * Permanently delete a user group in the data storage.
     * 
     * @param groupId User groupId of the group that need to be removed.
     * @return A boolean that indicates if the delete operation where
     *         successfully perform or not.
     * @throws DataStorageException
     */
    Boolean removeGroup(@NonNull String groupId) throws DataStorageException;

    /**
     * Retrieve user groups from the data storage based on the provided key
     * word.
     * 
     * @param keyWord Provided key word.
     * @param page Pageable object that indicated how many record need to be extracted per page.
     * @return A page of user group that belongs to the given key word.
     * @throws DataStorageException
     */
    Page<Group> retrieveGroups(@NonNull String keyWord, Pageable page) throws DataStorageException;

    /**
     * Retrieves all user groups from the data storage.
     * 
     * @param page Pageable object that indicated how many record need to beextracted per page.
     * @return
     * @throws DataStorageException
     */
    Page<Group> retrieveGroups(Pageable page) throws DataStorageException;

    /**
     * Add the provided resource to the data storage.
     * 
     * @param resource Resource to store.
     * @return
     * @throws DataStorageException
     */
    Resource addResource(@NonNull Resource resource) throws DataStorageException;

    /**
     * Update the provided resource information in the data storage.
     * 
     * @param resource Resource to be updated.
     * @return The updated resource.
     * @throws DataStorageException
     */
    Resource updateResource(@NonNull Resource resource) throws DataStorageException;

    /**
     * Retrieves a resource from the data storage based on the given ressourceId
     * 
     * @param resourceId provided resourceId.
     * @return The resource that belongs to the provided resourceId.
     * @throws DataStorageException
     */
    Resource retrieveResource(@NonNull String ressourceId) throws DataStorageException;

    /**
     * Permanently delete a resource in the data storage.
     * 
     * @param resourceId resourceId of the resource that need to be removed.
     * @return A boolean that indicates if the delete operation where
     *         successfully perform or not.
     * @throws DataStorageException
     */
    Boolean removeResource(@NonNull String resourceId) throws DataStorageException;

    /**
     * Retrieve resources from the data storage based on the provided key word.
     * 
     * @param keyWord Provided key word.
     * @param page
     *            Pageable object that indicated how many record need to be
     *            extracted per page.
     * @return A page of resources that belongs to the given key word.
     * @throws DataStorageException
     */
    Page<Resource> retrieveResources(@NonNull String keyWord, Pageable page) throws DataStorageException;

    /**
     * Retrieves all resources from the data storage.
     * 
     * @param page Pageable object that indicated how many record need to be extracted per page.
     * @return
     * @throws DataStorageException
     */
    Page<Resource> retrieveResources(Pageable page) throws DataStorageException;
}
