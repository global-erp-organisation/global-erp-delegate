package com.camlait.global.erp.delegate.document;

import static com.camlait.global.erp.domain.config.GlobalAppConstants.produitIndiponibleMessage;
import static com.camlait.global.erp.domain.config.GlobalAppConstants.verifyObjectNoFindException;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.document.DocumentDao;
import com.camlait.global.erp.dao.document.LigneDeDocumentTaxeDao;
import com.camlait.global.erp.dao.document.LigneDocumentDao;
import com.camlait.global.erp.delegate.partenaire.IPartenaireService;
import com.camlait.global.erp.delegate.produit.IProduitService;
import com.camlait.global.erp.delegate.util.IUtilService;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.LigneDeDocument;
import com.camlait.global.erp.domain.document.LigneDeDocumentTaxe;
import com.camlait.global.erp.domain.document.commerciaux.Taxe;
import com.camlait.global.erp.domain.document.commerciaux.vente.FactureClient;
import com.camlait.global.erp.domain.exception.GlobalErpServiceException;
import com.camlait.global.erp.domain.partenaire.Client;
import com.camlait.global.erp.domain.produit.Produit;
import com.camlait.global.erp.domain.util.Compute;
import com.camlait.global.erp.domain.util.Utility;

import lombok.NonNull;

@Transactional
public class DocumentService implements IDocumentService {

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private LigneDocumentDao ligneDeDocumentDao;

    @Autowired
    private LigneDeDocumentTaxeDao ligneDeDocumentTaxeDao;

    @Autowired
    private IUtilService utilService;

    @Autowired
    private IPartenaireService partenaireService;

    @Autowired
    private IProduitService produitService;

    @Override
    public Document ajouterDocument(@NonNull Document document) {
        document.setCodeDocument(utilService.genererCode(document));
        documentDao.save(document);
        ajouterLigneDocument(document.getLigneDocuments());
        return document;
    }

