package com.global.erp.service.produit;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
		categorie = CategorieProduit.builder().categorieProduitId(1L).codeCategorieProduit("PL").categorieTaxable(true)
				.dateDeCreation(new Date()).derniereMiseAJour(new Date()).descriptionCategorie("Produits laitiers")
				.portee(Portee.DETAIL).build();
	}

	@Test
	public void ajouterCategorieProduitTest() {
		when(service.ajouterCategorieProduit(categorie)).thenReturn(categorie);
		final CategorieProduit c = service.ajouterCategorieProduit(categorie);
		verify(service).ajouterCategorieProduit(categorie);
		verify(service, times(1)).ajouterCategorieProduit(categorie);
		Assert.assertEquals("Produit laitier", c.getDescriptionCategorie());
	}

	@Test
	public void modifierCategorieProduitTest() {
		when(service.modifierCategorieProduit(categorie)).thenReturn(categorie);
		final CategorieProduit c = service.modifierCategorieProduit(categorie);
		verify(service, times(1)).modifierCategorieProduit(categorie);
		Assert.assertEquals("Produit laitier", c.getDescriptionCategorie());
	}

	@Test
	public void supprimerCategorieProduitTest() {
		service.supprimerCategorieProduit(categorie.getCategorieProduitId());
		verify(service, times(1)).supprimerCategorieProduit(eq(categorie.getCategorieProduitId()));
	}

	@Test
	public void trouverCategorieProduitTest() {
		service.obtenirCategorieProduit(categorie.getCategorieProduitId());
		verify(service, times(1)).obtenirCategorieProduit(eq(categorie.getCategorieProduitId()));
	}

	@Test
	public void listerCategorieTest() {
		service.listerCategorieProduit();
		verify(service).listerCategorieProduit();
	}
}
