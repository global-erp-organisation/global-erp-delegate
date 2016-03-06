package com.camlait.global.erp.service.bmq;

import static com.camlait.global.erp.domain.config.GlobalAppConstants.verifyObjectNoFindException;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.camlait.global.erp.dao.bmq.BmqDao;
import com.camlait.global.erp.dao.bmq.LigneBmqDao;
import com.camlait.global.erp.dao.bmq.LigneBmqTaxeDao;
import com.camlait.global.erp.dao.operation.RecouvrementDao;
import com.camlait.global.erp.domain.bmq.Bmq;
import com.camlait.global.erp.domain.bmq.LigneBmq;
import com.camlait.global.erp.domain.bmq.LigneBmqTaxe;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.document.LigneDeDocument;
import com.camlait.global.erp.domain.document.LigneDeDocumentTaxe;
import com.camlait.global.erp.domain.document.commerciaux.Taxe;
import com.camlait.global.erp.domain.document.commerciaux.vente.FactureClientComptant;
import com.camlait.global.erp.domain.operation.Recouvrement;
import com.camlait.global.erp.domain.partenaire.ClientComptant;
import com.camlait.global.erp.domain.util.Compute;
import com.camlait.global.erp.domain.util.Utility;
import com.camlait.global.erp.service.document.IDocumentService;
import com.camlait.global.erp.service.inventaire.IInventaireService;
import com.camlait.global.erp.service.util.IUtilService;

