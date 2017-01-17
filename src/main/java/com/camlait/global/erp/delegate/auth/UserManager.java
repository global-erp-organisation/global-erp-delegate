package com.camlait.global.erp.delegate.auth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.delegate.exception.DataStorageExcetion;
import com.camlait.global.erp.domain.auth.Groupe;
import com.camlait.global.erp.domain.auth.Utilisateur;

import lombok.NonNull;

/**
 * This interface provides all operations that belong to user management.
 * 
 * @author Martin Blaise Signe
 *
 */
public interface UserManager {
	/**
	 * Add the provided user to the data storage.
	 * 
	 * @param user
	 *            User to store.
	 * 
	 * @return The user that have been added.
	 * @throws DataStorageExcetion
	 */
	Utilisateur addUser(@NonNull Utilisateur user) throws DataStorageExcetion;

	/**
	 * Update the provided user informations in the data storage.
	 * 
	 * @param user
	 *            User to be updated.
	 * @return The user that have been updated.
	 * @throws DataStorageExcetion
	 */
	Utilisateur updateUser(@NonNull Utilisateur user) throws DataStorageExcetion;

	/**
	 * Retrieve a user informations from the data storage based on the given
	 * user code.
	 * 
	 * @param userCode
	 *            Provided user code.
	 * @return The user that belongs to the given user code.
	 * @throws DataStorageExcetion
	 */
	Utilisateur retrieveUser(@NonNull String userCode) throws DataStorageExcetion;

	/**
	 * Permanently delete from the data storage a user based on the provided
	 * user code.
	 * 
	 * @param userCode
	 *            provided user code.
	 * @return a boolean that indicates if the delete operation where
	 *         successfully perform or not
	 * @throws DataStorageExcetion
	 */
	Boolean removeUser(@NonNull String userCode) throws DataStorageExcetion;

	/**
	 * Retrieve user from the data storage based on the provided key word.
	 * 
	 * @param keyWord
	 *            Provided key word.
	 * @param page
	 *            Pageable object that indicated how many record need to be
	 *            extracted per page.
	 * @return A page of user that belongs to the given key word.
	 * @throws DataStorageExcetion
	 */
	Page<Utilisateur> retrieveUsers(String keyWord, Pageable page) throws DataStorageExcetion;

	/**
	 * Retrieves all user from the data storage.
	 * 
	 * @param page
	 *            Pageable object that indicated how many record need to be
	 *            extracted per page.
	 * @return
	 * @throws DataStorageExcetion
	 */
	Page<Utilisateur> retrieveUsers(Pageable page) throws DataStorageExcetion;

	/**
	 * Add the provided user group to the data storage.
	 * 
	 * @param group
	 *            User group to store.
	 * @return
	 * @throws DataStorageExcetion
	 */
	Groupe addGroup(Groupe group) throws DataStorageExcetion;

	/**
	 * Update the provided user group information in the data storage.
	 * 
	 * @param group
	 *            User group to be updated.
	 * @return The updated user group.
	 * @throws DataStorageExcetion
	 */
	Groupe updateGroup(Groupe group) throws DataStorageExcetion;

	/**
	 * Retrieves a user group from the data storage based on the given user
	 * groupId.
	 * 
	 * @param groupId
	 *            provided user groupId.
	 * @return The user group that belongs to the provided groupId.
	 * @throws DataStorageExcetion
	 */
	Groupe retrieveGroup(String groupId) throws DataStorageExcetion;

	/**
	 * Permanently delete a user group in the data storage.
	 * 
	 * @param groupId
	 *            User groupId of the group that need to be removed.
	 * @return A boolean that indicates if the delete operation where successfully perform or not.
	 * @throws DataStorageExcetion
	 */
	Boolean removeGroup(String groupId) throws DataStorageExcetion;
	
	/**
	 * Retrieve user groups from the data storage based on the provided key word.
	 * 
	 * @param keyWord
	 *            Provided key word.
	 * @param page
	 *            Pageable object that indicated how many record need to be
	 *            extracted per page.
	 * @return A page of user group that belongs to the given key word.
	 * @throws DataStorageExcetion
	 */
	Page<Utilisateur> retrieveGroups(String keyWord, Pageable page) throws DataStorageExcetion;

	/**
	 * Retrieves all user groups from the data storage.
	 * 
	 * @param page
	 *            Pageable object that indicated how many record need to be
	 *            extracted per page.
	 * @return
	 * @throws DataStorageExcetion
	 */
	Page<Utilisateur> retrieveGroups(Pageable page) throws DataStorageExcetion;


}
