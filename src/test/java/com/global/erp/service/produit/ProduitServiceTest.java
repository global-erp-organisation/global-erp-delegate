package com.global.erp.service.produit;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.domain.enumeration.Portee;
import com.camlait.global.erp.domain.produit.CategorieProduit;
import com.camlait.global.erp.service.produit.ProduitService;

@RunWith(MockitoJUnitRunner.class)
public class ProduitServiceTest {

	@Mock
	private ProduitService service;
	private CategorieProduit categorie;

	@Before
	public void setup() {
		categorie = new CategorieProduit();
		categorie.setCategorieProduitId(1L);
		categorie.setCodeCategorieProduit("PL");
		categorie.setCategorieTaxable(true);
		categorie.setDateDeCreation(new Date());
		categorie.setDerniereMiseAJour(new Date());
		categorie.setDescriptionCategorie("Produits laitiers");
		categorie.setPortee(Portee.DETAIL);
	}

	@Test
	public void ajouterCategorieProduitTest() {
		service.ajouterCategorieProduit(categorie);
		verify(service).ajouterCategorieProduit(categorie);
		verify(service, times(1)).ajouterCategorieProduit(categorie);
	}

	@Test
	public void modifierCategorieProduitTest() {
		service.modifierCategorieProduit(categorie);
		verify(service).modifierCategorieProduit(categorie);
		verify(service, times(1)).modifierCategorieProduit(categorie);
	}

	@Test
	public void supprimerCategorieProduitTest() {
		when(service.ajouterCategorieProduit(categorie)).thenReturn(categorie);
		service.supprimerCategorieProduit(categorie.getCategorieProduitId());
		verify(service, times(1)).supprimerCategorieProduit(eq(categorie.getCategorieProduitId()));
	}

	@Test
	public void trouverCategorieProduitTest() {
		when(service.ajouterCategorieProduit(any(CategorieProduit.class))).thenReturn(categorie);
		service.trouverCategorieProduit(categorie.getCategorieProduitId());
		verify(service, times(1)).trouverCategorieProduit(eq(categorie.getCategorieProduitId()));
	}

	@Test
	public void listerCategorieTest() {

		Pageable p = mock(Pageable.class);
		service.ajouterCategorieProduit(mock(CategorieProduit.class));
		service.ajouterCategorieProduit(mock(CategorieProduit.class));
		service.ajouterCategorieProduit(mock(CategorieProduit.class));
		service.ajouterCategorieProduit(mock(CategorieProduit.class));
		service.listerCategorieProduit(p);

		verify(service, times(4)).ajouterCategorieProduit(any(CategorieProduit.class));
		verify(service).listerCategorieProduit(p);
	}
}
