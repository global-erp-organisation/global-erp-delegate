package com.camlait.global.erp.service.document;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.document.DocumentDao;
import com.camlait.global.erp.dao.document.LigneDeDocumentTaxeDao;
import com.camlait.global.erp.dao.document.LigneDocumentDao;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.LigneDeDocument;
import com.camlait.global.erp.domain.document.LigneDeDocumentTaxe;
import com.camlait.global.erp.domain.document.commerciaux.Taxe;
import com.camlait.global.erp.domain.document.commerciaux.vente.FactureClient;
import com.camlait.global.erp.service.GlobalErpServiceException;

@Transactional
public class DocumentService implements IDocumentService {
    
    @Autowired
    private DocumentDao documentDao;
    
    @Autowired
    private LigneDocumentDao ligneDeDocumentDao;
    
    @Autowired
    private LigneDeDocumentTaxeDao ligneDeDocumentTaxeDao;
    
    @Override
    public Document ajouterDocument(Document document) {
        if (document == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("document"));
        }
        documentDao.save(document);
        return document;
    }
    
    @Override
    public Document modifierDocument(Document document) {
        if (document == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("document"));
        }
        document.setDerniereMiseAJour(new Date());
        documentDao.saveAndFlush(document);
        return document;
    }
    
    @Override
    public Document obtenirDocument(Long documentId) {
        if (documentId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("documentId"));
        }
        Document d = documentDao.findOne(documentId);
        if (d != null) {
            Hibernate.initialize(d.getLigneDocuments());
            if (d instanceof FactureClient) {
                Hibernate.initialize(((FactureClient) d).getFactureReglements());
            }
            return d;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(Document.class, documentId));
        }
    }
    
    @Override
    public void supprimerDocument(Long documentId) {
        documentDao.delete(obtenirDocument(documentId));
    }
    
    @Override
    public Page<Document> listerDocument(Date debut, Date fin, Pageable p) {
        return documentDao.listerDocument(debut, fin, p);
    }
    
    @Override
    public LigneDeDocument ajouterLigneDocument(LigneDeDocument ligne) {
        if (ligne == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("ligne"));
        }
        ligneDeDocumentDao.save(ligne);
        ajouterLigneDeDocumentTaxe(ligne);
        return ligne;
    }
    
    @Override
    public Collection<LigneDeDocument> ajouterLigneDocument(Collection<LigneDeDocument> lignes) {
        if (lignes == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("lignes"));
        }
        ligneDeDocumentDao.save(lignes);
        ajouterLigneDeDocumentTaxe(lignes);
        return lignes;
    }
    
    @Override
    public LigneDeDocument modifierLigneDocument(LigneDeDocument ligne) {
        if (ligne == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("ligne"));
        }
        ligne.setDerniereMiseAJour(new Date());
        ligneDeDocumentDao.saveAndFlush(ligne);
        return ligne;
    }
    
    @Override
    public LigneDeDocument obtenirLigneDocument(Long ligneId) {
        if (ligneId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("ligneId"));
        }
        LigneDeDocument ld = ligneDeDocumentDao.findOne(ligneId);
        if (ld != null) {
            Hibernate.initialize(ld.getLigneDeDocumentTaxes());
            return ld;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(LigneDeDocument.class, ligneId));
        }
    }
    
    @Override
    public void supprimerLigneDocument(Long ligneId) {
        ligneDeDocumentDao.delete(obtenirLigneDocument(ligneId));
    }
    
    @Override
    public void supprimerLigneDocument(Document document) {
        ligneDeDocumentDao.delete(document.getLigneDocuments());
    }
    
    @Override
    public LigneDeDocumentTaxe ajouterLigneDeDocumentTaxe(LigneDeDocumentTaxe ligneDeDocumentTaxe) {
        if (ligneDeDocumentTaxe == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("ligneDeDocumentTaxe"));
        }
        ligneDeDocumentTaxeDao.save(ligneDeDocumentTaxe);
        return ligneDeDocumentTaxe;
    }
    
    @Override
    public LigneDeDocumentTaxe modifierLigneDeDocumentTaxe(LigneDeDocumentTaxe ligneDeDocumentTaxe) {
        if (ligneDeDocumentTaxe == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("ligneDeDocumentTaxe"));
        }
        ligneDeDocumentTaxe.setDerniereMiseAJour(new Date());
        ligneDeDocumentTaxeDao.saveAndFlush(ligneDeDocumentTaxe);
        return ligneDeDocumentTaxe;
    }
    
    @Override
    public LigneDeDocumentTaxe obtenirLigneDeDocumentTaxe(Long ligneDeDocumentTaxeId) {
        if (ligneDeDocumentTaxeId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("ligneDeDocumentTaxeId"));
        }
        LigneDeDocumentTaxe l = ligneDeDocumentTaxeDao.findOne(ligneDeDocumentTaxeId);
        if (l != null) {
            return l;
        } else {
            throw new GlobalErpServiceException(
                    GlobalAppConstants.buildNotFindMessage(LigneDeDocumentTaxe.class, ligneDeDocumentTaxeId));
        }
    }
    
    @Override
    public void spprimerLigneDeDocumentTaxe(Long ligneDeDocumentTaxeId) {
        ligneDeDocumentTaxeDao.delete(obtenirLigneDeDocumentTaxe(ligneDeDocumentTaxeId));
    }
    
    /**
     * Ajout des taxe a une ligne de document.
     * 
     * @param ligneDeDocument
     */
    private void ajouterLigneDeDocumentTaxe(LigneDeDocument ligneDeDocument) {
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
    private void ajouterLigneDeDocumentTaxe(Collection<LigneDeDocument> lignes) {
        lignes.parallelStream().forEach(ligne -> ajouterLigneDeDocumentTaxe(ligne));
    }
    
    @Override
    public double chiffreAffaireHorsTaxe(Document document) {
        double caht = 0.0;
        for (LigneDeDocument l : document.getLigneDocuments()) {
            caht += l.getPrixunitaiteLigne() * l.getQuantiteLigne();
        }
        return caht;
    }
    
    @Override
    public double valeurTotaleTaxe(Document document) {
        double totalTaxe = 0.0;
        document.getLigneDocuments().stream().forEach(l -> {
            l.getLigneDeDocumentTaxes().stream().forEach(ldt -> {
                // totalTaxe += l.getPrixunitaiteLigne() * l.getQuantiteLigne() * ldt.getTauxDeTaxe();
            });
        });
        return totalTaxe;
    }
    
    @Override
    public double chiffreAffaireTTC(Document document) {
        return chiffreAffaireHorsTaxe(document) + valeurTotaleTaxe(document);
    }
    
    @Override
    public double valeurTaxe(Taxe taxe, Document document) {
        final double valeur = 0;
        document.getLigneDocuments().stream().forEach(ld -> {
            ld.getLigneDeDocumentTaxes().stream().filter(ldt -> ldt.getTaxe().getTaxeId() == taxe.getTaxeId()).forEach(ldt -> {
                // valeur += ld.getPrixunitaiteLigne() * ld.getQuantiteLigne() * ldt.getTauxDeTaxe();
            });
        });
        
        return valeur;
    }
    
    @Override
    public double valeurMarge(Document document) {
        // TODO Auto-generated method stub
        return 0;
    }
}