    @Override
    public Document modifierDocument(@NonNull Document document) {
        document.setDerniereMiseAJour(new Date());
        documentDao.saveAndFlush(document);
        return document;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T obtenirDocument(@NonNull Class<T> entityClass, @NonNull Long documentId) {
        final Document d = documentDao.findOne(documentId);
        verifyObjectNoFindException(d, entityClass, documentId);
        Hibernate.initialize(d.getLigneDocuments());
        if (d instanceof FactureClient) {
            Hibernate.initialize(((FactureClient) d).getFactureReglements());
        }
        return (T) d;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T obtenirDocument(@NonNull Class<T> entityClass, @NonNull String codeDocument) {
        final List<Document> documents = documentDao.findByCodeDocument(codeDocument, new PageRequest(0, 1)).getContent();
        final Document d = (documents.isEmpty()) ? null : documents.get(0);
        verifyObjectNoFindException(d, entityClass, codeDocument);
        Hibernate.initialize(d.getLigneDocuments());
        if (d instanceof FactureClient) {
            Hibernate.initialize(((FactureClient) d).getFactureReglements());
        }
        return (T) d;
    }

    @Override
    public void supprimerDocument(@NonNull Long documentId) {
        documentDao.delete(obtenirDocument(Document.class, documentId));
    }

    @Override
    public Page<Document> listerDocument(@NonNull Date debut, @NonNull Date fin, @NonNull Pageable p) {
        return documentDao.listerDocument(debut, fin, p);
    }

    @Override
    public LigneDeDocument ajouterLigneDocument(@NonNull LigneDeDocument ligne) {
        if (!Utility.isDisponible(ligne)) {
            throw new GlobalErpServiceException(produitIndiponibleMessage(ligne));
        }
        ligneDeDocumentDao.save(ligne);
        ajouterLigneDeDocumentTaxe(ligne);
        return ligne;
    }

    @Override
    public Collection<LigneDeDocument> ajouterLigneDocument(@NonNull Collection<LigneDeDocument> lignes) {
        lignes.stream().forEach(l -> {
            ajouterLigneDocument(l);
        });
        ajouterLigneDeDocumentTaxe(lignes);
        return lignes;
    }

    @Override
    public LigneDeDocument modifierLigneDocument(@NonNull LigneDeDocument ligne) {
        ligne.setDerniereMiseAJour(new Date());
        ligneDeDocumentDao.saveAndFlush(ligne);
        return ligne;
    }

    @Override
    public LigneDeDocument obtenirLigneDocument(@NonNull Long ligneId) {
        LigneDeDocument ld = ligneDeDocumentDao.findOne(ligneId);
        verifyObjectNoFindException(ld, LigneDeDocument.class, ligneId);
        Hibernate.initialize(ld.getLigneDeDocumentTaxes());
        return ld;
    }

    @Override
    public void supprimerLigneDocument(@NonNull Long ligneId) {
        ligneDeDocumentDao.delete(obtenirLigneDocument(ligneId));
    }

    @Override
    public void supprimerLigneDocument(@NonNull Document document) {
        ligneDeDocumentDao.delete(document.getLigneDocuments());
    }

    @Override
    public LigneDeDocumentTaxe ajouterLigneDeDocumentTaxe(@NonNull LigneDeDocumentTaxe ligneDeDocumentTaxe) {
        ligneDeDocumentTaxeDao.save(ligneDeDocumentTaxe);
        return ligneDeDocumentTaxe;
    }

    @Override
    public LigneDeDocumentTaxe modifierLigneDeDocumentTaxe(@NonNull LigneDeDocumentTaxe ligneDeDocumentTaxe) {
        ligneDeDocumentTaxe.setDerniereMiseAJour(new Date());
        ligneDeDocumentTaxeDao.saveAndFlush(ligneDeDocumentTaxe);
        return ligneDeDocumentTaxe;
    }

    @Override
    public LigneDeDocumentTaxe obtenirLigneDeDocumentTaxe(@NonNull Long ligneDeDocumentTaxeId) {
        LigneDeDocumentTaxe l = ligneDeDocumentTaxeDao.findOne(ligneDeDocumentTaxeId);
        verifyObjectNoFindException(l, LigneDeDocumentTaxe.class, ligneDeDocumentTaxeId);
        return l;
    }

    @Override
    public void spprimerLigneDeDocumentTaxe(@NonNull Long ligneDeDocumentTaxeId) {
        ligneDeDocumentTaxeDao.delete(obtenirLigneDeDocumentTaxe(ligneDeDocumentTaxeId));
    }

    /**
     * Ajout des taxe a une ligne de document.
     * 
     * @param ligneDeDocument
     */
    private void ajouterLigneDeDocumentTaxe(@NonNull LigneDeDocument ligneDeDocument) {
        ligneDeDocument.getProduit().getProduitTaxes().parallelStream().forEach(pt -> {
            LigneDeDocumentTaxe l = new LigneDeDocumentTaxe();
            l.setLigneDeDocument(ligneDeDocument);
            l.setTaxe(pt.getTaxe());
            l.setTauxDeTaxe(pt.getTaxe().getValeurPourcentage());
            ajouterLigneDeDocumentTaxe(l);
        });
    }

    /**
     * Ajout des taxes aux lignes de document de maniere groupee.
     * 
     * @param lignes
     */
    private void ajouterLigneDeDocumentTaxe(@NonNull Collection<LigneDeDocument> lignes) {
        lignes.parallelStream().forEach(ligne -> ajouterLigneDeDocumentTaxe(ligne));
    }

    @Override
    public double chiffreAffaireHorsTaxe(@NonNull Document document) {
        final Compute caht = new Compute();
        document.getLigneDocuments().stream().forEach(l -> {
            caht.cummuler(l.getPrixunitaiteLigne() * l.getQuantiteLigne());
        });
        return caht.getValue();
    }

    @Override
    public double valeurTotaleTaxe(@NonNull Document document) {
        final Compute taxe = new Compute();
        document.getLigneDocuments().stream().forEach(l -> {
            l.getLigneDeDocumentTaxes().stream().forEach(ldt -> {
                taxe.cummuler(l.getPrixunitaiteLigne() * l.getQuantiteLigne() * ldt.getTauxDeTaxe());
            });
        });
        return taxe.getValue();
    }

    @Override
    public double chiffreAffaireTTC(@NonNull Document document) {
        return chiffreAffaireHorsTaxe(document) + valeurTotaleTaxe(document);
    }

    @Override
    public double valeurTaxe(@NonNull Taxe taxe, @NonNull Document document) {
        return document.getLigneDocuments().parallelStream().map(ld -> {
            return ld.getLigneDeDocumentTaxes().stream().filter(ldt -> ldt.getTaxe().getTaxeId() == taxe.getTaxeId()).map(ldt -> {
                return ld.getPrixunitaiteLigne() * ld.getQuantiteLigne() * ldt.getTauxDeTaxe();
            }).mapToDouble(Double::doubleValue).sum();
        }).mapToDouble(Double::doubleValue).sum();
    }

    @Override
    public double valeurMarge(@NonNull Document document) {
        return document.getLigneDocuments().stream().map(l -> {
            return l.getProduit().getPrixUnitaireMarge() * l.getQuantiteLigne();
        }).mapToDouble(Double::doubleValue).sum();
    }

    @Override
    public double obtenirPrixUnitaire(@NonNull Long partenaireId, @NonNull Long produitId) {
        Client c = partenaireService.obtenirPartenaire(Client.class, partenaireId);
        Produit p = produitService.obtenirProduit(produitId);
        if (p != null) {
            return p.getTarifications().stream().filter(t -> t.getTarif().getTarifId().equals(c.getTarif().getTarifId())).collect(Collectors.toList())
                    .get(0).getPrixUnitaire();
        }
        return 0;
    }

    @Override
    public double obtenirPrixUnitaireMarge(@NonNull Long partenaireId, @NonNull Long produitId) {
        Client c = partenaireService.obtenirPartenaire(Client.class, partenaireId);
        Produit p = produitService.obtenirProduit(produitId);
        if (p != null) {
            return p.getTarifications().stream().filter(t -> t.getTarif().getTarifId().equals(c.getTarif().getTarifId())).collect(Collectors.toList())
                    .get(0).getPrixUnitaireMarge();
        }
        return 0;
    }
}
