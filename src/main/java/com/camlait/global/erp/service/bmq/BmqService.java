package com.camlait.global.erp.service.bmq;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.bmq.BmqDao;
import com.camlait.global.erp.dao.bmq.LigneBmqDao;
import com.camlait.global.erp.dao.bmq.LigneBmqTaxeDao;
import com.camlait.global.erp.dao.operation.RecouvrementDao;
import com.camlait.global.erp.domain.bmq.Bmq;
import com.camlait.global.erp.domain.bmq.LigneBmq;
import com.camlait.global.erp.domain.bmq.LigneBmqTaxe;
import com.camlait.global.erp.domain.config.GlobalAppConstants;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.LigneDeDocument;
import com.camlait.global.erp.domain.document.LigneDeDocumentTaxe;
import com.camlait.global.erp.domain.operation.Recouvrement;
import com.camlait.global.erp.service.GlobalErpServiceException;

public class BmqService implements IBmqService {
    
    @Autowired
    private BmqDao bmqDao;
    
    @Autowired
    private LigneBmqDao ligneBmqDao;
    
    @Autowired
    private RecouvrementDao recouvrementDao;
    
    @Autowired
    private LigneBmqTaxeDao ligneBmqTaxeDao;
    
    @Transactional
    @Override
    public Bmq ajouterBmq(Bmq bmq) {
        
        if (bmq != null) {
            bmqDao.save(bmq);
        }
        return bmq;
    }
    
    @Transactional
    @Override
    public Bmq modifierBmq(Bmq bmq) {
        bmq.setDerniereMiseAJour(new Date());
        bmqDao.saveAndFlush(bmq);
        return bmq;
    }
    
