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
	public Document addDocument(final Document document) throws DataStorageException {
		return documentDao.save(document);
	}

	@Override
	public Document updateDocument(final Document document) throws DataStorageException {
		final Document stored = retrieveDocument(document.getDocumentId());
		return documentDao.saveAndFlush(document.merge(stored));
	}

	@Override
	public Document retrieveDocument(final String documentId) throws DataStorageException {
		final Document d = documentDao.findOne(documentId);
		if (d == null) {
			throw new DataStorageException("The document you are trying to retrieve does not exist.");
		}
		return d.lazyInit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Document> T retrieveDocument(final Class<T> clazz, final String documentId) throws DataStorageException {
		final Document d = retrieveDocument(documentId);
		return d.isTypeOf(clazz) ? (T) d : null;
	}

	@Override
	public Boolean removeDocument(final String documentId) throws DataStorageException {
		final Document d = retrieveDocument(documentId);
		documentDao.delete(d);
		return true;
	}

	@Override
	public Page<Document> retrieveDocuments(final String keyWord, Pageable p) throws DataStorageException {
		return documentDao.retrieveDocuments(keyWord, p);
	}

	@Override
	public Page<Document> retrieveDocuments(final Date start, final Date end, Pageable p) throws DataStorageException {
		return documentDao.retrieveDocuments(start, end, p);
	}

	@Override
	public Double documentValueWithoutTaxes(final String documentId) throws DataStorageException {
		final Document d = retrieveDocument(documentId);
		return d == null ? 0.0 : d.getLigneDocuments().stream().mapToDouble(l -> {
			return l.getPrixunitaiteLigne() * l.getQuantiteLigne();
		}).sum();
	}

	@Override
	public Double documentTaxesValue(final String documentId) throws DataStorageException {
		final Document d = retrieveDocument(documentId);
		return d == null ? 0.0 : d.getLigneDocuments().stream().mapToDouble(l -> {
			return l.getLigneDeDocumentTaxes().stream().mapToDouble(ldt -> {
				return l.getPrixunitaiteLigne() * l.getQuantiteLigne() * ldt.getTauxDeTaxe();
			}).sum();
		}).sum();
	}

	@Override
	public Double documentTaxesValue(final String taxId, final String documentId) throws DataStorageException {
		final Document d = retrieveDocument(documentId);
		return d == null ? 0.0 : d.getLigneDocuments().stream().mapToDouble(ld -> {
			return ld.getLigneDeDocumentTaxes().stream().filter(ldt -> ldt.getTaxe().getTaxeId() == taxId)
					.mapToDouble(ldt -> {
						return ld.getPrixunitaiteLigne() * ld.getQuantiteLigne() * ldt.getTauxDeTaxe();
					}).sum();
		}).sum();
	}

	@Override
	public Double documentValueWithTaxes(final String documentId) throws DataStorageException {
		return documentValueWithoutTaxes(documentId) + documentTaxesValue(documentId);
	}

	@Override
	public Double documentMarginValue(final String documentId) throws DataStorageException {
		final FactureMarge f = retrieveDocument(FactureMarge.class, documentId);
		if (f == null) {
			return 0.0;
		}
		final PriceType type = f.getPriceType();
		return f.getLigneDocuments().stream().mapToDouble(l -> {
			Double regularPrice = tarif.retrieveUnitPrice(type.getPriceTypeId(), f.getZone().getLocalId(),
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
	public Taxe addTax(final Taxe tax) throws DataStorageException {
		return taxDao.save(tax);
	}

	@Override
	public Taxe updateTax(final Taxe tax) throws DataStorageException {
		final Taxe t = retrieveTax(tax.getTaxeId());
		return taxDao.saveAndFlush(tax.merge(t));
	}

	@Override
	public Taxe retrieveTax(final String taxId) throws DataStorageException {
		final Taxe tax = retrieveTax(taxId);
	      if (tax == null) {
	            throw new DataStorageException("The tax your are trying to retireve does not exist.");
	        }
		return tax == null ? null : tax.lazyInit();
	}

	@Override
	public Boolean removeTax(final String taxId) throws DataStorageException {
		final Taxe tax = retrieveTax(taxId);
		taxDao.delete(tax);
		return true;
	}

	@Override
	public Page<Taxe> retrieveTaxes(final String keyWord, Pageable p) throws DataStorageException {
		return taxDao.retrieveTaxes(keyWord, p);
	}
}
