package com.camlait.global.erp.delegate.auth;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.auth.GroupRepository;
import com.camlait.global.erp.dao.auth.ResourceRepository;
import com.camlait.global.erp.dao.auth.UserRepository;
import com.camlait.global.erp.domain.auth.Group;
import com.camlait.global.erp.domain.auth.Resource;
import com.camlait.global.erp.domain.auth.User;
import com.camlait.global.erp.domain.exception.DataStorageException;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {

    @Mock
    private UserRepository userRepo;
    @Mock
    private GroupRepository groupRepo;
    @Mock
    private ResourceRepository resourceRepository;
    @Mock
    private Pageable page;

    private UserManager manager;

    @Before
    public void setup() {
        manager = new DefaultUserManager(userRepo, groupRepo, resourceRepository);
    }

    @Test
    public void testAddUser() {
        when(userRepo.save(any(User.class))).thenReturn(User.builder().build());
        final User u = manager.addUser(User.builder().build());
        assertNotNull(u);
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateExistingUser() {
        when(userRepo.findOne(anyString())).thenReturn(User.builder().userId("id").build());
        final User toUpdate = User.builder().userId("id").build();
        when(userRepo.saveAndFlush(any(User.class))).thenReturn(toUpdate);
        final User u = manager.updateUser(toUpdate);
        assertNotNull(u);
        assertThat(u.toJson(), is(toUpdate.toJson()));
        verify(userRepo, times(1)).saveAndFlush(any(User.class));
        verify(userRepo, times(1)).findOne(eq("id"));
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateNonExistingUser() {
        final User toUpdate = User.builder().userId("id").build();
        manager.updateUser(toUpdate);
        verify(userRepo, times(1)).findOne(eq("id"));
    }

    @Test
    public void testDeleteExistingUser() {
        when(userRepo.findOne(anyString())).thenReturn(User.builder().userId("id").build());
        final Boolean result = manager.removeUser("id");
        assertTrue(result);
        verify(userRepo, times(1)).delete(any(User.class));
        verify(userRepo, times(1)).findOne(eq("id"));
    }

    @Test
    public void testDeleteNonExistingUser() {
        assertFalse(manager.removeUser("id"));
        verify(userRepo, times(1)).findOne(eq("id"));
        verify(userRepo, never()).delete(any(User.class));
    }

    @Test
    public void testAddGroup() {
        when(groupRepo.save(any(Group.class))).thenReturn(Group.builder().build());
        final Group g = manager.addGroup(Group.builder().build());
        assertNotNull(g);
        verify(groupRepo, times(1)).save(any(Group.class));
    }

    @Test
    public void testUpdateExistingGroup() {
        when(groupRepo.findOne(anyString())).thenReturn(Group.builder().groupId("id").build());
        final Group toUpdate = Group.builder().groupId("id").build();
        when(groupRepo.saveAndFlush(any(Group.class))).thenReturn(toUpdate);
        final Group g = manager.updateGroup(toUpdate);
        assertNotNull(g);
        assertThat(g.toJson(), is(toUpdate.toJson()));
        verify(groupRepo, times(1)).saveAndFlush(any(Group.class));
        verify(groupRepo, times(1)).findOne(eq("id"));
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateNonExistingGroup() {
        final Group toUpdate = Group.builder().groupId("id").build();
        manager.updateGroup(toUpdate);
        verify(groupRepo, times(1)).findOne(eq("id"));
    }

    @Test
    public void testDeleteExistingGroup() {
        when(groupRepo.findOne(anyString())).thenReturn(Group.builder().groupId("id").build());
        final Boolean result = manager.removeGroup("id");
        assertTrue(result);
        verify(groupRepo, times(1)).delete(any(Group.class));
        verify(groupRepo, times(1)).findOne(eq("id"));
    }

    @Test
    public void testDeleteNonExistingGroup() {
        assertFalse(manager.removeGroup("id"));
        verify(groupRepo, times(1)).findOne(eq("id"));
        verify(groupRepo, never()).delete(any(Group.class));
    }

    @Test
    public void testAddResource() {
        when(resourceRepository.save(any(Resource.class))).thenReturn(Resource.builder().build());
        final Resource r = manager.addResource(Resource.builder().build());
        assertNotNull(r);
        verify(resourceRepository, times(1)).save(any(Resource.class));
    }

    @Test
    public void testUpdateExistingResource() {
        when(resourceRepository.findOne(anyString())).thenReturn(Resource.builder().resourceId("id").build());
        final Resource toUpdate = Resource.builder().resourceId("id").build();
        when(resourceRepository.saveAndFlush(any(Resource.class))).thenReturn(toUpdate);
        final Resource r = manager.updateResource(toUpdate);
        assertNotNull(r);
        assertThat(r.toJson(), is(toUpdate.toJson()));
        verify(resourceRepository, times(1)).saveAndFlush(any(Resource.class));
        verify(resourceRepository, times(1)).findOne(eq("id"));
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateNonExistingResource() {
        final Resource toUpdate = Resource.builder().resourceId("id").build();
        manager.updateResource(toUpdate);
        verify(resourceRepository, times(1)).findOne(eq("id"));
    }

    @Test
    public void testDeleteExistingResource() {
        when(resourceRepository.findOne(anyString())).thenReturn(Resource.builder().resourceId("id").build());
        final Boolean result = manager.removeResource("id");
        assertTrue(result);
        verify(resourceRepository, times(1)).delete(any(Resource.class));
        verify(resourceRepository, times(1)).findOne(eq("id"));
    }

    @Test
    public void testDeleteNonExistingResource() {
        assertFalse(manager.removeResource("id"));
        verify(resourceRepository, times(1)).findOne(eq("id"));
        verify(resourceRepository, never()).delete(any(Resource.class));
    }

    @Test
    public void testRetrieveUsers() {
        Page<User> p = mock(Page.class);
        // when(userRepo.retrieveUsers(anyString(), any())).thenReturn(p);
        manager.retrieveUsers("id", page);
        manager.retrieveUsers(page);
        // verify(userRepo, times(1)).retrieveUsers(anyString(), any());
    }

    @Test
    public void testRetrieveGroups() {
        Page<Group> p = mock(Page.class);
        // when(groupRepo.retrieveGroups(anyString(), any())).thenReturn(p);
        manager.retrieveGroups("id", page);
        manager.retrieveGroups(page);
        // verify(groupRepo, times(1)).retrieveGroups(anyString(), any());
    }

    @Test
    public void testRetrieveResources() {
        Page<Resource> p = mock(Page.class);
        // when(termRepository.retrieveTerms(anyString(), any())).thenReturn(p);
        manager.retrieveResources("id", page);
        manager.retrieveResources(page);
        // verify(termRepository, times(1)).retrieveTerms(anyString(), any());
    }
}
