package com.camlait.global.erp.delegate.bmq;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camlait.global.erp.dao.bmq.BmqDao;
import com.camlait.global.erp.delegate.document.DocumentManager;
import com.camlait.global.erp.delegate.inventaire.InventoryManager;
import com.camlait.global.erp.domain.bmq.Bmq;
import com.camlait.global.erp.domain.bmq.LigneBmq;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.LigneDeDocument;
import com.camlait.global.erp.domain.document.commerciaux.vente.FactureClientComptant;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.partenaire.ClientComptant;

@Transactional
@Component
public class DefaultBmqManager implements BmqManager {

	private final BmqDao bmqDao;
	private final DocumentManager documentManager;
	private final InventoryManager inventoryManager;

	@Autowired
	public DefaultBmqManager(BmqDao bmqDao, DocumentManager documentManager, InventoryManager inventoryManager) {
		this.bmqDao = bmqDao;
		this.documentManager = documentManager;
		this.inventoryManager=inventoryManager;
	}

	@Override
	public Bmq addBmq(Bmq bmq) throws DataStorageException {
		return bmqDao.save(bmq);
	}

	@Override
	public Bmq updateBmq(Bmq bmq) throws DataStorageException {
		final Bmq b = retrieveBmq(bmq.getBmqId());
		return bmqDao.saveAndFlush(bmq.merge(b));
	}

	@Override
	public Bmq retrieveBmq(String bmqId) throws DataStorageException {
		final Bmq b = bmqDao.findOne(bmqId);
		if (b == null) {
			throw new DataStorageException("The Bmq that your are looking for does not exist.");
		}
		return lazyInit(b);
	}

	@Override
	public Bmq buildBmqDetails(String bmqId) throws DataStorageException {
		final Bmq b = retrieveBmq(bmqId);
		return bmqDao.saveAndFlush(buildLigneBmq(b));
	}

	@Override
	public Boolean removeBmq(String bmqId) throws DataStorageException {
		final Bmq b = retrieveBmq(bmqId);
		bmqDao.delete(b);
		return true;
	}

	@Override
	public Page<Bmq> retrieveBmqs(String keyWord, Pageable p) throws DataStorageException {
		return bmqDao.retrieveBmqs(keyWord, p);
	}

	@Override
	public Page<Bmq> retrieveBmqs(Date start, Date end, Pageable p) throws DataStorageException {
		return bmqDao.retrieveBmqs(start, end, p);
	}

	@Override
	public void generateCashSales(String bmqId) throws DataStorageException {
		final Bmq b = retrieveBmq(bmqId);
		final Document d = createDocumentHeader(b);
		final Collection<LigneDeDocument> lignes = inventoryManager.getInventoryByStore(b.getMagasin())
				.parallelStream()
				.map(s->{
					return LigneDeDocument.builder()
							.document(d)
							.prixunitaiteLigne(s.getProduit().getDefaultUnitprice())
							.produit(s.getProduit())
							.produitId(s.getProduit().getProduitId())
							.quantiteLigne(s.getQuantiteDisponible())
							.sensOperation(d.getSensOperation())
							.build();
							
				}).collect(Collectors.toList());
		d.setLigneDocuments(lignes);
		b.getDocuments().add(d);
		bmqDao.saveAndFlush(b);
	}

	@Override
	public Double bmqValueWithoutTaxes(String bmqId) throws DataStorageException {
		final Bmq b = retrieveBmq(bmqId);
		return b.getDocuments().stream()
				.filter(d->d.isFactureClient())
				.mapToDouble(d->{
					return documentManager.documentValueWithoutTaxes(d.getDocumentId());
				}).sum();
	}

	@Override
	public Double bmqValueWithTaxes(String bmqId) throws DataStorageException {
		final Bmq b = retrieveBmq(bmqId);
		return b.getDocuments().stream()
				.filter(d->d.isFactureClient())
				.mapToDouble(d->{
					return documentManager.documentValueWithTaxes(d.getDocumentId());
				}).sum();
	}

	@Override
	public Double bmqTaxesValue(String bmqId) throws DataStorageException {
		final Bmq b = retrieveBmq(bmqId);
		return b.getDocuments().stream()
				.filter(d->d.isFactureClient())
				.mapToDouble(d->{
					return documentManager.documentTaxesValue(d.getDocumentId());
				}).sum();
	}

	@Override
	public Double bmqTaxesValue(String taxId, String bmqId) throws DataStorageException {
		final Bmq b = retrieveBmq(bmqId);
		return b.getDocuments().stream()
				.filter(d->d.isFactureClient())
				.mapToDouble(d->{
					return documentManager.documentTaxesValue(taxId, d.getDocumentId());
				}).sum();
	}

	@Override
	public Double bmqCashSalesValue(String bmqId) throws DataStorageException {
		final Bmq b = retrieveBmq(bmqId);
		return b.getDocuments().stream()
				.filter(d->d.isFactureComptant())
				.mapToDouble(d->{
					return documentManager.documentValueWithTaxes(d.getDocumentId());
				}).sum();
	}

	@Override
	public Double bmqMarginSalesValue(String bmqId) throws DataStorageException {
		final Bmq b = retrieveBmq(bmqId);
		return b.getDocuments().stream()
				.filter(d->d.isFactureMarge())
				.mapToDouble(d->{
					return documentManager.documentValueWithTaxes(d.getDocumentId());
				}).sum();
	}

	private Bmq lazyInit(Bmq b) {
		Hibernate.initialize(b.getLigneBmqs());
		Hibernate.initialize(b.getDocuments());
		Hibernate.initialize(b.getRecouvrements());
		return b;
	}
	
    private Bmq buildLigneBmq(Bmq bmq) {        
    	Set<LigneBmq> lignes = bmq.getDocuments().parallelStream().map(d->{
        	return d.getLigneDocuments().stream().map(l->{
        		return LigneBmq.builder()
        				.bmq(d.getBmq())
        				.bmqId(d.getBmq().getBmqId())
        				.document(d)
        				.documentId(d.getDocumentId())
        				.prixUnitaireLigne(l.getPrixunitaiteLigne())
        				.produit(l.getProduit())
        				.produitId(l.getProduit().getProduitId())
        				.quantiteLigne(l.getQuantiteLigne())
        				.build();
        	}).collect(Collectors.toSet());
        }).findFirst().get();
    	bmq.setLigneBmqs(lignes);
    	return bmq;
    }
    
    private Document createDocumentHeader(Bmq bmq) {
        final FactureClientComptant f = new FactureClientComptant();
        f.setBmq(bmq);
        f.setClient(new ClientComptant());
        f.setDateDocument(new Date());
        f.setMagasin(bmq.getMagasin());
        f.setResponsableDocument(bmq.getResponsable());
        f.setZone(bmq.getVendeur().getZoneDeVente());
        return f;
    }
}