import lombok.NonNull;

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
    
    @Autowired
    private IDocumentService documentService;
    
    @Autowired
    private IInventaireService inventaireService;
    
    @Autowired
    private IUtilService utilService;
    
    @Override
    public Bmq ajouterBmq(@NonNull Bmq bmq) {
        bmq.setCodeBmq(utilService.genererCode(bmq));
        bmqDao.save(bmq);
        return bmq;
    }
    
    @Override
    public Bmq modifierBmq(@NonNull Bmq bmq) {
        bmq.setDerniereMiseAJour(new Date());
        bmqDao.saveAndFlush(bmq);
        return bmq;
    }
    
    @Override
    public Bmq obtenirBmq(@NonNull Long bmqId) {
        final Bmq b = bmqDao.findOne(bmqId);
        verifyObjectNoFindException(b, Bmq.class, bmqId);
        Hibernate.initialize(b.getLigneBmqs());
        Hibernate.initialize(b.getDocuments());
        Hibernate.initialize(b.getRecouvrements());
        return b;
    }
    
    @Override
    public Bmq obtenirBmq(@NonNull String codeBmq) {
        final List<Bmq> bmqs = bmqDao.findByCodeBmq(codeBmq, new PageRequest(0, 1)).getContent();
        final Bmq b = (bmqs.isEmpty()) ? null : bmqs.get(0);
        verifyObjectNoFindException(b, Bmq.class, codeBmq);
        Hibernate.initialize(b.getLigneBmqs());
        Hibernate.initialize(b.getDocuments());
        Hibernate.initialize(b.getRecouvrements());
        return b;
    }
    
    @Override
    public void supprimerBmq(@NonNull Long bmqId) {
        bmqDao.delete(obtenirBmq(bmqId));
    }
    
    @Override
    public Page<Bmq> listerBmq(@NonNull Pageable p) {
        return bmqDao.findAll(p);
    }
    
    @Override
    public Page<Bmq> listerBmq(@NonNull Long vendeurId, @NonNull Pageable p) {
        return bmqDao.listerBmq(vendeurId, p);
    }
    
    @Override
    public Page<Bmq> listerBmq(@NonNull Date debut, @NonNull Date fin, @NonNull Pageable p) {
        return bmqDao.listerBmq(debut, fin, p);
    }
    
    @Override
    public Page<Bmq> listerBmq(@NonNull Long vendeurId, @NonNull Date debut, @NonNull Date fin, @NonNull Pageable p) {
        return bmqDao.listerBmq(vendeurId, debut, fin, p);
    }
    
    @Override
    public Collection<LigneBmq> ajouterLigneBmq(@NonNull Collection<LigneBmq> ligneBmqs) {
        ligneBmqDao.save(ligneBmqs);
        ajouterLigneBmqTaxe(ligneBmqs);
        return ligneBmqs;
    }
    
    @Override
    public LigneBmq obtenirLigneBmq(@NonNull Long ligneBmqId) {
        final LigneBmq lb = ligneBmqDao.findOne(ligneBmqId);
        verifyObjectNoFindException(lb, LigneBmq.class, ligneBmqId);
        Hibernate.initialize(lb.getLigneBmqTaxes());
        return lb;
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
    public Recouvrement ajouterRecouvrement(@NonNull Recouvrement recouvrement) {
        recouvrementDao.save(recouvrement);
        return recouvrement;
    }
    
    @Override
    public Recouvrement modifierRecouvrement(@NonNull Recouvrement recouvrement) {
        recouvrement.setDerniereMiseAJour(new Date());
        recouvrementDao.saveAndFlush(recouvrement);
        return recouvrement;
    }
    
    @Override
    public Recouvrement obtenirRecouvrement(@NonNull Long recouvrementId) {
        final Recouvrement r = recouvrementDao.findOne(recouvrementId);
        verifyObjectNoFindException(r, Recouvrement.class, recouvrementId);
        return r;
    }
    
    @Override
    public void supprimerRecouvrement(@NonNull Long recouvrementId) {
        recouvrementDao.delete(obtenirRecouvrement(recouvrementId));
    }
    
    @Override
    public void supprimerLigneBmq( @NonNull Bmq bmq) {
        ligneBmqDao.delete(bmq.getLigneBmqs());
    }
    
    @Override
    public LigneBmqTaxe ajouterLigneBmqTaxe(@NonNull LigneBmqTaxe ligneBmqTaxe) {
        ligneBmqTaxeDao.save(ligneBmqTaxe);
        return ligneBmqTaxe;
    }
    
    @Override
    public LigneBmqTaxe modifierLigneBmqTaxe(@NonNull LigneBmqTaxe ligneBmqTaxe) {
        ligneBmqTaxe.setDerniereMiseAJour(new Date());
        ligneBmqTaxeDao.saveAndFlush(ligneBmqTaxe);
        return ligneBmqTaxe;
    }
    
    @Override
    public LigneBmqTaxe trouverLigneBmqTaxe(@NonNull Long ligneBmqTaxeId) {
        final LigneBmqTaxe l = ligneBmqTaxeDao.findOne(ligneBmqTaxeId);
        verifyObjectNoFindException(l, LigneBmqTaxe.class, ligneBmqTaxeId);
        return l;
    }
    
    @Override
    public void supprimerLigneBmqTaxe(@NonNull Long ligneBmqTaxeId) {
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
    private Collection<LigneBmq> genererLigneBmq(Bmq bmq, @NonNull Collection<Document> documents) {
        final Collection<LigneBmq> lignes = new HashSet<>();
        documents.parallelStream().forEach(d -> {
            d.getLigneDocuments().parallelStream().forEach(ld -> {
                final LigneBmq lb = new LigneBmq();
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
    private Collection<LigneBmqTaxe> genererLigneBmqTaxe(@NonNull Collection<LigneBmq> ligneBmqs) {
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
    private Collection<LigneDeDocumentTaxe> obtenirLigneDocumentTaxe(@NonNull Bmq bmq) {
        final Collection<LigneDeDocumentTaxe> ligneDeDocumentTaxes = new HashSet<>();
        bmq.getDocuments().parallelStream().filter(d -> Utility.isDocumentDeVente(d)).forEach(d -> {
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
    private void ajouterLigneBmqTaxe(@NonNull Collection<LigneBmq> ligneBmqs) {
        genererLigneBmqTaxe(ligneBmqs).parallelStream().forEach(l -> {
            ajouterLigneBmqTaxe(l);
        });
    }
    
    @Override
    public double chiffreAffaireHorsTaxe(@NonNull Bmq bmq) {
        final Compute caHTBmq = new Compute();
        bmq.getDocuments().stream().filter(d -> Utility.isFactureClient(d)).forEach(d -> {
            caHTBmq.cummuler(documentService.chiffreAffaireHorsTaxe(d));
        });
        return caHTBmq.getValue();
    }
    
    @Override
    public double chiffreAffaireTTC(@NonNull Bmq bmq) {
        return chiffreAffaireHorsTaxe(bmq) + valeurTotaleTaxe(bmq);
    }
    
    @Override
    public double valeurTotaleTaxe(@NonNull Bmq bmq) {
        final Compute taxeBmq = new Compute();
        bmq.getDocuments().stream().filter(d -> Utility.isFactureClient(d)).forEach(d -> {
            taxeBmq.cummuler(documentService.valeurTotaleTaxe(d));
        });
        return taxeBmq.getValue();
    }
    
    @Override
    public double valeurTaxe(@NonNull Taxe taxe, @NonNull Bmq bmq) {
        final Compute valeur = new Compute();
        bmq.getDocuments().stream().filter(d -> Utility.isFactureClient(d)).forEach(d -> {
            valeur.cummuler(documentService.valeurTaxe(taxe, d));
        });
        return valeur.getValue();
    }
    
    @Override
    public double venteComptant(@NonNull Bmq bmq) {
        final Compute ca = new Compute();
        bmq.getDocuments().stream().filter(d -> Utility.isFactureComptant(d)).forEach(d -> {
            ca.cummuler(documentService.chiffreAffaireTTC(d));
        });
        return ca.getValue();
    }
    
    @Override
    public double valeurMarge(@NonNull Bmq bmq) {
        final Compute marge = new Compute();
        bmq.getDocuments().stream().filter(d -> Utility.isFactureMarge(d)).forEach(d -> {
            marge.cummuler(documentService.valeurMarge(d));
        });
        return marge.getValue();
    }
    
    @Override
    public void genererVenteComptant(@NonNull Bmq bmq) {
        final Document facture = creerEnteteFacture(bmq);
        Collection<LigneDeDocument> lignes = new HashSet<>();
        inventaireService.listerStockParMagasin(bmq.getMagasin().getMagasinId()).stream().forEach(s -> {
            LigneDeDocument l = new LigneDeDocument();
            l.setDocument(facture);
            l.setPrixunitaiteLigne(s.getProduit().getPrixUnitaireProduit());
            l.setProduit(s.getProduit());
            l.setQuantiteLigne(s.getQuantiteDisponible());
            lignes.add(l);
        });
        facture.setLigneDocuments(lignes);
        documentService.ajouterDocument(facture);
    }
    
    /**
     * Creer l'entete de facture.
     * 
     * @param bmq
     * @return
     */
    private Document creerEnteteFacture(@NonNull Bmq bmq) {
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
