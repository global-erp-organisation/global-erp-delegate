package com.camlait.global.erp.delegate.auth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.auth.user.Groupe;
import com.camlait.global.erp.domain.auth.user.Ressource;
import com.camlait.global.erp.domain.auth.user.RessourceGroupe;
import com.camlait.global.erp.domain.auth.user.RessourceUtilisateur;
import com.camlait.global.erp.domain.auth.user.Utilisateur;
import com.camlait.global.erp.domain.exception.DataStorageExcetion;

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
	Groupe addGroup(@NonNull Groupe group) throws DataStorageExcetion;

	/**
	 * Update the provided user group information in the data storage.
	 * 
	 * @param group
	 *            User group to be updated.
	 * @return The updated user group.
	 * @throws DataStorageExcetion
	 */
	Groupe updateGroup(@NonNull Groupe group) throws DataStorageExcetion;

	/**
	 * Retrieves a user group from the data storage based on the given user
	 * groupId.
	 * 
	 * @param groupId
	 *            provided user groupId.
	 * @return The user group that belongs to the provided groupId.
	 * @throws DataStorageExcetion
	 */
	Groupe retrieveGroup(@NonNull String groupId) throws DataStorageExcetion;

	/**
	 * Permanently delete a user group in the data storage.
	 * 
	 * @param groupId
	 *            User groupId of the group that need to be removed.
	 * @return A boolean that indicates if the delete operation where
	 *         successfully perform or not.
	 * @throws DataStorageExcetion
	 */
	Boolean removeGroup(@NonNull String groupId) throws DataStorageExcetion;

	/**
	 * Retrieve user groups from the data storage based on the provided key
	 * word.
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

	/**
	 * Add the provided resource to the data storage.
	 * 
	 * @param resource
	 *            Resource to store.
	 * @return
	 * @throws DataStorageExcetion
	 */
	Ressource addResource(@NonNull Ressource resource) throws DataStorageExcetion;

	/**
	 * Update the provided resource information in the data storage.
	 * 
	 * @param resource
	 *            Resource to be updated.
	 * @return The updated resource.
	 * @throws DataStorageExcetion
	 */
	Ressource updateResource(@NonNull Ressource resource) throws DataStorageExcetion;

	/**
	 * Retrieves a resource from the data storage based on the given ressourceId
	 * 
	 * @param resourceId
	 *            provided resourceId.
	 * @return The resource that belongs to the provided resourceId.
	 * @throws DataStorageExcetion
	 */
	Ressource retrieveResource(@NonNull String ressourceId) throws DataStorageExcetion;

	/**
	 * Permanently delete a resource in the data storage.
	 * 
	 * @param resourceId
	 *            resourceId of the resource that need to be removed.
	 * @return A boolean that indicates if the delete operation where
	 *         successfully perform or not.
	 * @throws DataStorageExcetion
	 */
	Boolean removeResource(@NonNull String resourceId) throws DataStorageExcetion;

	/**
	 * Retrieve resources from the data storage based on the provided key word.
	 * 
	 * @param keyWord
	 *            Provided key word.
	 * @param page
	 *            Pageable object that indicated how many record need to be
	 *            extracted per page.
	 * @return A page of resources that belongs to the given key word.
	 * @throws DataStorageExcetion
	 */
	Page<Ressource> retrieveResources(String keyWord, Pageable page) throws DataStorageExcetion;

	/**
	 * Retrieves all resources from the data storage.
	 * 
	 * @param page
	 *            Pageable object that indicated how many record need to be
	 *            extracted per page.
	 * @return
	 * @throws DataStorageExcetion
	 */
	Page<Ressource> retrieveResources(Pageable page) throws DataStorageExcetion;

	/**
	 * Add the provided resource user to the data storage.
	 * 
	 * @param ru
	 *            Resource user to store.
	 * @return
	 * @throws DataStorageExcetion
	 */
	RessourceUtilisateur addResourceUser(@NonNull RessourceUtilisateur ru) throws DataStorageExcetion;

	/**
	 * Update the provided resource user information in the data storage.
	 * 
	 * @param ru
	 *            resource user to be updated.
	 * @return The updated resource user.
	 * @throws DataStorageExcetion
	 */
	RessourceUtilisateur updateResourceUser(@NonNull RessourceUtilisateur ru) throws DataStorageExcetion;

	/**
	 * Retrieves a resource user from the data storage 
	 * 
	 * @param resourceId
	 *            Provided resource Id
	 * @param userId
	 *            Provided user Id.
	 *
	 * @return The user group that belongs to the provided groupId.
	 * @throws DataStorageExcetion
	 */
	RessourceUtilisateur retrieveResourceUser(@NonNull String resourceId, @NonNull String userId) throws DataStorageExcetion;

	/**
	 * Permanently delete a resource user in the data storage.
	 * 
	 * @param resourceId
	 *            Provided resource Id
	 * @param userId
	 *            Provided user Id.
	 * @return A boolean that indicates if the delete operation where
	 *         successfully perform or not.
	 * @throws DataStorageExcetion
	 */
	Boolean removeResourceUser(String resourceId, String userId) throws DataStorageExcetion;

	/**
	 * Retrieve resource users from the data storage based on the provided key
	 * word.
	 * 
	 * @param keyWord
	 *            Provided key word.
	 * @param page
	 *            Pageable object that indicated how many record need to be
	 *            extracted per page.
	 * @return A page of resource users that belongs to the given key word.
	 * @throws DataStorageExcetion
	 */
	Page<RessourceUtilisateur> retrieveResourceUsers(String keyWord, Pageable page) throws DataStorageExcetion;

	/**
	 * Retrieves all resource users from the data storage.
	 * 
	 * @param page
	 *            Pageable object that indicated how many record need to be
	 *            extracted per page.
	 * @return
	 * @throws DataStorageExcetion
	 */
	Page<RessourceUtilisateur> retrieveResourceUsers(Pageable page) throws DataStorageExcetion;

	/**
	 * Add the provided resource group to the data storage.
	 * 
	 * @param rg
	 *            Resource group to store.
	 * @return
	 * @throws DataStorageExcetion
	 */
	RessourceGroupe addResourceGroup(RessourceGroupe rg) throws DataStorageExcetion;

	/**
	 * Update the provided resource group information in the data storage.
	 * 
	 * @param rg
	 *            resource group to be updated.
	 * @return The updated resource group.
	 * @throws DataStorageExcetion
	 */
	RessourceGroupe updateResourceGroup(RessourceGroupe rg) throws DataStorageExcetion;

	/**
	 * Retrieves a resource group from the data storage based on the given user
	 * groupId.
	 * 
	 * @param resourceId
	 *            Provided resource Id
	 * @param groupeId
	 *            Provided group Id.
	 *
	 * @return The user group that belongs to the provided groupId.
	 * @throws DataStorageExcetion
	 */
	RessourceGroupe retrieveResourceGroup(String resourceId, String groupId) throws DataStorageExcetion;

	/**
	 * Permanently delete a resource group in the data storage.
	 * 
	 * @param resourceId
	 *            Provided resource Id
	 * @param groupeId
	 *            Provided group Id.
	 * @return A boolean that indicates if the delete operation where
	 *         successfully perform or not.
	 * @throws DataStorageExcetion
	 */
	Boolean removeResourceGroup(String resourceId, String groupId) throws DataStorageExcetion;

	/**
	 * Retrieve resource groups from the data storage based on the provided key
	 * word.
	 * 
	 * @param keyWord
	 *            Provided key word.
	 * @param page
	 *            Pageable object that indicated how many record need to be
	 *            extracted per page.
	 * @return A page of user group that belongs to the given key word.
	 * @throws DataStorageExcetion
	 */
	Page<RessourceGroupe> retrieveResourceGroups(String keyWord, Pageable page) throws DataStorageExcetion;

	/**
	 * Retrieves all resource groups from the data storage.
	 * 
	 * @param page
	 *            Pageable object that indicated how many record need to be
	 *            extracted per page.
	 * @return
	 * @throws DataStorageExcetion
	 */
	Page<RessourceGroupe> retrieveResourceGroups(Pageable page) throws DataStorageExcetion;

}
