package com.camlait.global.erp.delegate.document;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camlait.global.erp.dao.document.DocumentDao;
import com.camlait.global.erp.dao.document.comerciaux.TaxeDao;
import com.camlait.global.erp.delegate.price.TarificationManager;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.commerciaux.Taxe;
import com.camlait.global.erp.domain.document.commerciaux.vente.FactureMarge;
import com.camlait.global.erp.domain.exception.DataStorageException;
import com.camlait.global.erp.domain.tarif.PriceType;

/**
 * Default implementation of the Document manager service interface.
 * 
 * @author Martin Blaise Signe
 *
 */
@Component
@Transactional
public class DefaultDocumentManager implements DocumentManager {

	private final DocumentDao documentDao;
	private final TaxeDao taxDao;
	private final TarificationManager tarif;

	@Autowired
	public DefaultDocumentManager(DocumentDao documentDao, TaxeDao taxDao, TarificationManager tarif) {
		this.documentDao = documentDao;
		this.taxDao = taxDao;
		this.tarif = tarif;
	}

	@Override
	public Document addDocument(Document document) throws DataStorageException {
		return documentDao.save(document);
	}

	@Override
	public Document updateDocument(Document document) throws DataStorageException {
		final Document stored = retrieveDocument(document.getDocumentId());
		return documentDao.saveAndFlush(document.merge(stored));
	}

	@Override
	public Document retrieveDocument(String documentId) throws DataStorageException {
		final Document d = documentDao.findOne(documentId);
		if (d == null) {
			throw new DataStorageException("The document you are trying to retrieve does not exist.");
		}
		return d.lazyInit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Document> T retrieveDocument(Class<T> clazz, String documentId) throws DataStorageException {
		final Document d = retrieveDocument(documentId);
		return d.instanceOf(clazz) ? (T) d : null;
	}

	@Override
	public Boolean removeDocument(String documentId) throws DataStorageException {
		final Document d = retrieveDocument(documentId);
		documentDao.delete(d);
		return true;
	}

	@Override
	public Page<Document> retrieveDocuments(String keyWord, Pageable p) throws DataStorageException {
		return documentDao.retrieveDocuments(keyWord, p);
	}

	@Override
	public Page<Document> retrieveDocuments(Date start, Date end, Pageable p) throws DataStorageException {
		return documentDao.retrieveDocuments(start, end, p);
	}

	@Override
	public Double documentValueWithoutTaxes(String documentId) throws DataStorageException {
		final Document d = retrieveDocument(documentId);
		return d == null ? 0.0 : d.getLigneDocuments().stream().mapToDouble(l -> {
			return l.getPrixunitaiteLigne() * l.getQuantiteLigne();
		}).sum();
	}

	@Override
	public Double documentTaxesValue(String documentId) throws DataStorageException {
		final Document d = retrieveDocument(documentId);
		return d == null ? 0.0 : d.getLigneDocuments().stream().mapToDouble(l -> {
			return l.getLigneDeDocumentTaxes().stream().mapToDouble(ldt -> {
				return l.getPrixunitaiteLigne() * l.getQuantiteLigne() * ldt.getTauxDeTaxe();
			}).sum();
		}).sum();
	}

	@Override
	public Double documentTaxesValue(String taxId, String documentId) throws DataStorageException {
		final Document d = retrieveDocument(documentId);
		return d == null ? 0.0 : d.getLigneDocuments().parallelStream().mapToDouble(ld -> {
			return ld.getLigneDeDocumentTaxes().stream().filter(ldt -> ldt.getTaxe().getTaxeId() == taxId)
					.mapToDouble(ldt -> {
						return ld.getPrixunitaiteLigne() * ld.getQuantiteLigne() * ldt.getTauxDeTaxe();
					}).sum();
		}).sum();
	}

	@Override
	public Double documentValueWithTaxes(String documentId) throws DataStorageException {
		return documentValueWithoutTaxes(documentId) + documentTaxesValue(documentId);
	}

	@Override
	public Double documentMarginValue(String documentId) throws DataStorageException {
		final Document d = retrieveDocument(documentId);
		if (d == null || !d.isFactureMarge()) {
			return 0.0;
		}
		final FactureMarge f = (FactureMarge) d;
		final PriceType type = f.getPriceType();
		return d.getLigneDocuments().stream().mapToDouble(l -> {
			Double regularPrice = tarif.retrieveUnitPrice(f.getPriceType().getPriceTypeId(), f.getZone().getLocalId(),
					l.getProduit().getProduitId());
			if (regularPrice == null) {
				regularPrice = l.getProduit().getUnitPriceByType(type);
			}
			final Double currentValue = l.getQuantiteLigne() * l.getPrixunitaiteLigne();
			final Double regularValue = l.getQuantiteLigne() * regularPrice;
			return (currentValue - regularValue);
		}).sum();
	}

	@Override
	public Taxe addTax(Taxe tax) throws DataStorageException {
		return taxDao.save(tax);
	}

	@Override
	public Taxe updateTax(Taxe tax) throws DataStorageException {
		final Taxe t = retrieveTax(tax.getTaxeId());
		if (t == null) {
			throw new DataStorageException("The tax your are trying to update does not exist.");
		}
		return taxDao.saveAndFlush(tax.merge(t));
	}

	@Override
	public Taxe retrieveTax(String taxId) throws DataStorageException {
		final Taxe tax = retrieveTax(taxId);
		return tax == null ? null : tax.lazyInit();
	}

	@Override
	public Boolean removeTax(String taxId) throws DataStorageException {
		final Taxe tax = retrieveTax(taxId);
		if (tax == null) {
			throw new DataStorageException("The tax your are trying to remove does not exist.");
		}
		taxDao.delete(tax);
		return true;
	}

	@Override
	public Page<Taxe> retrieveTaxes(String keyWord, Pageable p) throws DataStorageException {
		return taxDao.retrieveTaxes(keyWord, p);
	}
}