    @Override
    public Bmq trouverBmq(Long bmqId) {
        if (bmqId == null) {
            throw new IllegalArgumentException("bmqId ne doit pas etre null");
        }
        final Bmq b = bmqDao.findOne(bmqId);
        if (b != null) {
            Hibernate.initialize(b.getLigneBmqs());
            Hibernate.initialize(b.getDocuments());
            Hibernate.initialize(b.getRecouvrements());
            return b;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFingMessage(Bmq.class, bmqId));
        }
    }
    
    @Transactional
    @Override
    public void supprimerBmq(Long bmqId) {
        bmqDao.delete(trouverBmq(bmqId));
    }
    
    @Override
    public Page<Bmq> listerBmq(Pageable p) {
        return bmqDao.findAll(p);
    }
    
    @Override
    public Page<Bmq> listerBmq(Long vendeurId, Pageable p) {
        return bmqDao.listerBmq(vendeurId, p);
    }
    
    @Override
    public Page<Bmq> listerBmq(Date debut, Date fin, Pageable p) {
        return bmqDao.listerBmq(debut, fin, p);
    }
    
    @Override
    public Page<Bmq> listerBmq(Long vendeurId, Date debut, Date fin, Pageable p) {
        return bmqDao.listerBmq(vendeurId, debut, fin, p);
    }
    
    @Transactional
    @Override
    public Collection<LigneBmq> ajouterLigneBmq(Collection<LigneBmq> ligneBmqs) {
        if (ligneBmqs != null) {
            ligneBmqDao.save(ligneBmqs);
            ajouterLigneBmqTaxe(ligneBmqs);
        }
        return ligneBmqs;
    }
    
    @Override
    public LigneBmq trouverLigneBmq(Long ligneBmqId) {
        if (ligneBmqId == null) {
            throw new IllegalArgumentException("ligneBmqId ne doit pas etre null");
        }
        final LigneBmq lb = ligneBmqDao.findOne(ligneBmqId);
        if (lb != null) {
            lb.getLigneBmqTaxes();
            return lb;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFingMessage(LigneBmq.class, ligneBmqId));
        }
    }
    
    @Transactional
    @Override
    public void supprimerLigneBmq(Long ligneBmqId) {
        ligneBmqDao.delete(trouverLigneBmq(ligneBmqId));
    }
    
    @Transactional
    @Override
    public Bmq genererBmq(Long bmqId, Collection<Document> documents, Collection<Recouvrement> recouvrements) {
        final Bmq bmq = trouverBmq(bmqId);
        bmq.setLigneBmqs(genererLigneBmq(bmq, documents));
        bmq.setRecouvrements(recouvrements);
        bmq.setDocuments(documents);
        return bmq;
    }
    
    /**
     * Genere les ligne du bmq à partir des documents associés.
     * 
     * @param bmq
     *            Bmq concerné.
     * @param documents
     *            documents associés.
     * @return
     */
    private Collection<LigneBmq> genererLigneBmq(Bmq bmq, Collection<Document> documents) {
        final Collection<LigneBmq> lignes = new HashSet<>();
        for (final Document d : documents) {
            for (final LigneDeDocument ld : d.getLigneDocuments()) {
                LigneBmq lb = new LigneBmq();
                lb.setBmq(bmq);
                lb.setDocument(d);
                lb.setPrixUnitaireLigne(ld.getPrixunitaiteLigne());
                lb.setProduit(ld.getProduit());
                lb.setQuantiteLigne(ld.getQuantiteLigne());
                lignes.add(lb);
            }
        }
        return lignes;
    }
    
    /**
     * Genere la liste de taxe associee a chaque ligne de bmq
     * 
     * @param ligneDocumentTaxes
     * @return
     */
    private Collection<LigneBmqTaxe> genererLigneBmqTaxe(Collection<LigneBmq> ligneBmqs) {
        final Collection<LigneBmqTaxe> ligneBmqTaxes = new HashSet<>();
        
        for (final LigneBmq ligneBmq : ligneBmqs) {
            
            for (final LigneDeDocumentTaxe ligneDeDocumentTaxe : obtenirLigneDocumentTaxe(ligneBmq.getBmq())) {
                LigneBmqTaxe ligneBmqTaxe = new LigneBmqTaxe();
                ligneBmqTaxe.setLigneBmq(ligneBmq);
                ligneBmqTaxe.setTaxe(ligneDeDocumentTaxe.getTaxe());
                ligneBmqTaxe.setTauxDeTaxe(ligneDeDocumentTaxe.getTauxDeTaxe());
                ligneBmqTaxes.add(ligneBmqTaxe);
            }
        }
        return ligneBmqTaxes;
    }
    
    /**
     * Extraire la liste des taxe associe a un bmq
     * 
     * @param bmq
     * @return
     */
    private Collection<LigneDeDocumentTaxe> obtenirLigneDocumentTaxe(Bmq bmq) {
        final Collection<LigneDeDocumentTaxe> ligneDeDocumentTaxes = new HashSet<>();
        
        for (final Document d : bmq.getDocuments()) {
            for (final LigneDeDocument ld : d.getLigneDocuments()) {
                for (LigneDeDocumentTaxe ldt : ld.getLigneDeDocumentTaxes()) {
                    LigneDeDocumentTaxe l = new LigneDeDocumentTaxe();
                    l.setLigneDeDocument(ld);
                    l.setTaxe(ldt.getTaxe());
                    l.setTauxDeTaxe(ldt.getTauxDeTaxe());
                    ligneDeDocumentTaxes.add(l);
                }
            }
        }
        return ligneDeDocumentTaxes;
    }
    
    /**
     * Ajouter de maniere groupe une collection de taxe a une ligne de bmq.
     * 
     * @param ligneBmqTaxes
     */
    private void ajouterLigneBmqTaxe(Collection<LigneBmq> ligneBmqs) {
        for (final LigneBmqTaxe l : genererLigneBmqTaxe(ligneBmqs)) {
            ajouterLigneBmqTaxe(l);
        }
    }
    
    @Transactional
    @Override
    public Recouvrement ajouterRecouvrement(Recouvrement recouvrement) {
        if (recouvrement != null) {
            recouvrementDao.save(recouvrement);
        }
        return recouvrement;
    }
    
    @Transactional
    @Override
    public Recouvrement modifierRecouvrement(Recouvrement recouvrement) {
        recouvrement.setDerniereMiseAJour(new Date());
        recouvrementDao.saveAndFlush(recouvrement);
        return recouvrement;
    }
    
    @Override
    public Recouvrement trouverRecouvrement(Long recouvrementId) {
        Recouvrement r = recouvrementDao.findOne(recouvrementId);
        if (r != null) {
            return r;
        } else {
            throw new GlobalErpServiceException(
                    "Le recouvrment ayant l'identifiant " + recouvrementId + " n'existe pas");
        }
    }
    
    @Transactional
    @Override
    public void supprimerRecouvrement(Long recouvrementId) {
        recouvrementDao.delete(trouverRecouvrement(recouvrementId));
    }
    
    @Transactional
    @Override
    public void supprimerLigneBmq(Bmq bmq) {
        ligneBmqDao.delete(bmq.getLigneBmqs());
    }
    
    @Transactional
    @Override
    public LigneBmqTaxe ajouterLigneBmqTaxe(LigneBmqTaxe ligneBmqTaxe) {
        if (ligneBmqTaxe != null) {
            ligneBmqTaxeDao.save(ligneBmqTaxe);
        }
        return ligneBmqTaxe;
    }
    
    @Transactional
    @Override
    public LigneBmqTaxe modifierLigneBmqTaxe(LigneBmqTaxe ligneBmqTaxe) {
        ligneBmqTaxe.setDerniereMiseAJour(new Date());
        ligneBmqTaxeDao.saveAndFlush(ligneBmqTaxe);
        return ligneBmqTaxe;
    }
    
    @Override
    public LigneBmqTaxe trouverLigneBmqTaxe(Long ligneBmqTaxeId) {
        if (ligneBmqTaxeId == null) {
            throw new IllegalArgumentException("ligneBmqTaxeId ne doit pas etre null");
        }
        LigneBmqTaxe l = ligneBmqTaxeDao.findOne(ligneBmqTaxeId);
        if (l != null) {
            return l;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFingMessage(LigneBmqTaxe.class, ligneBmqTaxeId));
        }
    }
    
    @Transactional
    @Override
    public void supprimerLigneBmqTaxe(Long ligneBmqTaxeId) {
        ligneBmqTaxeDao.delete(trouverLigneBmqTaxe(ligneBmqTaxeId));
    }
}
