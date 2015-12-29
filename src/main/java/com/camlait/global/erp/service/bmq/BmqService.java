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
import com.camlait.global.erp.domain.document.LigneDeDocumentTaxe;
import com.camlait.global.erp.domain.enumeration.TypeDocuments;
import com.camlait.global.erp.domain.operation.Recouvrement;
import com.camlait.global.erp.service.GlobalErpServiceException;

@Transactional
public class BmqService implements IBmqService {
    
    @Autowired
    private BmqDao bmqDao;
    
    @Autowired
    private LigneBmqDao ligneBmqDao;
    
    @Autowired
    private RecouvrementDao recouvrementDao;
    
    @Autowired
    private LigneBmqTaxeDao ligneBmqTaxeDao;
    
    @Override
    public Bmq ajouterBmq(Bmq bmq) {
        if (bmq == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("bmq"));
        }
        bmqDao.save(bmq);
        return bmq;
    }
    
    @Override
    public Bmq modifierBmq(Bmq bmq) {
        if (bmq == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("bmq"));
        }
        bmq.setDerniereMiseAJour(new Date());
        bmqDao.saveAndFlush(bmq);
        return bmq;
    }
    
    @Override
    public Bmq obtenirBmq(Long bmqId) {
        if (bmqId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("bmqId"));
        }
        final Bmq b = bmqDao.findOne(bmqId);
        if (b != null) {
            Hibernate.initialize(b.getLigneBmqs());
            Hibernate.initialize(b.getDocuments());
            Hibernate.initialize(b.getRecouvrements());
            return b;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(Bmq.class, bmqId));
        }
    }
    
    @Override
    public void supprimerBmq(Long bmqId) {
        bmqDao.delete(obtenirBmq(bmqId));
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
    
    @Override
    public Collection<LigneBmq> ajouterLigneBmq(Collection<LigneBmq> ligneBmqs) {
        if (ligneBmqs == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("ligneBmqs"));
        }
        ligneBmqDao.save(ligneBmqs);
        ajouterLigneBmqTaxe(ligneBmqs);
        return ligneBmqs;
    }
    
    @Override
    public LigneBmq obtenirLigneBmq(Long ligneBmqId) {
        if (ligneBmqId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("ligneBmqId"));
        }
        final LigneBmq lb = ligneBmqDao.findOne(ligneBmqId);
        if (lb != null) {
            lb.getLigneBmqTaxes();
            return lb;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(LigneBmq.class, ligneBmqId));
        }
    }
    
    @Override
    public void supprimerLigneBmq(Long ligneBmqId) {
        ligneBmqDao.delete(obtenirLigneBmq(ligneBmqId));
    }
    
    @Override
    public Bmq genererBmq(Long bmqId, Collection<Document> documents, Collection<Recouvrement> recouvrements) {
        final Bmq bmq = obtenirBmq(bmqId);
        bmq.setLigneBmqs(genererLigneBmq(bmq, documents));
        bmq.setRecouvrements(recouvrements);
        bmq.setDocuments(documents);
        return bmq;
    }
    
    @Override
    public Recouvrement ajouterRecouvrement(Recouvrement recouvrement) {
        if (recouvrement == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("recouvrement"));
        }
        recouvrementDao.save(recouvrement);
        return recouvrement;
    }
    
    @Override
    public Recouvrement modifierRecouvrement(Recouvrement recouvrement) {
        if (recouvrement == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("recouvrement"));
        }
        recouvrement.setDerniereMiseAJour(new Date());
        recouvrementDao.saveAndFlush(recouvrement);
        return recouvrement;
    }
    
    @Override
    public Recouvrement obtenirRecouvrement(Long recouvrementId) {
        Recouvrement r = recouvrementDao.findOne(recouvrementId);
        if (r != null) {
            return r;
        } else {
            throw new GlobalErpServiceException(GlobalAppConstants.buildNotFindMessage(Recouvrement.class, recouvrementId));
        }
    }
    
    @Override
    public void supprimerRecouvrement(Long recouvrementId) {
        recouvrementDao.delete(obtenirRecouvrement(recouvrementId));
    }
    
    @Override
    public void supprimerLigneBmq(Bmq bmq) {
        ligneBmqDao.delete(bmq.getLigneBmqs());
    }
    
    @Override
    public LigneBmqTaxe ajouterLigneBmqTaxe(LigneBmqTaxe ligneBmqTaxe) {
        if (ligneBmqTaxe == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("ligneBmqTaxe"));
        }        
        ligneBmqTaxeDao.save(ligneBmqTaxe);
        return ligneBmqTaxe;
    }
    
    @Override
    public LigneBmqTaxe modifierLigneBmqTaxe(LigneBmqTaxe ligneBmqTaxe) {
        if (ligneBmqTaxe == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("ligneBmqTaxe"));
        }
        ligneBmqTaxe.setDerniereMiseAJour(new Date());
        ligneBmqTaxeDao.saveAndFlush(ligneBmqTaxe);
        return ligneBmqTaxe;
    }
    
    @Override
    public LigneBmqTaxe trouverLigneBmqTaxe(Long ligneBmqTaxeId) {
        if (ligneBmqTaxeId == null) {
            throw new IllegalArgumentException(GlobalAppConstants.buildIllegalArgumentMessage("ligneBmqTaxeId"));
        }
        LigneBmqTaxe l = ligneBmqTaxeDao.findOne(ligneBmqTaxeId);
        if (l != null) {
            return l;
        } else {
            throw new GlobalErpServiceException(
                    GlobalAppConstants.buildNotFindMessage(LigneBmqTaxe.class, ligneBmqTaxeId));
        }
    }
    
    @Override
    public void supprimerLigneBmqTaxe(Long ligneBmqTaxeId) {
        ligneBmqTaxeDao.delete(trouverLigneBmqTaxe(ligneBmqTaxeId));
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
        documents.parallelStream().forEach(d -> {
            d.getLigneDocuments().parallelStream().forEach(ld -> {
                LigneBmq lb = new LigneBmq();
                lb.setBmq(bmq);
                lb.setDocument(d);
                lb.setPrixUnitaireLigne(ld.getPrixunitaiteLigne());
                lb.setProduit(ld.getProduit());
                lb.setQuantiteLigne(ld.getQuantiteLigne());
                lignes.add(lb);
            });
        });
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
        ligneBmqs.parallelStream().forEach(ligneBmq -> {
            obtenirLigneDocumentTaxe(ligneBmq.getBmq()).parallelStream().forEach(ligneDeDocumentTaxe -> {
                LigneBmqTaxe ligneBmqTaxe = new LigneBmqTaxe();
                ligneBmqTaxe.setLigneBmq(ligneBmq);
                ligneBmqTaxe.setTaxe(ligneDeDocumentTaxe.getTaxe());
                ligneBmqTaxe.setTauxDeTaxe(ligneDeDocumentTaxe.getTauxDeTaxe());
                ligneBmqTaxes.add(ligneBmqTaxe);
            });
        });
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
        bmq.getDocuments().parallelStream().filter(d -> d.getTypeDocument().equals(TypeDocuments.DOCUMENT_DE_VENTE))
                .forEach(d -> {
                    d.getLigneDocuments().parallelStream().forEach(ld -> {
                        ld.getLigneDeDocumentTaxes().parallelStream().forEach(ldt -> {
                            LigneDeDocumentTaxe l = new LigneDeDocumentTaxe();
                            l.setLigneDeDocument(ld);
                            l.setTaxe(ldt.getTaxe());
                            l.setTauxDeTaxe(ldt.getTauxDeTaxe());
                            ligneDeDocumentTaxes.add(l);
                        });
                    });
                });
        return ligneDeDocumentTaxes;
    }
    
    /**
     * Ajouter de maniere groupe une collection de taxe a une ligne de bmq.
     * 
     * @param ligneBmqs
     */
    private void ajouterLigneBmqTaxe(Collection<LigneBmq> ligneBmqs) {
        genererLigneBmqTaxe(ligneBmqs).parallelStream().forEach(l -> {
            ajouterLigneBmqTaxe(l);
        });
    }
}
